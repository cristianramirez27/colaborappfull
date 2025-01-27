package com.coppel.rhconecta.dev.views.utils

import android.app.NotificationManager
import android.content.Context
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.coppel.rhconecta.dev.BuildConfig
import com.coppel.rhconecta.dev.CoppelApp
import com.coppel.rhconecta.dev.views.activities.ActionsModelCallback
import com.coppel.rhconecta.dev.views.activities.ZendeskViewModel
import com.coppel.rhconecta.dev.views.modelview.HelpDeskDataRequired
import com.coppel.rhconecta.dev.views.utils.AppConstants.ZENDESK_CHAT_BOOT_NAME
import com.google.gson.JsonObject
import zendesk.answerbot.AnswerBotEngine
import zendesk.chat.*
import zendesk.core.Zendesk
import zendesk.messaging.Engine
import zendesk.messaging.MessagingActivity
import zendesk.support.Support
import javax.inject.Inject


const val TAG = "ZendeskManager"

class ZendeskManager @Inject constructor(val context: Context) : ActionsModelCallback {

    lateinit var activityFramework: AppCompatActivity
    lateinit var zendeskChatCallback: ZendeskStatusCallBack
    private lateinit var zendeskViewModel: ZendeskViewModel
    lateinit var userData: HelpDeskDataRequired
    private var answerBotEngine: Engine? = null
    private var chatEngine: Engine? = null
    private var featureIsEnable = false

    /**
     * Start instance zendesk
     */
    fun initialize(
        activity: AppCompatActivity,
        baseViewModel: ZendeskViewModel,
    ) {
        this.zendeskViewModel = baseViewModel
        this.zendeskViewModel.setCallBack(this)
        this.activityFramework = activity

        if (CoppelApp.getZendesk() == null) {
            initInstance()
            CoppelApp.setZendesk(this)
        }
    }

    /**
     * Init zendesk
     */
    private fun initInstance() {
        activityFramework.run {
            Zendesk.INSTANCE.init(
                this,
                BuildConfig.ZENDESK_URL,
                BuildConfig.ZENDESK_APPLICATION,
                BuildConfig.ZENDESK_CLIENT
            )
            Support.INSTANCE.init(Zendesk.INSTANCE)
            Chat.INSTANCE.init(
                this,
                BuildConfig.ZENDESK_ACCOUNT,
                BuildConfig.ZENDESK_APPLICATION
            )

            /**
             * Settings for zendesk notifications
             */
            val pushProvider = Chat.INSTANCE.providers()?.pushNotificationsProvider()
            val tokenFirebase = AppUtilities.getStringFromSharedPreferences(
                CoppelApp.getContext(),
                AppConstants.SHARED_PREFERENCES_FIREBASE_TOKEN
            )
            pushProvider?.registerPushToken(tokenFirebase)

            observationScope = ObservationScope()
        }
    }

    /**
     * Set the listener implemented in some class that zendesk uses for chat events
     */
    fun setCallBack(zendeskChatStatus: ZendeskStatusCallBack) {
        this.zendeskChatCallback = zendeskChatStatus
    }

    /**
     * Set the listener implemented in some class that zendesk uses for chat events.
     * and review chat changes to notify the implementing class
     */
    fun setCallBackAndRefreshStatus(zendeskChatStatus: ZendeskStatusCallBack) {
        this.zendeskChatCallback = zendeskChatStatus
        refreshFeature()
    }

    fun setFeatureIsEnable(enable: Boolean) {
        this.featureIsEnable = enable
        refreshFeature()
    }

    /**
     * Validate the visibility the zendesk feature
     */
    private fun refreshFeature() {
        zendeskViewModel.featureIsEnable(featureIsEnable)
    }

    /**
     * Set the user data required to start zendesk
     */
    fun setData(data: HelpDeskDataRequired) {
        this.userData = data
    }

    /**
     * This method is activated by the user and triggers the processes and validations prior to launching the chat
     */
    fun clickFeature(configuration : JsonObject) {
        zendeskViewModel.clickChatZendesk(configuration)
    }

    /**
     * Launch the chat screen
     */
    private fun initChat() {
        endTimer?.cancel()
        configureUserData(userData)
        cancelNotification()
        launchNotificationIfExist()
        activityFramework.run {
            answerBotEngine = AnswerBotEngine.engine()
            chatEngine = ChatEngine.engine()

            MessagingActivity.builder()
                .withEngines(answerBotEngine, chatEngine)
                .withBotLabelString(ZENDESK_CHAT_BOOT_NAME)
                .show(this, configChat())
        }
        monitorChatEvent()
    }

    /**
     * Set chat settings
     */
    private fun configChat(): ChatConfiguration {
        return ChatConfiguration.builder()
            .withAgentAvailabilityEnabled(false)
            .withTranscriptEnabled(true)
            .build()
    }

    /**
     * Set user data in chat settings
     */
    private fun configureUserData(data: HelpDeskDataRequired) {
        val profileProvider = Chat.INSTANCE.providers()!!.profileProvider()

        val userData = "Nombre: ${data.employName} " +
                "Número de colaborador: ${data.employNumber} " +
                "Puesto: ${data.jobName} " +
                "Centro: ${data.department} " +
                "Modelo: ${data.deviceName} " +
                "Versión del sistema operativo: ${data.versionAndroid} " +
                "Versión de la aplicación: ${data.versionApp}"

        val visitorInfo = VisitorInfo.builder()
            .withName(userData)
            .withEmail(data.email)
            .build()
        val chatProvidersConfiguration = ChatProvidersConfiguration.builder()
            .withVisitorInfo(visitorInfo)
            .withDepartment(AppConstants.ZENDESK_DEPARTMENT)
            .build()
        profileProvider.setVisitorInfo(visitorInfo, null)

        Chat.INSTANCE.chatProvidersConfiguration = chatProvidersConfiguration
    }

    /**
     * Monitor status when chat is open
     */
    private fun monitorChatEvent() {
        Chat.INSTANCE.providers()?.chatProvider()?.observeChatState(
            observationScope
        ) { chatState: ChatState ->
            if (chatState.chatSessionStatus == ChatSessionStatus.ENDED) {
                Chat.INSTANCE.resetIdentity()
            }
        }
    }

    /**
     * For a period of time it monitors the end of the chat, this is useful when you are on another
     * screen than the chat
     */

    fun startMonitorChatStatus(minutes: Float = MINUTES_MONITORING) {
        endTimer = object : CountDownTimer((minutes * 60000).toLong(), 3000) {
            override fun onTick(millisUntilFinished: Long) {
                chatEngine?.isConversationOngoing { _: Engine?, isConversationOngoing: Boolean ->
                    if (isConversationOngoing) {
                        saveLocalOnLineChatFlag(true)
                        actionsStatus(ZendeskStatus.ON_LINE)
                    } else {
                        actionsStatus(ZendeskStatus.OUT_LINE)
                        cancel()
                    }
                } ?: run {
                    val online = if (getLocalOnLineChatFlag())
                        ZendeskStatus.ON_LINE else ZendeskStatus.OUT_LINE
                    actionsStatus(online)
                    cancel()
                }

            }

            override fun onFinish() {
            }

        }.start()
    }

    /**
     * stops online chat monitoring when it is no longer needed,
     * it is useful for when a screen is no longer visible and you do not need this update
     */
    fun stopMonitorChatStatus() {
        endTimer?.cancel()
    }

    /**
     * Throws to the screen that monitors the actions corresponding to the state of the chat
     */
    private fun actionsStatus(onLine: ZendeskStatus) {
        when (onLine) {
            ZendeskStatus.ON_LINE -> {
                saveLocalOnLineChatFlag(true)
                zendeskChatCallback.zendeskChatOnLine()
            }
            ZendeskStatus.OUT_LINE -> {
                saveLocalOnLineChatFlag(false)
                cancelNotification()
                zendeskChatCallback.zendeskChatOutLine()
            }
            ZendeskStatus.WITH_NOTIFICATION -> {}
            ZendeskStatus.DEFAULT -> TODO()
        }
        launchNotificationIfExist()
    }

    /**
     * Manages the zendesk notification red dot. Use the value saved from @CoppelFirebaseMessagingService
     * using @PushNotificationsProvider
     */
    private fun launchNotificationIfExist() {
        val withNotification = getLocalNotificationFlag()
        if (withNotification) {
            zendeskChatCallback.zendeskSetNotification()
        } else {
            zendeskChatCallback.zendeskRemoveNotification()
        }
    }

    /**
     * Remove the value of the local flag used to check for the existence of zendesk notifications
     * and remove android notifications
     */
    private fun cancelNotification() {
        saveLocalNotificationFlag(false)

        val notificationService = Context.NOTIFICATION_SERVICE
        val notificationManager: NotificationManager =
            context.getSystemService(notificationService) as NotificationManager
        notificationManager.cancelAll()
    }

    /**
     * Flag used to check if zendesk notifications exist
     */
    private fun saveLocalNotificationFlag(value: Boolean) {
        AppUtilities.saveBooleanInSharedPreferences(
            CoppelApp.getContext(),
            AppConstants.ZENDESK_NOTIFICATION, value
        )
    }

    private fun getLocalNotificationFlag(): Boolean = AppUtilities.getBooleanFromSharedPreferences(
        CoppelApp.getContext(),
        AppConstants.ZENDESK_NOTIFICATION)

    /**
     * Flag used to check if the chat is online
     */
    private fun saveLocalOnLineChatFlag(value: Boolean) {
        AppUtilities.saveBooleanInSharedPreferences(
            CoppelApp.getContext(),
            AppConstants.ZENDESK_CHAT_ONLINE, value
        )
    }

    fun getLocalOnLineChatFlag(): Boolean = AppUtilities.getBooleanFromSharedPreferences(
        CoppelApp.getContext(),
        AppConstants.ZENDESK_CHAT_ONLINE)


    /**
     * Functions from Model
     */
    override fun launchChat() {
        initChat()
    }

    override fun launchMessage(value: String) {
        zendeskChatCallback.zendeskErrorMessage(value)
    }


    /**
     * Handle visibility the zendesk feature
     */
    override fun enableAndRefreshZendeskFeature() {
        zendeskChatCallback.enableZendeskFeature()
    }

    override fun disableZendeskFeature() {
        zendeskChatCallback.disableZendeskFeature()
    }

    companion object {
        var MINUTES_MONITORING: Float = 0.25F
        private var endTimer: CountDownTimer? = null
        lateinit var observationScope: ObservationScope
    }
}


interface ZendeskStatusCallBack {
    fun enableZendeskFeature()
    fun disableZendeskFeature()

    fun zendeskChatOnLine()
    fun zendeskChatOutLine()

    fun zendeskSetNotification()
    fun zendeskRemoveNotification()

    fun zendeskErrorMessage(message: String)
}

enum class ZendeskStatus {
    ON_LINE, OUT_LINE, WITH_NOTIFICATION, DEFAULT
}

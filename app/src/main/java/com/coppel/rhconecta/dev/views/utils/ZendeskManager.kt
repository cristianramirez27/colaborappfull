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
import zendesk.answerbot.AnswerBotEngine
import zendesk.chat.*
import zendesk.core.Zendesk
import zendesk.messaging.Engine
import zendesk.messaging.MessagingActivity
import zendesk.support.Support
import zendesk.support.SupportEngine
import javax.inject.Inject


const val TAG = "ZendeskUtil"

class ZendeskUtil @Inject constructor(val context: Context) : ActionsModelCallback {


    private var answerBotEngine: Engine? = null
    private var chatEngine: Engine? = null
    lateinit var activityFramework: AppCompatActivity
    lateinit var zendeskChatCallback: ZendeskStatusCallBack

    lateinit var userData: HelpDeskDataRequired

    private var featureIsEnable = false

    /**
     * Prueba inyectar ZendeskViewModel
     */
    private lateinit var zendeskViewModel: ZendeskViewModel


    fun initialize(
        activity: AppCompatActivity,
        data: HelpDeskDataRequired,
        baseViewModel: ZendeskViewModel,
    ) {
        this.zendeskViewModel = baseViewModel
        this.zendeskViewModel.setCallBack(this)
        this.activityFramework = activity
        this.userData = data

        if (CoppelApp.getZendesk() == null) {
            initInstance()
            CoppelApp.setZendesk(this)
        }
    }

    /**
     * 1
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

    fun setData(data: HelpDeskDataRequired) {
        this.userData = data
    }

    fun setCallBack(zendeskChatStatus: ZendeskStatusCallBack) {
        this.zendeskChatCallback = zendeskChatStatus
    }

    fun setCallBackAndRefreshStatus(zendeskChatStatus: ZendeskStatusCallBack) {
        this.zendeskChatCallback = zendeskChatStatus
        refreshFeature()
    }

    fun clickFeature() {
        zendeskViewModel.clickChatZendesk()
    }

    private fun initInstance() {
        activityFramework.run {
            Zendesk.INSTANCE.init(
                this,
                BuildConfig.ZENDESK_URL,
                BuildConfig.ZENDESK_APPLICATION,
                BuildConfig.ZENDESK_CLIENT
            )
            Support.INSTANCE.init(Zendesk.INSTANCE)
//            AnswerBot.INSTANCE.init(Zendesk.INSTANCE, Support.INSTANCE)
            Chat.INSTANCE.init(
                this,
                BuildConfig.ZENDESK_ACCOUNT,
                BuildConfig.ZENDESK_APPLICATION
            )

            /**
             * Notifications zendesk
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

    private fun initChat() {
        endTimer?.cancel()
        configureUserData(userData)
        cancelNotification()
        launchNotificationIfExist()
        activityFramework.run {
            answerBotEngine = AnswerBotEngine.engine()
            val supportEngine: Engine = SupportEngine.engine()
            chatEngine = ChatEngine.engine()

            MessagingActivity.builder()
                .withEngines(answerBotEngine, chatEngine)
                .withBotLabelString(ZENDESK_CHAT_BOOT_NAME)
                .show(this, configChat())
        }
        monitorChatEvent()
    }

    private fun configChat(): ChatConfiguration {
        return ChatConfiguration.builder()
            .withAgentAvailabilityEnabled(false)
            .withTranscriptEnabled(true)
            .build()
    }

    private fun configureUserData(data: HelpDeskDataRequired) {
        val profileProvider = Chat.INSTANCE.providers()!!.profileProvider()

        val tags = listOf(
            data.employNumber,
            data.jobName,
            data.department,
            data.deviceName,
            data.versionAndroid,
            data.versionApp
        )

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
            .withPhoneNumber(AppConstants.ZENDESK_MOCK_NUMBER)
            .build()
        val chatProvidersConfiguration = ChatProvidersConfiguration.builder()
            .withVisitorInfo(visitorInfo)
            .withDepartment(AppConstants.ZENDESK_DEPARTMENT)
            .build()
        profileProvider.setVisitorInfo(visitorInfo, null)

        Chat.INSTANCE.chatProvidersConfiguration = chatProvidersConfiguration
    }


    private fun monitorChatEvent() {
        Chat.INSTANCE.providers()?.chatProvider()?.observeChatState(
            observationScope
        ) { chatState: ChatState ->
            if (chatState.chatSessionStatus == ChatSessionStatus.ENDED) {
                Chat.INSTANCE.resetIdentity()
            }
        }
    }

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

    fun stopMonitorChatStatus() {
        endTimer?.cancel()
    }

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
        }
        launchNotificationIfExist()
    }

    /**
     * For setting message value from @CoppelFirebaseMessagingService @PushNotificationsProvider
     * this message is saved in a list to later use this list to set the message counter in icon zendesk
     */

    private fun launchNotificationIfExist() {
        val withNotification = getLocalNotificationFlag()
        if (withNotification) {
            zendeskChatCallback.zendeskSetNotification()
        } else {
            zendeskChatCallback.zendeskRemoveNotification()
        }
    }

    private fun cancelNotification() {
        saveLocalNotificationFlag(false)

        val notificationService = Context.NOTIFICATION_SERVICE
        val notificationManager: NotificationManager =
            context.getSystemService(notificationService) as NotificationManager
        notificationManager.cancelAll()
    }

    private fun saveLocalNotificationFlag(value: Boolean) {
        AppUtilities.saveBooleanInSharedPreferences(
            CoppelApp.getContext(),
            AppConstants.ZENDESK_NOTIFICATION, value
        )
    }

    private fun getLocalNotificationFlag(): Boolean = AppUtilities.getBooleanFromSharedPreferences(
        CoppelApp.getContext(),
        AppConstants.ZENDESK_NOTIFICATION)

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

    fun setFeatureIsEnable(enable: Boolean) {
        this.featureIsEnable = enable
        refreshFeature()
    }

    private fun refreshFeature() {
        zendeskViewModel.featureIsEnable(featureIsEnable)
    }

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

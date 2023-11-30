package com.coppel.rhconecta.dev.presentation.calculator

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.coppel.rhconecta.dev.R
import com.coppel.rhconecta.dev.business.Configuration.AppConfig
import com.coppel.rhconecta.dev.business.models.ProfileResponse
import com.coppel.rhconecta.dev.di.calculator.InjectorCalculator
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.CalculationViewModel
import com.coppel.rhconecta.dev.presentation.calculator.welcome.WelcomeCalculatorFragment
import com.coppel.rhconecta.dev.presentation.common.extension.IntentExtension
import com.coppel.rhconecta.dev.views.utils.AppConstants
import com.coppel.rhconecta.dev.views.utils.AppUtilities
import javax.inject.Inject

class CalculatorActivity : AppCompatActivity(), CalculatorFragmentCommunication {

    private lateinit var fragment: WelcomeCalculatorFragment
    private lateinit var containerHelp: LinearLayoutCompat
    private lateinit var profileResponse: ProfileResponse.Response
    private var currentStep = 0
    private val data = HashMap<String, Any>()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var viewModel: CalculationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        InjectorCalculator.getComponentCalculator().inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[CalculationViewModel::class.java]
        containerHelp = findViewById(R.id.container_help)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        containerHelp.visibility = GONE
        profileResponse = IntentExtension
            .getSerializableExtra(intent, AppConstants.BUNLDE_PROFILE_RESPONSE) as ProfileResponse.Response
        fragment = WelcomeCalculatorFragment.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.content, fragment).commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun showFooter(enable: Boolean) {
        containerHelp.visibility = if (enable) VISIBLE else GONE
    }

    override fun getProfileInformation(): ProfileResponse.Response {
        return profileResponse
    }

    override fun registerStep(step: Int) {
        if (currentStep < step) {
            currentStep = step
            val token = AppUtilities.getStringFromSharedPreferences(this, AppConstants.SHARED_PREFERENCES_TOKEN)
            val url = AppUtilities.getStringFromSharedPreferences(this, AppConfig.ENDPOINT_CALCULATOR_STEP)
            viewModel.registerStep(url, token, profileResponse.colaborador, profileResponse.nombre, step)
        }
    }

    override fun <T : Any> getInfo(key: String): T {
        return data[key] as T
    }

    override fun <T : Any> saveInfo(key: String, t: T) {
        data[key] = t
    }
}

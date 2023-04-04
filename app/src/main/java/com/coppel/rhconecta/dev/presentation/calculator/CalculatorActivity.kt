package com.coppel.rhconecta.dev.presentation.calculator

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.appcompat.widget.Toolbar
import com.coppel.rhconecta.dev.R
import com.coppel.rhconecta.dev.presentation.calculator.welcome.WelcomeCalculatorFragment

class CalculatorActivity : AppCompatActivity(), CalculatorFragmentCommunication {

    private lateinit var fragment: WelcomeCalculatorFragment
    private lateinit var containerHelp: LinearLayoutCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_calculator)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        containerHelp = findViewById<LinearLayoutCompat>(R.id.container_help)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        containerHelp.visibility = GONE
        fragment = WelcomeCalculatorFragment.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.content,fragment).commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun showFooter(enable: Boolean) {
        containerHelp.visibility = if (enable) VISIBLE else GONE
    }
}

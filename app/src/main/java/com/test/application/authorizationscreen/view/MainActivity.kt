package com.test.application.authorizationscreen.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.test.application.authorizationscreen.R
import com.test.application.core.navigation.Navigator

class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun navigateToPaymentsFragment(bundle: Bundle) {
        navController.navigate(R.id.action_loginFragment_to_paymentsFragment, bundle)
    }

    override fun navigateFromPaymentsToLoginFragment() {
        navController.navigate(R.id.action_paymentsFragment_to_loginFragment)
    }
}
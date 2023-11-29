package com.test.application.core.navigation

import android.os.Bundle

interface Navigator {

    fun navigateToPaymentsFragment(bundle: Bundle)
    fun navigateFromPaymentsToLoginFragment()
}
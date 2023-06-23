package com.biryulindevelop.unsplash.presentation

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.biryulindevelop.unsplash.R
import com.biryulindevelop.unsplash.application.ONBOARDING_IS_SHOWN
import com.biryulindevelop.unsplash.application.TOKEN_ENABLED_KEY
import com.biryulindevelop.unsplash.application.TOKEN_NAME
import com.biryulindevelop.unsplash.utils.SharedPreferencesUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var preferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferences = SharedPreferencesUtils.createSharedPrefs(requireContext(), TOKEN_NAME)

        val toOnboarding = MainFragmentDirections.actionMainFragmentToNavigationOnboarding()
        val toAuthentication = MainFragmentDirections.actionMainFragmentToAuthFragment()
        val toPhotos = MainFragmentDirections.actionMainFragmentToNavigationPhotos()

        val hasTokenEnabled = preferences.getBoolean(TOKEN_ENABLED_KEY, false)
        val hasOnboardingShown = preferences.getBoolean(ONBOARDING_IS_SHOWN, false)

        val destination = when {
            hasTokenEnabled -> toPhotos
            hasOnboardingShown -> toAuthentication
            else -> toOnboarding
        }
        findNavController().navigate(destination)
    }
}

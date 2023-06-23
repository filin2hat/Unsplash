package com.biryulindevelop.unsplash.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import com.biryulindevelop.unsplash.application.ONBOARDING_IS_SHOWN
import com.biryulindevelop.unsplash.application.TOKEN_ENABLED_KEY
import com.biryulindevelop.unsplash.application.TOKEN_SHARED_NAME
import com.biryulindevelop.unsplash.databinding.FragmentMainBinding
import com.biryulindevelop.unsplash.tools.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>() {

    override fun initBinding(inflater: LayoutInflater) =
        FragmentMainBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferences = createSharedPreference(TOKEN_SHARED_NAME)
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
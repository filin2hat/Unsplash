package com.biryulindevelop.unsplash.presentation.screens.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.biryulindevelop.unsplash.R
import com.biryulindevelop.unsplash.application.ONBOARDING_SHOWN
import com.biryulindevelop.unsplash.application.TOKEN_NAME
import com.biryulindevelop.unsplash.databinding.FragmentOnboardingBinding
import com.biryulindevelop.unsplash.presentation.utils.SharedPreferencesUtils
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingFragment : Fragment(R.layout.fragment_onboarding) {
    private val binding by viewBinding(FragmentOnboardingBinding::bind)
    private var mediator: TabLayoutMediator? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewPager()
        setTabs()
        setAuthorizeButton()
        saveOnboardingShown()
    }

    private fun setViewPager() {
        with(binding) {
            viewPager.adapter =
                ViewPagerAdapter(resources.getStringArray(R.array.onboarding_texts_array))
            viewPager.registerOnPageChangeCallback(AnimateImageOnPageChange(binding.ellipseImage))
        }

    }
    private fun setTabs() {
        mediator = TabLayoutMediator(binding.tabsView, binding.viewPager) { _, _ -> }
        mediator?.attach()
        binding.tabsView.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                updateAuthorizeButtonText(tab?.position == 2) // Замените "2" на индекс последней вкладки
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }
    private fun updateAuthorizeButtonText(isLastTab: Boolean) {
        binding.authorizeButton.text =
            if (isLastTab) resources.getString(R.string.onboarding_button_text_next)
            else resources.getString(R.string.go_straight_to_authorization)
    }

    private fun setAuthorizeButton() {
        binding.authorizeButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_onboarding_to_navigation_auth)
        }
    }

    private fun saveOnboardingShown() {
        val prefs = SharedPreferencesUtils.createSharedPrefs(requireContext(), TOKEN_NAME)
        prefs.edit().putBoolean(ONBOARDING_SHOWN, true).apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediator?.detach()
        mediator = null
    }
}
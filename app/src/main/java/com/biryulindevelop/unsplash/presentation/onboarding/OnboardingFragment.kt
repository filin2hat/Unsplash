package com.biryulindevelop.unsplash.presentation.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.biryulindevelop.unsplash.R
import com.biryulindevelop.unsplash.application.ONBOARDING_IS_SHOWN
import com.biryulindevelop.unsplash.application.TOKEN_NAME
import com.biryulindevelop.unsplash.databinding.FragmentOnboardingBinding
import com.biryulindevelop.unsplash.utils.SharedPreferencesUtils
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
        binding.viewPager.adapter =
            ViewPagerAdapter(resources.getStringArray(R.array.onboarding_texts_array))
        binding.viewPager.registerOnPageChangeCallback(AnimateImageOnPageChange(binding.ellipseImage))
    }

    private fun setTabs() {
        mediator = TabLayoutMediator(binding.tabsView, binding.viewPager) { _, _ -> }
        mediator!!.attach()
    }

    private fun setAuthorizeButton() {
        binding.authorizeButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_onboarding_to_navigation_auth)
        }
    }

    private fun saveOnboardingShown() {
        val prefs = SharedPreferencesUtils.createSharedPrefs(requireContext(), TOKEN_NAME)
        prefs.edit().putBoolean(ONBOARDING_IS_SHOWN, true).apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediator?.detach()
        mediator = null
    }
}
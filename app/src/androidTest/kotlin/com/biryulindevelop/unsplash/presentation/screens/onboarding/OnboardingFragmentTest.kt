package com.biryulindevelop.unsplash.presentation.screens.onboarding

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.biryulindevelop.unsplash.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OnboardingFragmentTest {

    @Test
    fun testOnboardingFlow() {
        // Запускаем фрагмент OnboardingFragment в контейнере
        launchFragmentInContainer<OnboardingFragment>()

        // Проверяем, что ViewPager отображается
        Espresso.onView(ViewMatchers.withId(R.id.viewPager))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // Прокручиваем ViewPager до последней вкладки
        Espresso.onView(ViewMatchers.withId(R.id.viewPager))
            .perform(ViewActions.swipeLeft())
            .perform(ViewActions.swipeLeft())

        // Проверяем, что текст кнопки авторизации изменился
        Espresso.onView(ViewMatchers.withId(R.id.authorize_button))
            .check(ViewAssertions.matches(ViewMatchers.withText(R.string.onboarding_button_text_next)))

        // Нажимаем на кнопку авторизации
        Espresso.onView(ViewMatchers.withId(R.id.authorize_button))
            .perform(ViewActions.click())

    }
}
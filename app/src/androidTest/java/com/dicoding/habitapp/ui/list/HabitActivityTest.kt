package com.dicoding.habitapp.ui.list

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.dicoding.habitapp.R
import com.dicoding.habitapp.ui.add.AddHabitActivity

//TODO 16 : Write UI test to validate when user tap Add Habit (+), the AddHabitActivity displayed
@RunWith(AndroidJUnit4::class)
class HabitActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(HabitListActivity::class.java)

    @Before
    fun setUp() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun whenAddHabitButtonIsTapped_AddHabitActivityIsDisplayed(){
        onView(withContentDescription(R.string.add_habit)).perform(click())

        Intents.intended(hasComponent(AddHabitActivity::class.java.name))
    }

}
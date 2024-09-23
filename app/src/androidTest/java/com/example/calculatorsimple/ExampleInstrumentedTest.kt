package com.example.calculatorsimple

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun calculatorTest() {
        rule.setContent {
            Calculator()
        }
        rule.onNodeWithText("Enter a number").performTextClearance()
        rule.onNodeWithText("Enter a number").performTextInput("2")

        rule.onNodeWithText("Enter another number").apply {
            performTextClearance()
            performTextInput("13")
        }

        rule.onNodeWithText("+").performClick()
        rule.onNodeWithText("Result: 15.0").assertExists()
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.calculatorsimple", appContext.packageName)
    }
}
package com.bindlish.pollwidget

import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.bindlish.pollwidget.adapter.OptionListAdapter
import com.bindlish.pollwidget.customviews.PollView
import com.bindlish.pollwidget.ui.MainActivity
import com.bindlish.pollwidget.ui.MainFragment
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainFragmentTest {

    @get:Rule
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun init() {
        activityTestRule.activity.supportFragmentManager.beginTransaction()
    }

    @Test
    fun check_fragment() {
        val fragment =
            activityTestRule.activity.supportFragmentManager.findFragmentByTag(MainFragment.TAG)
        Assert.assertTrue(fragment is MainFragment)
    }

    @Test
    fun check_recycler_in_text_poll() {
        Espresso.onView(ViewMatchers.withId(R.id.poll_with_text)).check { view, noViewFoundException ->
            val pollView = view as PollView
            val recyclerView = pollView.findViewById<RecyclerView>(R.id.poll_options_list)
            Assert.assertTrue(recyclerView is RecyclerView)
            (recyclerView as RecyclerView).adapter?.itemCount?.compareTo(4)
        }
    }

    @Test
    fun check_recycler_in_image_poll() {
        Espresso.onView(ViewMatchers.withId(R.id.poll_with_image)).check { view, noViewFoundException ->
            val pollView = view as PollView
            val recyclerView = pollView.findViewById<RecyclerView>(R.id.poll_options_list)
            Assert.assertTrue(recyclerView is RecyclerView)
            (recyclerView as RecyclerView).adapter?.itemCount?.compareTo(4)
        }
    }

    @Test
    fun click_option_in_text_poll() {
        Espresso.onView(withIndex(ViewMatchers.withId(R.id.poll_options_list), 0)).perform(
            RecyclerViewActions.actionOnItemAtPosition<OptionListAdapter.OptionViewHolder>(
                2,
                ViewActions.click()
            )
        )
        // check if percentage shown
        Espresso.onView(withIndex(ViewMatchers.withId(R.id.poll_options_list), 0))
            .check(ViewAssertions.matches(checkVisibleViewOf(2)))
    }

    @Test
    fun click_vote_again() {
        Espresso.onView(withIndex(ViewMatchers.withId(R.id.vote_button), 0))
            .perform(ViewActions.click())
        // check if views are clickable again
        Espresso.onView(withIndex(ViewMatchers.withId(R.id.poll_options_list), 0))
            .check(ViewAssertions.matches(checkClickableViewOf(2)))
    }

    @Test
    fun click_option_in_image_poll() {
        Espresso.onView(withIndex(ViewMatchers.withId(R.id.poll_options_list), 1))
            .perform(ViewActions.swipeLeft())
        Espresso.onView(withIndex(ViewMatchers.withId(R.id.poll_options_list), 1)).perform(
            RecyclerViewActions.actionOnItemAtPosition<OptionListAdapter.OptionViewHolder>(
                2,
                ViewActions.click()
            )
        )
        // check if percentage shown
        Espresso.onView(withIndex(ViewMatchers.withId(R.id.poll_options_list), 1))
            .check(ViewAssertions.matches(checkVisibleViewOf(2)))
    }

    /**
     * Method to check visibility of progressBar and percentage after click,
     * Also checks if options are disabled
     */
    private fun checkVisibleViewOf(pos: Int): Matcher<View> {
        return object :
            BoundedMatcher<View, View>(
                View::class.java
            ) {
            override fun describeTo(description: Description) {
                description.appendText("No item found")
            }

            override fun matchesSafely(view: View): Boolean {
                Assert.assertTrue("View is RecyclerView", view is RecyclerView)
                val recyclerView = view as RecyclerView
                val holder =
                    recyclerView.findViewHolderForAdapterPosition(pos) as OptionListAdapter.OptionViewHolder
                val percent =
                    holder.itemView.findViewById<TextView>(R.id.poll_option_percentage)
                val progress =
                    holder.itemView.findViewById<ProgressBar>(R.id.poll_progress)
                return percent != null && percent.visibility == View.VISIBLE
                        && progress != null && progress.visibility == View.VISIBLE
                        && !holder.itemView.isEnabled
            }

        }
    }

    /**
     * Method to check visibility of progressBar and percentage after click,
     * Also checks if options are disabled
     */
    private fun checkClickableViewOf(pos: Int): Matcher<View> {
        return object :
            BoundedMatcher<View, View>(
                View::class.java
            ) {
            override fun describeTo(description: Description) {
                description.appendText("No item found")
            }

            override fun matchesSafely(view: View): Boolean {
                Assert.assertTrue("View is RecyclerView", view is RecyclerView)
                val recyclerView = view as RecyclerView
                val holder =
                    recyclerView.findViewHolderForAdapterPosition(pos) as OptionListAdapter.OptionViewHolder
                return holder.itemView.isClickable
            }

        }
    }

    /**
     * Method to get views out of multiple ambiguous ids
     */
    private fun withIndex(matcher: Matcher<View>, index: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            internal var currentIndex = 0

            override fun describeTo(description: Description) {
                description.appendText("with index: ")
                description.appendValue(index)
                matcher.describeTo(description)
            }

            override fun matchesSafely(view: View): Boolean {
                return matcher.matches(view) && currentIndex++ == index
            }
        }
    }
}
package com.project.infosdepartment.views;

import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.project.infosdepartment.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UITest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    public MainActivity mainActivity;


    @Test
    public void getInteractiveMap() {
        mainActivityActivityTestRule.getActivity();
        onView(withId(R.id.interactiveCard)).perform(click());
    }

    @Test
    public void refreshData() {
        mainActivityActivityTestRule.getActivity();
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(anyOf(withText(R.string.refresh_cache), withId(R.id.refresh))).perform(click());
    }


    @Test
    public void getInfo() {
        mainActivityActivityTestRule.getActivity();
        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(15).perform(click());
    }

}

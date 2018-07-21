package finalproject_nutritionrecommendersystem.csun.personalnutritionrecommendersystem;


import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class FoodItemAddTest {

    @Rule
    public ActivityTestRule<Login> mActivityTestRule = new ActivityTestRule<>(Login.class);

    @Test
    public void foodItemAddTest() {
        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.textInputEditTextEmail),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textInputLayoutEmail),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(click());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.textInputEditTextEmail),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textInputLayoutEmail),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("summer@gmail.com"), closeSoftKeyboard());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.textInputEditTextPassword),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textInputLayoutPassword),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(replaceText("password"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.appCompatButtonLogin), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nestedScrollView),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.appCompatButtonMeal), withText("Add Item"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nestedScrollView),
                                        0),
                                3),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatImageView = onView(
                allOf(withClassName(is("android.support.v7.widget.AppCompatImageView")), withContentDescription("Search"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withId(R.id.searchView),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageView.perform(click());

        ViewInteraction searchAutoComplete = onView(
                allOf(withClassName(is("android.widget.SearchView$SearchAutoComplete")),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                0),
                        isDisplayed()));
        searchAutoComplete.perform(replaceText("big mac"), closeSoftKeyboard());

        DataInteraction relativeLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.listview),
                        childAtPosition(
                                withId(R.id.textInputLayout),
                                3)))
                .atPosition(0);
        relativeLayout.perform(click());

        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.AddQuantity),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.quantity),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText4.perform(replaceText("2"), closeSoftKeyboard());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.appCompatButtonCalculate),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nestedScrollView),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton3.perform(click());

        onView(withId(R.id.caloriesValue)).check(matches(withText("257")));


        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.appCompatDone), withText("Done"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nestedScrollView),
                                        0),
                                6),
                        isDisplayed()));
        appCompatButton4.perform(click());

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}

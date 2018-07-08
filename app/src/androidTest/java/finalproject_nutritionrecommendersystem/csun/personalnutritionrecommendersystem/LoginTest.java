package finalproject_nutritionrecommendersystem.csun.personalnutritionrecommendersystem;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.app.PendingIntent.getActivity;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.itextpdf.text.factories.GreekAlphabetFactory.getString;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginTest {

    @Rule
    public ActivityTestRule<Login> mActivityTestRule = new ActivityTestRule<>(Login.class);

    @Test
    public void loginTest() {
        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.textInputEditTextEmail), isDisplayed()));
        textInputEditText.perform(click());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.textInputEditTextEmail), isDisplayed()));
        textInputEditText2.perform(replaceText("summer@gmail.com"), closeSoftKeyboard());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.textInputEditTextPassword), isDisplayed()));
        textInputEditText3.perform(replaceText("wrongpassword"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.appCompatButtonLogin), withText("Login"), isDisplayed()));
        appCompatButton.perform(click());

        onView(withText(getString(R.string.error_valid_email_password))).inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.textInputEditTextPassword), withText("wrongpassword"), isDisplayed()));
        textInputEditText4.perform(replaceText("password"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.appCompatButtonLogin), withText("Login"), isDisplayed()));
        appCompatButton2.perform(click());

        onView(withId(R.id.appCompatButtonMeal)).check(matches(withText("Add Item")));

    }

}

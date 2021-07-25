package com.project.infosdepartment.views;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.test.InstrumentationRegistry;
import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.project.infosdepartment.R;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class InfoActivityMetropoleTest extends TestCase {

    @Rule
    public ActivityTestRule<InfoActivity> infoActivity = new ActivityTestRule<InfoActivity>(InfoActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            InstrumentationRegistry.getTargetContext();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.putExtra("departmentCode", "78");
            return intent;
        }
    };

    @Test
    public void ensureIntentDataIsDisplayed() throws Exception {
        InfoActivity activity = infoActivity.getActivity();

        TextView departmentCode = activity.findViewById(R.id.departmentCode);
        while (departmentCode.getText().toString().equals("")) {

        }

        assertThat(departmentCode, notNullValue());
        assertThat(departmentCode, instanceOf(TextView.class));
        assertEquals("Code départemental : 78", departmentCode.getText().toString());


        TextView department = activity.findViewById(R.id.departmentName);
        assertThat(department, notNullValue());
        assertThat(department, instanceOf(TextView.class));
        assertEquals("Département : Yvelines", department.getText().toString());


        TextView departmentInhabitant = activity.findViewById(R.id.departmentInhabitants);
        assertThat(departmentInhabitant, notNullValue());
        assertThat(departmentInhabitant, instanceOf(TextView.class));
        assertEquals("Population : 1431808", departmentInhabitant.getText().toString());

        TextView departmentTowns = activity.findViewById(R.id.departmentTowns);
        assertThat(departmentTowns, notNullValue());
        assertThat(departmentTowns, instanceOf(TextView.class));
        assertEquals("Nombre de communes : 259", departmentTowns.getText().toString());

        View departmentPosition = activity.findViewById(R.id.departmentPosition);
        assertEquals(View.VISIBLE, departmentPosition.getVisibility());
    }


}
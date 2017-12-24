package mtuci.nikitakutselay.mobileapplicationprogrammingcourse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
    implements LaboratoryWorksListFragment.OnLaboratoryWorkSelectedListener {

    private HashMap<String, Class> laboratoryWorksMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        laboratoryWorksMap = new HashMap<>();
        laboratoryWorksMap.put(
                getString(R.string.lab1_name),
                AuthorInfoActivity.class
        );
        laboratoryWorksMap.put(
                getString(R.string.lab2_name),
                FeedbackActivity.class
        );
        laboratoryWorksMap.put(
                getString(R.string.lab3_name),
                MainActivity.class
        );

        setContentView(R.layout.activity_main);
    }

    @Override
    public void onLaboratoryWorkSelected(String buttonName) {
        if (laboratoryWorksMap.containsKey(buttonName)) {
            Class activityClass = laboratoryWorksMap.get(buttonName);
            Intent intent = new Intent(this, activityClass);
            startActivity(intent);
        } else {
            showNotImplementedToast();
        }
    }

    private void showNotImplementedToast() {
        Toast toast = Toast.makeText(
                getApplicationContext(),
                getString(R.string.not_implemented_message),
                Toast.LENGTH_SHORT
        );
        toast.show();
    }
}

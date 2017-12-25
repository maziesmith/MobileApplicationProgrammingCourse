package mtuci.nikitakutselay.mobileapplicationprogrammingcourse;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
    implements LaboratoryWorksListFragment.OnLaboratoryWorkSelectedListener {

    private HashMap<String, Class> laboratoryWorksMap;
    private String lastLaunchedLab;
    private SharedPreferences settings;

    private Menu menu;

    public static final String APP_PREFERENCES = "Lab4Settings";
    public static final String APP_PREFERENCES_LAST_LAUNCHED_LAB = "LastLaunchedLab";

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
        laboratoryWorksMap.put(
                getString(R.string.lab4_name),
                ProcessListActivity.class
        );

        settings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        this.menu = menu;

        if (!settings.contains(APP_PREFERENCES_LAST_LAUNCHED_LAB)) {
            MenuItem launchLastLabItem = menu.findItem(R.id.action_launch_last_lab);
            launchLastLabItem.setVisible(false);
        }

        if (!BuildConfig.DEBUG) {
            MenuItem clearSettingsItem = menu.findItem(R.id.action_clear_settings);
            clearSettingsItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_launch_last_lab:
                String lastLab = settings.getString(APP_PREFERENCES_LAST_LAUNCHED_LAB, "");
                onLaboratoryWorkSelected(lastLab);
                return true;
            case R.id.action_clear_settings:
                clearSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onLaboratoryWorkSelected(String buttonName) {
        if (laboratoryWorksMap.containsKey(buttonName)) {
            lastLaunchedLab = buttonName;
            Class activityClass = laboratoryWorksMap.get(buttonName);
            Intent intent = new Intent(this, activityClass);
            startActivity(intent);
        } else {
            showNotImplementedToast();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveLastLaunchedLab();
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveLastLaunchedLab();
    }

    private void clearSettings() {
        lastLaunchedLab = null;

        MenuItem launchLastLabItem = menu.findItem(R.id.action_launch_last_lab);
        launchLastLabItem.setVisible(false);

        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.apply();
    }

    private void saveLastLaunchedLab() {
        if (lastLaunchedLab != null) {
            SharedPreferences.Editor editor = settings.edit();

            editor.putString(APP_PREFERENCES_LAST_LAUNCHED_LAB, lastLaunchedLab);
            editor.apply();
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

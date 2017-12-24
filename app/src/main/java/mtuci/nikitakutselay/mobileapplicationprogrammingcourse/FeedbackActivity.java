package mtuci.nikitakutselay.mobileapplicationprogrammingcourse;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.app.DialogFragment;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import mtuci.nikitakutselay.mobileapplicationprogrammingcourse.util.RandomDelay;

public class FeedbackActivity extends AppCompatActivity
        implements FeedbackDialog.FeedbackDialogListener {

    private Timer feedbackTimer;
    private TimerTask feedbackTimerTask;

    private final Handler feedbackHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        if (savedInstanceState != null) {
            return;
        }

        AuthorInfoFragment authorInfo = AuthorInfoFragment.newInstance(
                getString(R.string.lab2_name));

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lab2AuthorInfoContainer, authorInfo)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.feedback_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_feedback:
                DialogFragment feedbackDialog = new FeedbackDialog();
                feedbackDialog.show(getFragmentManager(), "feedback");
                return true;
            case R.id.action_individual_task:
                Intent intent = new Intent(this, ApplicationListActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        LoadingFragment loading = new LoadingFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.lab2AuthorInfoContainer, loading)
                .commit();

        initializeFeedbackTimerTask();
    }

    private void initializeFeedbackTimerTask() {
        if (feedbackTimer != null) {
            feedbackTimer.cancel();
            feedbackTimer = null;
        }
        feedbackTimer = new Timer();

        feedbackTimerTask = new TimerTask() {
            @Override
            public void run() {
                feedbackHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        AuthorInfoFragment authorInfo = AuthorInfoFragment.newInstance(
                                getString(R.string.lab2_name));

                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.lab2AuthorInfoContainer, authorInfo)
                                .commit();

                        Toast toast = Toast.makeText(getApplicationContext(),
                                getText(R.string.feedback_sent_notification),
                                Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
            }
        };

        RandomDelay randomDelay = new RandomDelay();
        feedbackTimer.schedule(feedbackTimerTask, randomDelay.getDelay());
    }
}

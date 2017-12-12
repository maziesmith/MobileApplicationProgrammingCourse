package mtuci.nikitakutselay.mobileapplicationprogrammingcourse;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;
import android.widget.ProgressBar;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity
                          implements FeedbackDialog.FeedbackDialogListener {

    private static final long MIN_DELAY = 200L;
    private static final long MAX_DELAY = 1600L;

    private Timer feedbackTimer;
    private TimerTask feedbackTimerTask;

    private final Handler feedbackHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView info = (TextView) this.findViewById(R.id.infoMessage);
        this.registerForContextMenu(info);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
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
    public void onCreateContextMenu (ContextMenu menu, View v,
                                     ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.infoMessage) {
            menu.add(0, InfoMessageMenuItem.SHOW_AUTHOR_INFO,
                    0, getText(R.string.show_author_info_menu_item)
            );
            menu.add(0, InfoMessageMenuItem.HIDE_AUTHOR_INFO,
                    0, getText(R.string.hide_author_info_menu_item)
            );
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case InfoMessageMenuItem.SHOW_AUTHOR_INFO:
                this.showAuthorInfo(this.findViewById(R.id.infoMessage));
                break;
            case InfoMessageMenuItem.HIDE_AUTHOR_INFO:
                this.removeAuthorInfo(this.findViewById(R.id.infoMessage));
                break;

        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.VISIBLE);
        initializeFeedbackTimerTask();
    }

    public void showAuthorInfo(View view) {
        TextView info = (TextView) this.findViewById(R.id.infoMessage);
        String authorInfo = this.getAuthorInfo();
        info.setText(getString(R.string.info_message) + '\n' + authorInfo);
    }

    public void removeAuthorInfo(View view) {
        TextView info = (TextView) this.findViewById(R.id.infoMessage);
        info.setText(R.string.info_message);
    }

    public void showApplicationList(View view) {

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
                        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                        Toast toast = Toast.makeText(getApplicationContext(),
                                getText(R.string.feedback_sent_notification),
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            }
        };

        feedbackTimer.schedule(feedbackTimerTask, getRandomDelay());

    }

    private long getRandomDelay() {
        Random random = new Random();
        return MIN_DELAY + ((long)(random.nextDouble() * (MAX_DELAY - MIN_DELAY)));
    }

    private String getAuthorInfo() {
        String authorInfo = String.format(
                "\n%s: %s",
                getString(R.string.author_label),
                getString(R.string.author_name)
        );
        String groupInfo = String.format(
                "\n%s: %s",
                getString(R.string.group_label),
                getString(R.string.author_group)
        );
        return authorInfo + groupInfo;
    }
}
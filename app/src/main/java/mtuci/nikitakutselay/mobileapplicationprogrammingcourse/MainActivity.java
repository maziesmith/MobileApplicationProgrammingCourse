package mtuci.nikitakutselay.mobileapplicationprogrammingcourse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView info = (TextView) this.findViewById(R.id.infoMessage);
        this.registerForContextMenu(info);
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

    public void showAuthorInfo(View view) {
        TextView info = (TextView) this.findViewById(R.id.infoMessage);
        String authorInfo = this.getAuthorInfo();
        info.setText(getString(R.string.info_message) + '\n' + authorInfo);
    }

    public void removeAuthorInfo(View view) {
        TextView info = (TextView) this.findViewById(R.id.infoMessage);
        info.setText(R.string.info_message);
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
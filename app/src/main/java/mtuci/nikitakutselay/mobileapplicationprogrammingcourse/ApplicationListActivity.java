package mtuci.nikitakutselay.mobileapplicationprogrammingcourse;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ApplicationListActivity extends ListActivity {

    private List<ApplicationInfo> applicationList = null;
    private PackageManager packageManager = null;
    private ArrayAdapter<String> applicationListAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_application_list);

        ListView listView = findViewById(android.R.id.list);
        registerForContextMenu(listView);

        packageManager = getPackageManager();

        new LoadApplicationList().execute();
    }

    @Override
    public void onCreateContextMenu (ContextMenu menu, View v,
                                     ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == android.R.id.list) {
            menu.add(0, ApplicationListMenuItem.COPY_APPLICATION_NAME,
                    0, getText(R.string.copy_application_name_menu_item)
            );
            menu.add(0, ApplicationListMenuItem.COPY_PACKAGE_NAME,
                    0, getText(R.string.copy_application_package_menu_item)
            );
            menu.add(0, ApplicationListMenuItem.SHOW_APPLICATION_INFO,
                    0, getText(R.string.show_application_info_menu_item)
            );
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item.getMenuInfo();
        ApplicationInfo info = null;
        switch (item.getItemId()) {
            case ApplicationListMenuItem.COPY_APPLICATION_NAME:
                info = applicationList.get(menuInfo.position);
                notifyAboutCopying(info.loadLabel(packageManager).toString());
                return true;
            case ApplicationListMenuItem.COPY_PACKAGE_NAME:
                info = applicationList.get(menuInfo.position);
                notifyAboutCopying(info.packageName);
                return true;
            case ApplicationListMenuItem.SHOW_APPLICATION_INFO:
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void notifyAboutCopying(String data) {
        String notification = String.format("%s: %s",
                getString(R.string.copying_notification),
                data);
        Toast toast = Toast.makeText(getApplicationContext(),
                notification,
                Toast.LENGTH_SHORT);
        toast.show();
    }

    private class LoadApplicationList extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progress = null;

        @Override
        protected Void doInBackground(Void... params) {

            applicationList = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

            List<String> applicationNames = new ArrayList<>();

            for (ApplicationInfo info : applicationList) {
                applicationNames.add(info.loadLabel(packageManager).toString());
            }

            applicationListAdapter = new ArrayAdapter<String>(ApplicationListActivity.this,
                    android.R.layout.simple_list_item_1, applicationNames);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            setListAdapter(applicationListAdapter);
            progress.dismiss();
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(ApplicationListActivity.this, null, getString(R.string.application_list_loading));
            super.onPreExecute();
        }
    }
}

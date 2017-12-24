package mtuci.nikitakutselay.mobileapplicationprogrammingcourse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AuthorInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_info);

        if (savedInstanceState != null) {
            return;
        }

        AuthorInfoFragment authorInfo = AuthorInfoFragment.newInstance(
                getString(R.string.lab1_name));

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lab1AuthorInfoContainer, authorInfo)
                .commit();
    }
}

package mtuci.nikitakutselay.mobileapplicationprogrammingcourse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import mtuci.nikitakutselay.mobileapplicationprogrammingcourse.util.AuthorInfo;

public class AuthorInfoFragment extends Fragment
    implements View.OnClickListener {

    private static final String LABORATORY_WORK_NAME_KEY = "laboratoryWorkName";

    private String laboratoryWorkName;

    private TextView authorInfoView;

    public static AuthorInfoFragment newInstance(String laboratoryWorkName) {
        Bundle bundle = new Bundle();
        bundle.putString(LABORATORY_WORK_NAME_KEY, laboratoryWorkName);

        AuthorInfoFragment fragment = new AuthorInfoFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            laboratoryWorkName = getArguments().getString(LABORATORY_WORK_NAME_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_author_info, container, false);

        Button authorButton = view.findViewById(R.id.authorButton);
        authorButton.setOnClickListener(this);

        authorInfoView = view.findViewById(R.id.infoMessage);
        authorInfoView.setText(laboratoryWorkName);
        this.registerForContextMenu(authorInfoView);

        return view;
    }

    @Override
    public void onCreateContextMenu (ContextMenu menu, View v,
                                     ContextMenu.ContextMenuInfo menuInfo) {
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
                this.showAuthorInfo();
                break;
            case InfoMessageMenuItem.HIDE_AUTHOR_INFO:
                this.removeAuthorInfo();
                break;

        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        showAuthorInfo();
    }

    public void showAuthorInfo() {
        AuthorInfo info = new AuthorInfo(
                getString(R.string.author_label),
                getString(R.string.author_name),
                getString(R.string.group_label),
                getString(R.string.author_group)
        );
        authorInfoView.setText(laboratoryWorkName + '\n' + info);
    }

    public void removeAuthorInfo() {
        authorInfoView.setText(laboratoryWorkName);
    }
}

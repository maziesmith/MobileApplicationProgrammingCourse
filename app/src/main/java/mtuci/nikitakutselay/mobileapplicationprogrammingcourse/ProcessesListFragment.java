package mtuci.nikitakutselay.mobileapplicationprogrammingcourse;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.util.Arrays;

public class ProcessesListFragment extends ListFragment {
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ProcessesListLoader loader = new ProcessesListLoader();

        String[] text = formatPSOutput(loader.getProcessesList());

        ListAdapter adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, text);
        setListAdapter(adapter);
    }

    private String[] formatPSOutput(String output) {
        String[] lines = output.split("\n");

        if (lines.length < 2) {
            return new String[0];
        } else {
            for (int i = 1; i < lines.length; i++) {
                lines[i] = formatProcessItem(lines[i]);
            }

            return Arrays.copyOfRange(lines, 1, lines.length);
        }
    }

    private String formatProcessItem(String line) {
        String[] fields = line.split("\\s+");
        return String.format("PID: %s, PPID: %s, NAME: %s",
                fields[1],
                fields[2],
                fields[8]);
    }
}

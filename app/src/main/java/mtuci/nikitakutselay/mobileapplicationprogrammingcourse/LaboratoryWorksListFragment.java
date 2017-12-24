package mtuci.nikitakutselay.mobileapplicationprogrammingcourse;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class LaboratoryWorksListFragment extends Fragment
    implements View.OnClickListener {

    private OnLaboratoryWorkSelectedListener listener;

    public interface OnLaboratoryWorkSelectedListener {
        void onLaboratoryWorkSelected(String buttonName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_laboratory_works_list, container, false);

        Button lab1Button = view.findViewById(R.id.lab1Button);
        lab1Button.setOnClickListener(this);

        Button lab2Button = view.findViewById(R.id.lab2Button);
        lab2Button.setOnClickListener(this);

        Button lab3Button = view.findViewById(R.id.lab3Button);
        lab3Button.setOnClickListener(this);

        Button lab4Button = view.findViewById(R.id.lab4Button);
        lab4Button.setOnClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnLaboratoryWorkSelectedListener) {
            listener = (OnLaboratoryWorkSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString() +
                    " must implement OnLaboratoryWorkSelectedListener");
        }
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            Button button = (Button) v;
            String buttonName = button.getText().toString();
            listener.onLaboratoryWorkSelected(buttonName);
        }
    }
}

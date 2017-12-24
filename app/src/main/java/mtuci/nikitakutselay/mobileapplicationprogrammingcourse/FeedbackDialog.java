package mtuci.nikitakutselay.mobileapplicationprogrammingcourse;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;


public class FeedbackDialog extends DialogFragment {

    public interface FeedbackDialogListener {
        void onDialogPositiveClick(DialogFragment dialog);
    }

    private FeedbackDialogListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (FeedbackDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FeedbackDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstance) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setTitle(R.string.feedback_dialog_title)
                .setView(inflater.inflate(R.layout.feedback_dialog, null))
                .setCancelable(false)
                .setPositiveButton(
                    R.string.feedback_dialog_positive_button,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            mListener.onDialogPositiveClick(FeedbackDialog.this);
                        }
                    }
                )
                .setNegativeButton(
                    R.string.feedback_dialog_negative_button,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }
                );
        return builder.create();
    }
}

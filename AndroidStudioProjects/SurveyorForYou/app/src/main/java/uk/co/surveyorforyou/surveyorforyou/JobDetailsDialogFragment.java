package uk.co.surveyorforyou.surveyorforyou;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by Ruwan on 22/02/2017.
 */

class JobDetailsDialogFragment extends DialogFragment {
    public JobDetailsDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    public static JobDetailsDialogFragment newInstance(String title) {
        JobDetailsDialogFragment frag = new JobDetailsDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        //args.putInt("position", position);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");
       // String position = getArguments().getString("position");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage("Are you sure?");
       // alertDialogBuilder.setMessage(position);
        alertDialogBuilder.setPositiveButton("OK",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // on success
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        return alertDialogBuilder.create();
    }
}

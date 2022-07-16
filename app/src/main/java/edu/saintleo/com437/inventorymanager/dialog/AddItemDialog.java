package edu.saintleo.com437.inventorymanager.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;

import edu.saintleo.com437.inventorymanager.R;

public class AddItemDialog extends DialogFragment {
    View dialogView;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_add_item, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView)
                .setPositiveButton(R.string.dialog_add, (dialogInterface, i) -> {

                    EditText txtItemName = dialogView.findViewById(R.id.txt_item_name);
                    Snackbar.make(
                            requireActivity().findViewById(R.id.nav_host_fragment_content_main),
                            txtItemName.getText().toString(),
                            Snackbar.LENGTH_SHORT).show();
                })
                .setNegativeButton(R.string.dialog_cancel, (dialogInterface, i) -> {

                });
        return builder.create();
    }
}

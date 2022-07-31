package edu.saintleo.com437.inventorymanager.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;

import edu.saintleo.com437.inventorymanager.R;

public class AddItemDialog extends DialogFragment {
    View dialogView;
    Spinner dropdown;
    String selectedItem;
    String[] items;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // fetch the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        // inflate the dialog view
        dialogView = inflater.inflate(R.layout.dialog_add_item, null);
        // get the dropdown control
        dropdown = dialogView.findViewById(R.id.ddl_item_category);
        // populate the dropdown
        items = getResources().getStringArray(R.array.categories);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedItem = items[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // create builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // build the view
        builder.setView(dialogView)
                .setTitle(R.string.dialog_add_item_title)
                .setPositiveButton(R.string.dialog_add, (dialogInterface, i) -> {
                    EditText txtItemName = dialogView.findViewById(R.id.txt_item_name);
                    String message = String.format("Item Name: %s, Item Category: %s", txtItemName.getText(), selectedItem);
                    Snackbar.make(
                            requireActivity().findViewById(R.id.nav_host_fragment_content_main),
                            message,
                            Snackbar.LENGTH_SHORT).show();
                })
                .setNegativeButton(R.string.dialog_cancel, (dialogInterface, i) -> {

                });
        return builder.create();
    }
}

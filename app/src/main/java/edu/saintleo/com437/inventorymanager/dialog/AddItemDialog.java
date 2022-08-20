package edu.saintleo.com437.inventorymanager.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import edu.saintleo.com437.inventorymanager.R;
import edu.saintleo.com437.inventorymanager.dao.AppDatabase;
import edu.saintleo.com437.inventorymanager.dao.ItemDao;
import edu.saintleo.com437.inventorymanager.dao.entities.Item;

public class AddItemDialog extends DialogFragment {
    View dialogView;
    Spinner dropdown;
    String selectedItem;
    String[] items;

    AppDatabase db;
    ItemDao dao;
    int categoryId;

    public static final String TAG = "AddItem";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // get the context
        Context context = requireActivity().getApplicationContext();
        // get database
        db = AppDatabase.getInstance(context);
        // get dao
        dao = db.itemDao();
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
                categoryId = i;
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
                .setPositiveButton(R.string.dialog_add, null)
                .setNegativeButton(R.string.dialog_cancel, (dialogInterface, i) -> { });
        AlertDialog dialog = builder.create();

        dialog.setOnShowListener(dialogInterface -> {
            Button okButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            okButton.setOnClickListener(view -> {
                EditText txtItemName = dialogView.findViewById(R.id.txt_item_name);
                final String itemName = txtItemName.getText().toString().trim();
                // verify that item name is NOT empty
                if (itemName.isEmpty()) {
                    txtItemName.setError("Item Name is required");
                    txtItemName.requestFocus();
                    return;
                }
                Item item = new Item();
                item.name = itemName;
                item.categoryId = categoryId;
                dao.insert(item);
                // close the dialog
                dialog.dismiss();
            });
        });

        return dialog;
    }

    @Override
    public void onDismiss(@NonNull final DialogInterface dialog) {
        super.onDismiss(dialog);
        FragmentActivity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }
}

package edu.saintleo.com437.inventorymanager.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import edu.saintleo.com437.inventorymanager.R;
import edu.saintleo.com437.inventorymanager.dao.AppDatabase;
import edu.saintleo.com437.inventorymanager.dao.ItemDao;
import edu.saintleo.com437.inventorymanager.dao.entities.Item;

public class RemoveItemDialog extends DialogFragment {

    View dialogView;

    AppDatabase db;
    ItemDao dao;

    Item itemToDelete;

    public static final String TAG = "RemoveItem";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // get item from bundle
        if (getArguments() == null) {
            return super.onCreateDialog(savedInstanceState);
        }
        this.itemToDelete = (Item)getArguments().getSerializable("item");
        // get the context
        Context context = requireActivity().getApplicationContext();
        // get database
        db = AppDatabase.getInstance(context);
        // get dao
        dao = db.itemDao();
        // fetch the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        // inflate the dialog view
        dialogView = inflater.inflate(R.layout.dialog_remove_item, null);
        // create builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // build the view
        builder.setView(dialogView)
                .setTitle(R.string.dialog_remove_item_title)
                .setPositiveButton(R.string.dialog_remove, (dialogInterface, i) -> dao.delete(this.itemToDelete))
                .setNegativeButton(R.string.dialog_cancel, (dialogInterface, i) -> {});
        return builder.create();
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

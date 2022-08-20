package edu.saintleo.com437.inventorymanager.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import edu.saintleo.com437.inventorymanager.R;
import edu.saintleo.com437.inventorymanager.dao.AppDatabase;
import edu.saintleo.com437.inventorymanager.dao.ShoppingListDao;
import edu.saintleo.com437.inventorymanager.dao.entities.ShoppingList;
import io.reactivex.rxjava3.annotations.NonNull;

public class RemoveShoppingListDialog extends DialogFragment {

    View dialogView;
    AppDatabase db;
    ShoppingListDao dao;

    ShoppingList shoppingListToDelete;

    public static final String TAG = "RemoveShoppingList";
    public static final String SERIALIZABLE_TAG = "shopping-list";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // get shopping list from bundle
        if (getArguments() == null) {
            return super.onCreateDialog(savedInstanceState);
        }
        this.shoppingListToDelete = (ShoppingList) getArguments().getSerializable(SERIALIZABLE_TAG);
        // get the context
        Context context = requireActivity().getApplicationContext();
        // get the database
        db = AppDatabase.getInstance(context);
        // get dao
        dao = db.shoppingListDao();
        // fetch the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        // inflate the dialog view
        dialogView = inflater.inflate(R.layout.dialog_remove_shopping_list, null);
        // create builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // build the view
        builder.setView(dialogView)
                .setTitle(R.string.dialog_remove_shopping_list_title)
                .setPositiveButton(R.string.dialog_remove, (dialogInterface, i) -> dao.delete(this.shoppingListToDelete))
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

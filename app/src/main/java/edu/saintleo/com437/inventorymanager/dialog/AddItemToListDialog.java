package edu.saintleo.com437.inventorymanager.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;

import edu.saintleo.com437.inventorymanager.R;
import edu.saintleo.com437.inventorymanager.dao.AppDatabase;
import edu.saintleo.com437.inventorymanager.dao.ShoppingListDao;
import edu.saintleo.com437.inventorymanager.dao.ShoppingListItemDao;
import edu.saintleo.com437.inventorymanager.dao.entities.Item;
import edu.saintleo.com437.inventorymanager.dao.entities.ShoppingList;
import edu.saintleo.com437.inventorymanager.dao.entities.ShoppingListItem;
import io.reactivex.rxjava3.annotations.NonNull;

public class AddItemToListDialog extends DialogFragment {

    View dialogView;
    Spinner dropdown;
    AppDatabase db;
    ShoppingListDao shoppingListDao;
    ShoppingListItemDao shoppingListItemDao;
    List<String> items;
    List<ShoppingList> shoppingLists;

    ShoppingList selectedShoppingList;
    Item itemToAdd;

    public static final String TAG = "AddItemToList";
    public static final String SERIALIZABLE_TAG = "list-item";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // get item from bundle
        if (getArguments() == null) {
            return super.onCreateDialog(savedInstanceState);
        }
        this.itemToAdd = (Item) getArguments().getSerializable(SERIALIZABLE_TAG);
        // get the context
        Context context = requireActivity().getApplicationContext();
        // get database
        db = AppDatabase.getInstance(context);
        // get daos
        shoppingListDao = db.shoppingListDao();
        shoppingListItemDao = db.shoppingListItemDao();
        // fetch the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        // inflate the dialog view
        dialogView = inflater.inflate(R.layout.dialog_add_item_to_list, null);
        // hide the label and edit text
        dialogView.findViewById(R.id.lbl_new_shopping_list_name).setVisibility(View.INVISIBLE);
        dialogView.findViewById(R.id.txt_new_shopping_list_name).setVisibility(View.INVISIBLE);
        // get dropdown control
        dropdown = dialogView.findViewById(R.id.ddl_shopping_list);
        initDropDownItems();
        // populate dropdown
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            getContext(),
            android.R.layout.simple_spinner_item,
            this.items
        );
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // if index is 0, then we need to create a new shopping list
                if (i == 0) {
                    selectedShoppingList = new ShoppingList();
                    // show label/edit text
                    dialogView.findViewById(R.id.lbl_new_shopping_list_name).setVisibility(View.VISIBLE);
                    dialogView.findViewById(R.id.txt_new_shopping_list_name).setVisibility(View.VISIBLE);
                } else {
                    // in order to get the correct shopping list, take the inbound
                    // index and subtract by 1 as index 0 is a new list
                    selectedShoppingList = shoppingLists.get(i - 1);
                    // hide label/edit text
                    dialogView.findViewById(R.id.lbl_new_shopping_list_name).setVisibility(View.INVISIBLE);
                    EditText editText = dialogView.findViewById(R.id.txt_new_shopping_list_name);
                    editText.setVisibility(View.INVISIBLE);
                    editText.setText(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // create build
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // build the view
        builder.setView(dialogView)
                .setTitle(R.string.dialog_add_item_to_list_title)
                .setPositiveButton(R.string.dialog_add, null)
                .setNegativeButton(R.string.dialog_cancel, (dialogInterface, i) -> {});
        AlertDialog dialog = builder.create();
        // set the on show listener
        dialog.setOnShowListener(dialogInterface -> {
            Button okButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            okButton.setOnClickListener(view -> {
                // if shopping list is 0, then create a new one
                if (selectedShoppingList.id == 0) {
                    EditText txtShoppingListName = dialogView.findViewById(R.id.txt_new_shopping_list_name);
                    String shoppingListName = txtShoppingListName.getText().toString().trim();
                    if (shoppingListName.isEmpty()) {
                        txtShoppingListName.setError("Shopping List Name is required");
                        txtShoppingListName.requestFocus();
                        return;
                    }
                    selectedShoppingList.name = shoppingListName;
                    long id = shoppingListDao.insert(selectedShoppingList);
                    // assign the selected shopping list with the id
                    selectedShoppingList.id = (int)id;
                }
                // create a new shopping list item
                ShoppingListItem item = new ShoppingListItem();
                item.shoppingListId = selectedShoppingList.id;
                item.itemId = itemToAdd.id;
                shoppingListItemDao.insert(item);
                // close the dialog
                dialog.dismiss();
            });
        });
        return dialog;
    }

    private void initDropDownItems() {
        // get shopping lists
        shoppingLists = shoppingListDao.getAll();
        items = new ArrayList<>();
        // push the default "Add"
        items.add("Add New List");
        // foreach shopping list, add the name
        for (ShoppingList list : shoppingLists) {
            items.add(list.name);
        }
    }
}

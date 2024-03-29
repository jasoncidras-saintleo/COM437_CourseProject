package edu.saintleo.com437.inventorymanager.dao.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.saintleo.com437.inventorymanager.R;
import edu.saintleo.com437.inventorymanager.ShoppingListItemActivity;
import edu.saintleo.com437.inventorymanager.constants.Strings;
import edu.saintleo.com437.inventorymanager.dao.entities.ShoppingList;
import edu.saintleo.com437.inventorymanager.dialog.RemoveShoppingListDialog;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder> {

    private final Context context;
    private final List<ShoppingList> shoppingLists;
    private final AppCompatActivity activity;

    public ShoppingListAdapter(
        List<ShoppingList> shoppingLists,
        Context context,
        AppCompatActivity activity
    ) {
        this.context = context;
        this.shoppingLists = shoppingLists;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ShoppingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(this.context).inflate(R.layout.shopping_list_item, parent, false);
        return new ShoppingListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ShoppingListViewHolder holder, int position) {
        ShoppingList shoppingList = this.shoppingLists.get(position);
        // sets the name of the shopping list
        holder.txtShoppingListName.setText(shoppingList.name);
        // deletes a shopping list
        holder.btnDeleteShoppingList.setOnClickListener(view -> {
            // set shopping list to bundle
            Bundle bundle = new Bundle();
            bundle.putSerializable(RemoveShoppingListDialog.SERIALIZABLE_TAG, shoppingList);
            RemoveShoppingListDialog dialog = new RemoveShoppingListDialog();
            dialog.setArguments(bundle);
            // get transaction
            FragmentTransaction ft = this.activity.getSupportFragmentManager().beginTransaction();
            // if there was a previous dialog, find it and remove it
            Fragment prev = this.activity.getSupportFragmentManager().findFragmentByTag(RemoveShoppingListDialog.TAG);
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            // show dialog
            dialog.show(ft, RemoveShoppingListDialog.TAG);
        });
        // navigate to the shopping items
        holder.itemView.setOnClickListener(view -> {
            // create a new intent and bundle the
            Intent intent = new Intent(this.context, ShoppingListItemActivity.class);
            intent.putExtra(Strings.SHOPPING_LIST_ID, shoppingList.id);
            this.activity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return this.shoppingLists.size(); }

    public static class ShoppingListViewHolder extends RecyclerView.ViewHolder {
        TextView txtShoppingListName;
        ImageButton btnDeleteShoppingList;
        public ShoppingListViewHolder(View view) {
            super(view);
            txtShoppingListName = view.findViewById(R.id.tv_shopping_list_name);
            btnDeleteShoppingList = view.findViewById(R.id.btn_delete_shopping_list);
        }
    }
}

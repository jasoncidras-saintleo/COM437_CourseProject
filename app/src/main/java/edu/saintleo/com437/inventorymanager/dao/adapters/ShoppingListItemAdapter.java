package edu.saintleo.com437.inventorymanager.dao.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.saintleo.com437.inventorymanager.R;
import edu.saintleo.com437.inventorymanager.dao.AppDatabase;
import edu.saintleo.com437.inventorymanager.dao.ShoppingListItemDao;
import edu.saintleo.com437.inventorymanager.dao.entities.ShoppingListItem;
import edu.saintleo.com437.inventorymanager.dialog.RemoveShoppingListItemDialog;
import io.reactivex.rxjava3.annotations.NonNull;

public class ShoppingListItemAdapter extends RecyclerView.Adapter<ShoppingListItemAdapter.ShoppingListItemViewHolder> {

    private final Context context;
    private final List<ShoppingListItem> items;
    private final AppCompatActivity activity;
    private final ShoppingListItemDao dao;

    public ShoppingListItemAdapter(
        List<ShoppingListItem> items,
        Context context,
        AppCompatActivity activity,
        ShoppingListItemDao dao
    ) {
        this.context = context;
        this.items = items;
        this.activity = activity;
        this.dao = dao;
    }

    @NonNull
    @Override
    public ShoppingListItemAdapter.ShoppingListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(this.context).inflate(R.layout.shopping_list_item_item, parent, false);
        return new ShoppingListItemAdapter.ShoppingListItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ShoppingListItemAdapter.ShoppingListItemViewHolder holder, int position) {
        ShoppingListItem item = this.items.get(position);
        holder.txtShoppingListItemName.setText(item.itemName);
        holder.cboxIsItemDone.setChecked(item.isDone);
        holder.cboxIsItemDone.setOnClickListener(view -> {
            item.isDone = holder.cboxIsItemDone.isChecked();
            dao.update(item);
        });
        holder.btnDeleteShoppingListItem.setOnClickListener(view -> {
            // set shopping list to bundle
            Bundle bundle = new Bundle();
            bundle.putSerializable(RemoveShoppingListItemDialog.SERIALIZABLE_TAG, item);
            RemoveShoppingListItemDialog dialog = new RemoveShoppingListItemDialog();
            dialog.setArguments(bundle);
            // get transaction
            FragmentTransaction ft = this.activity.getSupportFragmentManager().beginTransaction();
            // if there was a previous dialog, find it and remove it
            Fragment prev = this.activity.getSupportFragmentManager().findFragmentByTag(RemoveShoppingListItemDialog.TAG);
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            // show dialog
            dialog.show(ft, RemoveShoppingListItemDialog.TAG);
        });
    }

    @Override
    public int getItemCount() { return this.items.size(); }

    static class ShoppingListItemViewHolder extends RecyclerView.ViewHolder {

        TextView txtShoppingListItemName;
        CheckBox cboxIsItemDone;
        ImageButton btnDeleteShoppingListItem;

        public ShoppingListItemViewHolder(View view) {
            super(view);
            txtShoppingListItemName = view.findViewById(R.id.tv_shopping_list_item_name);
            cboxIsItemDone = view.findViewById(R.id.cbox_is_item_done);
            btnDeleteShoppingListItem = view.findViewById(R.id.btn_delete_shopping_list_item);
        }
    }

}

package edu.saintleo.com437.inventorymanager.dao.adapters;

import android.content.Context;
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
import edu.saintleo.com437.inventorymanager.dao.entities.Item;
import edu.saintleo.com437.inventorymanager.dialog.AddItemToListDialog;
import edu.saintleo.com437.inventorymanager.dialog.RemoveItemDialog;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private final Context context;
    private final List<Item> items;
    private final AppCompatActivity activity;

    public ItemAdapter(List<Item> items, Context context, AppCompatActivity activity) {
        this.context = context;
        this.items = items;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(this.context).inflate(R.layout.inventory_item, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Item item = this.items.get(position);
        holder.txtItemName.setText(item.name);
        holder.btnDeleteItem.setOnClickListener(view -> {
            // set item to bundle
            Bundle bundle = new Bundle();
            bundle.putSerializable(RemoveItemDialog.SERIALIZABLE_TAG, item);
            RemoveItemDialog dialog = new RemoveItemDialog();
            dialog.setArguments(bundle);
            // get transaction
            FragmentTransaction ft = this.activity.getSupportFragmentManager().beginTransaction();
            // if there was a previous dialog, find it and remove it
            Fragment prev = this.activity.getSupportFragmentManager().findFragmentByTag(RemoveItemDialog.TAG);
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            // show dialog
            dialog.show(ft, RemoveItemDialog.TAG);
        });
        holder.btnAddToList.setOnClickListener(view -> {
            // set item to bundle
            // set item to bundle
            Bundle bundle = new Bundle();
            bundle.putSerializable(AddItemToListDialog.SERIALIZABLE_TAG, item);
            AddItemToListDialog dialog = new AddItemToListDialog();
            dialog.setArguments(bundle);
            // get transaction
            FragmentTransaction ft = this.activity.getSupportFragmentManager().beginTransaction();
            // if there was a previous dialog, find it and remove it
            Fragment prev = this.activity.getSupportFragmentManager().findFragmentByTag(RemoveItemDialog.TAG);
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            // show dialog
            dialog.show(ft, AddItemToListDialog.TAG);
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView txtItemName;
        ImageButton btnDeleteItem;
        ImageButton btnAddToList;
        public ItemViewHolder(View view) {
            super(view);
            txtItemName = view.findViewById(R.id.tv_item_name);
            btnDeleteItem = view.findViewById(R.id.btn_delete_item);
            btnAddToList = view.findViewById(R.id.btn_add_to_list);
        }
    }
}

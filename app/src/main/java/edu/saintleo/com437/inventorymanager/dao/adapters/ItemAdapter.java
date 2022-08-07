package edu.saintleo.com437.inventorymanager.dao.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.saintleo.com437.inventorymanager.R;
import edu.saintleo.com437.inventorymanager.dao.entities.Item;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private final Context context;
    private final List<Item> items;

    public ItemAdapter(List<Item> items, Context context) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(this.context).inflate(R.layout.inventory_item, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.txtItemName.setText(this.items.get(position).name);
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView txtItemName;
        public ItemViewHolder(View view) {
            super(view);
            txtItemName = view.findViewById(R.id.tv_item_name);
        }
    }
}

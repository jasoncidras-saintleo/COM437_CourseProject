package edu.saintleo.com437.inventorymanager.dao.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.saintleo.com437.inventorymanager.R;

public class HeaderAdapter extends RecyclerView.Adapter<HeaderAdapter.HeaderViewHolder> {

    private final String title;
    private final boolean includeFilter;
    private final Context context;
    private final AdapterView.OnItemSelectedListener listener;

    public HeaderAdapter(
            String title,
            Context context,
            boolean includeFilter,
            @Nullable AdapterView.OnItemSelectedListener listener
    ) {
        this.title = title;
        this.context = context;
        this.includeFilter = includeFilter;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HeaderViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType
            ) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_item, parent, false);
        return new HeaderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HeaderViewHolder holder, int position) {
        holder.tvHeader.setText(this.title);
        holder.filter.setVisibility(includeFilter ? View.VISIBLE : View.INVISIBLE);
        if (includeFilter) {
            List<String> items = new ArrayList<String>() {
                {
                    add("-- Select Filter --"); // 0
                    add("View All"); // 1
                }
            };
            items.addAll(Arrays.asList(this.context.getResources().getStringArray(R.array.categories)));
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this.context,
                    android.R.layout.simple_spinner_item,
                    items
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.filter.setAdapter(adapter);
            holder.filter.setOnItemSelectedListener(this.listener);
        }
    }

    @Override
    public int getItemCount() { return 1; }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView tvHeader;
        Spinner filter;

        public HeaderViewHolder(View view) {
            super(view);
            tvHeader = view.findViewById(R.id.header_title);
            filter = view.findViewById(R.id.header_filter);
        }
    }
}

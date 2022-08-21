package edu.saintleo.com437.inventorymanager.dao.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import edu.saintleo.com437.inventorymanager.R;
import io.reactivex.rxjava3.annotations.NonNull;

public class HeaderAdapter extends RecyclerView.Adapter<HeaderAdapter.HeaderViewHolder> {

    private final String title;

    public HeaderAdapter(String title) {
        this.title = title;
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
    }

    @Override
    public int getItemCount() { return 1; }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView tvHeader;

        public HeaderViewHolder(View view) {
            super(view);
            tvHeader = view.findViewById(R.id.header_title);
        }
    }
}

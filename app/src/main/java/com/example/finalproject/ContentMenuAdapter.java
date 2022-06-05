package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContentMenuAdapter extends RecyclerView.Adapter<ContentMenuAdapter.ContentMenuViewHolder> {

    interface AdapterListener {
        void ContentHistoryAdapterClickListener(Content ch);
    }

    private ArrayList<Content> hisList;
    private Context ctx;
    AdapterListener listener;

    public ContentMenuAdapter(Context c, ArrayList<Content> history) {
        this.ctx = c;
        this.hisList = history;
    }

    @NonNull
    @Override
    public ContentMenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_list, viewGroup, false);

        return new ContentMenuViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ContentMenuViewHolder holder, int pos) {
        holder.onBind(hisList.get(pos));
    }

    @Override
    public int getItemCount() { return hisList.size(); }

    class ContentMenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView quote, author, date;

        public ContentMenuViewHolder(View v) {
            super(v);

            quote = v.findViewById(R.id.menu_quote);
            author = v.findViewById(R.id.menu_author);
            date = v.findViewById(R.id.menu_date);
            itemView.setOnClickListener(this);
        }

        public void onBind(Content ch) {
            quote.setText(ch.getQuote());
            author.setText(ch.getAuthor());
            date.setText(ch.getDate());
        }

        @Override
        public void onClick(View v) {
            Content ch = hisList.get(getAdapterPosition());
            listener.ContentHistoryAdapterClickListener(ch);
        }
    }
}

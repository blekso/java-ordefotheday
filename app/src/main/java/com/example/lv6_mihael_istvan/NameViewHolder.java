package com.example.lv6_mihael_istvan;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView tvName;
    private ImageView imgRemove;
    private RemoveClickListener clickListener;

    public NameViewHolder(@NonNull View itemView, RemoveClickListener listener) {
        super(itemView);
        this.clickListener = listener;
        this.tvName = itemView.findViewById(R.id.tvName);
        this.imgRemove = itemView.findViewById(R.id.imgRemove);
        imgRemove.setOnClickListener(this);
    }
    public void setName(String name) {
        tvName.setText(name);
    }

    public void onClick(View view){
        clickListener.onRemoveClick(getAdapterPosition());
    }
}


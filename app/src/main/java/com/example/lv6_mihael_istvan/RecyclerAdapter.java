package com.example.lv6_mihael_istvan;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<NameViewHolder> {

    private List<String> dataList = new ArrayList<>();
    private RemoveClickListener clickListener;

    public RecyclerAdapter(RemoveClickListener listener) {
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public NameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cellView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_name, parent, false);
        return new NameViewHolder(cellView, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NameViewHolder nameViewHolder, int position) {
        nameViewHolder.setName(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void addData(List<String> data) {
        this.dataList.clear();
        this.dataList.addAll(data);
        notifyDataSetChanged();
    }

    public void printList(){
        for(String model : dataList) {
            Log.i("listItem", model);
        }
    }

    public String getItemName(int position){
        return dataList.get(position);
    }

    public void addNewCell(String name, int position) {
        if (dataList.size() >= position) {
            dataList.add(position, name);
            notifyItemInserted(position);
        }
    }

    public void removeCell(int position) {
        if (dataList.size() > position) {
            dataList.remove(position);
            notifyItemRemoved(position);

        }
    }
}

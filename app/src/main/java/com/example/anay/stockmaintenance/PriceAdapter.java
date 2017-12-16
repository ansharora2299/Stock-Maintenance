package com.example.anay.stockmaintenance;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.*;

public class PriceAdapter extends RecyclerView.Adapter<PriceAdapter.ItemViewHolder>{
    static ArrayList<ItemModel> pricelist ;
    class ItemViewHolder extends RecyclerView.ViewHolder{
        public TextView name,quantity,price;
        public ItemViewHolder(View view){
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            quantity = (TextView) view.findViewById(R.id.quantity);
            price = (TextView) view.findViewById(R.id.price);

        }
    }
    public PriceAdapter(ArrayList<ItemModel> pricelist)
    {
        this.pricelist=pricelist;

    }

    public PriceAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_price,parent,false);
        return new PriceAdapter.ItemViewHolder(itemView);
    }

    public void onBindViewHolder(PriceAdapter.ItemViewHolder holder, int position){
        ItemModel item=pricelist.get(position);
        holder.name.setText(item.name);
        holder.quantity.setText(item.quantity);
        holder.price.setText(item.price);
    }

    public int getItemCount(){
        return pricelist.size();
    }

}

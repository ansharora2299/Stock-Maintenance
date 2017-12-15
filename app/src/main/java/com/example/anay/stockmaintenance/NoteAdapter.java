package com.example.anay.stockmaintenance;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.*;

/**
 * Created by Anay on 12-12-2017.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ItemViewHolder> {
   static ArrayList<ItemModel> itemlist ;
    class ItemViewHolder extends RecyclerView.ViewHolder{
        public TextView name,quantity;
         public ItemViewHolder(View view){
             super(view);
             name = (TextView) view.findViewById(R.id.name);
             quantity = (TextView) view.findViewById(R.id.quantity);
         }
    }
    public NoteAdapter(ArrayList<ItemModel> itemlist)
    {
        this.itemlist=itemlist;

    }

    public ItemViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item,parent,false);
        return new ItemViewHolder(itemView);
    }

    public void onBindViewHolder(ItemViewHolder holder,int position){
        ItemModel item=itemlist.get(position);
        holder.name.setText(item.name);
        holder.quantity.setText(item.quantity);
    }

    public int getItemCount(){
        return itemlist.size();
    }

}

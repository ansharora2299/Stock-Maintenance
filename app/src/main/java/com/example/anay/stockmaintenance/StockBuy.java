package com.example.anay.stockmaintenance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

public class StockBuy extends AppCompatActivity {

    static ArrayList<ItemModel> itemlist = new ArrayList<>();
    static ArrayList<ItemModel> buylist = new ArrayList<>();
    RecyclerView recyclerView;
    NoteAdapter itemAdapter;
    Button gohome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_buy);
        try
        {
            itemlist = readFromStock();
        }
        catch(Exception e){}
        for(int i=0;i<itemlist.size();i++)
        {
            if(Integer.parseInt(itemlist.get(i).quantity)<=5)
                buylist.add(itemlist.get(i));
        }
        buylist=sort(buylist);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        itemAdapter = new NoteAdapter(buylist);
        recyclerView.setAdapter(itemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        gohome = (Button) findViewById(R.id.gohome);
        gohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StockBuy.this, HomeActivity.class));
            }
        });
    }
    public ArrayList<ItemModel> readFromStock(){
        String filename="Stock.txt";
        ArrayList<ItemModel> itemlist = new ArrayList<>();
        Gson gson=new Gson();
        try{
            File file=new File(getApplicationContext().getFilesDir(),filename);
            String line;
            BufferedReader br=new BufferedReader(new FileReader(file));
            while((line=br.readLine())!=null){
                ItemModel item=gson.fromJson(line,ItemModel.class);
                itemlist.add(item);
            }
            br.close();
        }
        catch (Exception e){
            e.getMessage();
        }
        return itemlist;
    }
    public ArrayList<ItemModel> sort(ArrayList<ItemModel> stocklist)
    {
        for(int i=0;i<stocklist.size()-1;i++)
        {
            ItemModel itemi=stocklist.get(i);
            for(int j=i+1;j<stocklist.size();j++)
            {
                ItemModel itemj=stocklist.get(j);
                if(itemi.name.compareTo(itemj.name)>0)
                {
                    Collections.swap(stocklist,i,j);
                }
            }
        }
        return stocklist;
    }
}

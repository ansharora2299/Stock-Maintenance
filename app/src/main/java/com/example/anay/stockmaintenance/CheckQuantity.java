package com.example.anay.stockmaintenance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class CheckQuantity extends AppCompatActivity {

    static ArrayList<ItemModel> stocklist = new ArrayList<>();
    RecyclerView recyclerView;
    NoteAdapter itemAdapter;
    Button gobackhome1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_quantity);

        stocklist = readFromFile();
        File file=new File(getApplicationContext().getFilesDir(),"Buy.txt");
        file.delete();
        stocklist=sort(stocklist);
        for(int i=0;i<stocklist.size();i++)
        {
            if(Integer.parseInt(stocklist.get(i).quantity)<=5) {
                writeToBuy(stocklist.get(i));
            }
        }
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        itemAdapter= new NoteAdapter(stocklist);
        recyclerView.setAdapter(itemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        gobackhome1=(Button)findViewById(R.id.a);
        gobackhome1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CheckQuantity.this, HomeActivity.class));
            }
        });
    }


    public ArrayList<ItemModel> readFromFile(){
        String filename="Stock.txt";
        ArrayList<ItemModel> stocklist = new ArrayList<>();
        Gson gson=new Gson();
        try{
            File file=new File(getApplicationContext().getFilesDir(),filename);
            String line;
            BufferedReader br=new BufferedReader(new FileReader(file));
            while((line=br.readLine())!=null){
                ItemModel item=gson.fromJson(line,ItemModel.class);
                stocklist.add(item);
            }
            br.close();
        }catch (Exception e){
            e.getMessage();
        }
        return stocklist;
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
    public void writeToBuy(ItemModel item){
        String filename="Buy.txt";
        Gson gson=new Gson();
        String jsonItem =gson.toJson(item);
        try{
            File file=new File(getApplicationContext().getFilesDir(),filename);
            FileWriter fw=new FileWriter(file,true);
            fw.write(jsonItem+"\n");
            fw.close();
        }
        catch (IOException e){
            e.getMessage();
        }
    }
}
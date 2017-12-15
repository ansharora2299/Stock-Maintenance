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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class OldBill extends AppCompatActivity {

    static ArrayList<ItemModel> prevbills = new ArrayList<>();
    RecyclerView recyclerView;
    NoteAdapter itemAdapter;
    Button gobackhome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_bill);

        prevbills = readFromFile();
        Collections.reverse(prevbills);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        itemAdapter= new NoteAdapter(prevbills);
        recyclerView.setAdapter(itemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        gobackhome=(Button)findViewById(R.id.backtohome);
        gobackhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OldBill.this, HomeActivity.class));
            }
        });
    }


    public ArrayList<ItemModel> readFromFile(){
        String filename="Bills.txt";
        ArrayList<ItemModel> prevbills = new ArrayList<>();
        Gson gson=new Gson();
        try{
            File file=new File(getApplicationContext().getFilesDir(),filename);
            String line;
            BufferedReader br=new BufferedReader(new FileReader(file));
            while((line=br.readLine())!=null){
                ItemModel item=gson.fromJson(line,ItemModel.class);
                prevbills.add(item);
            }
            br.close();
        }catch (Exception e){
            e.getMessage();
        }
        return prevbills;
    }
}

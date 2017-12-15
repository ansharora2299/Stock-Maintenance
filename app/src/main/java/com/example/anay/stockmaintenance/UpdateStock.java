package com.example.anay.stockmaintenance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class UpdateStock extends AppCompatActivity {

    Button update;
    EditText itemnameid,itemquantityid;
    String name,quantity;
    static ArrayList<ItemModel> stocklist = new ArrayList<>();
    //SharedPreferences auth_sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_stock);
        stocklist=readFromFile();
        itemnameid=(EditText)findViewById(R.id.itemname);
        itemquantityid=(EditText)findViewById(R.id.itemquantity);
        update=(Button)findViewById(R.id.update);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get name and quantity from user
                String name=itemnameid.getText().toString();
                String quantity=itemquantityid.getText().toString();
                //auth_sharedPref=getApplicationContext().getSharedPreferences(getString(R.string.auth_sharedpref), Context.MODE_PRIVATE);

                //String saved_name = auth_sharedPref.getString(getString(R.string.key_name), "");

                //String saved_quantity = auth_sharedPref.getString(getString(R.string.key_quantity), "");


                if(name.isEmpty()||quantity.isEmpty())
                {
                    Toast.makeText(UpdateStock.this, "Fields Cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else if(!Digit(quantity))
                {
                    Toast.makeText(UpdateStock.this, "Quantity must be an Integer", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    boolean added=false;
                    ItemModel item = new ItemModel(name, quantity);
                    try{
                        NoteAdapter.itemlist.add(item);
                    }
                    catch(Exception e){}
                    Iterator<ItemModel> i=stocklist.iterator();
                    while(i.hasNext())
                    {
                        ItemModel o=i.next();
                        if(o.name.equalsIgnoreCase(item.name))
                        {
                            added=true;
                            o.quantity=Integer.toString(Integer.parseInt(item.quantity)+Integer.parseInt(o.quantity));
                        }
                        writeToFile(o);
                    }
                    if(!added)
                      writeToFile(item);
                    Intent intent = new Intent(UpdateStock.this, CheckQuantity.class);
                    intent.putExtra(getString(R.string.key_name), name);
                    intent.putExtra(getString(R.string.key_quantity), quantity);
                    startActivity(intent);
                }

            }
        });

    }
    public void writeToFile(ItemModel item){
        String filename="Stock.txt";
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
    public ArrayList<ItemModel> readFromFile(){
        String filename="Stock.txt";
        File file=new File(getApplicationContext().getFilesDir(),filename);
        ArrayList<ItemModel> stocklist = new ArrayList<>();
        Gson gson=new Gson();
        try{
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
        file.delete();
        return stocklist;
    }
    public boolean Digit(String quantity)
    {
        boolean check=true;
        for(int i=0;i<quantity.length();i++)
        {
            if(!Character.isDigit(quantity.charAt(i)))
            {
                check=false;
                break;
            }
        }
        return check;
    }
}
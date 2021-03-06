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
    EditText itemnameid,itemquantityid,itempriceid;
    String name,quantity;
    String price;
    static ArrayList<ItemModel> stocklist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_stock);
        itemnameid=(EditText)findViewById(R.id.itemname);
        itemquantityid=(EditText)findViewById(R.id.itemquantity);
        itempriceid=(EditText)findViewById(R.id.itemprice);
        update=(Button)findViewById(R.id.update);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name=itemnameid.getText().toString();
                quantity=itemquantityid.getText().toString();
                price=itempriceid.getText().toString();

                if(name.isEmpty()||quantity.isEmpty()||price.isEmpty())
                {
                    Toast.makeText(UpdateStock.this, "Fields Cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else if(!Digit(quantity))
                {
                    Toast.makeText(UpdateStock.this, "Quantity must be an Integer", Toast.LENGTH_SHORT).show();
                }
                else if(!Digitprice(price))
                {
                    Toast.makeText(UpdateStock.this, "Price must be Numeric", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    stocklist=readFromFile();
                    boolean added=false;
                    ItemModel item = new ItemModel(name, quantity,price);
                    try{
                        PriceAdapter.pricelist.add(item);
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
    public ArrayList<ItemModel> readFromNew(){
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
    public boolean Digitprice(String quantity)
    {
        boolean check=true;
        for(int i=0;i<quantity.length();i++)
        {
            if(quantity.charAt(i)=='.')
                continue;
            if(!Character.isDigit(quantity.charAt(i)))
            {
                check=false;
                break;
            }
        }
        return check;
    }
}
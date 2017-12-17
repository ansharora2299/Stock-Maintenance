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

public class AddItem extends AppCompatActivity {

    Button add;
    EditText nameid,quantityid;
    String name,quantity;
    static ArrayList<ItemModel> stocklist = new ArrayList<>();
    static ArrayList<ItemModel> itemlist = new ArrayList<>();
    static ArrayList<ItemModel> templist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        nameid=(EditText)findViewById(R.id.name);
        quantityid=(EditText)findViewById(R.id.quantity);
        add=(Button)findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name=nameid.getText().toString();
                quantity=quantityid.getText().toString();

                if(name.isEmpty()||quantity.isEmpty())
                {
                    Toast.makeText(AddItem.this, "Fields Cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else if(!Digit(quantity))
                {
                    Toast.makeText(AddItem.this, "Quantity must be an Integer", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    stocklist=readFromStock();
                    try{
                        templist=readFromTemp();
                    }
                    catch(Exception e){}
                    Iterator<ItemModel> t=templist.iterator();
                    Iterator<ItemModel> i=stocklist.iterator();
                    ItemModel item = new ItemModel(name, quantity);
                    boolean valid=false,check=true,temp=false;
                    ItemModel stockitem=null;
                    ItemModel tempitem=null;
                    while(i.hasNext())
                    {
                        stockitem=i.next();
                        if(stockitem.name.equalsIgnoreCase(item.name))
                        {
                            valid=true;
                            while (t.hasNext())
                            {
                                tempitem=t.next();
                                if(tempitem.name.equalsIgnoreCase(item.name))
                                {
                                    temp=true;
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    if(valid)
                    {
                            if(temp)
                            {
                                Toast.makeText(AddItem.this, "Stock Empty", Toast.LENGTH_SHORT).show();
                                check=false;
                            }
                            else if (Integer.parseInt(stockitem.quantity)<Integer.parseInt(item.quantity) && Integer.parseInt(stockitem.quantity) > 0) {
                                Toast.makeText(AddItem.this, "Required Quantity not available", Toast.LENGTH_SHORT).show();
                                item.quantity = stockitem.quantity;
                                stockitem.quantity="0";
                                writeToTemp(stockitem);
                                check=true;
                            }
                            else if (stockitem.quantity.equals("0")) {
                                Toast.makeText(AddItem.this, "Stock Empty", Toast.LENGTH_SHORT).show();
                                check=false;
                            }
                    }
                    else
                    {
                        Toast.makeText(AddItem.this, "Item not available", Toast.LENGTH_SHORT).show();
                        check=false;
                    }
                    if(check) {
                        itemlist=readFromItems();
                        for(int j=0;j<itemlist.size();j++)
                        {
                            if(itemlist.get(j).name.equalsIgnoreCase(item.name))
                            {
                                item.quantity=Integer.toString(Integer.parseInt(item.quantity)+Integer.parseInt(itemlist.get(j).quantity));;
                                continue;
                            }
                            writeToFile(itemlist.get(j));
                        }

                        for(int j=0;j<stocklist.size();j++)
                        {
                            ItemModel x=stocklist.get(j);
                            if(x.name.equalsIgnoreCase(item.name))
                            {
                                item.price=Float.toString(Integer.parseInt(item.quantity)*Float.parseFloat(x.price));
                                break;
                            }
                        }
                        PriceAdapter.pricelist.add(item);
                        writeToFile(item);
                    }
                    Intent intent = new Intent(AddItem.this, NewBill.class);
                    intent.putExtra(getString(R.string.key_name), name);
                    intent.putExtra(getString(R.string.key_quantity), quantity);
                    startActivity(intent);
                }

            }
        });

    }
        public void writeToFile(ItemModel item){
        String filename="Items.txt";
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
    public ArrayList<ItemModel> readFromStock(){
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
    public ArrayList<ItemModel> readFromItems(){
        String filename="Items.txt";
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
            file.delete();
        }catch (Exception e){
            e.getMessage();
        }
        return itemlist;
    }
    public void writeToTemp(ItemModel item){
        String filename="Temp.txt";
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
    public ArrayList<ItemModel> readFromTemp(){
        String filename="Temp.txt";
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
        }catch (Exception e){
            e.getMessage();
        }
        return itemlist;
    }
}
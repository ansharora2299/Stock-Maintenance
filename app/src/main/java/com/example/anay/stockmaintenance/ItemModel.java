package com.example.anay.stockmaintenance;

public class ItemModel {
    String name;
    String quantity;
    String price;

    public ItemModel(){}
    public ItemModel(String name,String quantity)
    {
        this.name=name;
        this.quantity=quantity;

    }
    public ItemModel(String name,String quantity,String price){
        this.name=name;
        this.quantity=quantity;
        this.price=price;

    }
}

package com.example.anay.stockmaintenance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
Button nb,ob,us,cq,sb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        nb=findViewById(R.id.newbill);
        nb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, NewBill.class));
            }
        });
        ob=findViewById(R.id.oldbill);
        ob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, OldBill.class));
            }
        });
        us=findViewById(R.id.update);
        us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, UpdateStock.class));
            }
        });
        cq=findViewById(R.id.check);
        cq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, CheckQuantity.class));
            }
        });
        sb=findViewById(R.id.stockbuy);
        sb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, StockBuy.class));
            }
        });
    }

}
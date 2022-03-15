package com.homerents.home_rent_billing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class MainActivity3 extends AppCompatActivity {
    TextView n,o,u,r,ec,hr,up,tot,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        FirebaseDatabase.getInstance().getReference().keepSynced(true);

        n=findViewById(R.id.news);
        o=findViewById(R.id.olds);
        u=findViewById(R.id.diff);
        r=findViewById(R.id.erate);
        ec=findViewById(R.id.echarge);
        hr=findViewById(R.id.hrent);
        up=findViewById(R.id.unp);
        tot=findViewById(R.id.textView25);
        name=findViewById(R.id.textView19);

        Intent intent = getIntent();

        String room=intent.getStringExtra("roomno");
        String nr=intent.getStringExtra("news");
        String or=intent.getStringExtra("olds");
        String rat=intent.getStringExtra("rate");
        String homer=intent.getStringExtra("rent");
        String un=intent.getStringExtra("unpaid");


        name.setText("Clint "+room);
        n.setText(nr);
        o.setText(or);
        int nr1=Integer.parseInt(nr);
        int or1=Integer.parseInt(or);
        int dif=nr1-or1;
        String q=String.valueOf(dif);

        u.setText(q);

        float rat1=Float.parseFloat(rat);
        rat1= (float) (Math.round(rat1*100.00)/100.00);
        float echarge=dif*rat1;
        echarge= (float) (Math.round(echarge*100.00)/100.00);

        String w=String.valueOf(rat1);
        String t=String.valueOf(echarge);
        r.setText(w);
        ec.setText(t);
        hr.setText(homer);
        up.setText(un);

        int homer1=Integer.parseInt(homer);
        float up1=Float.parseFloat(un);
        up1= (float) (Math.round(up1*100.00)/100.00);
        float total=echarge+homer1+up1;
        total= (float) (Math.round(total*100.00)/100.00);
        String l=String.valueOf(total);
        String l1="Rs. :- "+l+"/-";
        tot.setText(l1);

        Toast.makeText(this, "Take ScreenShot and save for Record's", Toast.LENGTH_LONG).show();


    }

    @Override
    protected void onStart() {

        FirebaseDatabase.getInstance().getReference().keepSynced(true);
        super.onStart();
    }
}
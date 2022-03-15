package com.homerents.home_rent_billing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Stats extends AppCompatActivity {
    private AdView mAdView;
    float addtot,addtot1,addtot2,addtot3,addtot4,temptot,temptot1,temptot2,temptot3,temptot4;
    int count,count1,count2,count3,count4;
    String gt,gt1,gt2,gt3,gt4,scount,scount1,scount2,scount3,scount4,idToSend;
    DatabaseReference db1,db2,db3,db4,db5,dataviewed;
    TextView cb,totbill,torecv,eunit,echarhe,viewed;

    float unrecived;
    List<Database> clintList,clintList1,clintList2,clintList3,clintList4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        FirebaseDatabase.getInstance().getReference().keepSynced(true);





        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adstats);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                super.onAdLoaded();
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
                super.onAdFailedToLoad(adError);
                mAdView.loadAd(adRequest);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });












        cb=findViewById(R.id.tvcb);
        totbill=findViewById(R.id.tvtotbill);
        torecv=findViewById(R.id.tvunrecived);
        eunit=findViewById(R.id.tveunit);
        echarhe=findViewById(R.id.tvecharge);
        viewed=findViewById(R.id.textView55);

        Intent intent=getIntent();
        idToSend=intent.getStringExtra("idtosend");


        clintList= new ArrayList<>();
        clintList1= new ArrayList<>();
        clintList2= new ArrayList<>();
        clintList3= new ArrayList<>();
        clintList4= new ArrayList<>();

    }

    @Override
    protected void onStart() {

        FirebaseDatabase.getInstance().getReference().keepSynced(true);


        addtot=0;
        db1 = FirebaseDatabase.getInstance().getReference("Database/"+idToSend+"/CB");
        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                count = (int) snapshot.getChildrenCount();

                if(count>0) {
                    scount = String.valueOf(count);
                    clintList.clear();

                    for (DataSnapshot studentsnapshot : snapshot.getChildren()) {
                        Database database = studentsnapshot.getValue(Database.class);
                        clintList.add(database);

                    }
                    for(int w=0;w<count;w++) {

                        Database database1 = clintList.get(w);
                        gt = database1.getCb();

                        temptot = Float.parseFloat(gt);

                        addtot = addtot + temptot;
                        addtot= (float) (Math.round(addtot*100.00)/100.00);
                        cb.setText("Rs." + addtot);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });








        addtot1=0;
        db2 = FirebaseDatabase.getInstance().getReference("Database/"+idToSend+"/TotBill");
        db2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                count1 = (int) snapshot.getChildrenCount();

                if(count1>0) {
                    scount1 = String.valueOf(count1);
                    clintList1.clear();

                    for (DataSnapshot studentsnapshot : snapshot.getChildren()) {
                        Database database = studentsnapshot.getValue(Database.class);
                        clintList1.add(database);

                    }
                    for(int w=0;w<count1;w++) {

                        Database database1 = clintList1.get(w);
                        gt1 = database1.getTotbill();

                        temptot1 = Float.parseFloat(gt1);

                        addtot1 = addtot1 + temptot1;
                        addtot1= (float) (Math.round(addtot1*100.00)/100.00);
                        totbill.setText("Rs." + addtot1);

                        unrecived=addtot1-addtot;
                        unrecived= (float) (Math.round(unrecived*100.00)/100.00);
                        torecv.setText(""+unrecived);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });








        addtot2=0;
        db3 = FirebaseDatabase.getInstance().getReference("Database/"+idToSend+"/EleUnit");
        db3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                count2 = (int) snapshot.getChildrenCount();

                if(count2>0) {
                    scount2 = String.valueOf(count2);
                    clintList2.clear();

                    for (DataSnapshot studentsnapshot : snapshot.getChildren()) {
                        Database database = studentsnapshot.getValue(Database.class);
                        clintList2.add(database);

                    }
                    for(int w=0;w<count2;w++) {

                        Database database1 = clintList2.get(w);
                        gt2 = database1.getEleunit();

                        temptot2 = Float.parseFloat(gt2);

                        addtot2 = addtot2 + temptot2;
                        addtot2= (float) (Math.round(addtot2*100.00)/100.00);

                        eunit.setText("Rs." + addtot2);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });














        addtot3=0;
        db4 = FirebaseDatabase.getInstance().getReference("Database/"+idToSend+"/EleAmt");
        db4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                count3 = (int) snapshot.getChildrenCount();

                if(count3>0) {
                    scount3 = String.valueOf(count3);
                    clintList3.clear();

                    for (DataSnapshot studentsnapshot : snapshot.getChildren()) {
                        Database database = studentsnapshot.getValue(Database.class);
                        clintList3.add(database);

                    }
                    for(int w=0;w<count3;w++) {

                        Database database1 = clintList3.get(w);
                        gt3 = database1.getEleamt();

                        temptot3 = Float.parseFloat(gt3);

                        addtot3 = addtot3 + temptot3;
                        addtot3= (float) (Math.round(addtot3*100.00)/100.00);

                        echarhe.setText("Rs." + addtot3);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });









/*
        addtot4=0;
        db5 = FirebaseDatabase.getInstance().getReference("Database/"+idToSend+"/Unrec");
        db5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                count4 = (int) snapshot.getChildrenCount();

                if(count4>0) {
                    scount4 = String.valueOf(count4);
                    clintList4.clear();

                    for (DataSnapshot studentsnapshot : snapshot.getChildren()) {
                        Database database = studentsnapshot.getValue(Database.class);
                        clintList4.add(database);

                    }
                    for(int w=0;w<count4;w++) {

                        Database database1 = clintList4.get(w);
                        gt4 = database1.getUnrec();

                        temptot4 = Integer.parseInt(gt4);

                        addtot4 = addtot4 + temptot4;
                        torecv.setText("Rs." + addtot4);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



 */

        dataviewed=FirebaseDatabase.getInstance().getReference("Database").child(idToSend).child("Viewed");
        dataviewed.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                count4 = (int) snapshot.getChildrenCount();

                if(count4>0){
                    viewed.setText("Statistics Not Updated\nKindly View newly Generated Bill of all clints\nStats Will update Automatically");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        super.onStart();
    }
}
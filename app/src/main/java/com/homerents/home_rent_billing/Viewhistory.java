package com.homerents.home_rent_billing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
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

public class Viewhistory extends AppCompatActivity {
    int count;
    DatabaseReference db;
    ListView lv;
    private AdView mAdView;
    String idToSend,temp;
    TextView t1,tv2;
    List<Database> clintList;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewhistory);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
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












        tv2=findViewById(R.id.textView43);

        Intent intent=getIntent();
        idToSend=intent.getStringExtra("idtosend");
        temp=intent.getStringExtra("cb");
        tv2.setText(temp);

        pb=findViewById(R.id.progressBaradd4);
        pb.setVisibility(View.VISIBLE);

        t1=findViewById(R.id.textviewview);

        lv=findViewById(R.id.cinputlistview);

        db= FirebaseDatabase.getInstance().getReference("Database").child(idToSend).child("Clints");
        clintList= new ArrayList<>();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Database database=clintList.get(i);
                    String cno=database.getClintno();
                    String crate=database.getClintrate();
                    String crent=database.getClintrent();
                    String name=database.getName();

                    Intent intent=new Intent(Viewhistory.this,Viewhistoryfinal.class);
                    intent.putExtra("clintno",cno);
                    intent.putExtra("clintname",name);
                    intent.putExtra("clintrate",crate);
                    intent.putExtra("clintrent",crent);
                    intent.putExtra("idtosend",idToSend);
                    startActivity(intent);

            }
        });

    }

    @Override
    protected void onStart() {

        FirebaseDatabase.getInstance().getReference().keepSynced(true);


        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                count= (int) snapshot.getChildrenCount();

                if(count<1000){
                    pb.setVisibility(View.INVISIBLE);
                }

                if(count==0){
                    AlertDialog.Builder altd= new AlertDialog.Builder(Viewhistory.this);
                    altd.setTitle("Alert!");
                    altd.setMessage("No CLint Found ");
                    altd.setCancelable(true);
                    altd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog d=altd.create();
                    d.show();
                }
                clintList.clear();

                for(DataSnapshot studentsnapshot : snapshot.getChildren()){
                    //DatabaseReference dj=studentsnapshot.getRef();
                    //dj.child("studentStatus").setValue("Click")
                    Database database=studentsnapshot.getValue(Database.class);
                    clintList.add(database);
                }

                ClintList adapter=new ClintList(Viewhistory.this, clintList);
                lv.setAdapter(adapter);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        super.onStart();
    }
}
package com.homerents.home_rent_billing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Viewhistoryfinal extends AppCompatActivity {
    DatabaseReference db,dbcb,dbcb1,dbcb2,dbcb3,dataviewed,dbname;
    ListView listView;

    String nnn;
    Button share;
    int count,k=0;
    float iunit,irate,irent,iunpaid,tot,echarge;
    List<Database> dataListlist;
    TextView tv;
    EditText e1;
    String idToSend,gt,clbal,eunit,erate,erent,eunpaid;
    String clintnumber,clintname;
    String name,n,o,rate,rent,unpaid,no,temp;

    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewhistoryfinal);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        FirebaseDatabase.getInstance().getReference().keepSynced(true);



        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequest1 = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-8585966944422416/7856149266", adRequest1,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i("TAG", "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i("TAG", loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });


        if (mInterstitialAd != null) {
            mInterstitialAd.show(Viewhistoryfinal.this);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }














        share=findViewById(R.id.button9);
        tv=findViewById(R.id.textviewview2);
        listView=findViewById(R.id.listbill);
        dataListlist= new ArrayList<>();

        Intent intent=getIntent();
        idToSend=intent.getStringExtra("idtosend");
        clintnumber=intent.getStringExtra("clintno");
        clintname=intent.getStringExtra("clintname");
        tv.setText("Clint "+clintnumber+" "+clintname+"\n1800"+idToSend+"/90"+clintnumber);




        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent shareintent=new Intent(Intent.ACTION_SEND);
                shareintent.setType("text/plain");
                String sharebody="Clint Id = 1800"+idToSend+" And Pin = 90"+clintnumber +"  Download :-  https://play.google.com/store/apps/details?id=com.homerents.home_rent_billing";
                String sharesub="Home Rental Billing App Download from Playstore ";

                shareintent.putExtra(Intent.EXTRA_SUBJECT,sharesub);
                shareintent.putExtra(Intent.EXTRA_TEXT,sharebody);

                startActivity(Intent.createChooser(shareintent,"Share Using"));

            }
        });







        db= FirebaseDatabase.getInstance().getReference("Database").child(idToSend).child("Datas").child(clintnumber);

        dbname =FirebaseDatabase.getInstance().getReference("Database").child(idToSend).child("Clints").child(clintnumber);









        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Database database=dataListlist.get(i);

                temp =clintnumber+". "+clintname;
                n=database.getNewreading();
                o=database.getOldreading();
                rate=database.getRate();
                rent=database.getRent();
                unpaid=database.getUnpaid();


                int e=dataListlist.size();
                Database database2=dataListlist.get(e-1);
                clbal=database2.getGrandtotal();


                eunit=database2.getDiff();
                erate =database2.getRate();
                iunit= Float.parseFloat(eunit);
                iunit= (float) (Math.round(iunit*100.00)/100.00);
                irate= Float.parseFloat(erate);
                irate= (float) (Math.round(irate*100.00)/100.00);
                echarge=irate*iunit;
                echarge= (float) (Math.round(echarge*100.00)/100.00);

                erent=database2.getRent();
                eunpaid=database2.getUnpaid();

                irent= Float.parseFloat(erent);
                irent= (float) (Math.round(irent*100.00)/100.00);
                iunpaid= Float.parseFloat(eunpaid);
                iunpaid= (float) (Math.round(iunpaid*100.00)/100.00);
                tot=echarge+irent+iunpaid;
                tot= (float) (Math.round(tot*100.00)/100.00);


                dataviewed=FirebaseDatabase.getInstance().getReference("Database").child(idToSend).child("Viewed").child(clintnumber);
                dataviewed.removeValue();

                //Database database1=new Database(clbal);
                dbcb = FirebaseDatabase.getInstance().getReference("Database/" + idToSend + "/CB/" + clintnumber+"/cb");
                dbcb.setValue(clbal);

                dbcb1 = FirebaseDatabase.getInstance().getReference("Database/" + idToSend + "/TotBill/" + clintnumber+"/totbill");
                dbcb1.setValue("" + tot);

                dbcb2 = FirebaseDatabase.getInstance().getReference("Database/" + idToSend + "/EleUnit/" + clintnumber+"/eleunit");
                dbcb2.setValue("" + iunit);

                dbcb3 = FirebaseDatabase.getInstance().getReference("Database/" + idToSend + "/EleAmt/" + clintnumber+"/eleamt");
                dbcb3.setValue("" + echarge);



                Intent intent1=new Intent(Viewhistoryfinal.this,MainActivity3.class);

                intent1.putExtra("roomno",temp);
                intent1.putExtra("news",n);
                intent1.putExtra("olds",o);
                intent1.putExtra("rate",rate);
                intent1.putExtra("rent",rent);
                intent1.putExtra("unpaid",unpaid);
                startActivity(intent1);
            }
        });


    }





    @Override
    protected void onStart() {

        FirebaseDatabase.getInstance().getReference().keepSynced(true);


        if (mInterstitialAd != null) {
            mInterstitialAd.show(Viewhistoryfinal.this);

            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    {

                    }
                }
            });
        }

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {




                dataListlist.clear();
                count = (int) snapshot.getChildrenCount();
                if(count==0){
                    AlertDialog.Builder altd = new AlertDialog.Builder(Viewhistoryfinal.this);
                    altd.setTitle("Alert!");
                    altd.setMessage("No Bill Yet, Generate Your First Bill, Then View Bill ");
                    altd.setCancelable(true);
                    altd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog d = altd.create();
                    d.show();
                }

                for(DataSnapshot studentsnapshot : snapshot.getChildren()) {
                    Database database = studentsnapshot.getValue(Database.class);
                    dataListlist.add(database);



                    if(dataListlist.size()>8){
                        dataListlist.remove(0);
                    }

                }



                Clintbilllist adapter=new Clintbilllist(Viewhistoryfinal.this, dataListlist);
                listView.setAdapter(adapter);
                listView.setSelection(8);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







        super.onStart();
    }
}
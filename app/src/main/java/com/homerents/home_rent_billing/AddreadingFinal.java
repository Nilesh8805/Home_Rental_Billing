package com.homerents.home_rent_billing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddreadingFinal extends AppCompatActivity {
    EditText reading,edit,oldreadingenter;
    List<Database> dataListlist;

    int abc,zero=0,w=0,we=0;

    Float rat,echarge,tot,up;
    String paid1,paid2;
    String idToSend,way;
    TextView tv,tv2,lastmonth;
    String edits,zz,currentDate;
    String newread,oldread,clintnumber,clintrate,clintrent,clintname,diff,unpaid,currenttotal,countstring;
    int count,n,o,rnt,d;
    DatabaseReference db,db2,db3,dbcb,dbcb1,dbcb2,dbcb3,dbcb4,dbcb5,dbcb6,dbcb7,dbcb8,dbcb9,dbcb10,dbcb11,dbcb111,dbcb222,dbcb333,dataviewed;
    Button submit;
    ImageButton ib;

    private InterstitialAd mInterstitialAd;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addreading_final);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        FirebaseDatabase.getInstance().getReference().keepSynced(true);

        zero=0;

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });


        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-8585966944422416/1690519561", adRequest,
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
            mInterstitialAd.show(AddreadingFinal.this);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }








        ib=findViewById(R.id.imageButton);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder altd= new AlertDialog.Builder(AddreadingFinal.this);
                altd.setTitle("Need Help !");
                altd.setMessage("If and Only if you want to Edit your Previously Generated Bill, \n\nThen Enter 0987654321 in Passcode Section, \n\nThank You");
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
        });
        reading=findViewById(R.id.peditTextTextPersonName2);
        submit=findViewById(R.id.pbutton2);
        tv=findViewById(R.id.ptextView7);
        tv2=findViewById(R.id.ptextView8);
        lastmonth=findViewById(R.id.ptextViewlastmonth);
        edit=findViewById(R.id.peditTextTextPersonName3);
        oldreadingenter=findViewById(R.id.oldreeeding);

        dataListlist= new ArrayList<>();
        Intent intent=getIntent();
        String pin=intent.getStringExtra("pin");
        clintnumber=intent.getStringExtra("clintno");
        clintname=intent.getStringExtra("clintname");
        clintrate=intent.getStringExtra("clintrate");
        clintrent=intent.getStringExtra("clintrent");
        idToSend=intent.getStringExtra("idtosend");

        //Toast.makeText(this, ""+clintnumber+clintrate+clintrent+clintname, Toast.LENGTH_SHORT).show();
        tv.setText("Clint No. :- "+clintnumber);
        tv2.setText("Clint Name :- "+clintname);

        Calendar calendar = Calendar.getInstance();
        currentDate = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(calendar.getTime());
        db= FirebaseDatabase.getInstance().getReference("Database").child(idToSend).child("Datas").child(clintnumber);
        db2= FirebaseDatabase.getInstance().getReference("Database").child(idToSend).child("Datas").child(clintnumber);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dataviewed=FirebaseDatabase.getInstance().getReference("Database").child(idToSend).child("Viewed").child(clintnumber);
                dataviewed.setValue("1");

                if (isNetworkConnect()) {
                    edits = edit.getText().toString();
                    if (edits.equals("0987654321")) {


                        if (count == 1) {
                            Database database1 = dataListlist.get(count - 1);
                            oldread = database1.getOldreading();
                            unpaid = "0";
                        } else {
                            Database database = dataListlist.get(count - 2);
                            oldread = database.getNewreading();
                            unpaid = database.getGrandtotal();
                        }
                        newread = reading.getText().toString();
                        n = Integer.parseInt(newread);
                        o = Integer.parseInt(oldread);
                        rat = Float.parseFloat(clintrate);
                        rat= (float) (Math.round(rat*100.00)/100.00);

                        rnt = Integer.parseInt(clintrent);
                        up = Float.parseFloat(unpaid);
                        up= (float) (Math.round(up*100.00)/100.00);
                        d = n - o;
                        diff = String.valueOf(d);
                        echarge = d * rat;
                        echarge= (float) (Math.round(echarge*100.00)/100.00);
                        tot = echarge + rnt + up;
                        tot= (float) (Math.round(tot*100.00)/100.00);

                        currenttotal = String.valueOf(tot);
                        paid1 = "0";
                        paid2 = "0";




                        if (!TextUtils.isEmpty(newread) && n > o) {


                            {
/*
                                dbcb = FirebaseDatabase.getInstance().getReference("Database/" + idToSend + "/CB/" + clintnumber+"/cb");
                                dbcb.setValue("" + currenttotal);

                                dbcb1 = FirebaseDatabase.getInstance().getReference("Database/" + idToSend + "/TotBill/" + clintnumber+"/totbill");
                                dbcb1.setValue("" + currenttotal);

                                dbcb2 = FirebaseDatabase.getInstance().getReference("Database/" + idToSend + "/EleUnit/" + clintnumber+"/eleunit");
                                dbcb2.setValue("" + diff);

                                dbcb3 = FirebaseDatabase.getInstance().getReference("Database/" + idToSend + "/EleAmt/" + clintnumber+"/eleamt");
                                dbcb3.setValue("" + echarge);


 */


                                w=12345;


                            }

                            if(w==12345) {


                                Database database = new Database(currenttotal, newread, oldread, paid1, paid2, clintrate, clintrent, unpaid, diff, currentDate);
                                zz = "/" + (count + 1);

                                way = "" + idToSend + "/Datas/" + clintnumber + zz;
                                db3 = FirebaseDatabase.getInstance().getReference("Database").child(way);
                                db3.setValue(database);

                                Toast.makeText(AddreadingFinal.this, "This month reading has been Edited\n\n\nLast Unpaid Rs" + unpaid + "\nBill Amount Rs." + currenttotal, Toast.LENGTH_LONG).show();
                                Toast.makeText(AddreadingFinal.this, "This month reading has been Edited\n\n\nLast Unpaid Rs" + unpaid + "\nBill Amount Rs." + currenttotal, Toast.LENGTH_SHORT).show();
                                submit.setEnabled(false);
                            }else {
                                Toast.makeText(AddreadingFinal.this, "Unsuccessful", Toast.LENGTH_SHORT).show();
                            }
                            AddreadingFinal.this.getParent().finish();


                        } else {
                            Toast.makeText(AddreadingFinal.this, "Enter Valid Reading, Should not be less than your last Reading", Toast.LENGTH_LONG).show();
                        }
                    }
                    if (edits.equals(pin)) {
                        if (count == 0) {
                            oldread = oldreadingenter.getText().toString();
                            unpaid = "0";
                            newread = reading.getText().toString();
                            n = Integer.parseInt(newread);
                            o = Integer.parseInt(oldread);
                            rat = Float.parseFloat(clintrate);
                            rat= (float) (Math.round(rat*100.00)/100.00);

                            rnt = Integer.parseInt(clintrent);
                            up = Float.parseFloat(unpaid);
                            up= (float) (Math.round(up*100.00)/100.00);
                            d = n - o;
                            diff = String.valueOf(d);
                            echarge = d * rat;
                            echarge= (float) (Math.round(echarge*100.00)/100.00);
                            tot = echarge + rnt + up;
                            tot= (float) (Math.round(tot*100.00)/100.00);
                            currenttotal = String.valueOf(tot);
                            paid1 = "0";
                            paid2 = "0";




                            if (!TextUtils.isEmpty(newread) && n > o) {


                                {
/*
                                    dbcb4 = FirebaseDatabase.getInstance().getReference("Database/" + idToSend + "/CB/" + clintnumber+"/cb");
                                    dbcb4.setValue("" + currenttotal);

                                    dbcb5 = FirebaseDatabase.getInstance().getReference("Database/" + idToSend + "/TotBill/" + clintnumber+"/totbill");
                                    dbcb5.setValue("" + currenttotal);

                                    dbcb6 = FirebaseDatabase.getInstance().getReference("Database/" + idToSend + "/EleUnit/" + clintnumber+"/eleunit");
                                    dbcb6.setValue("" + diff);

                                    dbcb7 = FirebaseDatabase.getInstance().getReference("Database/" + idToSend + "/EleAmt/" + clintnumber+"/eleamt");
                                    dbcb7.setValue("" + echarge);



 */

                                    we=12;
                                    w=123;
                                }

                                if(w==123 && we==12) {


                                    Database database1 = new Database(currenttotal, newread, oldread, paid1, paid2, clintrate, clintrent, unpaid, diff, currentDate);

                                    zz = "/" + (count + 1);
                                    way = "" + idToSend + "/Datas/" + clintnumber + zz;
                                    db3 = FirebaseDatabase.getInstance().getReference("Database").child(way);
                                    db3.setValue(database1);
                                    Toast.makeText(AddreadingFinal.this, "This month reading has been Added\n\n\nLast Unpaid Rs" + unpaid + "\nBill Amount Rs." + currenttotal, Toast.LENGTH_LONG).show();
                                    Toast.makeText(AddreadingFinal.this, "This month reading has been Added\n\n\nLast Unpaid Rs" + unpaid + "\nBill Amount Rs." + currenttotal, Toast.LENGTH_SHORT).show();
                                    submit.setEnabled(false);

                                }else{
                                    Toast.makeText(AddreadingFinal.this, "Unsuccessful", Toast.LENGTH_SHORT).show();
                                }


                                AddreadingFinal.this.getParent().finish();
                            }else {
                                Toast.makeText(AddreadingFinal.this, "Enter valid Data", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Database database = dataListlist.get(count - 1);
                            oldread = oldreadingenter.getText().toString();
                            //oldreadingenter.setText(oldread);
                            unpaid = database.getGrandtotal();
                            newread = reading.getText().toString();
                            n = Integer.parseInt(newread);
                            o = Integer.parseInt(oldread);
                            rat = Float.parseFloat(clintrate);
                            rat= (float) (Math.round(rat*100.00)/100.00);

                            rnt = Integer.parseInt(clintrent);
                            up = Float.parseFloat(unpaid);
                            up= (float) (Math.round(up*100.00)/100.00);
                            d = n - o;
                            diff = String.valueOf(d);
                            echarge = d * rat;
                            echarge= (float) (Math.round(echarge*100.00)/100.00);
                            tot = echarge + rnt + up;
                            tot= (float) (Math.round(tot*100.00)/100.00);
                            currenttotal = String.valueOf(tot);
                            paid1 = "0";
                            paid2 = "0";





                            if (!TextUtils.isEmpty(newread) && n > o) {


                                {
/*
                                    dbcb8 = FirebaseDatabase.getInstance().getReference("Database/" + idToSend + "/CB/" + clintnumber+"/cb");
                                    dbcb8.setValue("" + currenttotal);

                                    dbcb9 = FirebaseDatabase.getInstance().getReference("Database/" + idToSend + "/TotBill/" + clintnumber+"/totbill");
                                    dbcb9.setValue("" + currenttotal);

                                    dbcb10 = FirebaseDatabase.getInstance().getReference("Database/" + idToSend + "/EleUnit/" + clintnumber+"/eleunit");
                                    dbcb10.setValue("" + diff);

                                    dbcb11 = FirebaseDatabase.getInstance().getReference("Database/" + idToSend + "/EleAmt/" + clintnumber+"/eleamt");
                                    dbcb11.setValue("" + echarge);


 */


                                    w=1234;
                                }

                                if(w==1234) {

                                    Database database2 = new Database(currenttotal, newread, oldread, paid1, paid2, clintrate, clintrent, unpaid, diff, currentDate);
                                    zz = "/" + (count + 1);

                                    way = "Database/" + idToSend + "/Datas/" + clintnumber + zz;
                                    db3 = FirebaseDatabase.getInstance().getReference(way);

                                    db3.setValue(database2);
                                    Toast.makeText(AddreadingFinal.this, "This month reading has been Added\n\n\nLast Unpaid Rs" + unpaid + "\nBill Amount Rs." + currenttotal, Toast.LENGTH_LONG).show();
                                    Toast.makeText(AddreadingFinal.this, "This month reading has been Added\n\n\nLast Unpaid Rs" + unpaid + "\nBill Amount Rs." + currenttotal, Toast.LENGTH_SHORT).show();
                                    submit.setEnabled(false);


                                }else{
                                    Toast.makeText(AddreadingFinal.this, "Unsuccessful", Toast.LENGTH_SHORT).show();
                                }


                                AddreadingFinal.this.getParent().finish();

                                abc = 1;

                            } else {
                                Toast.makeText(AddreadingFinal.this, "Enter Valid Reading, Should not be less than your last Reading", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                } else {
                    AlertDialog.Builder altd= new AlertDialog.Builder(AddreadingFinal.this);
                    altd.setTitle("No Network !");
                    altd.setMessage("Make sure you are in network place ");
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

            }

        });

    }


    private boolean isNetworkConnect(){
        ConnectivityManager cm =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return  cm.getActiveNetworkInfo() !=null;
    }


    @Override
    protected void onStart() {

        FirebaseDatabase.getInstance().getReference().keepSynced(true);

            db2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    dataListlist.clear();
                    for (DataSnapshot studentsnapshot : snapshot.getChildren()) {
                        Database database = studentsnapshot.getValue(Database.class);
                        dataListlist.add(database);
                        //oldread=database.getNewreading();
                        //unpaid=database.getGrandtotal();
                        //Toast.makeText(AddreadingFinal.this, ""+oldread+" unpaid "+unpaid, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    count = (int) snapshot.getChildrenCount();
                    countstring = String.valueOf(count);
                    Toast.makeText(AddreadingFinal.this, "" + count, Toast.LENGTH_SHORT).show();

                    if (count == 0) {
                        lastmonth.setText("Enter Last Month Reading Here");
                    } else {
                        Database database = dataListlist.get(count - 1);
                        oldread = database.getNewreading();
                        oldreadingenter.setText(oldread);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            super.onStart();






        if (mInterstitialAd != null) {
            mInterstitialAd.show(AddreadingFinal.this);

            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    {

                    }
                }
            });
        }

    }
}
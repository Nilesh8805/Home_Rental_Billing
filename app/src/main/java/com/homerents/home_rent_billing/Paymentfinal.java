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

public class Paymentfinal extends AppCompatActivity {
    EditText reading,edit;
    List<Database> dataListlist;

    Float rat,echarge,tot,pay1chck,pay2chck,up,pay1,pay2,temptotint,pay3;
    int w=0;
    String idToSend,zz;
    TextView tv,tv2,tvbill,tvamout,tvamout2;
    String edits,bill,paid1,paid2,pay1check,pay2check,enteredvalue,p1,p2;
    String newread,oldread,clintnumber,clintrate,clintrent,clintname,diff,unpaid,currenttotal,countstring,temptotstr;
    int count,count2,n,o,rnt,d;
    DatabaseReference db,db2,dbcb,dbcb2,dbcb3,dbcb4,dbcb5,dbcb6;
    Button submit;
    ImageButton ib;

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentfinal);
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

        InterstitialAd.load(this,"ca-app-pub-8585966944422416/5047310647", adRequest1,
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
            mInterstitialAd.show(Paymentfinal.this);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }












        ib=findViewById(R.id.imageButton2);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder altd= new AlertDialog.Builder(Paymentfinal.this);
                altd.setTitle("Need Help !");
                altd.setMessage("If and Only if you want to Edit your Previous Entered Payment, \n\nTo Edit Payment 1 \nThan Enter 1111154321 \n\nAND To Edit Payment 2 \nThan Enter 2222254321 in Passcode Section, \n\nThank You");
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
        tvbill=findViewById(R.id.ptextView5);
        tvamout=findViewById(R.id.ptextView);
        tvamout2=findViewById(R.id.ptextView2);
        edit=findViewById(R.id.peditTextTextPersonName3);

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
        String currentDate = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(calendar.getTime());
        db= FirebaseDatabase.getInstance().getReference("Database").child(idToSend).child("Datas").child(clintnumber);
        db2= FirebaseDatabase.getInstance().getReference("Database").child(idToSend).child("Datas").child(clintnumber);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isNetworkConnect()) {

                    edits=edit.getText().toString();
                    if (edits.equals("1111154321")) {
                        Database database = dataListlist.get(count - 1);
                        pay1check=reading.getText().toString();
                        pay2check=database.getPaid2();
                        pay1chck= Float.parseFloat(pay1check);
                        pay1chck= (float) (Math.round(pay1chck*100.00)/100.00);
                        pay2chck= Float.parseFloat(pay2check);
                        pay2chck= (float) (Math.round(pay2chck*100.00)/100.00);

                        oldread = database.getOldreading();
                        unpaid = database.getUnpaid();
                        newread = database.getNewreading();
                        temptotstr=database.getGrandtotal();
                        temptotint= Float.parseFloat(temptotstr);
                        temptotint= (float) (Math.round(temptotint*100.00)/100.00);

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

                        tot = echarge + rnt + up - pay1chck -pay2chck;
                        tot= (float) (Math.round(tot*100.00)/100.00);
                        currenttotal = String.valueOf(tot);



                        if (!TextUtils.isEmpty(pay1check) ) {


                            {
                                dbcb = FirebaseDatabase.getInstance().getReference("Database/" + idToSend + "/CB/" + clintnumber);
                                dbcb.child("cb").setValue("" + currenttotal);


                                w=12345;
                            }


                            if(w==12345) {
                                Database database1 = new Database(currenttotal, newread, oldread, pay1check, pay2check, clintrate, clintrent, unpaid, diff, currentDate);
                                zz = "" + (count);
                                db.child(zz).setValue(database1);
                                Toast.makeText(Paymentfinal.this, "Payment has been Edited\n\n\nPaid Rs" + (pay1chck + pay2chck) + "\n\nRemaining Bill Amount Rs." + currenttotal, Toast.LENGTH_LONG).show();
                                Toast.makeText(Paymentfinal.this, "Payment has been Edited\n\n\nPaid Rs" + (pay1chck + pay2chck) + "\n\nRemaining Bill Amount Rs." + currenttotal, Toast.LENGTH_SHORT).show();
                            }


                            submit.setEnabled(false);
                            Paymentfinal.this.getParent().finish();



                        }
                        else
                        {
                            Toast.makeText(Paymentfinal.this, "Enter valid Amout", Toast.LENGTH_LONG).show();
                        }
                    }
                    if (edits.equals("2222254321")) {
                        Database database = dataListlist.get(count - 1);
                        pay2check=reading.getText().toString();
                        pay1check=database.getPaid1();
                        pay1chck= Float.parseFloat(pay1check);
                        pay1chck= (float) (Math.round(pay1chck*100.00)/100.00);
                        pay2chck= Float.parseFloat(pay2check);
                        pay2chck= (float) (Math.round(pay2chck*100.00)/100.00);

                        oldread = database.getOldreading();
                        unpaid = database.getUnpaid();
                        newread = database.getNewreading();
                        temptotstr=database.getGrandtotal();
                        temptotint= Float.parseFloat(temptotstr);
                        temptotint= (float) (Math.round(temptotint*100.00)/100.00);


                        enteredvalue = reading.getText().toString();
                        pay3 = Float.parseFloat(enteredvalue);
                        pay3= (float) (Math.round(pay3*100.00)/100.00);


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

                        tot = echarge + rnt + up - pay1chck -pay2chck;
                        tot= (float) (Math.round(tot*100.00)/100.00);
                        currenttotal = String.valueOf(tot);



                        if (!TextUtils.isEmpty(pay2check) ) {


                            {
                                dbcb3 = FirebaseDatabase.getInstance().getReference("Database/" + idToSend + "/CB/" + clintnumber);
                                dbcb3.child("cb").setValue("" + currenttotal);


                                w=1234;
                            }


                            if(w==1234) {
                                Database database1 = new Database(currenttotal, newread, oldread, pay1check, pay2check, clintrate, clintrent, unpaid, diff, currentDate);
                                zz = "" + (count);
                                db.child(zz).setValue(database1);
                                Toast.makeText(Paymentfinal.this, "Payment has been Edited\nPaid Rs" + (pay1chck + pay2chck) + "\nRemaining Bill Amount Rs." + currenttotal, Toast.LENGTH_LONG).show();
                                Toast.makeText(Paymentfinal.this, "Payment has been Edited\nPaid Rs" + (pay1chck + pay2chck) + "\nRemaining Bill Amount Rs." + currenttotal, Toast.LENGTH_SHORT).show();
                            }


                            submit.setEnabled(false);
                            Paymentfinal.this.getParent().finish();




                        }
                        else
                        {
                            Toast.makeText(Paymentfinal.this, "Enter valid Amout", Toast.LENGTH_LONG).show();
                        }
                    }
                    if (edits.equals(pin)){
                        Database database = dataListlist.get(count - 1);
                        pay1check=database.getPaid1();
                        pay1chck= Float.parseFloat(pay1check);
                        pay1chck= (float) (Math.round(pay1chck*100.00)/100.00);
                        oldread = database.getOldreading();
                        unpaid = database.getUnpaid();
                        newread = database.getNewreading();
                        temptotstr=database.getGrandtotal();
                        temptotint= Float.parseFloat(temptotstr);
                        temptotint= (float) (Math.round(temptotint*100.00)/100.00);

                        enteredvalue = reading.getText().toString();
                        pay3 = Float.parseFloat(enteredvalue);
                        pay3= (float) (Math.round(pay3*100.00)/100.00);

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

                        if(pay1chck>0) {
                            paid1=pay1check;
                            paid2 = reading.getText().toString();
                            pay1 = Float.valueOf(paid1);
                            pay1= (float) (Math.round(pay1*100.00)/100.00);
                            pay2 = Float.valueOf(paid2);
                            pay2= (float) (Math.round(pay2*100.00)/100.00);
                        }
                        else{
                            paid1 = reading.getText().toString();
                            pay1 = Float.valueOf(paid1);
                            pay1= (float) (Math.round(pay1*100.00)/100.00);
                            paid2 ="0";
                            pay2 = Float.valueOf(paid2);
                            pay2= (float) (Math.round(pay2*100.00)/100.00);
                        }
                        tot = echarge + rnt + up - pay1 -pay2;
                        tot= (float) (Math.round(tot*100.00)/100.00);
                        currenttotal = String.valueOf(tot);





                        if (!TextUtils.isEmpty(paid1) && pay3<=temptotint ) {


                            {
                                dbcb5 = FirebaseDatabase.getInstance().getReference("Database/" + idToSend + "/CB/" + clintnumber);
                                dbcb5.child("cb").setValue("" + currenttotal);



                                w=123;
                            }


                            if(w==123) {
                                Database database1 = new Database(currenttotal, newread, oldread, paid1, paid2, clintrate, clintrent, unpaid, diff, currentDate);
                                zz = "" + (count);
                                db.child(zz).setValue(database1);
                                Toast.makeText(Paymentfinal.this, "Payment has been Added\n\n\n\nPaid Rs" + paid1 + "\n\nRemaining Bill Amount Rs." + currenttotal, Toast.LENGTH_LONG).show();
                                Toast.makeText(Paymentfinal.this, "Payment has been Added\n\n\n\nPaid Rs" + paid1 + "\n\nRemaining Bill Amount Rs." + currenttotal, Toast.LENGTH_SHORT).show();
                            }

                            submit.setEnabled(false);
                            Paymentfinal.this.getParent().finish();



                        }
                        else
                        {
                            Toast.makeText(Paymentfinal.this, "Enter valid Amout", Toast.LENGTH_LONG).show();
                        }

                    }
                //
                }
                else {
                    AlertDialog.Builder altd= new AlertDialog.Builder(Paymentfinal.this);
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

        if (mInterstitialAd != null) {
            mInterstitialAd.show(Paymentfinal.this);

            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    {

                    }
                }
            });
        }


        db2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                count2= (int) snapshot.getChildrenCount();
                if(count2==0){
                    AlertDialog.Builder altd = new AlertDialog.Builder(Paymentfinal.this);
                    altd.setTitle("Alert!");
                    altd.setMessage("No Bill Yet, Generate Your First Bill, Then make Payment ");
                    altd.setCancelable(true);
                    altd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog d = altd.create();
                    d.show();
                    submit.setEnabled(false);
                }

                dataListlist.clear();
                for(DataSnapshot studentsnapshot : snapshot.getChildren()) {
                    Database database = studentsnapshot.getValue(Database.class);
                    bill=database.getGrandtotal();
                    p1=database.getPaid1();
                    p2=database.getPaid2();
                    float pf1= Float.parseFloat(p1);
                    float pf2= Float.parseFloat(p2);

                    pf1= (float) (Math.round(pf1*100.00)/100.00);
                    pf2= (float) (Math.round(pf2*100.00)/100.00);
                    dataListlist.add(database);
                    float b= Float.parseFloat(bill);
                    b= (float) (Math.round(b*100.00)/100.00);

                    tvbill.setText("Your Bill Amount :- Rs"+b);
                    reading.setText(""+b);
                    tvamout.setText("Payment 1. Rs."+pf1);
                    tvamout2.setText("Payment 2. Rs."+pf2);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                count= (int) snapshot.getChildrenCount();
                countstring= String.valueOf(count);
                //Toast.makeText(AddreadingFinal.this, ""+countstring, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        super.onStart();
    }
}
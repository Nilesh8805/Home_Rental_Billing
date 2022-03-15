package com.homerents.home_rent_billing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    Button b,n,read,add,viewbill,stats;
    DatabaseReference db1,db2,db3,db001;
    int i,rt,r,c,c2,count,ectot,ectotal=0,ce,count001,scount001,igt,finaligt=0,ieleunit=0,ielecharge=0,feleunit=0,felecharge=0,icountuser;
    String gt,ec,rtt,scount,clintnumber,clintdata,sgt,seleunit,selecharge,countuser;
    int i2,count3,rt2,ectot2,ectotal2;
    String gt2,ec2,rtt2;
    String idToSend,name,pin,tempp;
    List<Database> clintList,clintList2,billList001;
    TextView tv,tv2,tv3,tvname;

    Float addtot,temptot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main2);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        FirebaseDatabase.getInstance().getReference().keepSynced(true);


        stats=findViewById(R.id.button5);
        tv3=findViewById(R.id.textView28);
        tv2=findViewById(R.id.textView20);
        tv=findViewById(R.id.textView10);
        n = findViewById(R.id.addnewentry);
        read = findViewById(R.id.addread);
        add = findViewById(R.id.addpayment);
        viewbill = findViewById(R.id.viewbill);
        tvname=findViewById(R.id.tvname);

        clintList= new ArrayList<>();
        clintList2= new ArrayList<>();
        billList001= new ArrayList<>();



        Intent intent=getIntent();

        clintnumber=intent.getStringExtra("clintno");
        clintdata=intent.getStringExtra("clintdata");
        name=intent.getStringExtra("name");
        idToSend=intent.getStringExtra("idtosend");
        pin=intent.getStringExtra("pin");

        tvname.setText("Owner's Name :- "+name);

        c= Integer.parseInt(clintnumber);
        c2= Integer.parseInt(clintdata);




        n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity2.this,Addclint.class);
                i.putExtra("clintno",clintnumber);
                i.putExtra("pin",pin);
                i.putExtra("idtosend",idToSend);
                startActivity(i);
            }
        });

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(MainActivity2.this,AddMonthReadingData.class);
                i1.putExtra("idtosend",idToSend);
                i1.putExtra("pin",pin);
                startActivity(i1);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2=new Intent(MainActivity2.this,Addpaymentlist.class);
                i2.putExtra("idtosend",idToSend);
                i2.putExtra("pin",pin);
                startActivity(i2);
            }
        });

        viewbill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tempp=tv.getText().toString();
                Intent i3=new Intent(MainActivity2.this,Viewhistory.class);
                i3.putExtra("idtosend",idToSend);
                i3.putExtra("cb",tempp);
                Toast.makeText(MainActivity2.this, ""+tempp, Toast.LENGTH_SHORT).show();
                i3.putExtra("pin",pin);
                startActivity(i3);
            }
        });


















        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                {
                    Intent i4 = new Intent(MainActivity2.this, Stats.class);
                    i4.putExtra("idtosend", idToSend);
                    startActivity(i4);

                }

            }
        });
    }

    @Override
    protected void onStart() {

        FirebaseDatabase.getInstance().getReference().keepSynced(true);

        addtot= Float.valueOf(0);

                db1 = FirebaseDatabase.getInstance().getReference("Database/"+idToSend+"/CB");
                db2 = FirebaseDatabase.getInstance().getReference("Database/"+idToSend+"/Datas");

                db2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        icountuser = (int) snapshot.getChildrenCount();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

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
                                temptot= (float) (Math.round(temptot*100.00)/100.00);

                                addtot = addtot + temptot;
                                addtot= (float) (Math.round(addtot*100.00)/100.00);

                                tv.setText("Rs." + addtot + " to be Recived");
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });











        super.onStart();
    }
}
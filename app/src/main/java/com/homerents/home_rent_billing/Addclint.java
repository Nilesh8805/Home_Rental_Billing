package com.homerents.home_rent_billing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Addclint extends AppCompatActivity {
    DatabaseReference db, db2, db3, db4, db5,dbdb;
    ListView lv;
    List<Database> clintListlist;
    EditText ename, erate, erent, epass, no;
    int count, i, c;

    String clintnumber,idToSend;
    Button b1;
    ImageButton ib;

    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addclint);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        FirebaseDatabase.getInstance().getReference().keepSynced(true);


        pb=findViewById(R.id.progressBaradd);
        pb.setVisibility(View.VISIBLE);
        ib=findViewById(R.id.imageButton3);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder altd= new AlertDialog.Builder(Addclint.this);
                altd.setTitle("Need Help !");
                altd.setMessage("If you want to Edit Clints Name / Rent Amount / Electric Rate, \n\nThen see Clint No. in List \nAnd Enter the same id and ReEnter all Information with Changes, \n\nAnd Enter Your Passcode, \nThank You");
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





        ename = findViewById(R.id.editTextTextPersonName);
        erate = findViewById(R.id.editTextTextPersonName4);
        erent = findViewById(R.id.editTextTextPersonName5);
        epass = findViewById(R.id.editTextTextPassword);
        no = findViewById(R.id.editTextTextPersonName7);
        b1 = findViewById(R.id.button3);
        lv = findViewById(R.id.clist);
        clintListlist = new ArrayList<>();

        Intent intent=getIntent();
        idToSend=intent.getStringExtra("idtosend");
        String pin=intent.getStringExtra("pin");

        dbdb=FirebaseDatabase.getInstance().getReference("Database").child(idToSend).child("Clints");
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = epass.getText().toString();
                if (pass.equals(pin)) {
                    addClint();
                    ename.setText("");
                    epass.setText("");
                    erate.setText("");
                    erent.setText("");
                } else {
                    if (pass.equals("0987654321")) {

                    }else {
                        Toast.makeText(Addclint.this, "Wrong Passcode", Toast.LENGTH_SHORT).show();
                    }
                }
                if (pass.equals("0987654321")) {
                    editClint();
                    ename.setText("");
                    epass.setText("");
                    erate.setText("");
                    erent.setText("");
                }
            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Database database=clintListlist.get(i);
                String showname=database.getName();
                String showrate=database.getClintrate();
                String showrent=database.getClintrent();
                String showno=database.getClintno();

                AlertDialog.Builder altd = new AlertDialog.Builder(Addclint.this);
                altd.setTitle("Clint No = "+showno);
                altd.setMessage("\n\n\nClint Name = "+showname+"\n\nClint Electricity Rate = "+showrate+" Rs/Unit\n\nClint Room Rent = "+showrent+" Rs/Month");
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
        });

    }

    private void editClint() {
        String num = no.getText().toString();
        String name = ename.getText().toString();
        String rate = erate.getText().toString();
        String rent = erent.getText().toString();
        float intrate= Float.parseFloat(rate);
        int intrent= Integer.parseInt(rent);

        if(3>intrate || intrate>25 ){
            Toast.makeText(this, "Please Enter Electricity rate Between(5 to 20)", Toast.LENGTH_SHORT).show();
        }
        else {
            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(num) && !TextUtils.isEmpty(rate) && !TextUtils.isEmpty(rent)) {
                db2 = FirebaseDatabase.getInstance().getReference("Database").child(idToSend).child("Clints").child(num).child("clintno");
                db2.setValue(num);
                db3 = FirebaseDatabase.getInstance().getReference("Database").child(idToSend).child("Clints").child(num).child("clintrent");
                db3.setValue(rent);
                db4 = FirebaseDatabase.getInstance().getReference("Database").child(idToSend).child("Clints").child(num).child("clintrate");
                db4.setValue(rate);
                db5 = FirebaseDatabase.getInstance().getReference("Database").child(idToSend).child("Clints").child(num).child("clintname");
                db5.setValue(name);

                Toast.makeText(this, "Clint " + num + ". " + name + " has been Edited", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void addClint() {
        String mobno;
        String num = no.getText().toString();
        String name = ename.getText().toString();
        String rate = erate.getText().toString();
        String rent = erent.getText().toString();
        float intrate= Float.parseFloat(rate);
        int intrent= Integer.parseInt(rent);

        if(3>intrate || intrate>25){
            Toast.makeText(this, "Please Enter Electricity rate Between(5 to 20)", Toast.LENGTH_SHORT).show();
        }
        else {
            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(num) && !TextUtils.isEmpty(rate) && !TextUtils.isEmpty(rent)) {
                Database database = new Database(num, rate, rent, name);
                dbdb.child(num).setValue(database);
                Toast.makeText(this, "Clint " + num + ". " + name + " has been Registered", Toast.LENGTH_SHORT).show();


            }
        }
    }

    @Override
    protected void onStart() {
        FirebaseDatabase.getInstance().getReference().keepSynced(true);

        db = FirebaseDatabase.getInstance().getReference("Database").child(idToSend).child("Clints");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                count = (int) snapshot.getChildrenCount();
                Toast.makeText(Addclint.this, ""+count, Toast.LENGTH_SHORT).show();
                no.setText("" + (count + 1));

                if(count<1000){
                    pb.setVisibility(View.INVISIBLE);
                }

                if (count == 0) {
                    AlertDialog.Builder altd = new AlertDialog.Builder(Addclint.this);
                    altd.setTitle("Alert!");
                    altd.setMessage("No CLint Found ");
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
                clintListlist.clear();

                for (DataSnapshot studentsnapshot : snapshot.getChildren()) {
                    //DatabaseReference dj=studentsnapshot.getRef();
                    //dj.child("studentStatus").setValue("Click")
                    Database database = studentsnapshot.getValue(Database.class);
                    clintListlist.add(database);
                }

                ClintList adapter = new ClintList(Addclint.this, clintListlist);
                lv.setAdapter(adapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        super.onStart();
    }
}
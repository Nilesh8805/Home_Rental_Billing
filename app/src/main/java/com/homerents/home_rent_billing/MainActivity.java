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
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {
    Button b,bsignup,forgetbutton,clint;
    EditText eid,epin;
    List<Database> dataListlist;

    TextView tv;
    int i,j,k=0,countuser=0;
    int check=0;
    String count,count2,ii,testnamelower,inputclower,dot,test1,test2,id,no;
    String idToSend,testname,testpin,inputa,inputb,inputbGo ,inputc,inputd,name;
    DatabaseReference db,db2,dbcheck,dbload;
    ProgressBar pbr1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });



        if (isNetworkConnect()){
            Toast.makeText(this, "Good Network", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "No Network", Toast.LENGTH_SHORT).show();
        }




        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        pbr1=findViewById(R.id.progressBar2);
        pbr1.setVisibility(View.GONE);

        dataListlist= new ArrayList<>();

        clint=findViewById(R.id.button6);
        forgetbutton=findViewById(R.id.button4);
        eid=findViewById(R.id.userid);
        epin=findViewById(R.id.userpin);
        b=findViewById(R.id.button);
        bsignup=findViewById(R.id.button2);
        dbload=FirebaseDatabase.getInstance().getReference("Users");
        tv=findViewById(R.id.textView41);

        FirebaseDatabase.getInstance().getReference().keepSynced(true);




        clint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dot = tv.getText().toString();
                if (dot.equals(" ")) {

                    test1 = eid.getText().toString();
                    test2 = epin.getText().toString();

                    String a=test1.substring(0,4);
                    String b=test2.substring(0,2);

                    if (!TextUtils.isEmpty(test1) && !TextUtils.isEmpty(test2) && test1.length()>4 && test2.length()>2 && a.equals("1800") && b.equals("90")) {
                        id = test1.substring(4);
                        no = test2.substring(2);

                        name=" ";


                        Intent intent=new Intent(MainActivity.this,Viewhistoryfinal.class);
                        intent.putExtra("idtosend",id);
                        intent.putExtra("clintno",no);
                        intent.putExtra("clintname", name);
                        startActivity(intent);
                    }else {
                        Toast.makeText(MainActivity.this, "Enter Clint Id and Passcode ", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(MainActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                }




            }
        });







        forgetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder altd= new AlertDialog.Builder(MainActivity.this);
                altd.setTitle("Forget Pin !");
                altd.setMessage("You can Enter Your Birth Year in YYYY Formate as PIN, Thankyou.");
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


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dot = tv.getText().toString();
                if (dot.equals(" ")){
                        check = 0;
                    String test1 = eid.getText().toString();
                    String test2 = epin.getText().toString();


                    if (!TextUtils.isEmpty(test1) && !TextUtils.isEmpty(test2)) {
                        int test3 = Integer.parseInt(test2);
                        if (test3 < 10000 && test3 > 999) {
                            pbr1.setVisibility(View.VISIBLE);

                            for (int ii = 0; ii < countuser; ii++) {

                                try {
                                    testname = eid.getText().toString();
                                    testpin = epin.getText().toString();
                                    testnamelower = testname.toLowerCase();

                                    Database database = dataListlist.get(ii);
                                    inputa = database.getUsermobno();
                                    inputc = database.getUsername();
                                    inputclower = inputc.toLowerCase();
                                    inputb = database.getUserpin();
                                    inputd = database.getUserage();


                                    if ((testname.equals(inputa) || testnamelower.equals(inputclower)) && testpin.equals(inputb)) {
                                        name = database.getUsername();
                                        idToSend = database.getUserid();
                                        inputbGo = inputb;
                                        check = 1;
                                        Toast.makeText(MainActivity.this, "loged in with id= " + idToSend, Toast.LENGTH_SHORT).show();

                                    }
                                    if ((testname.equals(inputa) || testnamelower.equals(inputclower)) && testpin.equals(inputd)) {
                                        name = database.getUsername();
                                        idToSend = database.getUserid();
                                        inputbGo = inputd;
                                        check = 1;
                                        Toast.makeText(MainActivity.this, "loged in with id= " + idToSend, Toast.LENGTH_SHORT).show();

                                    }


                                } catch (Exception e) {
                                    if (check == 0) {
                                        Toast.makeText(MainActivity.this, "Please Enter Right Details", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(MainActivity.this, "If You Are New Here, Then Signup", Toast.LENGTH_LONG).show();
                                    }
                                }


                            }
                            if (check == 0) {
                                Toast.makeText(MainActivity.this, "Please Enter Right Details", Toast.LENGTH_SHORT).show();
                                Toast.makeText(MainActivity.this, "If You Are New Here, Then Signup", Toast.LENGTH_LONG).show();
                                pbr1.setVisibility(View.GONE);
                                eid.setText("");
                                epin.setText("");
                            }

                            if (check == 1) {
                                //b.setEnabled(false);
                                db = FirebaseDatabase.getInstance().getReference("Database").child(idToSend).child("Clints");
                                db.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        count = String.valueOf((int) snapshot.getChildrenCount());
                                        //b.setEnabled(true);
                                        //pbr1.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                                db2 = FirebaseDatabase.getInstance().getReference("Database").child(idToSend).child("Datas");
                                db2.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        count2 = String.valueOf((int) snapshot.getChildrenCount());
                                        //b.setEnabled(true);
                                        pbr1.setVisibility(View.GONE);


                                        {

                                            Intent i = new Intent(MainActivity.this, MainActivity2.class);
                                            i.putExtra("clintno", count);
                                            i.putExtra("clintdata", count2);

                                            //String scountuser= String.valueOf(countuser);
                                            //i.putExtra("usercount",scountuser);
                                            i.putExtra("idtosend", idToSend);
                                            i.putExtra("pin", inputbGo);
                                            i.putExtra("name", name);
                                            startActivity(i);


                                        }


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }

                        } else {
                            Toast.makeText(MainActivity.this, "Pin sholud Only 4 Digits", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Please Enter valid above Fields ", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    tv.setText("Network Issue\nKindly check your Internet");
                }
            }
        });


        bsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii= new Intent(MainActivity.this, New_SignUp.class);
                startActivity(ii);

            }
        });
    }



    private boolean isNetworkConnect(){
        ConnectivityManager cm =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return  cm.getActiveNetworkInfo() !=null;
    }





    @Override
    protected void onStart() {
        super.onStart();
        epin.setText("");
        eid.setText("");

        FirebaseDatabase.getInstance().getReference().keepSynced(true);


        dbload.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                countuser = (int) snapshot.getChildrenCount();


                if(countuser>0){
                    pbr1.setVisibility(View.GONE);
                    tv.setText(" ");
                }

                dataListlist.clear();
                for(DataSnapshot studentsnapshot : snapshot.getChildren()) {
                    Database database = studentsnapshot.getValue(Database.class);
                    dataListlist.add(database);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
package com.homerents.home_rent_billing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class New_SignUp extends AppCompatActivity {
    DatabaseReference dbdb,dbload;
    EditText ename,emob,eyear,epin,epinpin;
    TextView idid;
    Button reg;
    int countuser,qq=0,count;
    String inputa;
    List<Database> dataListlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__sign_up);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        FirebaseDatabase.getInstance().getReference().keepSynced(true);



        dbload=FirebaseDatabase.getInstance().getReference("Users");
        dataListlist= new ArrayList<>();

        idid=findViewById(R.id.ididid);
        ename =findViewById(R.id.name);
        emob =findViewById(R.id.mob);
        eyear =findViewById(R.id.year);
        epin =findViewById(R.id.pin);
        epinpin =findViewById(R.id.pinpin);

        reg=findViewById(R.id.reg);
        dbdb= FirebaseDatabase.getInstance().getReference("Users");
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String iii=idid.getText().toString();
                if(iii.equals("Loading")){
                    reg.setEnabled(false);
                    Toast.makeText(New_SignUp.this, "Network Error, Kindly Re-open App", Toast.LENGTH_SHORT).show();
                }
                else {
                    addUser();
                }
                idid.setText("");
                ename.setText("");
                emob.setText("");
                eyear.setText("");
                epin.setText("");
                epinpin.setText("");
                idid.setText(""+(count+1));

            }
        });
    }


    private void addUser() {

        String id=idid.getText().toString();
        String name = ename.getText().toString();
        String mob = emob.getText().toString();
        String year = eyear.getText().toString();
        String pin = epin.getText().toString();
        String pin2= epinpin.getText().toString();



            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(mob) && !TextUtils.isEmpty(year) && !TextUtils.isEmpty(pin) && !TextUtils.isEmpty(pin2)) {
                int testp1= Integer.parseInt(pin);
                int testp2= Integer.parseInt(pin2);


                for (int ii = 0; ii < countuser; ii++) {
                    try {

                        Database database = dataListlist.get(ii);
                        inputa = database.getUsermobno();

                        if (mob.equals(inputa)) {
                            qq = 1;
                            AlertDialog.Builder altd= new AlertDialog.Builder(New_SignUp.this);
                            altd.setTitle("Mobile No. Already Exist to other Account !");
                            altd.setMessage("If You Have Previous Account with this Mobile No. \n\nTry Entering {DOB} as YYYY Format in Pin Section\nThank You");
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
                    } catch (Exception e) {

                    }
                }


                if(qq==1){
                    Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (testp1 < 10000 && testp1 == testp2) {
                        Database database = new Database(id, name, mob, year, pin);
                        dbdb.child(id).setValue(database);
                        reg.setEnabled(false);

                        AlertDialog.Builder altd= new AlertDialog.Builder(New_SignUp.this);
                        altd.setTitle("Congratulation !");
                        altd.setMessage("Account Successfully Opened\nYour User Id = Your Mob no.\n\nThank You");
                        altd.setCancelable(true);
                        altd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        AlertDialog d=altd.create();
                        d.show();

                    } else {
                        Toast.makeText(this, "Pin And Conform Pin Not Matched", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(this, "Enter all Fields", Toast.LENGTH_SHORT).show();
            }

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseDatabase.getInstance().getReference().keepSynced(true);


        dbdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                count = (int) snapshot.getChildrenCount();
                idid.setText(""+(count+1));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        dbload.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                countuser = (int) snapshot.getChildrenCount();
                Toast.makeText(New_SignUp.this, ""+countuser, Toast.LENGTH_SHORT).show();


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
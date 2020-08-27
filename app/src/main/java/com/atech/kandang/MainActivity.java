package com.atech.kandang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    TextView suhu;
    TextView tekan;
    ImageView chicken_pic;
    Button buttoncall;
    DatabaseReference dref;
    String temp;
    String press;
    Integer value;
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        suhu = (TextView) findViewById(R.id.display_temp);
        tekan = (TextView) findViewById(R.id.display_press);
        buttoncall = (Button) findViewById(R.id.btn_emergency);
        chicken_pic = (ImageView) findViewById(R.id.chicken);

        dref= FirebaseDatabase.getInstance().getReference();
        dref.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                temp=dataSnapshot.child("BMP_085/suhu").getValue().toString();
                press = dataSnapshot.child("BMP_085/Tekanan").getValue().toString();
                phoneNumber = dataSnapshot.child("BMP_085/NOHP").getValue().toString();

                tekan.setText(press );
                suhu.setText((temp)+" \u2103");

                value = Integer.valueOf(temp);
                if (value > 35){
                    chicken_pic.setImageResource(R.drawable.chickenhot);
                }
                else
                {
                    chicken_pic.setImageResource(R.drawable.chicken);
                }

                buttoncall.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View arg0) {
                        String number = phoneNumber;
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:"+number));
                        startActivity(callIntent);
                    }

                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
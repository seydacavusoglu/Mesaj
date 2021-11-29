package com.example.mesaj;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MessageActivity extends AppCompatActivity {
    RadioButton rb1,rb2,rb3;
    Button btn;
    EditText multiLine;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        rb1 =  findViewById(R.id.rbFriends);
        rb2 = findViewById(R.id.rbSchool);
        rb3 =  findViewById(R.id.rbWorks);
        btn =  findViewById(R.id.button);
        multiLine =  findViewById(R.id.editTextTextMultiLine);
        rb1.setChecked(true);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!multiLine.getText().toString().trim().isEmpty()){

                    Database db = new Database(getApplicationContext());
                    String groupId = "1";
                    if(rb2.isChecked()){
                        groupId ="2";
                    }else if(rb3.isChecked()){
                        groupId= "3";
                    }
                    String numbers = db.numberGroup(groupId);
                    if(numbers != ""){
                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setData(Uri.parse("smsto:" + Uri.encode(numbers)));
                        intent.putExtra("sms_body", multiLine.getText().toString().trim());
                        startActivity(intent);
                    }else {
                        Toast.makeText(getApplicationContext(), "Grupda numara yok!", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Mesaj boş bırakılamaz", Toast.LENGTH_SHORT).show();
                }
            }
        });
        rb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    rb2.setChecked(false);
                    rb3.setChecked(false);
                }
            }
        });
        rb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    rb1.setChecked(false);
                    rb3.setChecked(false);
                }
            }
        });
        rb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    rb1.setChecked(false);
                    rb2.setChecked(false);
                }
            }
        });


    }
}

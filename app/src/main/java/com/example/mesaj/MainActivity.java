package com.example.mesaj;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    EditText etName, etSchoolNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etName = findViewById(R.id.editTextName);
        etSchoolNumber =  findViewById(R.id.editTextSchoolNumber);
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.READ_CONTACTS)
                        == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS)
                        == PackageManager.PERMISSION_GRANTED) {
                    if (!etName.getText().toString().isEmpty() && etSchoolNumber.getText().toString().length() == 12) {
                        Intent intent = new Intent(getApplicationContext(), PhoneActivity.class);
                        startActivity(intent);
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Yetkileri kabul ederek giriş yapınız!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etSchoolNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.READ_CONTACTS)
                        == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS)
                        == PackageManager.PERMISSION_GRANTED) {
                    if (!etName.getText().toString().isEmpty() && etSchoolNumber.getText().toString().length() == 12) {
                        Intent intent = new Intent(getApplicationContext(), PhoneActivity.class);
                        startActivity(intent);
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Yetkileri kabul ederek giriş yapınız!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED) {

        }else{
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_CONTACTS,Manifest.permission.SEND_SMS},
                    1);
        }
    }

}

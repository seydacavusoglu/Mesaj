package com.example.mesaj;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class PhoneActivity extends AppCompatActivity {
    RadioButton rbFriends, rbSchool, rbWorks;
    Button btn;
    ListView directoryList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        rbFriends = findViewById(R.id.rbFriends);
        rbSchool =  findViewById(R.id.rbSchool);
        rbWorks = findViewById(R.id.rbWorks);
        btn = findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
                startActivity(intent);
            }
        });
        rbFriends.setChecked(true);

        rbFriends.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    rbSchool.setChecked(false);
                    rbWorks.setChecked(false);
                }
            }
        });
        rbSchool.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    rbFriends.setChecked(false);
                    rbWorks.setChecked(false);
                }
            }
        });
        rbWorks.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    rbFriends.setChecked(false);
                    rbSchool.setChecked(false);
                }


            }
        });
        directoryList =findViewById(R.id.rehber_list);

        ArrayList<Users> users = new ArrayList<Users>();

        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,  null, null);
        while (cursor.moveToNext())
        {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            @SuppressLint("Range") String contactID=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));

            Users myUser = new Users();
            myUser.setName(name);
            myUser.setPhone(phone);
            myUser.setImage(ContactPhoto(contactID));
            users.add(myUser);
        }

        cursor.close();
        UserAdapter userAdapter = new UserAdapter(this, users);
        if (directoryList != null) {
            directoryList.setAdapter(userAdapter);
            directoryList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                public boolean onItemLongClick(AdapterView<?> arg0, View v, int index, long arg3) {

                    if(rbFriends.isChecked()){
                        Database db = new Database(getApplicationContext());
                        String number = users.get(index).getPhone();
                        Boolean isExist = db.hasNumber(number);
                        if(isExist){
                            db.deleteNumber(users.get(index).getPhone());
                        }
                        Boolean addNumber = db.addNumber(users.get(index).getPhone(), users.get(index).getName(),"1");
                        if(addNumber){
                            Toast.makeText(getApplicationContext(), "Arkadaşlar Grubuna Eklendi", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Eklemede hata!", Toast.LENGTH_SHORT).show();
                        }
                    }else if(rbSchool.isChecked()){
                        Database db = new Database(getApplicationContext());
                        String number = users.get(index).getPhone();
                        Boolean isExist = db.hasNumber(number);
                        if(isExist){
                            db.deleteNumber(users.get(index).getPhone());
                        }
                        Boolean addNumber = db.addNumber(users.get(index).getPhone(), users.get(index).getName(),"2");
                        if(addNumber){
                            Toast.makeText(getApplicationContext(), "Okul Grubuna Eklendi", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Eklemede hata!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Database db = new Database(getApplicationContext());
                        String number = users.get(index).getPhone();
                        Boolean varMi = db.hasNumber(number);
                        if(varMi){
                            db.deleteNumber(users.get(index).getPhone());
                        }
                        Boolean addNumber = db.addNumber(users.get(index).getPhone(), users.get(index).getName(),"3");
                        if(addNumber){
                            Toast.makeText(getApplicationContext(), "İş Grubuna Eklendi", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Eklemede hata!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    return false;
                }
            });

        }
    }





    public Bitmap ContactPhoto(String contactId) {
        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.valueOf(contactId));
        Uri photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
        Cursor cursor = getContentResolver().query(photoUri,
                new String[]{ContactsContract.Contacts.Photo.PHOTO}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToNext();
            byte[] data = cursor.getBlob(0);
            if (data != null)
                return BitmapFactory.decodeStream(new ByteArrayInputStream(data));
            else
                return null;
        }
        cursor.close();
        return null;
    }

}

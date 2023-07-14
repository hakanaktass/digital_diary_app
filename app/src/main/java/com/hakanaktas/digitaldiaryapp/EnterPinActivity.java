package com.hakanaktas.digitaldiaryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hakanaktas.digitaldiaryapp.databinding.ActivityEnterPinBinding;
import com.hakanaktas.digitaldiaryapp.databinding.ActivityPinBinding;

public class EnterPinActivity extends AppCompatActivity {
    private ActivityEnterPinBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEnterPinBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        SharedPreferences sharedPreferences=getSharedPreferences("storedPassword",MODE_PRIVATE);
        String password=sharedPreferences.getString("password",null);


         binding.enterButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if(binding.enterTextTextPassword.getText().toString().matches("")){
                     Toast.makeText(EnterPinActivity.this, "Please enter the password!", Toast.LENGTH_SHORT).show();
                 }else{
                     String enterpassword = binding.enterTextTextPassword.getText().toString();
                     if(enterpassword.equals(password)){
                         Intent intent = new Intent(EnterPinActivity.this,MainActivity.class);
                         startActivity(intent);
                     }else{
                         Toast.makeText(EnterPinActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                     }

                 }

             }
         });

    }

}
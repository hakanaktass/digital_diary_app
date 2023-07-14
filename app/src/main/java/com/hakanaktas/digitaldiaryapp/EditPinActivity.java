package com.hakanaktas.digitaldiaryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hakanaktas.digitaldiaryapp.databinding.ActivityEditPinBinding;
import com.hakanaktas.digitaldiaryapp.databinding.ActivityPinBinding;

public class EditPinActivity extends AppCompatActivity {
   private ActivityEditPinBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditPinBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.editTextTextPassword.getText().toString().matches("")){
                    Toast.makeText(EditPinActivity.this, "Please enter the password!", Toast.LENGTH_SHORT).show();
                }else{
                    SharedPreferences sharedPreferences=getSharedPreferences("storedPassword",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("password",binding.editTextTextPassword.getText().toString());
                    editor.apply();
                    Intent intent = new Intent(EditPinActivity.this,PinActivity.class);
                    startActivity(intent);
                }

            }
        });
    }
}
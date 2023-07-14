package com.hakanaktas.digitaldiaryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hakanaktas.digitaldiaryapp.databinding.ActivityCreateMemoryBinding;
import com.hakanaktas.digitaldiaryapp.databinding.ActivityPinBinding;

public class PinActivity extends AppCompatActivity {
    private ActivityPinBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPinBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.editPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PinActivity.this,EditPinActivity.class);
                startActivity(intent);
            }
        });


        binding.buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent= getIntent();
                //int password = intent.getIntExtra("storedPassword",0);

                Intent intent = new Intent(PinActivity.this,EnterPinActivity.class);
                //intent.putExtra("storedPassword",password);
                startActivity(intent);
            }
        });


    }

   /* private void editPin (View view){
        Intent intent = new Intent(PinActivity.this,EditPinActivity.class);
        startActivity(intent);

    private void login(View view){
        Intent intent=getIntent();
        Integer password = Integer.parseInt(intent.getStringExtra("storedPassword"));
        intent = new Intent(PinActivity.this,EnterPinActivity.class);
        intent.putExtra("storedPassword",password);
        startActivity(intent);
    }

    */
}
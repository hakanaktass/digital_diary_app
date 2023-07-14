package com.hakanaktas.digitaldiaryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.hakanaktas.digitaldiaryapp.databinding.ActivityCreateMemoryBinding;
import com.hakanaktas.digitaldiaryapp.databinding.ActivityMemoryDetailsBinding;

import java.net.URI;
import java.text.DateFormat;
import java.util.Calendar;

public class MemoryDetails extends AppCompatActivity {
    private ActivityMemoryDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMemoryDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setSupportActionBar(binding.tbmemorydetails);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Calendar calendar = Calendar.getInstance();
        String date = DateFormat.getDateInstance().format(calendar.getTime());
        binding.detailTextDate.setText(date);

        Intent data = getIntent();

        binding.editmemory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(view.getContext(),EditMemoryActivity.class);
            intent.putExtra("title",data.getStringExtra("title"));
            intent.putExtra("content",data.getStringExtra("content"));
            intent.putExtra("id",data.getStringExtra("id"));
            view.getContext().startActivity(intent);

            }
        });

        binding.memorydetailcontent.setText(data.getStringExtra("content"));
        binding.memorydetailstitle.setText(data.getStringExtra("title"));
        //binding.imageViewDetailContent.setImageURI(Uri.parse(data.getStringExtra("image")));




    }


    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
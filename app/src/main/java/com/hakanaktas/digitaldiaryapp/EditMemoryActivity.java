package com.hakanaktas.digitaldiaryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hakanaktas.digitaldiaryapp.databinding.ActivityCreateMemoryBinding;
import com.hakanaktas.digitaldiaryapp.databinding.ActivityEditMemoryBinding;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class EditMemoryActivity extends AppCompatActivity {

    private ActivityEditMemoryBinding binding;

    Intent data;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditMemoryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        data=getIntent();
        String id = data.getStringExtra("id");
        Calendar calendar = Calendar.getInstance();
        String date = DateFormat.getDateInstance().format(calendar.getTime());
        binding.editTextDate.setText(date);
        firebaseFirestore = FirebaseFirestore.getInstance();
        setSupportActionBar(binding.tbeditmemory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        binding.saveeditmemory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newtitle = binding.editmemorytitle.getText().toString();
                String newcontent = binding.memoryeditcontent.getText().toString();

                if (newtitle.isEmpty() || newcontent.isEmpty()){
                    Toast.makeText(EditMemoryActivity.this, "Title or Content is empty.", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    DocumentReference documentReference = firebaseFirestore.collection("memories").document(id);
                    HashMap<String,Object> hashMap = new HashMap<>();
                    hashMap.put("title",newtitle);
                    hashMap.put("content",newcontent);
                    documentReference.set(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(EditMemoryActivity.this, "The Memory is Updated.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(EditMemoryActivity.this,MainActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditMemoryActivity.this, "Failed to Update.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });


        String title = data.getStringExtra("title");
        String content = data.getStringExtra("content");
        binding.memoryeditcontent.setText(content);
        binding.editmemorytitle.setText(title);
    }


    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
package com.hakanaktas.digitaldiaryapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.hakanaktas.digitaldiaryapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    RecyclerView recyclerView;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    FirebaseFirestore firebaseFirestore;
    ArrayList<Memory> memoryArrayList;
    MemoryAdapter memoryAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        memoryArrayList = new ArrayList<>();
        firebaseFirestore=FirebaseFirestore.getInstance();
        getData();
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,staggeredGridLayoutManager.VERTICAL);
        binding.recyclerview.setLayoutManager(staggeredGridLayoutManager);
        memoryAdapter = new MemoryAdapter(memoryArrayList,this);
        binding.recyclerview.setAdapter(memoryAdapter);


        getSupportActionBar().setTitle("All Memories");

        binding.createM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(MainActivity.this,CreateMemory.class));
            }
        });
    }

    private void getData(){
        firebaseFirestore.collection("memories").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Toast.makeText(MainActivity.this,error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
                if(value!=null){
                    for(DocumentSnapshot snapshot: value.getDocuments()){
                        Map<String,Object> data = snapshot.getData();

                        String title = (String) data.get("title");
                        String content = (String) data.get("content");
                        String id = (String) snapshot.getId();
                        String uri = (String) data.get("image");
                        String enlem = (String) data.get("enlem");
                        String boylam = (String) data.get("boylam");
                        String password = (String) data.get("password");


                        Memory memory = new Memory(title,content,id,uri,enlem,boylam,password);
                        memoryArrayList.add(memory);
                    }
                    memoryAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
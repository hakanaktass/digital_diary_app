package com.hakanaktas.digitaldiaryapp;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hakanaktas.digitaldiaryapp.databinding.MemoryLayoutBinding;

import java.util.ArrayList;

public class MemoryAdapter extends RecyclerView.Adapter<MemoryAdapter.MemoryHolder> {
    FirebaseFirestore firebaseFirestore;
    private ArrayList<Memory> memoryArrayList;
    Activity activity;
    Button Enter;
    EditText popupText;


    public MemoryAdapter(ArrayList<Memory> memoryArrayList, Activity activity) {
        this.memoryArrayList = memoryArrayList;
        this.activity= activity;
    }

    @NonNull
    @Override
    public MemoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MemoryLayoutBinding memoryLayoutBinding = MemoryLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        firebaseFirestore=FirebaseFirestore.getInstance();
        return new MemoryHolder(memoryLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoryHolder holder, @SuppressLint("RecyclerView") int position) {

        ImageView menubutton = holder.itemView.findViewById(R.id.menubutton);
        holder.memoryLayoutBinding.memorytitle.setText(memoryArrayList.get(position).title);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialogpopup= new Dialog(activity);
                dialogpopup.setContentView(R.layout.popupmemory);
                Enter = dialogpopup.findViewById(R.id.Enter);
                popupText = dialogpopup.findViewById(R.id.popupText);
                Enter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(memoryArrayList.get(position).password.equals(popupText.getText().toString())){
                            Intent intent = new Intent(activity,MemoryDetails.class);
                            intent.putExtra("title",memoryArrayList.get(position).title);
                            intent.putExtra("content",memoryArrayList.get(position).content);
                            intent.putExtra("id",memoryArrayList.get(position).id);
                            intent.putExtra("image",memoryArrayList.get(position).uri);
                            intent.putExtra("enlem",memoryArrayList.get(position).enlem);
                            intent.putExtra("boylam",memoryArrayList.get(position).boylam);
                            activity.startActivity(intent);

                        }else{
                            Toast.makeText(activity.getApplicationContext(), "Password Incorrect",Toast.LENGTH_SHORT).show();
                            dialogpopup.dismiss();
                        }

                    }
                });
                dialogpopup.show();

            }
        });

        menubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(),view);
                popupMenu.setGravity(Gravity.END);
                popupMenu.getMenu().add("Edit").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Intent intent = new Intent(view.getContext(),EditMemoryActivity.class);
                        intent.putExtra("title",memoryArrayList.get(position).title);
                        intent.putExtra("content",memoryArrayList.get(position).content);
                        intent.putExtra("id",memoryArrayList.get(position).id);
                        intent.putExtra("image",memoryArrayList.get(position).uri);

                        view.getContext().startActivity(intent);

                        return false;
                    }
                });

                popupMenu.getMenu().add("Share").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, memoryArrayList.get(position).title);
                        intent.putExtra(android.content.Intent.EXTRA_TEXT, memoryArrayList.get(position).content);
                        activity.startActivity(Intent.createChooser(intent,"Share"));

                        return false;
                    }
                });

                popupMenu.getMenu().add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        DocumentReference documentReference = firebaseFirestore.collection("memories").document(memoryArrayList.get(position).id);
                        Intent intent = new Intent(view.getContext(),PinActivity.class);
                        view.getContext().startActivity(intent);
                        documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(view.getContext(),"The Memory is deleted.",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(view.getContext(),"Failed to delete.",Toast.LENGTH_SHORT).show();

                            }
                        });
                        return false;
                    }
                });

                popupMenu.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return memoryArrayList.size();
    }

    class MemoryHolder extends RecyclerView.ViewHolder{
        MemoryLayoutBinding memoryLayoutBinding;

        public MemoryHolder(MemoryLayoutBinding memoryLayoutBinding) {
            super(memoryLayoutBinding.getRoot());
            this.memoryLayoutBinding = memoryLayoutBinding;
        }
    }
}

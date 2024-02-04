package com.example.chattingapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.example.chattingapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private String stremail;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private RecyclerView rcvMessage;
    private CircleImageView civAvatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcvMessage = findViewById(R.id.rcvMessage);
        civAvatar = findViewById(R.id.civAvatar);
        stremail = getIntent().getStringExtra("email");

        rcvMessage.setHasFixedSize(true);

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading ......");
        progressDialog.show();


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String strAvatarProfile = snapshot.child("Users").child("Profile_Pictures").getValue(String.class);
                Picasso.get().load(strAvatarProfile).into(civAvatar);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });

    }

}
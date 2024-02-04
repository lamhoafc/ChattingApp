    package com.example.chattingapp.ui;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.appcompat.app.AppCompatActivity;

    import android.app.ProgressDialog;
    import android.content.Intent;
    import android.net.Uri;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.Toast;

    import com.example.chattingapp.R;
    import com.google.android.gms.auth.api.signin.internal.Storage;
    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.OnFailureListener;
    import com.google.android.gms.tasks.OnSuccessListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.storage.FirebaseStorage;
    import com.google.firebase.storage.StorageReference;
    import com.google.firebase.storage.UploadTask;

    import java.util.HashMap;

    import de.hdodenhof.circleimageview.CircleImageView;

    public class SetupActivity extends AppCompatActivity {

        private CircleImageView civAvatar;
        private EditText edtName;
        private Button btnUpdate;
        private Uri uriImage;
        private FirebaseAuth mAuth;
        private FirebaseUser mUser;
        DatabaseReference mDatabaseReference;
        StorageReference mStorageReference;
        ProgressDialog mDialog;
        private static final int REQUEST_CODE = 101;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_setup);

            mDialog = new ProgressDialog(this);
            civAvatar = findViewById(R.id.civAvatar);
            edtName = findViewById(R.id.edtName);
            btnUpdate = findViewById(R.id.btnUpdate);


            mAuth = FirebaseAuth.getInstance();
            mUser = FirebaseAuth.getInstance().getCurrentUser();
            mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
            mStorageReference = FirebaseStorage.getInstance().getReference().child("Profile_Pictures");

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SaveData();
                }
            });

            civAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");

                    startActivityForResult(intent, REQUEST_CODE);
                }
            });
        }

        private void SaveData() {
            String strName = edtName.getText().toString().trim();
            if(strName.isEmpty() || strName.length()<3){
                Toast.makeText(SetupActivity.this, "Username is not valid", Toast.LENGTH_SHORT).show();
            } else if(uriImage==null){
                Toast.makeText(SetupActivity.this, "Please update your avatar", Toast.LENGTH_SHORT).show();
            } else {
                mDialog.setTitle("Adding Setup Profile");
                mDialog.setCanceledOnTouchOutside(false);
                mDialog.show();

                mStorageReference.child(mUser.getUid()).putFile(uriImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){
                            mStorageReference.child(mUser.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    HashMap hashMap = new HashMap<>();
                                    hashMap.put("name", strName);
                                    hashMap.put("avatar", uri.toString());

                                    mDatabaseReference.child(mUser.getUid()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                                        @Override
                                        public void onSuccess(Object o) {
                                            Intent intent = new Intent(SetupActivity.this,MainActivity.class);
                                            startActivity(intent);
                                            mDialog.dismiss();
                                            Toast.makeText(SetupActivity.this, "Update success", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            mDialog.dismiss();
                                            Toast.makeText(SetupActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                        }
                    }
                });
            }
        }



        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data!=null){
                uriImage = data.getData();
                civAvatar.setImageURI(uriImage);
            }
        }
    }
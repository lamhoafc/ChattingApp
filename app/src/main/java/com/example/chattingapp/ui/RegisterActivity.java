package com.example.chattingapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chattingapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtConfilmPassword;
    private Button btnRegister;
    private TextView tvLogin;
    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initUI();
        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickLogin();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atemptRegister();
            }
        });
    }

    private void clickLogin() {
        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    private void initUI() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfilmPassword = findViewById(R.id.edtConfilmPassword);
        btnRegister = findViewById(R.id.btnLoginAccount);
        tvLogin = findViewById(R.id.tvLoginRegister);
    }






    private void atemptRegister() {

    String strEmail = edtEmail.getText().toString().trim();
    String strPassword = edtPassword.getText().toString().trim();
    String strConfilmPassword = edtConfilmPassword.getText().toString().trim();

    if(strEmail.isEmpty() || strPassword.isEmpty() || strConfilmPassword.isEmpty() ){
        Toast.makeText(RegisterActivity.this,"All Fields Requires",Toast.LENGTH_SHORT).show();

    }
    else
        if(strPassword.length() <8 || strConfilmPassword.length()<8){
        Toast.makeText(RegisterActivity.this,"Password must be more than 8 characters",Toast.LENGTH_SHORT).show();
    }
        else
            if(!strPassword.equals(strConfilmPassword)){
        Toast.makeText(RegisterActivity.this,"Password does not match",Toast.LENGTH_SHORT).show();
    }
                else{
                    mDialog.setTitle("Registration");
                    mDialog.setMessage("Please wait");
                    mDialog.setCanceledOnTouchOutside(true);
                    mDialog.show();

                    mAuth.createUserWithEmailAndPassword(strEmail, strPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                mDialog.dismiss();
                                Intent intent = new Intent(RegisterActivity.this,SetupActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                mDialog.dismiss();
                                Toast.makeText(RegisterActivity.this,"Registration failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
}
        };
    }
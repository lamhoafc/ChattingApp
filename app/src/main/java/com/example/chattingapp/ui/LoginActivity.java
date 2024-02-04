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

public class LoginActivity extends AppCompatActivity {
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnLogin;
    private TextView tvRegister;
    private FirebaseAuth mAuth;

    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUi();
        mAuth = FirebaseAuth.getInstance();
        register();
        mDialog = new ProgressDialog(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atemptLogin();
            }
        });
    }

    private void atemptLogin() {
        String strEmail = edtEmail.getText().toString().trim();
        String strPassword = edtPassword.getText().toString().trim();

        if(strEmail.isEmpty() || strPassword.isEmpty() ){
            Toast.makeText(LoginActivity.this,"All Fields Requires",Toast.LENGTH_SHORT).show();

        }
        else
        if(strPassword.length() <8 ){
            Toast.makeText(LoginActivity.this,"Password must be more than 8 characters",Toast.LENGTH_SHORT).show();
        }
        else{
            mDialog.setTitle("Login");
            mDialog.setMessage("Please wait");
            mDialog.setCanceledOnTouchOutside(true);
            mDialog.show();

            mAuth.signInWithEmailAndPassword(strEmail, strPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        mDialog.dismiss();
                        Intent intent = new Intent(LoginActivity.this,SetupActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        mDialog.dismiss();
                        Toast.makeText(LoginActivity.this,"Login failed",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    };

    private void initUi() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLoginAccount);
        tvRegister = findViewById(R.id.tvLoginRegistor);
    }

    private void register() {
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }


}
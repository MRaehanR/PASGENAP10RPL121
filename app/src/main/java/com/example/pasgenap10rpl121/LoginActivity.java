package com.example.pasgenap10rpl121;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText et_email, et_password;
    Button btn_register, btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        et_email = findViewById(R.id.tf_email);
        et_password = findViewById(R.id.tf_password);
        btn_submit = findViewById(R.id.btn_submit);
        btn_register = findViewById(R.id.btn_registernow);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_email.getText().toString().trim();
                String password = et_password.getText().toString().trim();

                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                }else {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                        clearText();
                                        finish();
                                        Intent intent = new Intent(getApplicationContext(), NotesActivity.class);
                                        startActivity(intent);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                                        clearText();
                                    }

                                    // ...
                                }
                            });
                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }


    void clearText(){
        et_email.setText("");
        et_password.setText("");
    }
}
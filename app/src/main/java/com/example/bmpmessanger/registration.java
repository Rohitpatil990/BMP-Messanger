package com.example.bmpmessanger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class registration extends AppCompatActivity {

    TextView loginbut;
    EditText rg_username, rg_email, rg_password;
    Button rg_signup;
    CircleImageView rg_profileImg;
    FirebaseAuth auth;
    Uri imageURI;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseDatabase database;
    ProgressDialog progressDialog;

    Cloudinary cloudinary; // Cloudinary instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Establishing The Account");
        progressDialog.setCancelable(false);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        // Initialize Cloudinary with your credentials
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dwaiareqs",  // Replace with your Cloudinary cloud name
                "api_key", "594614142572794",        // Replace with your Cloudinary API key
                "api_secret", "jetANCMDKWq4MSIg2VJji-dg4lA"  // Replace with your Cloudinary API secret
        ));

        loginbut = findViewById(R.id.LoginTextView);
        rg_username = findViewById(R.id.UsernameEdit);
        rg_email = findViewById(R.id.SignUpEmailEdit);
        rg_password = findViewById(R.id.PasswordEditForSIgnUp);
        rg_profileImg = findViewById(R.id.profileImg);
        rg_signup = findViewById(R.id.SignUpbutton);

        loginbut.setOnClickListener(v -> {
            Intent intent = new Intent(registration.this, LoginIn.class);
            startActivity(intent);
            finish();
        });

        rg_signup.setOnClickListener(v -> {
            String name = rg_username.getText().toString();
            String email = rg_email.getText().toString();
            String password = rg_password.getText().toString();
            String status = "Hey I'm Using This Application";

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                progressDialog.dismiss();
                Toast.makeText(registration.this, "Please Enter Valid Information", Toast.LENGTH_SHORT).show();
            } else if (!email.matches(emailPattern)) {
                progressDialog.dismiss();
                rg_email.setError("Type A Valid Email Here");
            } else if (password.length() < 6) {
                progressDialog.dismiss();
                rg_password.setError("Password Must Be 6 Characters Or More");
            } else {
                progressDialog.show();
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String id = task.getResult().getUser().getUid();
                        DatabaseReference reference = database.getReference().child("user").child(id);

                        if (imageURI != null) {
                            uploadToCloudinary(imageURI, new CloudinaryUploadCallback() {
                                @Override
                                public void onSuccess(String cloudinaryUrl) {
                                    Users user = new Users(id, name, email, password, cloudinaryUrl, status);
                                    reference.setValue(user).addOnCompleteListener(userTask -> {
                                        progressDialog.dismiss();
                                        if (userTask.isSuccessful()) {
                                            Intent intent = new Intent(registration.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(registration.this, "Error in creating the user", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                                @Override
                                public void onError(Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(registration.this, "Image Upload Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            String defaultImage = "https://res.cloudinary.com/dwaiareqs/image/upload/f_auto,q_auto/gwljhctcxzsmbgrxcwxf"; // Replace with a Cloudinary default image URL
                            Users user = new Users(id, name, email, password, defaultImage, status);
                            reference.setValue(user).addOnCompleteListener(userTask -> {
                                progressDialog.dismiss();
                                if (userTask.isSuccessful()) {
                                    Intent intent = new Intent(registration.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(registration.this, "Error in creating the user", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(registration.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        rg_profileImg.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                } else {
                    selectImage();
                }
            } else {
                selectImage();
            }
        });
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK && data != null) {
            imageURI = data.getData();
            rg_profileImg.setImageURI(imageURI);
        }
    }

    private void uploadToCloudinary(Uri fileUri, CloudinaryUploadCallback callback) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(fileUri);
            File tempFile = File.createTempFile("upload", null, getCacheDir());

            try (FileOutputStream out = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
            }

            new Thread(() -> {
                try {
                    Map uploadResult = cloudinary.uploader().upload(tempFile, ObjectUtils.emptyMap());
                    String secureUrl = (String) uploadResult.get("secure_url");
                    runOnUiThread(() -> callback.onSuccess(secureUrl));
                } catch (Exception e) {
                    runOnUiThread(() -> callback.onError(e));
                }
            }).start();

        } catch (Exception e) {
            callback.onError(e);
        }
    }

    interface CloudinaryUploadCallback {
        void onSuccess(String cloudinaryUrl);

        void onError(Exception e);
    }
}

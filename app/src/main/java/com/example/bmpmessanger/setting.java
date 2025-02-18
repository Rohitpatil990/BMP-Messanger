package com.example.bmpmessanger;

//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
//import com.squareup.picasso.Picasso;
//
//import java.net.URI;
//import java.net.URL;
//
//public class setting extends AppCompatActivity {
//
//    ImageView setprofile;
//    EditText setname, setstatus;
//    Button donebut;
//    FirebaseAuth auth;
//    FirebaseDatabase database;
//    FirebaseStorage storage;
//    String email,password;
//    Uri setImageUri;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_setting);
//        auth = FirebaseAuth.getInstance();
//        database = FirebaseDatabase.getInstance();
//        storage = FirebaseStorage.getInstance();
//        setprofile = findViewById(R.id.settingprofile);
//        setname = findViewById(R.id.settingname);
//        setstatus = findViewById(R.id.settingstatus);
//        donebut = findViewById(R.id.donebutt);
//
//        DatabaseReference reference = database.getReference().child("user").child(auth.getUid());
//        StorageReference storageReference = storage.getReference().child("upload").child(auth.getUid());
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                email = snapshot.child("mail").getValue().toString();
//                password = snapshot.child("password").getValue().toString();
//                String name = snapshot.child("userName").getValue().toString();
//                String profile = snapshot.child("profilepic").getValue().toString();
//                String status = snapshot.child("status").getValue().toString();
//                setname.setText(name);
//                setstatus.setText(status);
//                Picasso.get().load(profile).into(setprofile);
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        setprofile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setType("imge/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent,"Select Picture"), 10);
//            }
//        });
//        donebut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String name = setname.getText().toString();
//                String status = setstatus.getText().toString();
//                if(setImageUri!=null){
//                    storageReference.putFile(setImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                @Override
//                                public void onSuccess(Uri uri) {
//                                    String finalimageUri = uri.toString();
//                                    Users users = new Users(auth.getUid(), name,email,password,finalimageUri,status);
//                                    reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            if (task.isSuccessful()){
//                                                Toast.makeText(setting.this, "Data Is Save", Toast.LENGTH_SHORT).show();
//                                                Intent intent = new Intent(setting.this,MainActivity.class);
//                                                startActivity(intent);
//                                                finish();
//                                            }else {
//                                                Toast.makeText(setting.this, "Some Thing went...", Toast.LENGTH_SHORT).show();
//                                            }
//                                        }
//                                    });
//                                }
//                            });
//                        }
//                    });
//                }else {
//                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            String finaImageUri = uri.toString();
//                            Users users = new Users(auth.getUid(), name, email,password,finaImageUri,status);
//                            reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if(task.isSuccessful()){
//                                        Toast.makeText(setting.this, "Data Is Save", Toast.LENGTH_SHORT).show();
//                                        Intent intent = new Intent(setting.this,MainActivity.class);
//                                        startActivity(intent);
//                                        finish();
//                                    }else {
//                                        Toast.makeText(setting.this, "Some Thing went...", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                        }
//                    });
//                }
//            }
//        });
//
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 10){
//            if (data != null){
//                setImageUri = data.getData();
//                setprofile.setImageURI(setImageUri);
//            }
//
//        }
//
//
//    }
//}

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
import android.widget.ImageView;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.cloudinary.android.MediaManager;
import com.cloudinary.utils.ObjectUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class setting extends AppCompatActivity {
    ImageView setprofile;
    EditText setname, setstatus;
    Button donebut;
    FirebaseAuth auth;
    FirebaseDatabase database;
    Uri setImageUri;
    ProgressDialog progressDialog;
    Cloudinary cloudinary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().hide();

        // Initialize Firebase
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        // Initialize Cloudinary
        Map config = new HashMap();
        config.put("cloud_name", "YOUR_CLOUD_NAME");
        config.put("api_key", "YOUR_API_KEY");
        config.put("api_secret", "YOUR_API_SECRET");
        MediaManager.init(this, config);
        cloudinary = new Cloudinary(config);

        // Initialize UI Components
        setprofile = findViewById(R.id.settingprofile);
        setname = findViewById(R.id.settingname);
        setstatus = findViewById(R.id.settingstatus);
        donebut = findViewById(R.id.donebutt);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.setCancelable(false);

        // Fetch user data from Firebase Database
        DatabaseReference reference = database.getReference().child("user").child(auth.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String email = snapshot.child("mail").getValue(String.class);
                    String password = snapshot.child("password").getValue(String.class);
                    String name = snapshot.child("userName").getValue(String.class);
                    String profile = snapshot.child("profilepic").getValue(String.class);
                    String status = snapshot.child("status").getValue(String.class);

                    setname.setText(name);
                    setstatus.setText(status);
                    if (profile != null && !profile.isEmpty()) {
                        Picasso.get().load(profile).into(setprofile);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(setting.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });

        // Profile Image Click Listener (Select Image)
        setprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 10);
            }
        });

        // Done Button Click Listener (Upload Image and Save Data)
        donebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String name = setname.getText().toString();
                String status = setstatus.getText().toString();

                if (setImageUri != null) {
                    uploadImageToCloudinary(setImageUri, name, status);
                } else {
                    saveUserData(null, name, status);
                }
            }
        });
    }

    // Upload Image to Cloudinary
    private void uploadImageToCloudinary(Uri imageUri, String name, String status) {
        new Thread(() -> {
            try {
                File file = new File(RealPathUtil.getRealPath(setting.this, imageUri));
                Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
                String imageUrl = uploadResult.get("secure_url").toString();
                runOnUiThread(() -> saveUserData(imageUrl, name, status));
            } catch (Exception e) {
                runOnUiThread(() -> {
                    progressDialog.dismiss();
                    Toast.makeText(setting.this, "Image Upload Failed", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }

    // Save User Data to Firebase
    private void saveUserData(String imageUrl, String name, String status) {
        DatabaseReference reference = database.getReference().child("user").child(auth.getUid());
        Map<String, Object> userUpdates = new HashMap<>();
        userUpdates.put("userName", name);
        userUpdates.put("status", status);
        if (imageUrl != null) {
            userUpdates.put("profilepic", imageUrl);
        }

        reference.updateChildren(userUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(setting.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(setting.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(setting.this, "Update Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Handle Image Selection
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK && data != null) {
            setImageUri = data.getData();
            setprofile.setImageURI(setImageUri);
        }
    }
}

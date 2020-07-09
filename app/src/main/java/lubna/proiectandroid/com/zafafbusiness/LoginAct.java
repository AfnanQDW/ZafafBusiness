package lubna.proiectandroid.com.zafafbusiness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import lubna.proiectandroid.com.zafafbusiness.Model.BusinessUser;
import lubna.proiectandroid.com.zafafbusiness.Model.Hajz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginAct extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("ZEFAF");
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user;

    private ArrayList<Hajz> mUploads = new ArrayList<>();

    private TextView tvforgetpassword;
    private EditText mEditTextEmail;

    private EditText mEditTextpass;
    private Button mButtonlogin;

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(LoginAct.this, ShowData.class));
            finish();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEditTextEmail = findViewById(R.id.editTextemail);
        mEditTextpass = findViewById(R.id.editTextpass);
        mButtonlogin = findViewById(R.id.buttonlogin);
        tvforgetpassword = findViewById(R.id.tvforgetpass);


        mButtonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLogin(mEditTextEmail.getText().toString(), mEditTextpass.getText().toString());

            }
        });

        tvforgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginAct.this, ForGetPassWord.class);
                startActivity(intent);
            }
        });
    }

    private void startLogin(final String email, String password) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("الرجاء الإنتظار");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                user = mAuth.getCurrentUser();

                if (task.isSuccessful()) {

                    startActivity(new Intent(LoginAct.this, ShowData.class));
                    finish();

                    if (user != null) {

                        UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                                .setDisplayName(email)
                                .build();

                        user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginAct.this, "Profile updated", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        final BusinessUser bu = new BusinessUser();
                        bu.setUid(user.getUid());
                        bu.setEmail(email);

                        myRef.child("Venues").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                                    Log.i("AFQ", snap.toString());

                                    ArrayList<String> ownedVenues = new ArrayList<>();

                                    Hajz upload = snap.getValue(Hajz.class);

                                    if (upload != null && user.getUid().equals(upload.getOwnerUid())) {

                                        mUploads.add(upload);
                                        ownedVenues.add(upload.getOwnerUid());
                                        myRef.child("BusinessUser").child(user.getUid()).child("ownedVenues")
                                                .setValue(ownedVenues);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        myRef.child("BusinessUser").child(user.getUid()).setValue(bu).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(LoginAct.this, "user added", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } else {
                    Toast.makeText(LoginAct.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }



            }
        });

    }
}

package lubna.proiectandroid.com.zafafbusiness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginAct extends AppCompatActivity {

    private ImageView mImageView2;

    private TextView tvforgetpassword;
    private EditText mEditTextphone;

    private EditText mEditTextpass;
    private EditText editphon;
    private Button mButtonlogin;

    private ProgressBar progressBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();


        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(LoginAct.this,ShowData.class));
            finish();
            return;
        }

        mEditTextphone = findViewById(R.id.editTextemail);
        editphon = findViewById(R.id.editphone);
        mEditTextpass = findViewById(R.id.editTextpass);
        mButtonlogin = findViewById(R.id.buttonlogin);

        mButtonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* editphon.getText().toString();
                Intent intent=new Intent(LoginAct.this,PinCodeActivity.class);
                startActivity(intent);*/

                startLogin(mEditTextphone.getText().toString(),mEditTextpass.getText().toString());

            }
        });


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     /*   mImageView2 = findViewById(R.id.imageView2);

        tvforgetpassword = findViewById(R.id.tvforgetpass);
        mEditTextphone = findViewById(R.id.editTextemail);
        mEditTextpass = findViewById(R.id.editTextpass);
        mButtonlogin = findViewById(R.id.buttonlogin);
        mAuth = FirebaseAuth.getInstance();

        mButtonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    userlogin();
                }

            }
        });

        tvforgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginAct.this,ForGetPassWord.class);
                startActivity(intent);
            }
        });
    }

    private void userlogin() {
        String phone = mEditTextphone.getText().toString().trim();
        String password = mEditTextpass.getText().toString().trim();


        if (password.isEmpty()) {
            mEditTextpass.setError("Password is required");
            mEditTextpass.requestFocus();
            return;
        }

        if (password.length() < 6) {
            mEditTextpass.setError("Minimum lenght of password should be 6");
            mEditTextpass.requestFocus();
            return;
        }


        if (phone.isEmpty()) {
            mEditTextphone.setError("email is required");
            mEditTextphone.requestFocus();
            return;
        }
        if (mEditTextphone.length() < 10) {
            mEditTextphone.setError("Enter a valid email");
            mEditTextphone.requestFocus();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(phone, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    finish();
                    Toast.makeText(LoginAct.this, "sucsess", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginAct.this, ShowData.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, ShowData.class));

        }*/


    }

    private void startLogin(String email,String password){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("الرجاء الإنتظار");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    //عمل تسجيل دخول بنجاح


                    //check is Verify
//                    if(task.getResult().getUser().isEmailVerified()) {

                    //startActivity
                    startActivity(new Intent(LoginAct.this, ShowData.class));
                    finish();
//                    }else{
//                        Toast.makeText(MainActivity.this, "الرجاء تفعيل حسابك عن طريق البريد الالكتروني", Toast.LENGTH_SHORT).show();
//                    }
                }else{
                    //اظهر رسالة الخطأ
                    Toast.makeText(LoginAct.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}

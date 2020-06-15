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
        tvforgetpassword = findViewById(R.id.tvforgetpass);


        mButtonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* editphon.getText().toString();
                Intent intent=new Intent(LoginAct.this,PinCodeActivity.class);
                startActivity(intent);*/

                startLogin(mEditTextphone.getText().toString(),mEditTextpass.getText().toString());

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







    private void startLogin(String email,String password){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("الرجاء الإنتظار");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){

                    startActivity(new Intent(LoginAct.this, ShowData.class));
                    finish();

                }else{
                    Toast.makeText(LoginAct.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}

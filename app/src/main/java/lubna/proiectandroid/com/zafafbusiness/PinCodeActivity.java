package lubna.proiectandroid.com.zafafbusiness;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class PinCodeActivity extends AppCompatActivity {

    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_code);

        token =  getIntent().getStringExtra("token");

        final EditText code = findViewById(R.id.edit_code);
        Button singIn = findViewById(R.id.button);

        singIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCredential(code.getText().toString());

            }
        });



    }



    private void createCredential(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(token, code);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(PinCodeActivity.this, ShowData.class));
                    finish();
                }
            }
        });
    }
}

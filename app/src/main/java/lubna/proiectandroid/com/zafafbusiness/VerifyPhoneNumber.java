package lubna.proiectandroid.com.zafafbusiness;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneNumber extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_number);


        FirebaseAuth.getInstance().setLanguageCode("ar");
        verifyPhone("+9720597504550");
    }




    private void verifyPhone(String phoneNumber){
        PhoneAuthProvider authProvider = PhoneAuthProvider.getInstance();

        authProvider.verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                //login Success
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                e.printStackTrace();

            }

            @Override
            public void onCodeSent(@NonNull String token, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(token, forceResendingToken);

                //PinCodeActivityh


                Intent intent = new Intent(VerifyPhoneNumber.this,PinCodeActivity.class);
                intent.putExtra("token",token);
                startActivity(intent);
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);

            }
        });





    }
}

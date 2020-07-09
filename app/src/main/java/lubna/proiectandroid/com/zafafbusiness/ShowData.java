package lubna.proiectandroid.com.zafafbusiness;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import lubna.proiectandroid.com.zafafbusiness.Adapter.HajzAdapter;
import lubna.proiectandroid.com.zafafbusiness.Model.Reservation;

public class ShowData extends AppCompatActivity {


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("ZEFAF");

    FirebaseAuth auth = FirebaseAuth.getInstance();
    String key;

    private RecyclerView mRecyclerView;
    private HajzAdapter mAdapter;
    private ProgressBar mProgressCircle;
    private ArrayList<Reservation> mUploads;
    private FloatingActionButton fab;

    private String orderDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        mRecyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fab);

        showdata();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate();

            }
        });
    }

    public void pickDate() {
        // Initialize
        final SwitchDateTimeDialogFragment dateTimeDialogFragment = SwitchDateTimeDialogFragment.newInstance(
                "أختر موعد المناسبة",
                "OK",
                "Cancel"
        );

        dateTimeDialogFragment.startAtCalendarView();
        dateTimeDialogFragment.setMinimumDateTime(Calendar.getInstance().getTime());
        dateTimeDialogFragment.setDefaultDateTime(Calendar.getInstance().getTime());


        // Set listener
        dateTimeDialogFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                String d = ConvertToDateString(date);
                orderDate = d;
                sendOrder();

            }

            @Override
            public void onNegativeButtonClick(Date date) {
                dateTimeDialogFragment.dismiss();
            }
        });

        dateTimeDialogFragment.show(getSupportFragmentManager(), "dialog_time");

    }

    public void sendOrder() {

        final Reservation r = new Reservation();
        if (auth.getCurrentUser() != null) {
            r.setUserName(auth.getCurrentUser().getDisplayName());
            r.setVenueName(auth.getCurrentUser().getDisplayName().toString());
        }
//        r.setVenueAddress(txtVenueAddress.getText().toString());
//        r.setVenuePrice(txtVenuePrice.getText().toString());
        r.setReservationDate(orderDate);
        r.setReservationStatus(1);

        myRef.child("BusinessUser").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Reservations").push().setValue(r).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    myRef.child("Reservations").push().setValue(r);
                    Toast.makeText(ShowData.this, "success", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(ShowData.this, "fail", Toast.LENGTH_SHORT).show();
            }


        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menulogout:

                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, MainActivity.class));

                break;
            case R.id.contact:
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("*/*");
                intent.setData(Uri.parse("afnan.alqudwa@gmail.com"));
                intent.putExtra(Intent.EXTRA_EMAIL, "afnan.alqudwa@gmail.com");

                startActivity(Intent.createChooser(intent, "Send Email"));

                break;
        }

        return true;
    }

    public void showdata() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressCircle = findViewById(R.id.progress_circle);
        mUploads = new ArrayList<>();

        myRef.child("BusinessUser").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Reservations")
                .addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        Log.i("AFQ", dataSnapshot.toString());

                        key = dataSnapshot.getKey();
                        Reservation upload = dataSnapshot.getValue(Reservation.class);
                        mUploads.add(upload);

                        mRecyclerView.setAdapter(mAdapter);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(ShowData.this));
                        mRecyclerView.setHasFixedSize(true);

                        mProgressCircle.setVisibility(View.INVISIBLE);
                        mAdapter = new HajzAdapter(mUploads, new HajzAdapter.OnHajzClickListener() {
                            @Override
                            public void onHajzClick(String string, String toString, String s, String i) {

                            }

                            @Override
                            public void onHajzConfirmListener(int position) {

                                HashMap<String, Object> map = new HashMap<>();
                                map.put("reservationStatus", 1);

                                myRef.child("Reservations").child(key).updateChildren(map);
                                mAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onHajzDeniedListener(int position) {

                                HashMap<String, Object> map = new HashMap<>();
                                map.put("reservationStatus", 2);

                                myRef.child("Reservations").child(key).updateChildren(map, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                        mAdapter.notifyDataSetChanged();

                                    }
                                });
                            }
                        });
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }


    public static String ConvertToDateString(Date date) {
        SimpleDateFormat dateformat = new SimpleDateFormat("dd MMM yyyy");
        return dateformat.format(date.getTime());
    }

}

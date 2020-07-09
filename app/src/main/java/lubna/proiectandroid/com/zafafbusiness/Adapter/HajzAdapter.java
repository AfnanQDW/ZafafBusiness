package lubna.proiectandroid.com.zafafbusiness.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import lubna.proiectandroid.com.zafafbusiness.Model.Reservation;
import lubna.proiectandroid.com.zafafbusiness.R;

public class HajzAdapter extends RecyclerView.Adapter<HajzAdapter.Hajzviewholder> {
    ArrayList<Reservation> reservations;
    OnHajzClickListener listener;

    public static final int RESERVATION_PENDING = 0;
    public static final int RESERVATION_CONFIRMED = 1;
    public static final int RESERVATION_DENIED = 2;

    public HajzAdapter(ArrayList<Reservation> reservations
            , OnHajzClickListener onemployeeclicklistener
    ) {
        this.reservations = reservations;
        this.listener = onemployeeclicklistener;
    }


    public interface OnHajzClickListener {
        void onHajzClick(String string, String toString, String s, String i);

        void onHajzConfirmListener(int position);

        void onHajzDeniedListener(int position);

    }

    @NonNull
    @Override
    public Hajzviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, null);

        return new Hajzviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Hajzviewholder holder, int position) {
        Reservation reservation = reservations.get(position);

        holder.mTextnamevenus.setText("الصالة : "+reservation.getVenueName());
        holder.mTextnameuser.setText("المستاجر : "+reservation.getUserName());
        holder.mTextdate.setText("التاريخ : "+reservation.getReservationDate());
        holder.mTextprice.setText(reservation.getVenuePrice());

        if (reservations.get(position).getReservationStatus() == RESERVATION_CONFIRMED) {
            holder.status.setText(R.string.reservation_confirmed);
            holder.decline.setVisibility(View.GONE);
            holder.confirm.setVisibility(View.GONE);
            holder.status.setVisibility(View.VISIBLE);
        } else if (reservations.get(position).getReservationStatus() == RESERVATION_DENIED) {
            holder.status.setText(R.string.reservation_denied);
            holder.decline.setVisibility(View.GONE);
            holder.confirm.setVisibility(View.GONE);
            holder.status.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    class Hajzviewholder extends RecyclerView.ViewHolder {

        TextView mTextnamevenus;
        TextView mTextnameuser;
        TextView mTextdate;
        TextView mTextprice;
        Button confirm;
        Button decline;
        TextView status;

        public Hajzviewholder(@NonNull View itemView) {
            super(itemView);

            confirm = itemView.findViewById(R.id.confirm);
            decline = itemView.findViewById(R.id.decline);
            mTextnamevenus = itemView.findViewById(R.id.texnamevenues);
            mTextnameuser = itemView.findViewById(R.id.texnameusers);
            mTextdate = itemView.findViewById(R.id.texdate);
            mTextprice = itemView.findViewById(R.id.texprice);
            status = itemView.findViewById(R.id.status);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onHajzClick(mTextnamevenus.getText().toString(), mTextnameuser.getText().toString(),
                            mTextprice.getText().toString(), mTextdate.getText().toString());
                }
            });

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onHajzConfirmListener(position);
                        }
                    }
                }
            });

            decline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onHajzDeniedListener(position);
                        }
                    }
                }
            });

        }
    }
}

package lubna.proiectandroid.com.zafafbusiness;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class HajzAdapter extends RecyclerView.Adapter<HajzAdapter.Hajzviewholder> {
    ArrayList<Hajz> hajzs = new ArrayList<>();
    Onhijzclicklistener listener;
    private TextView mTextnamevenus;
    private TextView mTextnameuser;
    private TextView mTextdate;
    private TextView mTextprice;

    public HajzAdapter(ArrayList<Hajz> emps, Onhijzclicklistener onemployeeclicklistener) {
        this.hajzs = emps;
        this.listener = onemployeeclicklistener;

    }

    public HajzAdapter(ShowData showData, List<Hajz> mUploads) {

    }


    @NonNull
    @Override
    public Hajzviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, null);
        Hajzviewholder holder = new Hajzviewholder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Hajzviewholder holder, int position) {
        Hajz employee = hajzs.get(position);
        holder.texnmaevenus.setText(employee.getNamevenus());
        holder.texnmaeuser.setText(employee.getNameuser());
        holder.texdata.setText((CharSequence) employee.getDate());
        holder.texprice.setText(employee.getPrice());


    }

    @Override
    public int getItemCount() {
        return hajzs.size();
    }

    class Hajzviewholder extends RecyclerView.ViewHolder {
        TextView texnmaevenus, texprice,texnmaeuser,texdata;
        public Hajzviewholder(@NonNull View itemView) {
            super(itemView);

           mTextnamevenus  = itemView.findViewById(R.id.texnamevenues);
            mTextnameuser = itemView.findViewById(R.id.texnameusers);
            mTextdate =itemView. findViewById(R.id.texdate);
            mTextprice = itemView.findViewById(R.id.texprice);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.Onhijzclicklistener(texnmaevenus.getText().toString(),texnmaeuser.getText().toString(),
                            texprice.getText().toString(), texdata.getText().toString());
                }
            });

        }
    }
}

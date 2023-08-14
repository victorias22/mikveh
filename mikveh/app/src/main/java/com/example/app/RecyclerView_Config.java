package com.example.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class RecyclerView_Config {
    private Context mContext;
    private MikvehAdapter mMikvehAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<Mikveh> mikvot, List<String> keys) {
        mContext = context;
        mMikvehAdapter = new MikvehAdapter(mikvot, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mMikvehAdapter);
    }

    class MikvehItemView extends RecyclerView.ViewHolder {
        private TextView mAddress;
        private TextView mCity;
        private TextView mNeighbor;

        //Rating layout
        private TextView totalRate_header;
        private TextView totalRatingNumber;
        private RatingBar totalRatingBar;

        private String mReligious_Council;
        private String mOpening_Hours_Summer;
        private String mPhone;
        private String mOwner_ID;
        private String mOpening_Hours_Winter;
        private String mOpening_Hours_Holiday_Eve_Shabat_Eve;
        private String mOpening_Hours_Saturday_Night_Good_Day;
        private String mAccessibility;
        private String mSchedule_Appointment;
        private String mNotes;
        private String totalRate;

        private String key;


        public MikvehItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(R.layout.mikveh_list_item, parent, false));
            mAddress = (TextView) itemView.findViewById(R.id.address_txtView);
            mCity = (TextView) itemView.findViewById(R.id.city_txtView);
            mNeighbor = (TextView) itemView.findViewById(R.id.neighbor_txtView);

            totalRate_header = (TextView)itemView.findViewById(R.id.total_rate);
            totalRatingNumber = (TextView)itemView.findViewById(R.id.total_rate_number);
            totalRatingBar = (RatingBar)itemView.findViewById(R.id.ratingBar);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ///
                    DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Users")
                            .document(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if(document.exists()) {
                                    if(document.get("profile").toString().equals("User")) {
                                        Log.d("Type", "User");
                                        Intent intent = new Intent(mContext, MikvehProfileActivity.class);
                                        intent.putExtra("key", key);
                                        intent.putExtra("address", mAddress.getText().toString());
                                        intent.putExtra("city", mCity.getText().toString());
                                        intent.putExtra("neighbor", mNeighbor.getText().toString());
                                        //intent.putExtra("totalRate", totalRatingNumber.getText().toString());

                                        intent.putExtra("religious_Council", mReligious_Council);
                                        intent.putExtra("opening_Hours_Summer", mOpening_Hours_Summer);
                                        intent.putExtra("phone", mPhone);
                                        intent.putExtra("owner_ID", mOwner_ID);
                                        intent.putExtra("opening_Hours_Winter", mOpening_Hours_Winter);
                                        intent.putExtra("opening_Hours_Holiday_Eve_Shabat_Eve", mOpening_Hours_Holiday_Eve_Shabat_Eve);
                                        intent.putExtra("opening_Hours_Saturday_Night_Good_Day", mOpening_Hours_Saturday_Night_Good_Day);
                                        intent.putExtra("accessibility", mAccessibility);
                                        intent.putExtra("schedule_Appointment", mSchedule_Appointment);
                                        intent.putExtra("notes", mNotes);

                                        mContext.startActivity(intent);
                                    }
                                    else {
                                        Log.d("Type", "Admin/Owner");
                                        Intent intent = new Intent(mContext, MikvehDetailsActivity.class);
                                        intent.putExtra("key", key);
                                        intent.putExtra("address", mAddress.getText().toString());
                                        intent.putExtra("city", mCity.getText().toString());
                                        intent.putExtra("neighbor", mNeighbor.getText().toString());
                                        //intent.putExtra("totalRate", totalRatingNumber.getText().toString());

                                        intent.putExtra("religious_Council", mReligious_Council);
                                        intent.putExtra("opening_Hours_Summer", mOpening_Hours_Summer);
                                        intent.putExtra("phone", mPhone);
                                        intent.putExtra("owner_ID", mOwner_ID);
                                        intent.putExtra("opening_Hours_Winter", mOpening_Hours_Winter);
                                        intent.putExtra("opening_Hours_Holiday_Eve_Shabat_Eve", mOpening_Hours_Holiday_Eve_Shabat_Eve);
                                        intent.putExtra("opening_Hours_Saturday_Night_Good_Day", mOpening_Hours_Saturday_Night_Good_Day);
                                        intent.putExtra("accessibility", mAccessibility);
                                        intent.putExtra("schedule_Appointment", mSchedule_Appointment);
                                        intent.putExtra("notes", mNotes);

                                        mContext.startActivity(intent);
                                    }
                                }
                                else {
                                    Log.d("Error","No such document");
                                }
                            }
                            else {
                                Log.d("Error","get failed with", task.getException());

                            }
                        }
                    });
                }
            });
        }


        public void bind(Mikveh mikveh, String key) {
            mAddress.setText(mikveh.getMikve_Address());
            mCity.setText(mikveh.getCity());
            mNeighbor.setText(mikveh.getNeighborhood());

            //Display the current mikveh totalRate
            //totalRate = mikveh.getTotalRate();
            float f = Float.parseFloat(totalRate);
            totalRatingNumber.setText(totalRate);
            totalRatingBar.setRating(f);



            //When user click on the stars for rating
            totalRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    //mikveh.setTotalRate(String.valueOf(v));
                    totalRate = mikveh.getTotalRate();
                    totalRatingNumber.setText(totalRate);
                    totalRatingBar.setRating(v);
                }
            });

            this.key = key;

            this.mReligious_Council = mikveh.getReligious_Council();
            this.mOpening_Hours_Summer = mikveh.getOpening_Hours_Summer();
            this.mPhone = mikveh.getPhone();
            this.mOwner_ID = mikveh.getOwner_ID();
            this.mOpening_Hours_Winter = mikveh.getOpening_Hours_Winter();
            this.mOpening_Hours_Holiday_Eve_Shabat_Eve = mikveh.getOpening_Hours_Holiday_Eve_Shabat_Eve();
            this.mOpening_Hours_Saturday_Night_Good_Day = mikveh.getOpening_Hours_Saturday_Night_Good_Day();
            this.mAccessibility = mikveh.getAccessibility();
            this.mSchedule_Appointment = mikveh.getSchedule_Appointment();
            this.mNotes = mikveh.getNotes();
        }
    }


    class MikvehAdapter extends RecyclerView.Adapter<MikvehItemView> {
        private List<Mikveh> mMikvot;
        private List<String> mKeys;

        public MikvehAdapter(List<Mikveh> mMikvot, List<String> mKeys) {
            this.mMikvot = mMikvot;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public MikvehItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MikvehItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull MikvehItemView holder, int position) {
            holder.bind(mMikvot.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mMikvot.size();
        }
    }
}

package com.global.visitormanagment.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.global.visitormanagment.AddNewVisitor;
import com.global.visitormanagment.Api;
import com.global.visitormanagment.MainActivity;
import com.global.visitormanagment.R;
import com.global.visitormanagment.model.VisitorModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VisitorAdapter extends RecyclerView.Adapter<VisitorAdapter.ViewHolder> {

    private Context context;
    private List<VisitorModel> visitorModelArrayList;
    private static final String TAG = "VisitorAdapter";
    private String outTime;
    final Calendar myCalendar = Calendar.getInstance();
    private String BaseUrl = "http://65.1.117.91/";
    private int hide;


    public VisitorAdapter(Context context, List<VisitorModel> visitorModelArrayList) {
        this.context = context;
        this.visitorModelArrayList = visitorModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_visitor, parent, false);
        final VisitorAdapter.ViewHolder holder = new VisitorAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (visitorModelArrayList.get(position).getOut_time() != null) {
            holder.imgStatusMarked.setImageDrawable(context.getResources().getDrawable(R.drawable.marked));
            holder.btnMarkOut.setChecked(true);
            holder.btnMarkOut.setClickable(false);
            holder.outTime.setText(visitorModelArrayList.get(position).getOut_time());

        }
        holder.name.setText(visitorModelArrayList.get(position).getName());
        holder.phone.setText(visitorModelArrayList.get(position).getPhone()+",");
        holder.purpose.setText(visitorModelArrayList.get(position).getPurpose());
//        holder.date.setText(visitorModelArrayList.get(position).getDate());
        holder.inTime.setText(visitorModelArrayList.get(position).getIn_time());
        holder.noOfPersons.setText(Integer.toString(visitorModelArrayList.get(position).getNo_of_person()));


        holder.btnMarkOut.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
                if (isChecked){
//                    Toast.makeText(CheckBoxTuts.this, "male" , Toast.LENGTH_SHORT).show();
                    holder.btnMarkOut.setClickable(false);
                    // disable checkbox

                    holder.imgStatusMarked.setImageDrawable(context.getResources().getDrawable(R.drawable.marked));
                    hide = 0;
                    String myFormat2 = "h:mm a"; //In which you need put here
                    SimpleDateFormat sdf2 = new SimpleDateFormat(myFormat2, Locale.US);

                    outTime = String.valueOf(sdf2.format(myCalendar.getTime()));
                    //  markout(visitorModelArrayList.get(position).getId(),outTime);

                    Retrofit retrofit = new Retrofit.Builder().baseUrl(BaseUrl + "api/").addConverterFactory(GsonConverterFactory.create()).build();
                    Api api = retrofit.create(Api.class);

                    // calling api to mark out the visitor
                    Call<VisitorModel> call2 = api.markOutVisitor(outTime, visitorModelArrayList.get(position).getId()
                    );

                    call2.enqueue(new Callback<VisitorModel>() {
                        @Override
                        public void onResponse(Call<VisitorModel> call, Response<VisitorModel> response) {
                            if (response != null) {
                                Toast.makeText(context, "marked out", Toast.LENGTH_SHORT).show();

                                holder.btnMarkOut.setVisibility(View.GONE);


                                Log.d(TAG, "onResponse: " + response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<VisitorModel> call, Throwable t) {

                            hide = 0;
                            Log.d(TAG, "onFailure: " + t.getMessage());
                        }
                    });
                }
            }
        });

    }

//    private Boolean markout(int id, String outTime) {
//        Retrofit retrofit = new Retrofit.Builder().baseUrl(BaseUrl + "api/").addConverterFactory(GsonConverterFactory.create()).build();
//        Api api = retrofit.create(Api.class);
//
//        Log.d(TAG, "addNewVisitor: ");
//        Call<VisitorModel> call2 = api.markOutVisitor(outTime, id
//        );
//
//        call2.enqueue(new Callback<VisitorModel>() {
//            @Override
//            public void onResponse(Call<VisitorModel> call, Response<VisitorModel> response) {
//                if (response != null) {
//                    Toast.makeText(context, "marked out", Toast.LENGTH_SHORT).show();
//
//
////                    mainActivity.getVisitorData();
////                    saveButton.setEnabled(true);
////                    loadingDialogProgressBar.dismissDialog();
////
////                    flag=1;
//                    Log.d(TAG, "onResponse: " + response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<VisitorModel> call, Throwable t) {
////                saveButton.setEnabled(true);
////                loadingDialogProgressBar.dismissDialog();
//                hide = 0;
//                Log.d(TAG, "onFailure: " + t.getMessage());
//            }
//        });
//
//        Log.d(TAG, "markout: " + hide);
//        if (hide == 1) {
//            return true;
//        } else {
//            return false;
//        }
//
//
//    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: visitorModelArrayList.size" + visitorModelArrayList.size());
        return visitorModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, phone, purpose, date, noOfPersons, inTime,outTime;
        private CheckBox btnMarkOut;
        private ImageView imgStatusMarked;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.txtName);
            phone = itemView.findViewById(R.id.txtPhone);
            purpose = itemView.findViewById(R.id.txtPurpose);

            noOfPersons = itemView.findViewById(R.id.txtNoOfPerson);
            inTime = itemView.findViewById(R.id.txtIntime);
            btnMarkOut = itemView.findViewById(R.id.btnMarkOut);
            imgStatusMarked=itemView.findViewById(R.id.imgStatusMarked);
            outTime=itemView.findViewById(R.id.txtOuttime);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}

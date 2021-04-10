package com.global.visitormanagment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.global.visitormanagment.Adapter.VisitorAdapter;
import com.global.visitormanagment.model.VisitorModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.global.visitormanagment.AddNewVisitor.flag;

public class MainActivity extends AppCompatActivity {

    private TextView addNewVisitor;
    private RecyclerView recyclerView;
    private VisitorAdapter visitorAdapter;
    private List<VisitorModel> visitorModelArrayList = new ArrayList<>();
    private VisitorModel visitorModel;
    private static final String TAG = "MainActivity";
    final Calendar myCalendar = Calendar.getInstance();
    Retrofit retrofit;
    private String currentDate;
    ArrayList<VisitorModel> lessonArrayList = new ArrayList<>();
    final LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
//    private Button refreshButton;
    Api api;
    private String BaseUrl = "http://65.1.117.91/";
    private TextView todayDate;
    private String todayDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv);
        addNewVisitor = findViewById(R.id.addNewVisitor);
//        refreshButton = findViewById(R.id.refreshButton);
        todayDate=findViewById(R.id.currentDate);
        currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

//
        todayDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(new Date());





        if (todayDateFormat!=null){
            todayDate.setText(todayDateFormat);
        }
        retrofit = new Retrofit.Builder().baseUrl(BaseUrl + "api/").addConverterFactory(GsonConverterFactory.create()).build();
        api = retrofit.create(Api.class);

        if (flag == 1) {
            getVisitorData();
            flag = 0;
        }

        getVisitorData();


        // to refresh data
//        refreshButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getVisitorData();
//            }
//        });

        // to go new visitor activity
        addNewVisitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNewVisitor.class);
                startActivity(intent);
                flag = 0;
            }
        });


    }

    // get Visitor api data
    public void getVisitorData() {
        Call<List<VisitorModel>> call = api.getVisitor(currentDate);
        call.enqueue(new Callback<List<VisitorModel>>() {
            @Override
            public void onResponse(Call<List<VisitorModel>> call, Response<List<VisitorModel>> response) {
                if (response != null) {

                    Log.d(TAG, "onResponse: response.message()" + response.message());
                    Log.d(TAG, "onResponse:response.body() " + response.body());

                    visitorModelArrayList = response.body();
                    initRecycler();
                }


            }

            @Override
            public void onFailure(Call<List<VisitorModel>> call, Throwable t) {

            }

        });
    }

    private void initRecycler() {
        visitorAdapter = new VisitorAdapter(MainActivity.this, visitorModelArrayList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(visitorAdapter);
        visitorAdapter.notifyDataSetChanged();

    }
}
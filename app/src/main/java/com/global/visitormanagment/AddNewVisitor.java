package com.global.visitormanagment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.global.visitormanagment.model.VisitorModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class AddNewVisitor extends AppCompatActivity {

    //init
    private static final String TAG = "AddNewVisitor";
    private static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 1;
    final int ACTIVITY_SELECT_IMAGE1 = 2;
    final int ACTIVITY_SELECT_IMAGE2 = 3;
    final int ACTIVITY_SELECT_IMAGE3 = 4;
    final int ACTIVITY_SELECT_IMAGE4 = 5;

    private static final int PERMISSION_REQUEST_CODE = 100;
    //string
    private String encodedImage1, encodedImage2;
    private String currentDate;
    private String name, phone, purpose, inTime, finalDate, noOfPerson;
    private int id;
    private String BaseUrl = "http://65.1.117.91/";
    public static int flag = 0;
    private LoadingDialogProgressBar loadingDialogProgressBar;

    //vars
    private ImageView image1, image2;
    private Button saveButton;
    private TextView date;
    private TextView addImage1, addImage2;
    final Calendar myCalendar = Calendar.getInstance();
    private Uri imageUri1, imageUri2;
    private EditText edtName, edtPhone, edtPurpose, edtID, edtNoOfPerson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_visitor);

        if (!checkPermission()) {
            requestPermission();
        }
        date = findViewById(R.id.enterDate);
        addImage1 = findViewById(R.id.txtAddImage1);
        addImage2 = findViewById(R.id.txtAddImage2);
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        saveButton = findViewById(R.id.saveButton);
        edtName = findViewById(R.id.enterName);
        edtPhone = findViewById(R.id.enterPhoneNUmber);
        edtPurpose = findViewById(R.id.enterPurpose);

//        edtID=findViewById(R.id.enterId);
        edtNoOfPerson = findViewById(R.id.enterNoOfPerson);


        loadingDialogProgressBar = new LoadingDialogProgressBar(AddNewVisitor.this);

        //getting current date
        currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());



        if (currentDate != null) {

            date.setText(currentDate);
        }

        DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel();

            }


        };

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddNewVisitor.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        addImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(AddNewVisitor.this);
                builder1.setTitle("Select Photo");
                //  builder1.setMessage("Please Add Symptoms etc. in your Booking Screen");
                builder1.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        captureImage1(ACTIVITY_SELECT_IMAGE1, false);
                    }
                });
                builder1.setNegativeButton("Gallery", (dialog, which) -> {
                    captureImage1(ACTIVITY_SELECT_IMAGE1, true);

                });
                builder1.setCancelable(false);
                builder1.create().show();



            }
        });
        addImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(AddNewVisitor.this);
                builder1.setTitle("Select Photo");
                //  builder1.setMessage("Please Add Symptoms etc. in your Booking Screen");
                builder1.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        captureImage2(ACTIVITY_SELECT_IMAGE2, false);
                    }
                });
                builder1.setNegativeButton("Gallery", (dialog, which) -> {
                    captureImage2(ACTIVITY_SELECT_IMAGE2, true);

                });
                builder1.setCancelable(false);
                builder1.create().show();
//                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//                startActivityForResult(intent, ACTIVITY_SELECT_IMAGE2);


            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                getInTime();

                if (edtName != null && edtPhone != null && edtPurpose != null && edtNoOfPerson != null && date != null && edtPhone.length() == 10) {
                    name = edtName.getText().toString().trim();
                    phone = edtPhone.getText().toString().trim();
                    purpose = edtPurpose.getText().toString().trim();
//                id= Integer.parseInt(edtID.getText().toString().trim());
                    noOfPerson = edtNoOfPerson.getText().toString().trim();
                    finalDate = date.getText().toString().trim();
                    saveButton.setEnabled(false);
                    loadingDialogProgressBar.setLoadingDialog();
                    addNewVisitor();
                } else {
                    Toast.makeText(AddNewVisitor.this, "Complete the details", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }


    // post api to add new visitor
    private void addNewVisitor() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BaseUrl + "api/").addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);

        Log.d(TAG, "addNewVisitor: ");
        Call<VisitorModel> call2 = api.addNewVisitor(name,
                phone,
                purpose,
                Integer.parseInt(noOfPerson),
                finalDate,
                inTime,
                encodedImage1,
                encodedImage2
        );

        Log.d(TAG, "addLesson: flag0" + "name:- " + name
                + " phone:- " + phone
                + " purpose:- " + purpose
                + " noOfPerson:- " + noOfPerson
                + " finalDate:- " + finalDate
                + " inTime:- " + inTime
        );

        call2.enqueue(new Callback<VisitorModel>() {
            @Override
            public void onResponse(Call<VisitorModel> call, Response<VisitorModel> response) {
                if (response != null) {
                    Toast.makeText(AddNewVisitor.this, "success", Toast.LENGTH_SHORT).show();
                    saveButton.setEnabled(true);
                    loadingDialogProgressBar.dismissDialog();
                    Intent intent = new Intent(AddNewVisitor.this, MainActivity.class);
                    startActivity(intent);
                    flag = 1;
                    Log.d(TAG, "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<VisitorModel> call, Throwable t) {
                saveButton.setEnabled(true);
                loadingDialogProgressBar.dismissDialog();
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    // getting in Time
    private void getInTime() {
        String myFormat2 = "h:mm a"; //In which you need put here
        SimpleDateFormat sdf2 = new SimpleDateFormat(myFormat2, Locale.US);

        inTime = String.valueOf(sdf2.format(myCalendar.getTime()));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult: requestCode"+requestCode);

        if (requestCode == ACTIVITY_SELECT_IMAGE1 && resultCode == RESULT_OK) {
            Uri path = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            encodedImage1 = "";
            image1.setVisibility(View.VISIBLE);
            imageUri1 = path;


            Glide.with(AddNewVisitor.this)
                    .asBitmap()
                    .load(path)
                    .into(image1);


            try {

                InputStream inputStream = AddNewVisitor.this.getContentResolver().openInputStream(path);
                byte[] pdfInBytes = new byte[inputStream.available()];
                inputStream.read(pdfInBytes);
                encodedImage1 = Base64.encodeToString(pdfInBytes, Base64.DEFAULT);

                Log.d(TAG, "onActivityResult: encodedQuestionImage" + encodedImage1);

                //   Toast.makeText(this, "Document Selected", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        if (requestCode == ACTIVITY_SELECT_IMAGE3 && resultCode == RESULT_OK) {

            encodedImage1 = "";
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            image1.setVisibility(View.VISIBLE);

            image1.setImageBitmap(bitmap);



            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            encodedImage1 = Base64.encodeToString(byteArray, Base64.DEFAULT);

            Log.d(TAG, "onActivityResult:encodedImage1 " + encodedImage1);



        }
        if (requestCode == ACTIVITY_SELECT_IMAGE2 && resultCode == RESULT_OK) {

            Uri path = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            encodedImage2 = "";
            image2.setVisibility(View.VISIBLE);
            imageUri2 = path;


            Glide.with(AddNewVisitor.this)
                    .asBitmap()
                    .load(path)
                    .into(image2);
            try {

                InputStream inputStream = AddNewVisitor.this.getContentResolver().openInputStream(path);
                byte[] pdfInBytes = new byte[inputStream.available()];
                inputStream.read(pdfInBytes);
                encodedImage2 = Base64.encodeToString(pdfInBytes, Base64.DEFAULT);

                Log.d(TAG, "onActivityResult: encodedImage2" + encodedImage2);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == ACTIVITY_SELECT_IMAGE4 && resultCode == RESULT_OK) {

            encodedImage2 = "";
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            image2.setVisibility(View.VISIBLE);

            image2.setImageBitmap(bitmap);


            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            encodedImage2 = Base64.encodeToString(byteArray, Base64.DEFAULT);

            Log.d(TAG, "onActivityResult:encodedImage2 " + encodedImage2);


        }

    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        date.setText(sdf.format(myCalendar.getTime()));
    }

    public void captureImage1(int code, boolean b) {
        if (b) {
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(gallery, ACTIVITY_SELECT_IMAGE1);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            startActivityForResult(intent, ACTIVITY_SELECT_IMAGE3);
        }
    }

    public void captureImage2(int code, boolean b) {
        if (b) {
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(gallery, ACTIVITY_SELECT_IMAGE2);
        } else {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            startActivityForResult(intent, ACTIVITY_SELECT_IMAGE4);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        flag = 0;
    }

    private boolean checkPermission() {

        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result4 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);

        return result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED && result4 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }
}
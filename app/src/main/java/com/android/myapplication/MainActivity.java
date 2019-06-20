package com.android.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText ed_name , ed_email , ed_password ;
    Button btn_submit;
    String name, email, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed_name = (EditText) findViewById(R.id.editText_name);
        ed_email = (EditText) findViewById(R.id.editText_email);
        ed_password = (EditText) findViewById(R.id.editText_password);
        btn_submit = (Button) findViewById(R.id.button);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = ed_name.getText().toString();
                email= ed_email.getText().toString();
                password = ed_password.getText().toString();

                OkHttpClient client = new OkHttpClient();
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.43.62")
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                apiservice service = retrofit.create(apiservice.class);
                Student student = new Student();
                student.setName(name);
                student.setEmail(email);
                student.setPassword(password);

                Call<ResponseBody> myCall = service.insertData(name,email,password);
                myCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
                    {
                        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)
                                != PackageManager.PERMISSION_GRANTED)
                        {
                            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                                    Manifest.permission.SEND_SMS))
                            {

                            } else {
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.SEND_SMS},
                                        0);
                            }
                        }
                        else
                        {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage("0775502733", null, "We doing fine fine", null, null);
                            Toast.makeText(getApplicationContext(), "SMS sent.",
                                    Toast.LENGTH_LONG).show();
                            Log.d("Sms","Sms has been sent");
                        }
                        Toast.makeText(MainActivity.this, "Adding the user was a success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t)
                    {
                        Toast.makeText(MainActivity.this,"Adding the user was a fail " , Toast.LENGTH_LONG).show();
                        Log.d("ErrorAuthenticating","This was the error " + t.getMessage());
                    }
                });

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage("0775502733", null, "We doing fine fine", null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                    Log.d("Sms","Sms has been sent");
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }





    }


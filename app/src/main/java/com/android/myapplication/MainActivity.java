package com.android.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
                        .baseUrl("http://192.168.1.107")
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
}

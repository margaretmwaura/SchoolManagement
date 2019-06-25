package com.android.myapplication;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReadingFromFirebase extends AppCompatActivity implements OnItemClickListener{

    private RecyclerView recyclerView;
    private StudentAdapter studentAdapter;
    List<Student> studentList = new ArrayList<>();
    String newToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_from_firebase);


        gettingFirebaseToken();
//        Getting the elements of the UI
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        studentAdapter = new StudentAdapter();
        studentAdapter.setClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);


        readDataFromTable();
    }

    public void readDataFromTable()
    {
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
        Call<List<Student>> myCall = service.readData();
        myCall.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response)
            {
              studentList = response.body();

              Log.d("ListSize","This is the size of the array " + studentList.size());
              studentAdapter.setmStudentList(studentList);
              recyclerView.setAdapter(studentAdapter);
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t)
            {
               Log.d("Errors","This is the error " + t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View view, int position)
    {
        Log.d("TheToken ","This is a token " + newToken);
        Log.d("CLickingItem","A recyler view item has been called ");
      Student student = studentList.get(position);
      String name = "MagiiiShii";
      String email = student.getEmail();

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
        Call<ResponseBody> myCall = service.updateData(name,email,newToken);
        myCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                Toast.makeText(ReadingFromFirebase.this, "Data has been updated ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
                Toast.makeText(ReadingFromFirebase.this, "Error reading data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void gettingFirebaseToken()
    {

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( ReadingFromFirebase.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
               newToken = instanceIdResult.getToken();
                Log.e("newToken",newToken);

            }
        });


    }
}

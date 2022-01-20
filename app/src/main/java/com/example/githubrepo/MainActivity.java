package com.example.githubrepo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.githubrepo.model.GitRepo;
import com.example.githubrepo.network.ApiInterface;
import com.example.githubrepo.viewmodel.TopRepoViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TopRepoViewModel topRepoViewModel;
    private static final String TAG = "gitrep";
    String baseUrl = "https://api.github.com/search/";
    String q="created:>2019-01-10";
    String sort="stars";
    String order="desc";
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = findViewById(R.id.txt);

        topRepoViewModel = new ViewModelProvider(this).get(TopRepoViewModel.class);
        topRepoViewModel.getTopRepo(q,sort,order).observe(this, topRepo -> {
            txt.setText(topRepo.getItems().get(0).getName());
        });

    }
}
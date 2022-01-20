package com.example.githubrepo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.githubrepo.adapter.TopRepoAdapter;
import com.example.githubrepo.databinding.ActivityMainBinding;
import com.example.githubrepo.model.GitRepo;
import com.example.githubrepo.model.Items;
import com.example.githubrepo.network.ApiInterface;
import com.example.githubrepo.viewmodel.TopRepoViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    TopRepoViewModel topRepoViewModel;
    List<Items> list;
    TopRepoAdapter adapter;
    private static final String TAG = "gitrep";
    String baseUrl = "https://api.github.com/search/";
    String q = "created:>2019-01-10";
    String sort = "stars";
    String order = "desc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initRecycler();

        topRepoViewModel = new ViewModelProvider(this).get(TopRepoViewModel.class);
        topRepoViewModel.getTopRepo(q, sort, order).observe(this, topRepo -> {
            // binding.txt.setText(topRepo.getItems().get(0).getName());
            list = topRepo.getItems();
            if (list.size() != 0) {
                adapter = new TopRepoAdapter(list, this);
                binding.recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

    }

    private void initRecycler() {
        list = new ArrayList<>();
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
package com.example.githubrepo.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.githubrepo.util.CacheOperation;
import com.example.githubrepo.Constants;
import com.example.githubrepo.util.NetworkCheck;
import com.example.githubrepo.R;
import com.example.githubrepo.adapter.TopRepoAdapter;
import com.example.githubrepo.databinding.ActivityMainBinding;
import com.example.githubrepo.model.Items;
import com.example.githubrepo.viewmodel.TopGitRepoViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    ActivityMainBinding binding;
    NetworkCheck networkCheck;
    TopGitRepoViewModel topRepoViewModel;
    List<Items> list;
    TopRepoAdapter adapter;
    CacheOperation cacheOperation;
    SharedPreferences prefs;
    String pickedDate;
    int numberOfItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        init();
        initRecycler();
        numberOfItems = cacheOperation.getIntFromPreference(Constants.ITEMS_KEY, Constants.DEFAULT_ITEMS_NUMBER);
        pickedDate = cacheOperation.getStringFromPreference(Constants.DATE_KEY, Constants.DEFAULT_DATE);
        getData(pickedDate, numberOfItems);
    }

    private void getData(String date, int perPage) {

        topRepoViewModel = new ViewModelProvider(this).get(TopGitRepoViewModel.class);
        topRepoViewModel.getTopRepo(date, Constants.SORT_BY, Constants.ORDER_IN, perPage)
                .observe(this, topRepo -> {

                    if (topRepo.getError() == null) {
                        list = topRepo.getItems();
                        binding.progressBar.setVisibility(View.GONE);
                        adapter = new TopRepoAdapter(list, this);
                        binding.recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }else {
                        Throwable e = topRepo.getError();
                         errorChecking(e);
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.floating_btn:
                BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
                bottomSheetFragment.show(this.getSupportFragmentManager(), bottomSheetFragment.getTag());
                break;
            case R.id.btn_retry:
                if (networkCheck.isNetworkAvailable()){
                    getData(pickedDate,numberOfItems);
                    binding.btnRetry.setVisibility(View.GONE);
                    binding.animationView.setVisibility(View.GONE);
                    binding.tvError.setVisibility(View.GONE);
                    binding.floatingBtn.setVisibility(View.VISIBLE);
                }
        }

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key == Constants.ITEMS_KEY) {
            numberOfItems = cacheOperation.getIntFromPreference(key, Constants.DEFAULT_ITEMS_NUMBER);
            getData(pickedDate, numberOfItems);
        } else if (key == Constants.DATE_KEY) {
            pickedDate = cacheOperation.getStringFromPreference(key, Constants.DEFAULT_DATE);
            getData(pickedDate, numberOfItems);

        }

    }

    private void initRecycler() {
        list = new ArrayList<>();
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void init() {
        prefs = this.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        prefs.registerOnSharedPreferenceChangeListener(this);
        cacheOperation = new CacheOperation(this);
        networkCheck = new NetworkCheck(this);
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.floatingBtn.setOnClickListener(this);
        binding.btnRetry.setOnClickListener(this);
    }

    private void errorChecking(Throwable e){
        if (networkCheck.isNetworkAvailable()){
            binding.progressBar.setVisibility(View.GONE);
            binding.animationView.setVisibility(View.VISIBLE);
            binding.animationView.setAnimation(R.raw.err);
            binding.floatingBtn.setVisibility(View.GONE);
        }else {
            binding.tvError.setVisibility(View.VISIBLE);
            binding.animationView.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.GONE);
            binding.floatingBtn.setVisibility(View.GONE);
            binding.btnRetry.setVisibility(View.VISIBLE);
        }


    }
}
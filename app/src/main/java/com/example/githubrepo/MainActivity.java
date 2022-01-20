package com.example.githubrepo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.githubrepo.adapter.TopRepoAdapter;
import com.example.githubrepo.databinding.ActivityMainBinding;
import com.example.githubrepo.model.GitRepo;
import com.example.githubrepo.model.Items;
import com.example.githubrepo.network.ApiInterface;
import com.example.githubrepo.viewmodel.TopRepoViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMainBinding binding;
    TopRepoViewModel topRepoViewModel;
    List<Items> list;
    TopRepoAdapter adapter;
    private static final String TAG = "gitrep";
    String baseUrl = "https://api.github.com/search/";
    String q = "created:>";
    final String defaultDate = "created:>2019-01-10";
    String sort = "stars";
    String order = "desc";
    String pickedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.datePicker.setOnClickListener(this);
        binding.progressBar.setVisibility(View.VISIBLE);

        initRecycler();
        getData(defaultDate);
    }



    private void initRecycler() {
        list = new ArrayList<>();
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private DatePickerDialog.OnDateSetListener getDate(){
        DatePickerDialog.OnDateSetListener setListener;
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                binding.progressBar.setVisibility(View.VISIBLE);
                Calendar calendar = Calendar.getInstance();
                calendar.set(year,month,day);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String date = formatter.format(calendar.getTime());
                pickedDate= q+date;
                binding.pickedDate.setText(date);
                getData(pickedDate);
            }
        };
        return  setListener;
    }
    private void getData(String date){
        topRepoViewModel = new ViewModelProvider(this).get(TopRepoViewModel.class);
        topRepoViewModel.getTopRepo(date, sort, order).observe(this, topRepo -> {
            // binding.txt.setText(topRepo.getItems().get(0).getName());
            list = topRepo.getItems();
            if (list.size() != 0) {
                binding.progressBar.setVisibility(View.GONE);
                adapter = new TopRepoAdapter(list, this);
                binding.recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.date_picker){
            Calendar calendar = Calendar.getInstance();
            final int year = calendar.get(Calendar.YEAR);
            final int month = calendar.get(Calendar.MONTH);
            final int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this
                    , R.style.Base_Theme_Material3_Light_Dialog,
                    getDate(), year, month, day);
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple_200)));
            datePickerDialog.show();
        }
    }
}
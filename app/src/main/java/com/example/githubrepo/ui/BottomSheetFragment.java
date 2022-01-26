package com.example.githubrepo.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.githubrepo.util.CacheOperation;
import com.example.githubrepo.Constants;
import com.example.githubrepo.R;
import com.example.githubrepo.databinding.FragmentBottomSheetBinding;
import com.example.githubrepo.util.NetworkCheck;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BottomSheetFragment extends BottomSheetDialogFragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {


    CacheOperation cacheOperation;
    NetworkCheck networkCheck;
    FragmentBottomSheetBinding binding;
    private static final String TAG = "bottomSheet";

    public BottomSheetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBottomSheetBinding.inflate(getLayoutInflater());


        init();
        //when the fragmen first created we display the last option user select
        getCurrentListValue();
        getCurrentDate();


        return binding.getRoot();
    }

    private void getCurrentListValue() {

        //check the last seleced radioBtn
        int value = cacheOperation.getIntFromPreference(Constants.ITEMS_KEY, Constants.DEFAULT_ITEMS_NUMBER);
        switch (value) {
            case 10:
                binding.radioBtn10.setChecked(true);
                break;
            case 50:
                binding.radioBtn50.setChecked(true);
                break;
            case 100:
                binding.radioBtn100.setChecked(true);
                break;
        }
    }

    private void getCurrentDate() {
        //get the last selected date
        String date = cacheOperation.getStringFromPreference(Constants.DATE_KEY, Constants.DEFAULT_DATE);
        binding.tvDate.setText(date.substring(9));
    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();

        if (dialog != null) {
            View bottomSheet = dialog.findViewById(R.id.design_bottom_sheet);
            bottomSheet.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        }
        View view = getView();
        //set the bottom sheet full screen
        view.post(() -> {
            View parent = (View) view.getParent();
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) (parent).getLayoutParams();
            CoordinatorLayout.Behavior behavior = params.getBehavior();
            BottomSheetBehavior bottomSheetBehavior = (BottomSheetBehavior) behavior;
            bottomSheetBehavior.setPeekHeight(view.getMeasuredHeight());

        });
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        // get the checked radio btn
        if (networkCheck.isNetworkAvailable()) {
            switch (i) {
                case R.id.radioBtn10:
                    cacheOperation.PutIntToPreference(Constants.ITEMS_KEY, 10);
                    break;
                case R.id.radioBtn50:
                    cacheOperation.PutIntToPreference(Constants.ITEMS_KEY, 50);
                    break;
                case R.id.radioBtn100:
                    cacheOperation.PutIntToPreference(Constants.ITEMS_KEY, 100);
                    break;
            }
        } else {
            Toast.makeText(getContext(), "check your interner", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.date_picker) {
            initDatePicker();
        }
    }

    private void initDatePicker() {
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext()
                , R.style.Base_Theme_Material3_Light_Dialog,
                getDate(), year, month, day);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple_200)));
        datePickerDialog.show();
    }


    private DatePickerDialog.OnDateSetListener getDate() {
        DatePickerDialog.OnDateSetListener setListener;
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String date = formatter.format(calendar.getTime());
                if (!isAfterCurrentDate(date)) {
                    String pickedDate = Constants.URL_DATE_PORTION + date;
                    binding.tvDate.setText(date);
                    cacheOperation.PutStringToPreference(Constants.DATE_KEY, pickedDate);
                } else {
                    Toast.makeText(getContext(), "Please Enter Valid Date", Toast.LENGTH_LONG).show();
                }

                //getData(pickedDate,numberOfItems);
            }
        };
        return setListener;
    }

    private boolean isAfterCurrentDate(String selectedDate) {
        //check if the selected date is after today
        Date enteredDate = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            enteredDate = sdf.parse(selectedDate);
        } catch (Exception ex) {
            Log.d(TAG, "isAfterCurrentDate: " + ex.getMessage());
            return true;
        }
        Date currentDate = new Date();
        if (enteredDate.after(currentDate)) {
            return true;
        }
        return false;
    }

    private void init() {
        cacheOperation = new CacheOperation(getContext());
        networkCheck = new NetworkCheck(getContext());
        binding.radioGroup.setOnCheckedChangeListener(this);
        binding.datePicker.setOnClickListener(this);

    }


}
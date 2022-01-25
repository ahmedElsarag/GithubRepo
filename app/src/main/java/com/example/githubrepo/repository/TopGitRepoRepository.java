package com.example.githubrepo.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.githubrepo.Constants;
import com.example.githubrepo.model.GitRepo;
import com.example.githubrepo.network.ApiClient;
import com.example.githubrepo.network.ApiInterface;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TopGitRepoRepository {

    ApiInterface apiInterface;
    private static final String TAG = "toprepository";

    public TopGitRepoRepository() {
        apiInterface = ApiClient.getInstance().create(ApiInterface.class);
    }

    public LiveData<GitRepo> getTopRepo(String q, String sort, String order, int perPage) {
        MutableLiveData<GitRepo> data = new MutableLiveData<>();
        Observable observable = apiInterface.getRepo(q,sort,order,perPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Observer<GitRepo> observer = new Observer<GitRepo>() {
            @Override
            public void onSubscribe(Disposable d) {

            }
            @Override
            public void onNext(GitRepo value) {
                data.setValue(value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: "+e.getMessage());
                data.postValue(new GitRepo(e));
            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);

        return data;
    }

}

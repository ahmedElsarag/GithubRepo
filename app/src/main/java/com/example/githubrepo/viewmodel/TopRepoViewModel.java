package com.example.githubrepo.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.githubrepo.model.GitRepo;
import com.example.githubrepo.repository.GithupRepo;

public class TopRepoViewModel extends ViewModel {

    GithupRepo githupRepo;

    public TopRepoViewModel() {
        githupRepo = new GithupRepo();
    }
    public LiveData<GitRepo> getTopRepo(String q, String sort, String order,int perPage){

        return githupRepo.getTopRepo(q,sort,order,perPage);
    }
}

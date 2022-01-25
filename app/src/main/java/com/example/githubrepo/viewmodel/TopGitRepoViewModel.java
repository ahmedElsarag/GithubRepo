package com.example.githubrepo.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.githubrepo.model.GitRepo;
import com.example.githubrepo.repository.TopGitRepoRepository;

public class TopGitRepoViewModel extends ViewModel {

    TopGitRepoRepository topGitRepoRepository;

    public TopGitRepoViewModel() {
        topGitRepoRepository = new TopGitRepoRepository();
    }
    public LiveData<GitRepo> getTopRepo(String q, String sort, String order,int perPage){

        return topGitRepoRepository.getTopRepo(q,sort,order,perPage);
    }
}

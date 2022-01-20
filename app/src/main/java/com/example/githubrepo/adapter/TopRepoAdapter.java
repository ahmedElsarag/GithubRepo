package com.example.githubrepo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.githubrepo.R;
import com.example.githubrepo.databinding.TopRepoItemBinding;
import com.example.githubrepo.model.Items;


import java.util.List;

public class TopRepoAdapter extends RecyclerView.Adapter<TopRepoAdapter.SurahHolder> {
    List<Items> list;
    Context context;

    public TopRepoAdapter(List<Items> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public SurahHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        TopRepoItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.top_repo_item, parent, false);
        return new SurahHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SurahHolder holder, int position) {
        holder.binding.repoName.setText(list.get(position).getName());
        holder.binding.repoDesc.setText(list.get(position).getDescription());
        holder.binding.starsNum.setText(Integer.toString(list.get(position).getStargazers_count()));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SurahHolder extends RecyclerView.ViewHolder{
        TopRepoItemBinding binding;

        public SurahHolder(@NonNull TopRepoItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

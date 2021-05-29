package com.example.myapplication.Ebizz.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.Ebizz.model.Category;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.UserViewHolder> {

    Context context;
    List<Category> list;
    public  int selectedCatgory = 0;

    public interface CategoryListener {
        void onClickItem(int position);

    }

    CategoryListener categoryListener;

    public CategoryAdapter(Context context, List<Category> category) {
        this.context = context;
        this.categoryListener = (CategoryListener) context;
        this.list = category;
    }

    @NonNull
    @NotNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lay_category, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull CategoryAdapter.UserViewHolder holder, int position) {


        Category category = list.get(position);
        holder.tv_name.setText(category.getNameEng());
        if (!TextUtils.isEmpty(category.getImage()))
            Glide.with(context).load(category.getImage()).circleCrop().into(holder.image);
        else
            Glide.with(context).load(R.drawable.ic_launcher_foreground).into(holder.image);
        if (selectedCatgory == position) {
            holder.itemView.setBackgroundColor(Color.DKGRAY);
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                categoryListener.onClickItem(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        View view;
        ConstraintLayout lay_item;
        TextView tv_name;
        ImageView image;

        public UserViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            view = itemView;
            lay_item = view.findViewById(R.id.lay_item);
            tv_name = view.findViewById(R.id.tv_name);
            image = view.findViewById(R.id.image);
        }
    }
}

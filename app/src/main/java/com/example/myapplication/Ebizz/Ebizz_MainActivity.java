package com.example.myapplication.Ebizz;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Ebizz.API.RetrofitClient;
import com.example.myapplication.Ebizz.Adapter.CategoryAdapter;
import com.example.myapplication.Ebizz.Adapter.ShopAdapter;
import com.example.myapplication.Ebizz.model.Category;
import com.example.myapplication.Ebizz.model.Example;
import com.example.myapplication.Ebizz.model.ShopExample;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Ebizz_MainActivity extends AppCompatActivity implements CategoryAdapter.CategoryListener {

    Context context;
    RecyclerView rv_category, rv_List;
    private CategoryAdapter categoryAdapter;
    private ShopAdapter shopAdapter;
    private List<Category> lisCategory;
    private ProgressDialog progressBar;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UI();
        setup();

    }

    private void setup() {

        progressBar = new ProgressDialog(context);
        progressBar.setMessage("Please Wait");
        progressBar.show();
        Call<Example> call = RetrofitClient.getInstance().getMyApi().getExample();

        call.enqueue(new Callback<Example>() {


            @Override
            public void onResponse(Call<Example> call, retrofit2.Response<Example> response) {

                Example list = response.body();
                lisCategory = new ArrayList<>();
                lisCategory.add(new Category("all"));
                lisCategory.addAll(list.getResponse().getCategory());
                categoryAdapter = new CategoryAdapter(context, lisCategory);
                rv_category.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                rv_category.setAdapter(categoryAdapter);
                onClickItem(0);

            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                //handle error or failure cases here
                progressBar.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void UI() {
        context = this;
        rv_category = findViewById(R.id.rv_category);
        rv_List = findViewById(R.id.rv_List);
        editText = findViewById(R.id.edTerxt);


    }

    @Override
    public void onClickItem(int position) {
        Category category = lisCategory.get(position);


        Call<ShopExample> call = RetrofitClient.getInstance().getMyApi().getShopCatgory(category.getNameEng().toLowerCase(),
                "3099", "21.1956942", "72.808376");
        progressBar.show();
        call.enqueue(new Callback<ShopExample>() {
            @Override
            public void onResponse(Call<ShopExample> call, Response<ShopExample> response) {
                categoryAdapter.selectedCatgory = position;
                categoryAdapter.notifyDataSetChanged();
                shopAdapter = new ShopAdapter(context, response.body().getResponse().getShopData(),
                        response.body().getResponse().getImgUrl());
                rv_List.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                rv_List.setAdapter(shopAdapter);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        shopAdapter.getFilter().filter(s.toString());

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                progressBar.dismiss();
            }

            @Override
            public void onFailure(Call<ShopExample> call, Throwable t) {
                progressBar.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
package com.example.myapplication.Practice;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.Practice.API_Interface.API_Client;
import com.example.myapplication.Practice.model.Datum;
import com.example.myapplication.Practice.model.Example;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveDataConstant extends ViewModel {


    //MVVM
    //particular view model for every UI element like activity , fragment

    //example USErListFrag
    //userListViewModel
    //
    public MutableLiveData<List<Datum>> list_user;

    public MutableLiveData<List<Datum>> getList_Post(int page) {
        if (list_user == null) {
            list_user = new MutableLiveData<>();
            fetchUserData(1);
        } else if (page != 0) {
            fetchUserData(page);
        }
        return list_user;
    }

    public void fetchUserData(int page) {


        Call<Example> datumCall = API_Client.getInstanceAPI2().getAllUser(String.valueOf(page));

        datumCall.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                list_user.postValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                list_user.setValue(null);
            }
        });
    }


}

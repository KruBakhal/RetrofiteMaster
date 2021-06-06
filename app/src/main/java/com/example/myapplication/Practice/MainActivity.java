package com.example.myapplication.Practice;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.Constants.WebConstants;
import com.example.myapplication.Practice.API_Interface.API_Interface;
import com.example.myapplication.Practice.Adapter.MyPagerAdapter_Native;
import com.example.myapplication.Practice.Adapter.User_Adapter;
import com.example.myapplication.Practice.Frag.PostData_Fragment;
import com.example.myapplication.Practice.model.ConnectionModel;
import com.example.myapplication.Practice.model.Datum;
import com.example.myapplication.Practice.model.Example;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityMain2Binding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myapplication.Practice.API_Interface.API_Client.getInstanceAPI;

public class MainActivity extends AppCompatActivity implements User_Adapter.CategoryListener {

    RecyclerView rv_Llist;
    private List<Datum> list_user;
    TextView tvConnectivityStatus;
    ViewPager viewPager;
    boolean isOffline = true;
    IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
    public API_Interface api_interface;
    ProgressDialog progressDialog;
    private User_Adapter user_adapter;
    private MyPagerAdapter_Native myPagerAdapter_native;
    private LiveDataConstant viewModelProvider;
    private @NonNull ActivityMain2Binding activityMainBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main2);
        activityMainBinding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        UI(activityMainBinding.getRoot());
        internetSetup();
        viewModelProvider = new ViewModelProvider(this).get(LiveDataConstant.class);
        viewModelProvider.getList_Post(0).observe(this, obsererUser);
    }

    private void internetSetup() {

        ConnectionLiveData connectionLiveData = new ConnectionLiveData(getApplicationContext());
        connectionLiveData.observe(this, new Observer<ConnectionModel>() {
            @Override
            public void onChanged(@Nullable ConnectionModel connection) {
                if (connection.getIsConnected()) {
                    switch (connection.getType()) {
                        case 0:
                            Toast.makeText(MainActivity.this, String.format("Wifi turned ON"), Toast.LENGTH_SHORT).show();
                            isOffline = false;
                            tvConnectivityStatus.setText("Online");
                            break;
                        case 1:
                            isOffline = false;
                            tvConnectivityStatus.setText("Online");
                            Toast.makeText(MainActivity.this, String.format("Mobile data turned ON"), Toast.LENGTH_SHORT).show();
                            break;
                    }
                } else {
                    isOffline = true;
                    tvConnectivityStatus.setText("Offline");
                    Toast.makeText(MainActivity.this, String.format("Connection turned OFF"), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Observer<? super List<Datum>> obsererUser = new Observer<List<Datum>>() {
        @Override
        public void onChanged(List<Datum> data) {
            if (list_user != null && !list_user.isEmpty()) {
                list_user.clear();
                list_user = null;
            }
            list_user = new ArrayList<>();
            list_user.addAll(data);
            user_adapter = new User_Adapter(MainActivity.this, list_user, MainActivity.this);
            rv_Llist.setAdapter(user_adapter);
            user_adapter.selectedCatgory = 0;
            setPager();
        }
    };

    private void fetch_Users(int page) {
        api_interface = getInstanceAPI().create(API_Interface.class);

        Call<Example> datumCall = api_interface.getAllUser(String.valueOf(page));
        progressDialog.show();

        datumCall.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {

                list_user = response.body().getData();
                user_adapter = new User_Adapter(MainActivity.this, list_user, MainActivity.this);
                rv_Llist.setAdapter(user_adapter);
                user_adapter.selectedCatgory = 0;
                setPager();
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Toast.makeText(MainActivity.this, "" + t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }


    private void setPager() {
//        cleanBackStack(viewPager.getId());
        if (list_user == null || list_user.isEmpty())
            return;
        List<Fragment> listFRag = new ArrayList<>();
        for (Datum datum : list_user) {
            Log.d("TAG", "onResponse: " + datum.toString());
            listFRag.add(PostData_Fragment.newInstance(String.valueOf(datum.getId()), datum.getName()));
        }
        if (myPagerAdapter_native != null) {
            myPagerAdapter_native.clear();
        }
        myPagerAdapter_native = new MyPagerAdapter_Native(getSupportFragmentManager(),
                MainActivity.this, listFRag);

        viewPager.setAdapter(myPagerAdapter_native);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(1);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                user_adapter.selectedCatgory = position;
                user_adapter.notifyDataSetChanged();
                rv_Llist.scrollToPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void UI(View root) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        rv_Llist = root.findViewById(R.id.rv_Llist);
        tvConnectivityStatus = root.findViewById(R.id.tvConnectivityStatus);
        viewPager = root.findViewById(R.id.viewPager);
//        rv_Llist=activityMainBinding.rvList;
//        rv_Llist=activityMainBinding.rvList;
        rv_Llist.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

    }

    @Override
    public void onClickItem(int position) {
        viewPager.setCurrentItem(position);
       /* if (progressDialog != null && !progressDialog.isShowing())
            progressDialog.show();


        Call<Post_Example> post_exampleCall = api_interface.getSpecific_User_Post(list_user.get(position).getId().toString());
        post_exampleCall.enqueue(new Callback<Post_Example>() {
            @Override
            public void onResponse(Call<Post_Example> call, Response<Post_Example> response) {

//                PostAdapter postAdapter = new PostAdapter(MainActivity.this, response.body().getData(), null);
//                viewPager.setAdapter(postAdapter);
                user_adapter.selectedCatgory = 0;
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Post_Example> call, Throwable t) {
                progressDialog.dismiss();
            }
        });*/

    }

    @Override
    protected void onResume() {
        super.onResume();
//        registerReceiver(broadcastInternet, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        unregisterReceiver(broadcastInternet);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private BroadcastReceiver broadcastInternet = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                if (WebConstants.isNetworkAvailable(context)) {
                    isOffline = false;
                    tvConnectivityStatus.setText("Online");
                } else {
                    isOffline = true;
                    tvConnectivityStatus.setText("Offline");
                }
            }
        }
    };

    public void fecth1(View view) {
        viewModelProvider.getList_Post(1).observe(this, obsererUser);
    }

    public void fecth2(View view) {
        viewModelProvider.getList_Post(2).observe(this, obsererUser);
    }

    public void fecth3(View view) {
        viewModelProvider.getList_Post(3).observe(this, obsererUser);
    }
}
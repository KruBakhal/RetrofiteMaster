package com.example.myapplication.Practice.Frag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Practice.Adapter.PostAdapter;
import com.example.myapplication.Practice.PostModel.Post_Example;
import com.example.myapplication.R;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myapplication.Practice.API_Interface.API_Client.getInstanceAPI2;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostData_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostData_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView rv_post;

    public PostData_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostData_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostData_Fragment newInstance(String param1, String param2) {
        PostData_Fragment fragment = new PostData_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_data_, container, false);
        rv_post = view.findViewById(R.id.rv_post);
        rv_post.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Call<Post_Example> post_exampleCall = getInstanceAPI2().getSpecific_User_Post
                (mParam1);

        post_exampleCall.enqueue(new Callback<Post_Example>() {
            @Override
            public void onResponse(Call<Post_Example> call, Response<Post_Example> response) {

                if (response.body().getData() == null || response.body().getData().size() == 0) {
                    rv_post.setAdapter(null);
                } else {
                    PostAdapter postAdapter = new PostAdapter(getContext(), response.body().getData(), null);
                    rv_post.setAdapter(postAdapter);
                }
//                user_adapter.selectedCatgory = 0;
//                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Post_Example> call, Throwable t) {

//                progressDialog.dismiss();
            }
        });

    }
}
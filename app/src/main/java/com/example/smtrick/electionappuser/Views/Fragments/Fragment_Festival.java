package com.example.smtrick.electionappuser.Views.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smtrick.electionappuser.Callback.CallBack;
import com.example.smtrick.electionappuser.Constants.Constants;
import com.example.smtrick.electionappuser.Models.PostVO;
import com.example.smtrick.electionappuser.R;
import com.example.smtrick.electionappuser.Repositories.Impl.LeedRepositoryImpl;
import com.example.smtrick.electionappuser.Repositories.LeedRepository;
import com.example.smtrick.electionappuser.Views.Adapters.Post_Adapter;
import com.example.smtrick.electionappuser.Views.ProgressDialogClass;

import java.util.ArrayList;


public class Fragment_Festival extends Fragment {

    private RecyclerView recycleSocial;
    private LeedRepository leedRepository;
    private ArrayList<PostVO> postVOArrayList;
    Post_Adapter adapter;
    private ProgressDialogClass progressDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_social, container, false);

        leedRepository = new LeedRepositoryImpl();
        postVOArrayList = new ArrayList<>();
        progressDialog = new ProgressDialogClass(getActivity());

        recycleSocial = view.findViewById(R.id.social_recycle);
        recycleSocial.setHasFixedSize(true);
        recycleSocial.setLayoutManager(new LinearLayoutManager(getContext()));

        readSocialPosts();
        return view;
    }

    private void readSocialPosts() {

        progressDialog.showDialog(getString(R.string.loading), getString(R.string.PLEASE_WAIT));

        leedRepository.readPostsByCategory(Constants.CAT_FESTIVAL, new CallBack() {
            @Override
            public void onSuccess(Object object) {

                if (object != null){
                    postVOArrayList = (ArrayList<PostVO>) object;
                }

                serAdapter(postVOArrayList);
                progressDialog.dismissDialog();
            }

            @Override
            public void onError(Object object) {
                progressDialog.dismissDialog();
            }
        });
    }

    private void serAdapter(ArrayList<PostVO> leedsModels) {
        if (leedsModels != null) {
            if (adapter == null) {
                adapter = new Post_Adapter(getActivity(), leedsModels);
                recycleSocial.setAdapter(adapter);
                //   onClickListner();
            } else {
                ArrayList<PostVO> leedsModelArrayList = new ArrayList<>();
                leedsModelArrayList.addAll(leedsModels);
                adapter.reload(leedsModelArrayList);
            }
        }
    }
}

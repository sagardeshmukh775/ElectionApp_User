package com.example.smtrick.electionappuser.Views.Fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.smtrick.electionappuser.Listeners.OnFragmentInteractionListener;
import com.example.smtrick.electionappuser.R;
import com.example.smtrick.electionappuser.Views.Adapters.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class Tab_Fragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    public Tab_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mListener != null) {
            mListener.onFragmentInteraction("Members");
        }
        View view = inflater.inflate(R.layout.tab_layout, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragement(new Fragment_Social(), "Social");
        viewPagerAdapter.addFragement(new Fragment_Agricultural(), "Agricultural");
        viewPagerAdapter.addFragement(new Fragment_Festival(), "Festival");
        viewPagerAdapter.addFragement(new Fragment_Business(), "Business");
        viewPagerAdapter.addFragement(new Fragment_Poytical(), "Polytical");
        viewPagerAdapter.addFragement(new Fragment_Educational(), "Educational");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        if (isNetworkAvailable()){
//            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
//        PagerTabStrip pagerTabStrip = (PagerTabStrip) view.findViewById(R.id.viewPager);
//        pagerTabStrip.setDrawFullUnderline(true);
//        pagerTabStrip.setTabIndicatorColor(Color.BLUE);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            // NOTE: This is the part that usually gives you the error
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

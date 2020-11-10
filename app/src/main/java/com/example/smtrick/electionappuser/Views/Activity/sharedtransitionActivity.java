package com.example.smtrick.electionappuser.Views.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.smtrick.electionappuser.R;
import com.github.chrisbanes.photoview.PhotoView;


public class sharedtransitionActivity extends AppCompatActivity {
    PhotoView photoView;
    ProgressBar imageprogress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharedtransition);

        imageprogress = (ProgressBar) findViewById(R.id.image_progress);
        imageprogress.setVisibility(View.VISIBLE);
        photoView = (PhotoView) findViewById(R.id.imageView);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String resid = bundle.getString("url");
            Glide.with(this)
                    .load(resid)
                    .placeholder(R.drawable.loading)
                    .into(photoView);


            imageprogress.setVisibility(View.GONE);

            }
    }
}

package com.jorgereina.moviedbapp2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    private TextView titleTv;
    private TextView overviewTv;
    private ImageView backdropIv;
    private String baseUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        titleTv = (TextView) findViewById(R.id.details_title_tv);
        overviewTv = (TextView) findViewById(R.id.details_overview_tv);
        backdropIv = (ImageView) findViewById(R.id.backdrop_iv);
        baseUrl = "http://image.tmdb.org/t/p/w1280/";

        Intent intent = getIntent();
        String titleStr = intent.getStringExtra("title");
        String overviewStr = intent.getStringExtra("overview");
        String imageStr = intent.getStringExtra("backdrop");

        titleTv.setText(titleStr);
        overviewTv.setText(overviewStr);
        Picasso.with(this).load(baseUrl+imageStr).placeholder(R.mipmap.ic_launcher).fit().into(backdropIv);




    }
}

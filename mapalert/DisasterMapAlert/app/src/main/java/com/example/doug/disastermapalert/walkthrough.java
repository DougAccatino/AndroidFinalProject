package com.example.doug.disastermapalert;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.support.v4.view.ViewPager.*;

public class walkthrough extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mDottLayout;

    private TextView[] mDots;

    private com.example.doug.disastermapalert.SliderAdapter sliderAdapter;

    private Button mNextBtn;
    private Button mBackBtn;

    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough);

        mSlideViewPager = (ViewPager) findViewById(R.id.SlideViewPager);
        mDottLayout = (LinearLayout) findViewById(R.id.dotsLayout);

        mNextBtn= (Button) findViewById(R.id.nextBtn) ;
        mBackBtn = (Button) findViewById(R.id.prevBtn);

        sliderAdapter = new com.example.doug.disastermapalert.SliderAdapter(this);
        mSlideViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);

        mNextBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mSlideViewPager.setCurrentItem(mCurrentPage + 1);


            }

        });

        mBackBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                mSlideViewPager.setCurrentItem(mCurrentPage - 1);
            }
        } );
    }

    public void addDotsIndicator(int position){
        mDots = new TextView[3];
        mDottLayout.removeAllViews();

        for(int i = 0; i < mDots.length; i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));


            mDottLayout.addView(mDots[i]);

        }
        if(mDots.length > 0){

            mDots[position].setTextColor(getResources().getColor(R.color.primaryTextColor));
        }

    }


    ViewPager.OnPageChangeListener viewListener = new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);
            mCurrentPage = i;
            if(i==0){
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(false);
                mBackBtn.setVisibility(View.INVISIBLE);

                mNextBtn.setText("Next");
                mBackBtn.setText("");
            }else if(i == mDots.length -1){

                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.VISIBLE);

                mNextBtn.setText("Finish");
                mBackBtn.setText("Back");

            Intent intent = new Intent(walkthrough.this, HOME.class);
            startActivity(intent);



            }else{

                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.VISIBLE);

                mNextBtn.setText("Next");
                mBackBtn.setText("Back");

            }



        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };



}





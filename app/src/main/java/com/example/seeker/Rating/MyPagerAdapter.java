package com.example.seeker.Rating;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.seeker.R;

import java.util.List;

public class MyPagerAdapter extends PagerAdapter {

//    private SwipeDirection direction;
    Context context;
    List<PagerModel> pagerArr;
    List<PagerModel> responses;
    LayoutInflater inflater;
    RatingBar r;

    public MyPagerAdapter(Context context, List<PagerModel> pagerArr) {
        this.context = context;
        this.pagerArr = pagerArr;

        inflater = ((Activity) context).getLayoutInflater();
//        this.direction = SwipeDirection.all;

    }


    @Override
    public int getCount() {
        return pagerArr.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = inflater.inflate(R.layout.pager_list_item, container, false );

        TextView tv = view.findViewById(R.id.rate_q);
        view.setTag(position);

        (container).addView(view);

        PagerModel model = pagerArr.get(position);
        tv.setText(model.getTitle());
        r = view.findViewById(R.id.rb);
        getR(r);
        return view;

    }

    public float getR(RatingBar rb){

        if(rb.getRating() > 0)
            return rb.getRating();
        else return 0;
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((View) object);
    }




}//End class


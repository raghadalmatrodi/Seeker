package com.example.seeker.PostProject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.example.seeker.R;

public class PaymentTypeFragment extends Fragment {
    private View view;

    private RelativeLayout fixedPriceBtn;
    private RelativeLayout hourlyBtn;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_payment_type, container, false);

        init();


        fixedPriceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //we will do something

            }
        });

        hourlyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //we will do something
            }
        });


        return view;
    }//End of onCreateView()

    private void init() {

        fixedPriceBtn = view.findViewById(R.id.fixed_price);
        hourlyBtn = view.findViewById(R.id.hourly);
    }//End of init()

}

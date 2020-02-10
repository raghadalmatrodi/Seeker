package com.example.seeker.PostProject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.example.seeker.R;

public class PaymentTypeFragment extends Fragment {
    private View view;
    private PaymentListener paymentListener;
    private RelativeLayout fixedPriceBtn;
    private RelativeLayout hourlyBtn;
    private BackPaymentListener backPaymentListener;
    private ImageView backBtn;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_payment_type, container, false);

        init();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                backPaymentListener.onBackPaymentClick();
            }
        });

        fixedPriceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                paymentListener.onPaymentTypeItemSelected("FixedPrice");

            }
        });

        hourlyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                paymentListener.onPaymentTypeItemSelected("Hourly");
            }
        });


        return view;
    }//End of onCreateView()

    @Override
    public void onResume() {
        super.onResume();


    }

    private void init() {

        fixedPriceBtn = view.findViewById(R.id.fixed_price);
        hourlyBtn = view.findViewById(R.id.hourly);
        backBtn = view.findViewById(R.id.project_payment_back);
    }//End of init()


    public void setListener (PaymentListener paymentListener, BackPaymentListener backPaymentListener)
    {
        this.paymentListener = paymentListener;
        this.backPaymentListener = backPaymentListener;
    }


    public interface PaymentListener{

        void onPaymentTypeItemSelected(String paymentType);
    }//End of interface

    public interface BackPaymentListener{

        void onBackPaymentClick();
    }

//        @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        paymentListener = (PaymentListener) getActivity();
//    }

}

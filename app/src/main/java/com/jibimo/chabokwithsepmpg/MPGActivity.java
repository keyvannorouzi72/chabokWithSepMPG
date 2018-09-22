package com.jibimo.chabokwithsepmpg;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ir.sep.mobilepayment.binder.SepBundleKeys;
import ir.sep.mobilepayment.binder.SepPaymentService;
import ir.sep.paymentservices.req.IPaymentRequest;
import ir.sep.paymentservices.res.IPaymentResponseHandler;

public class MPGActivity extends AppCompatActivity {
    private Button mStartMPG_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpg);

        mStartMPG_Button = findViewById(R.id.button_main_start_mpg);
        mStartMPG_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMPG();
            }
        });
    }

    private void showMPG() {


//        final int i = amount.intValue();
        Intent intent = new Intent(MPGActivity.this, SepPaymentService.class);
        intent.setComponent(new ComponentName("com.jibimo.chabokwithsepmpg", SepPaymentService.class.getCanonicalName()));
        intent.setPackage("com.jibimo.chabokwithsepmpg");
        intent.setAction("ir.sep.mobilepayment.action.paymentrequest");

        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                IPaymentRequest paymentRequest = IPaymentRequest.Stub.asInterface(iBinder);

                try {
                    Bundle bundle = new Bundle();
                    bundle.putString(SepBundleKeys.REQ_MERCHANT_ID, "");
                    bundle.putString(SepBundleKeys.REQ_PAYMENT_PARAMS, "0|20000");
                    bundle.putString(SepBundleKeys.REQ_ADDITIONAL_DATA, "324165");
                    bundle.putString(SepBundleKeys.REQ_USER_MSISDN, "09370265166");
                    paymentRequest.startPaymentProcess(bundle, new IPaymentResponseHandler.Stub() {
                        @Override
                        public void onResult(Bundle bundle) {
                        }
                    });
                } catch (Exception e) {
                }


            }


            @Override
            public void onServiceDisconnected(ComponentName componentName) {
            }
        }, Service.BIND_AUTO_CREATE);
    }
}

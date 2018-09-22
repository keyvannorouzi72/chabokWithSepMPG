package com.jibimo.chabokwithsepmpg;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.adpdigital.push.AdpPushClient;
import com.adpdigital.push.ConnectionStatus;
import com.adpdigital.push.EventMessage;

import ir.sep.mobilepayment.binder.SepBundleKeys;
import ir.sep.mobilepayment.binder.SepPaymentService;
import ir.sep.paymentservices.req.IPaymentRequest;
import ir.sep.paymentservices.res.IPaymentResponseHandler;

public class MainActivity extends AppCompatActivity {
    private AdpPushClient chabok;

    private Button mStartMPGActivity_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStartMPGActivity_Button = findViewById(R.id.button_main_start_mpg);
        mStartMPGActivity_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MPGActivity.class));
            }
        });


        chabok = ((APP) getApplication()).getPushClient();

        if (chabok != null && chabok.hasProtectedAppSupport()) {

            chabok.showProtectedAppSettings(MainActivity.this, getString(R.string.app_name), null, null);


            chabok.subscribe("default", new com.adpdigital.push.Callback() {
                @Override
                public void onSuccess(Object o) {
                }

                @Override
                public void onFailure(Throwable throwable) {

                }
            });

        }

    }

    private void fetchAndUpdateConnectionStatus() {
        if (chabok == null) {
            return;
        }
        chabok.getStatus(new com.adpdigital.push.Callback<ConnectionStatus>() {
            @Override
            public void onSuccess(ConnectionStatus connectionStatus) {
                updateConnectionStatus(connectionStatus);

            }

            @Override
            public void onFailure(Throwable throwable) {
            }
        });
    }

    private void updateConnectionStatus(ConnectionStatus status) {

//        TextView connectionStatus = (TextView) findViewById(R.id.connection_status);
//
        switch (status) {
            case CONNECTED:
                break;

            case CONNECTING:

                break;

            case DISCONNECTED:

                break;
        }
    }


    private void attachPushClient() {
        if (chabok != null) {
            chabok.addListener(this);
        }

        fetchAndUpdateConnectionStatus();

    }


    @Override
    protected void onPause() {
        super.onPause();
        detachPushClient();
    }


    @Override
    protected void onResume() {

        super.onResume();
        attachPushClient();
    }

    @Override
    protected void onDestroy() {
        detachPushClient();
        super.onDestroy();
    }

    private void detachPushClient() {
        if (chabok != null) {
            chabok.removePushListener(this);
        }

    }

    public void onEvent(final EventMessage eventMessage) {

    }


    public void onEvent(final ConnectionStatus status) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                updateConnectionStatus(status);
//            }
//        });
    }
}

package com.example.aidl.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private IMyAidlInterface iMyAidlInterface;
    private View bindService;
    private View btn;
    private View unbindService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindService = findViewById(R.id.textView);
        btn = findViewById(R.id.textView2);
        unbindService = findViewById(R.id.textView3);

        bindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bindService();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int sum = iMyAidlInterface.add(1, 2);
                    Log.i("TAG_AIDL", sum + "");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        unbindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unBinderService();
            }
        });

    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            iMyAidlInterface = null;
        }
    };

    private void bindService() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.example.aidl.client",
                "com.example.aidl.aidldemo.IRemoteService"));
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void unBinderService() {
        unbindService(serviceConnection);
    }

}
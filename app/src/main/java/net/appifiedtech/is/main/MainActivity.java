package net.appifiedtech.is.main;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MyResultReceiver.ResultReceiverListener {
    private MyResultReceiver myResultReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myResultReceiver = new MyResultReceiver(new Handler());
        myResultReceiver.setContext(MainActivity.this);
    }

    public void startService(View view) {
        Intent intent = new Intent(MainActivity.this,MyIntentService.class);
        intent.putExtra("url",Constants.URL_APPS_LIST);
        intent.putExtra("receiver",myResultReceiver);
        startService(intent);
    }

    public void stopService(View view) {

    }

    @Override
    public void setResult(int resultCode, Bundle resultData) {
        if(resultCode == 1){
            String dataResp =resultData.getString("dataResp");
            Toast.makeText(MainActivity.this, ""+dataResp, Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
        }
    }
}

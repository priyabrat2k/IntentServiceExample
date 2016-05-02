package net.appifiedtech.is.main;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Priyabrat on 29-04-2016.
 */
public class MyIntentService extends IntentService {

    private static final String TAG = "MyIntentService";
    private ResultReceiver resultReceiver;

    public MyIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        resultReceiver = intent.getParcelableExtra("receiver");
        String dataUrl = intent.getStringExtra("url");
        String response = fetchHttpRequest(dataUrl);
        Bundle bundle = new Bundle();
        bundle.putString("dataResp",response);
        if(response != null)
            resultReceiver.send(1,bundle);
        else
            resultReceiver.send(0,bundle);
    }

    public String fetchHttpRequest(String dataUrl){
        String response = null;
        String datStr = "";
        String params = "";
        try {
            URL url = new URL(dataUrl);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            httpConnection.setRequestProperty("Content-Length",String.valueOf(params.getBytes().length));
            httpConnection.setUseCaches(false);
            httpConnection.setDoInput(true);
            httpConnection.setDoOutput(true);
            OutputStream outputStream = httpConnection.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeBytes(params);
            dataOutputStream.flush();
            dataOutputStream.close();
            int respCode = httpConnection.getResponseCode();
            if(respCode == 200)
            {
                InputStream inputStream = httpConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null)
                {
                    stringBuffer.append(line);
                    stringBuffer.append("\r");
                }
                bufferedReader.close();
                response = stringBuffer.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}

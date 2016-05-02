package net.appifiedtech.is.main;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * Created by Priyabrat on 02-05-2016.
 */
public class MyResultReceiver extends ResultReceiver {

    private ResultReceiverListener listener;

    public MyResultReceiver(Handler handler) {
        super(handler);
    }

    public void setContext(Activity activity){
        listener = (ResultReceiverListener) activity;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        super.onReceiveResult(resultCode, resultData);
        if(listener != null)
            listener.setResult(resultCode,resultData);
    }

    public interface ResultReceiverListener {
        public void setResult(int resultCode, Bundle resultData);
    }
}

package utils;

import android.content.Context;
import android.os.AsyncTask;

import com.badoo.roman.badootransactionviewer.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Roman on 13-Mar-17.
 */

public class LoadAssets extends AsyncTask<String, Void, String> {

    private AsyncResponse delegate = null;
    private Context context;

    public interface AsyncResponse {
        void processFinish(String output);
    }

    public LoadAssets(Context context, AsyncResponse delegate){
        this.delegate = delegate;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {

        String json;
        try {
            InputStream is = context.getAssets().open(params[0]);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, context.getString(R.string.encoding));
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }

    @Override
    protected void onPostExecute(String result) {
        delegate.processFinish(result);
    }

}
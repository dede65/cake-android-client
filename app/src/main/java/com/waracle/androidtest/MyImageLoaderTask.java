package com.waracle.androidtest;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyImageLoaderTask extends AsyncTask<String, Integer, byte[]> {

    private ImageView imageView;

    public MyImageLoaderTask(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected byte[] doInBackground(String... strings) {

        HttpURLConnection connection = null;
        InputStream inputStream = null;
        try {
            try {
                connection = (HttpURLConnection) new URL(strings[0]).openConnection();
                // Read data from workstation
                inputStream = connection.getInputStream();
            } catch (IOException e) {
                // Read the error from the workstation
                assert connection != null;
                inputStream = connection.getErrorStream();
            }

            // Can you think of a way to make the entire
            // HTTP more efficient using HTTP headers??

            return StreamUtils.readUnknownFully(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close the input stream if it exists.
            StreamUtils.close(inputStream);

            // Disconnect the connection
            assert connection != null;
            connection.disconnect();
        }

        return null;
    }

    @Override
    protected void onPostExecute(byte[] bytes) {
        super.onPostExecute(bytes);
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));

    }
}

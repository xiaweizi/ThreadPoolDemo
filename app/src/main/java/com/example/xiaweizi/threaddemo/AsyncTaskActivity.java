package com.example.xiaweizi.threaddemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class AsyncTaskActivity extends AppCompatActivity {

    private static final String TAG = "AsyncTask::";
    private TextView mTvContent;
    private CalculateSizeTask calculateSizeTask;
    private static final String[] DATA = new String[]{"1234", "123456789", "1", "12", "123456789", "123", "123456"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        mTvContent = findViewById(R.id.content);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start:
                calculateSizeTask = new CalculateSizeTask(this);
                calculateSizeTask.execute(DATA);
                break;
            case R.id.cancel:
                calculateSizeTask.cancel(false);
                break;
        }
    }

    static class CalculateSizeTask extends AsyncTask<String, Float, Long> {

        WeakReference<AsyncTaskActivity> mActivity;

        CalculateSizeTask(AsyncTaskActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        protected Long doInBackground(String... urls) {
            Log.i(TAG, "doInBackground: " + Thread.currentThread().getName());
            int length = urls.length;
            long totalSize = 0;
            for (int i = 0; i < length; i++) {
                publishProgress(i * 1.0f / length);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                totalSize += urls[i].length();
            }
            return totalSize;
        }

        @Override
        protected void onProgressUpdate(Float... values) {
            Log.i(TAG, "onProgressUpdate: " + values[0]);
            updateContent(String.format("%s%%", values[0]));
        }

        @Override
        protected void onPostExecute(Long aLong) {
            updateContent("Complete! totalSize:\t" + aLong);
        }

        @Override
        protected void onPreExecute() {
            updateContent("loading....");
        }

        @Override
        protected void onCancelled(Long aLong) {
            super.onCancelled(aLong);
            updateContent("已取消 " + aLong);
        }

        void updateContent(String content) {
            AsyncTaskActivity theActivity = mActivity.get();
            if (theActivity == null || theActivity.isFinishing()) {
                return;
            }
            theActivity.mTvContent.setText(content);
        }
    }
}

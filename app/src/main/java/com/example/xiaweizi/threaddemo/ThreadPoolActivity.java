package com.example.xiaweizi.threaddemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPoolActivity extends AppCompatActivity {

    private static final String TAG = "ThreadPool::";
    private TextView mTvContent;
    private StringBuilder sb = new StringBuilder();
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool);
        mHandler = new Handler(Looper.getMainLooper());
        mTvContent = findViewById(R.id.content);
    }

    private void fixedThreadPool() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 10; i++) {
            executorService.execute(getRunnable());
        }
    }

    private void singleThreadPool() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            executorService.execute(getRunnable());
        }
    }

    private void scheduledThreadPool() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
//        scheduledExecutorService.schedule(getRunnable(), 1, TimeUnit.SECONDS);
        scheduledExecutorService.scheduleAtFixedRate(getRunnable(), 1, 1, TimeUnit.SECONDS);
    }

    private void cacheThreadPool() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 120; i++) {
            executorService.execute(getRunnable());
        }
    }

    private Runnable getRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                sb.append(Thread.currentThread().getName()).append("\n");
                printLog();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void printLog(){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mTvContent.setText(sb.toString());
            }
        });
    }

    public void threadPool(View view) {
        sb.delete(0, sb.length());
        switch (view.getId()) {
            case R.id.fixed:
                fixedThreadPool();
                break;
            case R.id.single:
                singleThreadPool();
                break;
            case R.id.scheduled:
                scheduledThreadPool();
                break;
            case R.id.cached:
                cacheThreadPool();
                break;
        }
    }
}

package com.example.exampleservice;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;




@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public  class ExampleService extends JobService {

    private static final String TAG = "Example Service";

    private boolean jobCancelled =false;


    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG,"Job Started");
        doBackgroundWork(jobParameters);
        return true;
    }
    private void doBackgroundWork (final JobParameters jobParameters){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<10;i++);{
                    Log.d(TAG,"run:");
                    if (jobCancelled){
                         return;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.d(TAG,"Job Finished");
                jobFinished(jobParameters,false);

            }
        }).start();


         }
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG,"Job Cancelled Before Completion");
        jobCancelled=false;
        return false;
    }
}

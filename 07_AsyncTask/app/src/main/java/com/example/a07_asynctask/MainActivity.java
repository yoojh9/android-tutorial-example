package com.example.a07_asynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        MyTask myTask = new MyTask();
        myTask.execute();
    }


    class MyTask extends AsyncTask<Void, Integer, Void> {

        // Integer ... : 배열
        @Override
        protected void onProgressUpdate(Integer... values) {
            btnStart.setText("count: " + values[0]);
        }

        // 백그라운드에서 어떤 작업을 할지 (Thread 영역에 해당 - myThread의 run()부분과 동일)
        @Override
        protected Void doInBackground(Void... voids) {
            for(int i=0; i<=100; i++){
                Log.d("myTask", "count : "+ i);
                publishProgress(i);  // 중간에 값을 넘김 -> onProgressUpdate() 에서 데이터를 받음

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }


    }
}

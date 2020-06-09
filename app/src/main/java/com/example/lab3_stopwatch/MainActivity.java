package com.example.lab3_stopwatch;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Properties
    private Handler timeHandler;
    private ArrayAdapter<String> itemsAdapter;
    private TextView txtTimer;
    private Button btnStartPause, btnLapReset;

    //Vars to keep track of time
    private long millisecondTime, startTime, pausedTime, updateTime = 0;

    //Vars to display time
    private int seconds, minutes, milliseconds;

    //Vars to handle Stopwatch state
    private boolean stopWatchStarted, stopWatchPaused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // only used once so it wont be global variable
        ListView lvLaps;

        //time handler is bound to a thread
        //used to schedule our runnable after particular actions
        timeHandler = new Handler();

        //sets the layout for each item of the list view
        itemsAdapter = new ArrayAdapter<>( this, android.R.layout.simple_list_item_1);
        txtTimer= findViewById(R.id.txtTimer);
        btnStartPause=findViewById(R.id.btnStartPause);
        btnLapReset = findViewById(R.id.btnLapReset);
        lvLaps=findViewById(R.id.lvLaps);

        //binds data from adapter to the ListView
        lvLaps.setAdapter(itemsAdapter);

        //handles the stopwatch actions for starting and stopping
        btnStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if start or pause

                if(stopWatchStarted || stopWatchPaused) {
                    stopWatchStarted =true ;
                    stopWatchPaused=false ;

                    startTime= SystemClock.uptimeMillis();

                    //enqueue the runnable to be called by the message queue after the specified time elapses.
                    //message queue lives on the main thread of process.
                    timeHandler.postDelayed(timerRunnable,0);

                    //switch label strings

                    btnStartPause.setText(R.string.lblPause);
                    btnLapReset.setText(R.string.btnLap);
                }
                else {
                    pausedTime += millisecondTime;
                    stopWatchPaused = true;
                    //remove pending post of timer runnable in message queue
                    timeHandler.removeCallbacks(timerRunnable);

                    //switch label strings
                    btnStartPause.setText(R.string.lblStart);
                    btnLapReset.setText(R.string.lblReset);
                }
            }
        });

        //handles the stop watch action for creating a new lap and resetting

        btnLapReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //check if the action is to create a new lap or reset the stopwatch

                if(stopWatchStarted && !stopWatchPaused){
                    String lapTime = minutes + ":" + String.format("%02d",seconds) + ":" + String.format("%03d", milliseconds);
                    itemsAdapter.add(lapTime);
                }
                else if (stopWatchStarted) {
                    stopWatchStarted = false;
                    stopWatchPaused = false;

                    //remove pending post of timerRunnable in message queue
                    timeHandler.removeCallbacks(timerRunnable);

                    //reset all values
                    millisecondTime = 0;
                    startTime = 0;
                    pausedTime = 0;
                    updateTime = 0;
                    seconds = 0;
                    minutes = 0;
                    milliseconds = 0;


                    //switch label strings
                    txtTimer.setText(R.string.lblTimer);
                    btnLapReset.setText(R.string.btnLap);

                    //wipe resources

                    itemsAdapter.clear();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Timer hasn't started yet!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            millisecondTime = SystemClock.uptimeMillis() - startTime;

            //values used to keep track of stopwatch time left off

            updateTime = pausedTime + millisecondTime;
            milliseconds = (int) (updateTime %1000);
            seconds = (int) (updateTime / 1000);


            //convert values to display

            minutes =       seconds/60;
            seconds = seconds % 60;
            String updatedTime = minutes +  ":" + String.format("%02d", seconds) + ":" + String.format("%03d", milliseconds);
            txtTimer.setText(updatedTime);

            //enqueues the runnable to be called by the message queues after the specified amount of time elapses
            timeHandler.postDelayed(this, 0);
        }
    };
}

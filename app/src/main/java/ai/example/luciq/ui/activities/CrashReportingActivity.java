package ai.example.luciq.ui.activities;

import android.os.Bundle;

import ai.example.luciq.BaseActivity;
import ai.example.luciq.R;
import ai.luciq.library.logging.LuciqLog;

public class CrashReportingActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crash_reporting);

        //Luciq logs
        LuciqLog.d("Crash reporting activity - Created");


    }
}

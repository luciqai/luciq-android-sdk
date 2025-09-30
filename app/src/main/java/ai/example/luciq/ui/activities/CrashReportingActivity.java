package ai.example.luciq.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import ai.example.luciq.BaseActivity;
import ai.example.luciq.R;
import ai.luciq.bug.BugReporting;
import ai.luciq.crash.CrashReporting;
import ai.luciq.crash.models.LuciqNonFatalException;
import ai.luciq.library.Feature;
import ai.luciq.library.logging.LuciqLog;

public class CrashReportingActivity extends BaseActivity {

    Switch crashReportingSwitch;
    Switch anrSwitch;
    Switch ndkSwitch;
    EditText exceptionNameEditText;
    EditText fingerPrintEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crash_reporting);
        crashReportingSwitch = findViewById(R.id.crash_reporting_switch);
        anrSwitch = findViewById(R.id.anr_switch);
        ndkSwitch = findViewById(R.id.ndk_switch);
        exceptionNameEditText = findViewById(R.id.set_exception_name_edit_text);
        fingerPrintEditText = findViewById(R.id.set_finger_print_edit_text);

        //Luciq logs
        LuciqLog.d("Crash reporting activity - Created");
        crashReportingSwitch.setOnCheckedChangeListener((view, isChecked) -> {
            boolean bugReportingState = isChecked;
            onCrashReportingState(view, bugReportingState);
        });
        anrSwitch.setOnCheckedChangeListener((view, isChecked) -> {
            boolean anrState = isChecked;
            onANRState(view, anrState);
        });
        ndkSwitch.setOnCheckedChangeListener((view, isChecked) -> {
            boolean ndkState = isChecked;
            onNDKState(view, ndkState);
        });

    }
    public void onCrashReportingState(View view, boolean crashReportingState) {
        if(!crashReportingState)
        {
            CrashReporting.setState(Feature.State.DISABLED);
        }
        else
        {
            CrashReporting.setState(Feature.State.ENABLED);
        }
    }
    public void onANRState(View view, boolean anrState) {
        if(!anrState)
        {
            CrashReporting.setAnrState(Feature.State.DISABLED);
        }
        else
        {
            CrashReporting.setAnrState(Feature.State.ENABLED);
        }
    }
    public void onNDKState(View view, boolean ndkState) {
        if(!ndkState)
        {
            CrashReporting.setNDKCrashesState(Feature.State.DISABLED);
        }
        else
        {
            CrashReporting.setNDKCrashesState(Feature.State.ENABLED);
        }
    }
    public void onReportNonFatalClicked(View view) {
        String exceptionName = exceptionNameEditText.getText().toString();
        String fingerPrint = fingerPrintEditText.getText().toString();
        if(exceptionName.isEmpty())
        {
            exceptionName = "Test exception";
        }
        LuciqNonFatalException.Builder builder = new LuciqNonFatalException.Builder(
                new NullPointerException(exceptionName)
        ).setLevel(LuciqNonFatalException.Level.CRITICAL);
        if (!fingerPrint.isEmpty()) {
            builder.setFingerprint(fingerPrint);
        }
        LuciqNonFatalException exception = builder.build();
        CrashReporting.report(exception);
        LuciqLog.d("Exception name is: " + exceptionName);
    }
}

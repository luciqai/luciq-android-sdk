package ai.example.luciq.ui.activities;

import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.appcompat.widget.Toolbar;

import ai.example.luciq.BaseActivity;
import ai.example.luciq.R;
import ai.luciq.bug.BugReporting;
import ai.luciq.library.Feature;
import ai.luciq.library.Luciq;
import ai.luciq.library.extendedbugreport.ExtendedBugReport;
import ai.luciq.library.logging.LuciqLog;

public class BugReportingActivity extends BaseActivity {

    EditText emailEditText;
    Spinner extendedBugReportSpinner;
    Switch bugReportingSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bug_reporting);
        emailEditText = findViewById(R.id.set_email_edit_text);
        extendedBugReportSpinner = findViewById(R.id.extended_bug_report_state);
        bugReportingSwitch = findViewById(R.id.bug_reporting_switch);
        //Luciq logs
        LuciqLog.d("Bug reporting activity - Created");
        extendedBugReportSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String optionSelected = parent.getItemAtPosition(position).toString();
                onSelectExtendedBugReportOption(view, optionSelected);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        bugReportingSwitch.setOnCheckedChangeListener((view, isChecked) -> {
                boolean bugReportingState = isChecked;
            onBugReportingState(view, bugReportingState);
        });
    }

    public void onSendBugClicked(View view) {
        BugReporting.show(BugReporting.ReportType.BUG);
    }
    public void onSendFeedbackClicked(View view) {
        BugReporting.show(BugReporting.ReportType.FEEDBACK);
    }
    public void onAskQuestionClicked(View view) {
        BugReporting.show(BugReporting.ReportType.QUESTION);
    }
    public void onSetEmailClicked(View view) {
        String email = emailEditText.getText().toString().trim();
        if(email.isEmpty())
        {
            LuciqLog.d("Email field can't be empty");
            emailEditText.setError("Email is required");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            LuciqLog.d("Invalid Email");
            emailEditText.setError("Invalid Email");
        }
        else {
            Luciq.identifyUser(null,email,null);
            LuciqLog.d("Valid email set: " + email);
        }
    }
    public void onSelectExtendedBugReportOption(View view, String option)
    {
        switch (option){
            case "Enabled and required":
                BugReporting.setExtendedBugReportState(ExtendedBugReport.State.ENABLED_WITH_REQUIRED_FIELDS);
                break;
            case "Enabled and optional":
                BugReporting.setExtendedBugReportState(ExtendedBugReport.State.ENABLED_WITH_OPTIONAL_FIELDS);
                break;
            case "Disabled":
                BugReporting.setExtendedBugReportState(ExtendedBugReport.State.DISABLED);
                break;
        }
    }
    public void onBugReportingState(View view, boolean bugReportingState) {
        if(!bugReportingState)
        {
            BugReporting.setState(Feature.State.DISABLED);
        }
        else
        {
            BugReporting.setState(Feature.State.ENABLED);
        }
    }
}

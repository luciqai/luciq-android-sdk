package ai.example.luciq;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import androidx.multidex.MultiDex;

import java.util.Locale;

import ai.luciq.apm.APM;
import ai.luciq.bug.BugReporting;
import ai.luciq.library.LogLevel;
import ai.luciq.library.Luciq;
import ai.luciq.library.LuciqColorTheme;
import ai.luciq.library.LuciqCustomTextPlaceHolder;
import ai.luciq.library.internal.module.LuciqLocale;
import ai.luciq.library.invocation.LuciqInvocationEvent;
import ai.luciq.library.ui.onboarding.WelcomeMessage;


public class SampleApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //initializing Luciq
        new Luciq.Builder(this, "f8c84548ad70cb0cf65b48c802247073")
                .setInvocationEvents(LuciqInvocationEvent.SHAKE, LuciqInvocationEvent.SCREENSHOT,
                        LuciqInvocationEvent.FLOATING_BUTTON, LuciqInvocationEvent.TWO_FINGER_SWIPE_LEFT)
                .setSdkDebugLogsLevel(LogLevel.VERBOSE) // <--- don't use this in production
                .build();

        //Choosing Luciq theme
        Luciq.setColorTheme(LuciqColorTheme.LuciqColorThemeLight);
        //Choosing type of attachments allowed
        //1. initial screenshot, 2. extra screenshot, 3. image from gallery, 4. voice note
        //5. screen record
        Luciq.setWelcomeMessageState(WelcomeMessage.State.LIVE);

        // TODO the following are 3 acceptable ways to force Locale in Luciq (last one is the only 1 applied)
        Luciq.setLocale(new Locale(LuciqLocale.SIMPLIFIED_CHINESE.getCode(),
                LuciqLocale.SIMPLIFIED_CHINESE.getCountry()));
        Luciq.setLocale(new Locale(LuciqLocale.FRENCH.getCode()));
        Luciq.setLocale(Locale.ENGLISH);

        //Settings custom strings to replace Luciq's strings
        LuciqCustomTextPlaceHolder placeHolder = new LuciqCustomTextPlaceHolder();
        placeHolder.set(LuciqCustomTextPlaceHolder.Key.REPORT_FEEDBACK, "Send Feedback");
        placeHolder.set(LuciqCustomTextPlaceHolder.Key.REPORT_BUG, "Send Bug Report");

        Luciq.setCustomTextPlaceHolders(placeHolder);

        //setting user attributes
        Luciq.setUserAttribute("USER_TYPE", "luciq user");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            BugReporting.setAutoScreenRecordingEnabled(true);
        }

        APM.setEnabled(true);

    }
}

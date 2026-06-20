package ai.example.luciq.ui.views;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

import ai.example.luciq.R;

public class RoundedButton extends AppCompatButton {

    public RoundedButton(Context context) {
        super(context);
        init();
    }

    public RoundedButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundedButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundResource(R.drawable.rounded_button);
    }
}

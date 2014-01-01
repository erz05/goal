package com.goal.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.goal.R;
import com.goal.listeners.OnPauseListener;

/**
 * Created by erz on 12/27/13.
 */
public class Pause extends LinearLayout {

    private OnPauseListener listener;

    public Pause(Context context) {
        super(context);
        init(context);
    }

    public void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.pause_layout, this, true);

        final Button quitYes = (Button) findViewById(R.id.quitYes);
        final Button quitNo = (Button) findViewById(R.id.quitNo);
        quitYes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPause(true);
            }
        });
        quitNo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPause(false);
            }
        });
    }

    public void setListener(OnPauseListener listener){
        this.listener = listener;
    }
}

package com.goal.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.goal.R;
import com.goal.listeners.MenuListener;

/**
 * Created by erz on 12/9/13.
 */
public class Menu extends LinearLayout {

    private MenuListener listener;

    public Menu(Context context) {
        super(context);
        init(context);
    }

    public Menu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.menu_layout, this, true);

        ImageView play = (ImageView) findViewById(R.id.playButton);
        play.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onPlay();
                }
            }
        });
    }

    public void setListener(MenuListener listener){
        this.listener = listener;
    }
}

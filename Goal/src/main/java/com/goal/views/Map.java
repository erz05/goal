package com.goal.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.goal.R;
import com.goal.adapters.ListLevelsAdapter;
import com.goal.listeners.MapListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by erz on 12/9/13.
 */
public class Map extends LinearLayout {
    private MapListener listener;

    public Map(Context context) {
        super(context);
        init(context);
    }

    public Map(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.map_layout, this, true);

        JSONObject object = null;
        try {
            object = new JSONObject(loadJSONFromAsset(context, R.raw.levels));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray array = null;
        try {
            array = object.getJSONArray("levels");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(array != null){
            Log.v("DELETE_THIS", "array size = "+array.length());
            ListLevelsAdapter adapter = new ListLevelsAdapter(context, array);
            ListView listView = (ListView) findViewById(R.id.mapList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(listener != null){
                        listener.onLevelSelected((JSONObject)parent.getItemAtPosition(position));
                    }
                }
            });
        }
    }

    public void setListener(MapListener listener){
        this.listener = listener;
    }

    public String loadJSONFromAsset(Context context, int id) {
        String json = null;
        try {
            InputStream is = context.getResources().openRawResource(id);
            //InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}

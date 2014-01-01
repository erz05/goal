package com.goal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.goal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by erz on 12/14/13.
 */
public class ListLevelsAdapter extends BaseAdapter {

    private JSONArray data;
    private Context context;

    public ListLevelsAdapter(Context context, JSONArray data){
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.length();
    }

    @Override
    public Object getItem(int position) {
        try {
            return data.get(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = newView();

        TextView level = (TextView) convertView.findViewById(R.id.level);
        TextView difficulty = (TextView) convertView.findViewById(R.id.difficulty);

        if(level == null || difficulty == null){
            convertView = newView();
            level = (TextView) convertView.findViewById(R.id.level);
            difficulty = (TextView) convertView.findViewById(R.id.difficulty);
        }

        JSONObject object = null;
        try {
            object = (JSONObject) data.get(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(object != null){
            try {
                level.setText("Level: "+object.getInt("level"));
                difficulty.setText("difficulty: "+object.getInt("difficulty"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return convertView;
    }

    private View newView(){
        LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return vi.inflate(R.layout.level_item, null);
    }
}

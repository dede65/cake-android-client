package com.waracle.androidtest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyCustomAdapter extends BaseAdapter {

    // Can you think of a better way to represent these items???
    private Context context;
    private JSONArray mItems;
    private ImageLoader mImageLoader;

    public MyCustomAdapter(Context context) {
        this(new JSONArray(),context);
    }

    public MyCustomAdapter(JSONArray items, Context context) {
        this.context = context;
        mItems = items;
        mImageLoader = new ImageLoader();
    }


    @Override
    public int getCount() {
        return mItems.length();
    }

    @Override
    public Object getItem(int position) {
        try {
            return mItems.get(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        try {
            return mItems.get(position).hashCode();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View root = inflater.inflate(R.layout.list_item_layout, parent, false);
        if (root != null) {
            TextView title = root.findViewById(R.id.title);
            TextView desc = root.findViewById(R.id.desc);
            ImageView image = root.findViewById(R.id.image);
            try {
                JSONObject object = (JSONObject) getItem(position);
                title.setText(object.getString("title"));
                desc.setText(object.getString("desc"));
                //mImageLoader.load(object.getString("image"), image);
                new MyImageLoaderTask(image).execute(object.getString("image"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return root;
    }

    public void setItems(JSONArray items) {
        mItems = items;
    }
}

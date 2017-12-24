package com.dexpaser.zk.myapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zk on 2017/8/24.
 */

public class AppListAdapter extends BaseAdapter {
    Context mContext;
    List<ListItem> mListData;

    AppListAdapter(Context context, List<ListItem> data) {
        this.mContext = context;
        this.mListData = data;
    }

    public int getCount() {
        return this.mListData.size();
    }

    public Object getItem(int arg0) {
        return this.mListData.get(arg0);
    }

    public long getItemId(int arg0) {
        return (long) arg0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.list_item_test, null);
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.name = (TextView) convertView.findViewById(R.id.name1);
            holder.pkg = (TextView) convertView.findViewById(R.id.pkg);
            holder.path = (TextView) convertView.findViewById(R.id.path);
            holder.layout = (RelativeLayout) convertView.findViewById(R.id.RelativeLayout1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ListItem info = (ListItem) this.mListData.get(position);
        holder.icon.setImageDrawable(info.icon);
        holder.name.setText(info.name);
        holder.pkg.setText(info.pkg);
        holder.path.setText(info.path);

       /* holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.layout.setBackgroundColor(Color.parseColor("#F5F5DC"));
            }
        });*/

        return convertView;
    }

    static class ListItem {
        Drawable icon;
        String name;
        String path;
        String pkg;

        ListItem() {
        }
    }

    final class ViewHolder {
        public ImageView icon;
        public TextView name;
        public TextView path;
        public TextView pkg;
        public RelativeLayout layout;
    }

}


package com.example.myapplication;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import  android.widget.ListView;
import  android.widget.TextView;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.text.Layout;


import java.util.List;


public class customAdapter extends BaseAdapter {
    private final Context context;
    private  final List<DataItem> dataItems;
    private ViewHolder holder;


    public customAdapter(Context context, List<DataItem> dataItems) {
        this.context = context;
        this.dataItems = dataItems;
    }

    @Override
   public int getCount()
    {
        return dataItems.size();
    }


    @Override
    public DataItem getItem(int position )
    {
        return dataItems.get(position);

    }

    @Override
    public  long getItemId(int position){return  position;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView==null)
        {
            convertView=LayoutInflater.from(context).inflate(R.layout.activity_main2,parent,false);
            holder=new ViewHolder();
            holder.keyTextView=convertView.findViewById(R.id.textView1);
            holder.valueTextView=convertView.findViewById(R.id.textView2);
            convertView.setTag(holder);

        }
        else {
            holder=(ViewHolder) convertView.getTag();
        }
        DataItem item=getItem(position);
        holder.keyTextView.setText(item.getKey());
        holder.valueTextView.setText(item.getValue());

        return  convertView;
    }

  static class ViewHolder
    {
        TextView keyTextView;
        TextView valueTextView;
    }

}

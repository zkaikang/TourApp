package com.example.a11369.tourapp;


import android.content.Context;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemAdapter extends BaseAdapter {
    private List<ItemBean> mlist;
    private LayoutInflater mInflater;
    public ItemAdapter(Context context, List<ItemBean> list){
        mlist=list;
        mInflater=LayoutInflater.from(context);
    }


    @Override
    public int getCount(){ return mlist.size(); }
    @Override
    public Map<String,String> getItem(int position){
        Map<String,String> map=new HashMap<String,String>();
        map.put("title",mlist.get(position).getTitle());
        map.put("text",mlist.get(position).getText());
        map.put("author",mlist.get(position).getAuthor());
        map.put("address",mlist.get(position).getAddress());
        return map;
    }

    @Override
    public long getItemId(int position){return position;}
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=mInflater.inflate(R.layout.activity_item,null);
            viewHolder.title=(TextView) convertView.findViewById(R.id.title);
            viewHolder.address=(TextView) convertView.findViewById(R.id.address);
            viewHolder.author=(TextView) convertView.findViewById(R.id.author);
            viewHolder.text=(TextView) convertView.findViewById(R.id.text);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        ItemBean bean=mlist.get(position);
        viewHolder.title.setText(bean.getTitle());
        viewHolder.text.setText(bean.getText());
        viewHolder.author.setText(bean.getAuthor());
        viewHolder.address.setText(bean.getAddress());

        return convertView;
    }
    class ViewHolder{
        public TextView title;
        public TextView author;
        public TextView address;
        public TextView text;
    }

}

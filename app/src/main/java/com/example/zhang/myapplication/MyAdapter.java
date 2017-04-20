package com.example.zhang.myapplication;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by zhang on 2017/4/20.
 */
class MyAdapter extends RecyclerView.Adapter {

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private TextView subTextView;
        private View root;

        public MyViewHolder(View itemView) {
            super(itemView);
            subTextView = (TextView) itemView.findViewById(R.id.textView2);
            textView = (TextView) itemView.findViewById(R.id.textView1);
        }

        public TextView getSubTextView() {
            return subTextView;
        }

        public TextView getTextView() {
            return textView;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_list_item_2, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        viewHolder.getTextView().setText(list.get(position).get("name"));
        viewHolder.getSubTextView().setText(Html.fromHtml(list.get(position).get("detail")));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private ArrayList<Map<String, String>> list;

    public void initList() {
        list = new ArrayList<>();
    }

    public ArrayList<Map<String, String>> getList() {
        return list;
    }

}

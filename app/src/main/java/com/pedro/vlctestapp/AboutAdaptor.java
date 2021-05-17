package com.pedro.vlctestapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class AboutAdaptor extends RecyclerView.Adapter<AboutAdaptor.ViewHolder> {

    private List<AboutObj> mAboutList;

    public AboutAdaptor(List<AboutObj> list) {
        mAboutList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_aboutxx, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.mview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
//                AboutObj obj= mAboutList.get(position);
//                obj.setChosen();
                notifyItemChanged(position);
//                Toast.makeText(view.getContext(), "you click: " + obj.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AboutObj obj = mAboutList.get(position);
        holder.mname.setText(obj.getName());
        holder.mcontent.setText(obj.getAbout());

    }

    @Override
    public int getItemCount() {
        return mAboutList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mname;
        TextView mcontent;
        LinearLayout mlayout;
        View mview;

        public ViewHolder(View view) {
            super(view);
            mview = view;
            mname = (TextView) view.findViewById(R.id.about_name);
            mcontent = (TextView) view.findViewById(R.id.about_content);
            mlayout = (LinearLayout) view.findViewById(R.id.about_layout);
        }
    }
}
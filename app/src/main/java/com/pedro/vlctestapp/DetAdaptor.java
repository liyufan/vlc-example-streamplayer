package com.pedro.vlctestapp;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class DetAdaptor extends RecyclerView.Adapter<DetAdaptor.ViewHolder> {

    private List<DetObj> mDetList;

    public DetAdaptor(List<DetObj> list) {
        mDetList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_for_detection, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.mview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                DetObj obj = mDetList.get(position);
                obj.setChosen();
                notifyItemChanged(position);
//                Toast.makeText(view.getContext(), "you click: " + obj.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DetObj obj = mDetList.get(position);
        holder.mdetClass.setText(obj.getName());
        holder.mchooseImage.setImageResource(obj.getId());
        if (obj.getchosen()) {
            holder.mlayout.setBackgroundColor(Color.GREEN);
        } else {
            holder.mlayout.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return mDetList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mdetClass;
        ImageView mchooseImage;
        LinearLayout mlayout;
        View mview;

        public ViewHolder(View view) {
            super(view);
            mview = view;
            mdetClass = (TextView) view.findViewById(R.id.obj_name);
            mchooseImage = (ImageView) view.findViewById(R.id.choose_image);
            mlayout = (LinearLayout) view.findViewById(R.id.obj_layout);
        }
    }
}

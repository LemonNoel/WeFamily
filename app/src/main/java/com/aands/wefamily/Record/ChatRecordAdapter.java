package com.aands.wefamily.Record;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aands.wefamily.R;

import java.util.List;

/**
 * Created by LemonNoel on 2017/5/10.
 */

public class ChatRecordAdapter extends RecyclerView.Adapter<ChatRecordAdapter.ViewHolder> {
    private List<Records> mRecordList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView photoImage;
        TextView contactName;
        TextView lastTime;
        TextView lastMessage;

        public ViewHolder(View view) {
            super(view);
            photoImage = (ImageView)view.findViewById(R.id.photo_image);
            contactName = (TextView)view.findViewById(R.id.contact_name);
            lastTime = (TextView)view.findViewById(R.id.last_time);
            lastMessage = (TextView)view.findViewById(R.id.last_message);
        }
    }

    public ChatRecordAdapter(List<Records> recordsList) {
        mRecordList = recordsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.record_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Records record = mRecordList.get(position);
        holder.photoImage.setImageResource(record.getImageId());
        holder.contactName.setText(record.getName());
        holder.lastTime.setText(record.getLastTime());
        holder.lastMessage.setText(record.getLastMessage());
    }

    @Override
    public int getItemCount() {
        return mRecordList.size();
    }
}

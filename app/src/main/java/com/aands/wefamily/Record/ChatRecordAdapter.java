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
    //点击内部接口
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Records record = mRecordList.get(position);
        holder.photoImage.setImageResource(record.getImageId());
        holder.contactName.setText(record.getName());
        holder.lastTime.setText(record.getLastTime());
        holder.lastMessage.setText(record.getLastMessage());

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
        if (mOnItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemLongClickListener.onItemLongClick(holder.itemView, position);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mRecordList.size();
    }
}

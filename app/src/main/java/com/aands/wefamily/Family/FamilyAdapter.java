package com.aands.wefamily.Family;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aands.wefamily.R;
import com.aands.wefamily.Record.Records;

import java.util.List;

/**
 * Created by LemonNoel on 2017/5/10.
 */

public class FamilyAdapter extends RecyclerView.Adapter<FamilyAdapter.ViewHolder> {
    private List<Tag> mTagList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView tagImage;
        TextView tagName;

        public ViewHolder(View view) {
            super(view);
            tagImage = (ImageView)view.findViewById(R.id.tag_image);
            tagName = (TextView)view.findViewById(R.id.tag_name);
        }
    }

    public FamilyAdapter(List<Tag> tagList) {
        mTagList = tagList;
    }

    @Override
    public FamilyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tag_item, parent, false);
        FamilyAdapter.ViewHolder holder = new FamilyAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(FamilyAdapter.ViewHolder holder, int position) {
        Tag tag = mTagList.get(position);
        holder.tagImage.setImageResource(tag.getImageId());
        holder.tagName.setText(tag.getName());
    }

    @Override
    public int getItemCount() {
        return mTagList.size();
    }
}


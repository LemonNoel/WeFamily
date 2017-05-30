package com.aands.wefamily.Family;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
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
        TextView showLetter;

        public ViewHolder(View view) {
            super(view);
            tagImage = (ImageView)view.findViewById(R.id.tag_image);
            tagName = (TextView)view.findViewById(R.id.tag_name);
            showLetter = (TextView) view.findViewById(R.id.show_letter);
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

        //获得当前position是属于哪个分组
        int sectionForPosition = getSectionForPosition(position);
        //获得该分组第一项的position
        int positionForSection = getPositionForSection(sectionForPosition);
        //查看当前position是不是当前item所在分组的第一个item
        //如果是，则显示showLetter，否则隐藏
        if (position == positionForSection) {
            holder.showLetter.setVisibility(View.VISIBLE);
            holder.showLetter.setText(tag.getFirstLetter());
        } else {
            holder.showLetter.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mTagList.size();
    }
    public Object getItem(int position) {
        return mTagList.get(position);
    }

    //传入一个分组值[A....Z],获得该分组的第一项的position
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < mTagList.size(); i++) {
            if (mTagList.get(i).getFirstLetter().charAt(0) == sectionIndex) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //传入一个position，获得该position所在的分组
    public int getSectionForPosition(int position) {
        return mTagList.get(position).getFirstLetter().charAt(0);
    }
}


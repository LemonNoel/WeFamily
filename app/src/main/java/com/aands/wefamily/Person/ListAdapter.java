package com.aands.wefamily.Person;




import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aands.wefamily.Family.FamilyAdapter;
import com.aands.wefamily.Family.Tag;
import com.aands.wefamily.R;

import java.util.List;

/**
 * Created by renwendi on 17/5/31.
 */

public class ListAdapter extends ArrayAdapter<Zhang>  {
    private LayoutInflater mInflater;


    private List<Zhang> mpersonList;



    //点击内部接口
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private FamilyAdapter.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(FamilyAdapter.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
    public ListAdapter(Context context, int textViewResourceId,Zhang[] obj) {
        super(context, textViewResourceId,obj);
        // TODO Auto-generated constructor stub

        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            //创建新的view视图.
            convertView = mInflater.inflate(R.layout.personlistview, null); //see above, you can use the passed resource id.
        }

        ViewHolder holder = null;
        if(holder==null){
            holder = new ViewHolder();
            //查找每个ViewItem中,各个子View,放进holder中
            holder.name = (TextView) convertView.findViewById(R.id.person_name);
            holder.age = (TextView) convertView.findViewById(R.id.person_age);
            holder.email = (TextView) convertView.findViewById(R.id.person_email);
            holder.address = (TextView) convertView.findViewById(R.id.person_address);
            //保存对每个显示的ViewItem中, 各个子View的引用对象
            convertView.setTag(holder);
        }
        else// I think this a bug, program can not run here!!!--linc2014.11.12
        {
            holder = (ViewHolder)convertView.getTag();
        }

        //获取当前要显示的数据
        Zhang person = getItem(position);

        holder.name.setText(person.getName());
        holder.age.setText(String.valueOf(person.getAge()));
        holder.email.setText(person.getEmail());
        holder.address.setText(person.getAddress());
        return convertView;
    }

    private static class ViewHolder
    {
        TextView name;
        TextView age;
        TextView email;
        TextView address;
    }
}

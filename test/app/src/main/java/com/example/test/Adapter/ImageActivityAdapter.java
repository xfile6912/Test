package com.example.test.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.test.Model.ImageCheck;
import com.example.test.R;

import java.util.ArrayList;

public class ImageActivityAdapter extends BaseAdapter {
    Context context=null;
    ArrayList<ImageCheck> images;
    public ImageActivityAdapter(Context context, ArrayList<String> imageList)
    {
        this.context=context;
        images=new ArrayList<>();
        for(String image:imageList)
        {
            this.images.add(new ImageCheck(image, false));
        }
    }
    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public ImageCheck getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View contentView, ViewGroup parent) {

        if(contentView==null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            contentView = inflater.inflate(R.layout.imagelayout, parent, false);
        }
        ImageView imageView=(ImageView) contentView.findViewById(R.id.imageView);
        CheckBox checkBox = (CheckBox) contentView.findViewById(R.id.checkbox);

        ImageCheck imageCheck=images.get(position);
        Glide.with(context)
                .load(imageCheck.getImage())
                .override(300)
                .into(imageView);
        return contentView;
    }

    public void setChecked(int position)
    {
        if(images.get(position).isChecked())
            images.get(position).setChecked(false);
        else
            images.get(position).setChecked(true);
    }
    public void setImages(ArrayList<String> imageList)
    {
        images=new ArrayList<>();
        for(String image:imageList)
        {
            this.images.add(new ImageCheck(image, false));
        }
    }
    public ArrayList<ImageCheck> getItems()
    {
        return images;
    }
}
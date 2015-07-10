package com.codepath.instagramclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by abe on 7/8/2015.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
    // what data do we need from the Activity
    //context and data source
    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }
    //what about the look
    // using templates to display image
    public View getView (int position,View convertView,ViewGroup parent){
        //getting data for pos
        InstagramPhoto photo=getItem(position);
        // check if we are using a recycled View if not we inflate
        if (convertView==null){
            // create view from template
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item_photo,parent,false);
        }
        // look up the view to populate data
        TextView tvCaption =(TextView) convertView.findViewById(R.id.tvCaption);
        ImageView ivPhoto=(ImageView) convertView.findViewById(R.id.ivPhoto);
        // insert the item data into each view items (populate)
        tvCaption.setText(photo.caption);
        // clear image
        ivPhoto.setImageResource(0);
        //insert image using picasso
        Picasso.with(getContext()).load(photo.imageurl).into(ivPhoto);

        // return the view
        return convertView;

    }
}

package edu.westga.android.fileexplorer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

/**
 * Created by allen on 1/16/18.
 */

public class EntryAdapter  extends ArrayAdapter<Entry> {
    private static final String MP3_EXT = "mp3";
    private static final String TXT_EXT  = "txt";
    private static final String PNG_EXT = "png";
    private static final String JPG_EXT = "jpg";

    private Context context;
    private int resource;
    private List<Entry> list;
    private ImageView imgView;

    public EntryAdapter(Context context,int resource,List<Entry> list){
        super(context,resource,list);
        this.context = context;
        this.resource = resource;
        this.list = list;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(this.context);
        @SuppressLint("ViewHolder") View entryView = inflater.inflate(R.layout.entry_view,parent,false);
        Entry entry = this.list.get(position);
        ImageView imgView = entryView.findViewById(R.id.imgID);
        this.setImageView(entry, imgView);
        TextView text = entryView.findViewById(R.id.textView);
        text.setText(entry.getName());
        return entryView;
    }

    private void setImageView(Entry entry, ImageView img) {
        if (entry.isDirectory()) {
            img.setImageResource(R.drawable.dir_icon);
        } else {
            switch (entry.getExtension()) {
                case MP3_EXT:
                    img.setImageResource(R.drawable.mp3_icon);
                    break;
                case TXT_EXT:
                    img.setImageResource(R.drawable.txt_icon);
                    break;
                case PNG_EXT:
                    img.setImageBitmap(this.makeThumbnail(entry));
                    break;
                case JPG_EXT:
                    img.setImageBitmap(this.makeThumbnail(entry));
                    break;
                default:
                    img.setImageResource(R.drawable.file_icon);
                    break;
            }
        }
    }

    public Bitmap makeThumbnail(Entry entry) {
        String path = entry.getPath();
        Bitmap thumbnail = BitmapFactory.decodeFile(path);
       // Bitmap resized = Bitmap.createBitmap(thumbnail, 1, 1, Ma)
        return thumbnail;

    }

}

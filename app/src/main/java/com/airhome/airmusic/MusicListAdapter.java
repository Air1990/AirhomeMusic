package com.airhome.airmusic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.airhome.airmusic.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by airhome on 2016/4/8.
 */
public class MusicListAdapter extends BaseAdapter {
    private List<Track> mData;
    private Context mContext;

    public MusicListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Track> data) {
        if (data != null) {
            mData = data;
        } else {
            mData = new ArrayList<>();
        }
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_music_list, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.song_name);
            viewHolder.artist = (TextView) convertView.findViewById(R.id.singer);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(mData.get(position).getName());
        viewHolder.artist.setText(mData.get(position).getArtist());
        return convertView;
    }

    private static class ViewHolder {
        TextView name;
        TextView artist;
    }

}

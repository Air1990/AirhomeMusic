package com.airhome.airmusic;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.airhome.airmusic.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView mLocalMusicLv;
    private List<Track> mTrackList;
    private MusicListAdapter mMusicListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLocalMusicLv = (ListView) findViewById(R.id.music_list);
        new LoadLocalMusicTask().execute();
    }

    private class LoadLocalMusicTask extends AsyncTask<Void, Void, List<Track>> {
        @Override
        protected List<Track> doInBackground(Void... params) {
            Cursor cursor = MainActivity.this.getContentResolver().query(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    null, null, null, MediaStore.Audio.Media.DATE_ADDED + " DESC");
            if (cursor == null) {
                return null;
            }
            int idIndex = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int nameIndex = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int artistIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int durationIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            mTrackList = new ArrayList<>();
            while (cursor.moveToNext()) {
                Track track = new Track();
                track.setId(cursor.getString(idIndex));
                track.setName(cursor.getString(nameIndex));
                Log.i("=========", cursor.getString(nameIndex));
                track.setArtist(cursor.getString(artistIndex));
                track.setDuration(cursor.getInt(durationIndex));
                mTrackList.add(track);
            }
            cursor.close();
            return mTrackList;
        }

        @Override
        protected void onPostExecute(List<Track> tracks) {
            super.onPostExecute(tracks);
            mMusicListAdapter = new MusicListAdapter(MainActivity.this);
            mMusicListAdapter.setData(mTrackList);
            mLocalMusicLv.setAdapter(mMusicListAdapter);
        }
    }
}

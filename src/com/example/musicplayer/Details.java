package com.example.musicplayer;

import java.io.File;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ListActivity;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;


public class Details extends Activity{
	private Utilities utils=new Utilities();
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        String songPath=getIntent().getStringExtra("songPath");
        
        File song=new File(songPath);
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(songPath);
        String Album = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
        String Artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        if(Artist==null)
        	Artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUMARTIST);
       
        String Title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        String Year = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_YEAR);
        String Length = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        
        TextView length=(TextView)findViewById(R.id.length);
        TextView title=(TextView)findViewById(R.id.title);
        TextView artist=(TextView)findViewById(R.id.artist);
        TextView album=(TextView)findViewById(R.id.album);
        TextView year=(TextView)findViewById(R.id.year);
        TextView location=(TextView)findViewById(R.id.location);
        long len=0;
        for(int i=0;i<Length.length();i++)
        {
        	len=len*10+(Length.charAt(i)-48);
        }
        length.setText(utils.milliSecondsToTimer(len));
     
        if(Title!=null)
        title.setText(Title);
        else
        title.setText(song.getName().substring(0, (song.getName().length() - 4)));

        if(Album!=null)
        album.setText(Album);
        else
        album.setText("<Unknown>");
        
        if(Artist!=null)
        artist.setText(Artist);
        else
        artist.setText("Various Artists");
        
        if(Year!=null)
        year.setText(Year);
        else
        year.setText("<Unknown>");
        
        location.setText(songPath);     
    }
}

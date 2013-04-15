package com.example.musicplayer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class CustomizedListView extends Activity{
	
	private int currentIndex;
	private String[] menuItems = {"Play","Share Music Via","Details"};
	private LinkedList<File> songsList = new LinkedList<File>();
	private ArrayList<HashMap<String, String>> songsListdata = new ArrayList<HashMap<String, String>>();
	private MediaMetadataRetriever mmr = new MediaMetadataRetriever();
	private Utilities utils=new Utilities();
	ListView list=null;
    ModifiedAdapter adapter=null;
    SongsManager plm=null;
    Button search;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlist);
        
        plm = new SongsManager();
        File extStore = Environment.getExternalStorageDirectory();
		// get all songs from sdcard
        this.songsList = plm.getFilesInFolder(extStore);
        for (int i = 0; i < songsList.size(); i++) {
            // creating new HashMap
            HashMap<String, String> song = new HashMap<String, String>();
            mmr.setDataSource(songsList.get(i).getAbsolutePath().toString());
            
            //getting artist
            String artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUMARTIST);
            if(artist==null)
            	artist=mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            
            //getting Duration
            String len = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            
            long Len=0;
            try
            {
            	Len=Integer.parseInt(len);
            }
            catch(Exception e)
            {
            	Log.i(null, ":conversion error");
            }
            len=utils.milliSecondsToTimer(Len);
            Log.i(null, "length"+len);
            song.put("songTitle", (songsList.get(i)).getName().substring(0, ((songsList.get(i)).getName().length() - 4)));
            song.put("songArtist", artist);
            song.put("duration", len);
            song.put("songPath",songsList.get(i).getAbsolutePath().toString());
            // adding HashList to ArrayList
            songsListdata.add(song);
        }
        
        list=(ListView)findViewById(R.id.list);
        // Getting adapter by passing xml data ArrayList
        adapter=new ModifiedAdapter(this, songsListdata);
        list.setAdapter(adapter);
        // Click event for single list row
        list.setOnItemClickListener(new OnItemClickListener() {
        	@Override
            public void onItemClick(AdapterView<?> parent, View view,
                    final int position, long id) {
               
        		final String songPath =songsList.get(position).getAbsolutePath().toString();
        		AlertDialog.Builder builder = new AlertDialog.Builder(CustomizedListView.this);
        		builder.setTitle((songsList.get(position)).getName().substring(0, ((songsList.get(position)).getName().length() - 4)));
        		builder.setItems(menuItems, new DialogInterface.OnClickListener()
			     {
							    
			            public void onClick(DialogInterface dialog, int item)
			            {  
			            	
			            	if(item==0)
			            	{
			            		Intent in = new Intent(getApplicationContext(),MainActivity.class);
			                    // Sending songIndex to PlayerActivity
			                    in.putExtra("songIndex", position);
			                    setResult(100, in);
			                    // Closing PlayListView
			                    finish();
			            	}
			            	else if(item==2)
			            	{
			            		Intent details = new Intent(getApplicationContext(),Details.class);
			        	    	details.putExtra("songPath", songPath);
			        	    	startActivity(details);
			            	}
			            	else if(item==1)
			            	{
			            		Intent intent = new Intent();  
			        	        intent.setAction(Intent.ACTION_SEND);  
			        	        intent.setType("audio/*");
			        	        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(songPath)));
			        	        startActivity(intent);
			            	}
			            }
			     });
        		AlertDialog alert = builder.create();
				alert.show();
        	}
        });
        
        //Search for a song implementations
        search=(Button)findViewById(R.id.searchForSong);
        search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent launchBrowser=new Intent(getApplicationContext(), Browser.class);
				startActivity(launchBrowser);
			}
		});
    }
    
}

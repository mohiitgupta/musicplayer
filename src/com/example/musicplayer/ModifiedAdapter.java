package com.example.musicplayer;

import com.mpatric.mp3agic.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ModifiedAdapter extends BaseAdapter{
	
	private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    
    public ModifiedAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view=convertView;
        if(convertView==null)
            view = inflater.inflate(R.layout.list_row, null);
        TextView title = (TextView)view.findViewById(R.id.title); // title
        TextView artist = (TextView)view.findViewById(R.id.artist); // artist name
        TextView duration = (TextView)view.findViewById(R.id.duration); // duration
        ImageView imageView = (ImageView)view.findViewById(R.id.list_image); //album art
        HashMap<String, String> song = new HashMap<String, String>();
        song=data.get(position);
        
        // Setting all values in listview
        title.setText(song.get("songTitle"));
        artist.setText(song.get("songArtist"));
        duration.setText(song.get("duration"));       
        //setting album art
        Bitmap bm=null;
        try {
			Mp3File mp3file = new Mp3File(song.get("songPath"),false);
			if(mp3file.hasId3v2Tag() && mp3file.getId3v2Tag().getAlbumImage() != null)
			{
				byte[] albumImage = mp3file.getId3v2Tag().getAlbumImage();
				bm = BitmapFactory.decodeByteArray(albumImage, 0, albumImage.length);
			}
        } catch (UnsupportedTagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if(bm!=null)
        {
        	Log.i(null,"Bitmap found");
        	imageView.setImageBitmap(bm);
        }
        else
        {
        	Log.i(null," Bitmap not found");
        	imageView.setImageResource(R.drawable.rihanna);
        }
		return view;
	}
}

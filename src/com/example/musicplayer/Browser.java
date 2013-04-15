package com.example.musicplayer;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Browser extends Activity{
	
	Button go;
	String songUrl;
	EditText url;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_browser);
        
        go=(Button)findViewById(R.id.go);
        url=(EditText)findViewById(R.id.url);
        
        go.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				songUrl="https://"+url.getText().toString();
				Uri uriUrl = Uri.parse(songUrl);
				Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
				startActivity(launchBrowser);
			}
		});
	}
}

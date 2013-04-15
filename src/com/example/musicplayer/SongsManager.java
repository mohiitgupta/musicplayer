package com.example.musicplayer;
 
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import android.util.Log;

public class SongsManager {
    
    // this method creates a list of file extensions that will be searched
 	
    public static ArrayList<String> createAudioExtensionsList()
 	{
 		ArrayList<String> extension = new ArrayList<String>();

 		extension.add(".mp3");
 		return extension;
 	}
    // Constructor
    public SongsManager(){
 
    }
 
    /**
     * Function to read all mp3 files from sdcard
     * and store the details in ArrayList
     * */
   
 	public static String getFileExtension(String fileName)
 	{ 
 		String result = fileName.substring(fileName.lastIndexOf(".")); 
 		return result;
 	}

 
 	public static LinkedList<File> getFilesInFolder(File file) 
 	{
 		Log.i(null, "enterd in"+file.getAbsolutePath());
 		LinkedList<File> songsList = new LinkedList<File>();
 		File files[]=file.listFiles(filter);
 		for(File f:files)
 		{
 			if(f.isDirectory()&&f.canRead())
 			{
 				
 				songsList.addAll(getFilesInFolder(f));
 			}
 			else if(f.isFile())
 			{
 				Log.i(null,"file taken"+f.getAbsolutePath());
 				songsList.add(f);
 			}
 		}
		return songsList;
 	}
    //USE FileFilter 
 	static FileFilter filter=new FileFilter(){
 	@Override
 	public boolean accept(File f)
 	{
 	    return (f.isDirectory()||(f.getName().matches("^.+\\.(mp3|MP3)$")));
 	}
 	};
}
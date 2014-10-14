package edu.ntua.dblab.hecataeus.graph.visual;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class VisualFileColor {
	protected static List<String> files;
	protected static HashMap<String, Color> FileColor;
	
	public VisualFileColor(){}
	
	public void setFileNames(List<String> files){
		this.files = new ArrayList<String>(files);
		this.FileColor = new HashMap<String, Color>(setColorForFiles(this.files));
	}
	
	protected Color getColorForFile(String fileName){
		return this.FileColor.get(fileName);
	}
	
	public HashMap<String, Color> getFileColorMap(){
		return this.FileColor;
	}
	
	private HashMap<String, Color> setColorForFiles(List<String> fileNames){
		List<String> foldersAndFiles=new ArrayList<>();
		String prjFolder= fileNames.get(0);
		HashMap<String, Color> FileColor = new HashMap<String, Color>();
		for(String name : fileNames)
		{
			String fNames = name.substring(name.indexOf("SQLS/")+5);
			String[] fs = fNames.split("/");
			boolean flag=true;
			for(int i=0;i<fs.length;i++)
			{
				if(flag)
				{
					flag=false;
					foldersAndFiles.add(fs[i]);
				}
				else
				{
					foldersAndFiles.add(foldersAndFiles.get(foldersAndFiles.size()-1)+"/"+fs[i]);
				}
			}
		}
		HashSet<String> hs = new HashSet<String>();	// Eliminate duplicates;
		hs.addAll(foldersAndFiles);
		foldersAndFiles.clear();
		foldersAndFiles.addAll(hs);
		Collections.sort(foldersAndFiles);
		int cnt=0;
		for(String pf : foldersAndFiles)	// Colorize
		{	// TODO: Use recolorize for files of folders.
			Color c=null;
			if(pf.contains("/"))
			{	// take your parents color
				c=FileColor.get(prjFolder.substring(0, prjFolder.indexOf("SQLS/")+5)+pf.substring(0,pf.lastIndexOf("/")));
			}
			if(c==null)
			{	// your father doesn't have color, so get a color
				FileColor.put(prjFolder.substring(0, prjFolder.indexOf("SQLS/")+5)+pf, getColor(cnt%7));
			}
			else
			{	// since your father had a color, just change it a little bit
				FileColor.put(prjFolder.substring(0, prjFolder.indexOf("SQLS/")+5)+pf, recolorize(c, cnt%7));
			}
			cnt++;
		}
		return FileColor;
	}
	
	private Color recolorize(Color c, int counter)
	{	// TODO: return better colors (not only brighter)? 
		c=c.brighter();
		return(c);
	}
	
	private Color getColor(int c){
		switch (c){
		case 0: return new Color(16, 78, 139);
		case 1: return new Color(255,102,102);
		case 2: return new Color(255,178,102);
		case 3: return new Color(178,255,102);
		case 4: return new Color(102,178,255);
		case 5: return new Color(178,102,255);
		case 6: return new Color(255,102,102);
		default : return new Color(0,0,0);
		}
	}
}

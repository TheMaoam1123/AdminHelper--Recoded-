package de.YonasCode.AdminHelper.Database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import de.YonasCode.AdminHelper.Main;

public class BadWordsDatabase {
	
	private File database = new File("plugins/" + Main.INSTANCE.getDescription().getName(), "badwords.txt");
	private ArrayList<String> cache = new ArrayList<String>();
	private InputStream is;
	private InputStreamReader isr;
	private BufferedReader br;
	
	public void loadDatabase() throws IOException {
		try {
			is = new FileInputStream(database);
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr); 
			
			if(!(database.exists())) {
				database.createNewFile();
			}
		
			String line;
		
			while((line = br.readLine()) != null) {
				cache.add(line.toLowerCase());
			}
		
			Main.LOG.info("Cache-Size: " + cache.size());
		} finally {
			if(is != null) is.close();
			if(isr != null) isr.close();
			if(br != null) br.close();
		}
	}
	
	public boolean check(String word) {
		boolean is = false;
		for(String s : cache) {
			if(s != null)
				if(s.equalsIgnoreCase(word.toLowerCase()))
					is = true;
		}
		return is;
	}
	
	public boolean contains(String word) {
		String pattern = "(" + StringUtils.join(this.cache, "|") + ")";
		Pattern pat = Pattern.compile(pattern);
		Matcher mat = pat.matcher(word.toLowerCase());
		return mat.find();
	}
	
	/*
	 * this class is in work
	 */
	
}
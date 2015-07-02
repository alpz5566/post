package com.youngball.util;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class luceneUtils {
	public static Directory directory;
	public static Analyzer analyzer;
	static{
		try {
			directory = FSDirectory.open(new File("./indexDir"));
			analyzer = new StandardAnalyzer(Version.LUCENE_30);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

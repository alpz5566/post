package com.youngball.analyzer;

import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.cn.ChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class AnalyzerTest {
	
	/**
	 * 英文关键字测试
	 * @throws Exception
	 */
	@Test
	public void testEn() throws Exception{
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
		String text = "oh my god ";
		this.testAnalyzer(analyzer, text);
	}
	
	/**
	 * 中文关键字测试
	 */
	@Test
	public void testCh() throws Exception{
		Analyzer analyzer = new ChineseAnalyzer();
		Analyzer analyzer2 = new CJKAnalyzer(Version.LUCENE_30);
		Analyzer analyzer3 = new IKAnalyzer();
		String text = "廖乒竹子和毛浩明天的";
		//this.testAnalyzer(analyzer, text);
		System.out.println("============================");
		//this.testAnalyzer(analyzer2, text);
		System.out.println("============================");
		this.testAnalyzer(analyzer3, text);
	}
	
	/**
	 * 经过该方法可以把分词后的结果输出
	 * @param analyzer
	 * @param text
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private void testAnalyzer(Analyzer analyzer,String text)throws Exception{
		TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(text));
		tokenStream.addAttribute(TermAttribute.class);
		while(tokenStream.incrementToken()){
			TermAttribute termAttribute = tokenStream.getAttribute(TermAttribute.class);
			System.out.println(termAttribute.term());
		}
	}
}

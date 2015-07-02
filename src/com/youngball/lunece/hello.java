package com.youngball.lunece;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Before;
import org.junit.Test;

import com.youngball.bean.Aticle;

/**
 * 1.Aticle对象存放在索引库中
 * 根据关键词提取
 * @author lpz
 *
 */
public class hello {
	@Test
	public void testCreateIndex() throws Exception{
		/**
		 * 1、创建一个Aticle对象，并且把信息存放进去
		 * 2、调用indexWriter的API把数据存放在索引库中
		 * 3、关闭indexWriter
		 */
		//创建一个Aticle对象，并且把信息存放进去
		Aticle Aticle = new  Aticle();
		Aticle.setId(1L);
		Aticle.setTitle("lucene可以做搜索引擎");
		Aticle.setContent("baidu,google都是很好的搜索引擎");
		//调用indexWriter的API把数据存放在索引库中
		   /**
		    * 创建一个IndexWriter
		    *    参数三个
		    *       1、索引库   指向索引库的位置
		    *       2、分词器
		    */
		       //创建索引库
			   Directory directory = FSDirectory.open(new File("./indexDir"));
			   //创建分词器
			   Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
		IndexWriter indexWriter = new IndexWriter(directory, analyzer, MaxFieldLength.LIMITED);
			  //把一个Aticle对象转化成document
		Document document = new Document();
		Field idField = new Field("id",Aticle.getId().toString(),Store.YES,Index.NOT_ANALYZED);
		Field titleField = new Field("title",Aticle.getTitle(),Store.YES,Index.ANALYZED);
		Field contentField = new Field("content",Aticle.getContent(),Store.YES,Index.ANALYZED);
		document.add(idField);
		document.add(titleField);
		document.add(contentField);
		indexWriter.addDocument(document);
		//关闭indexWriter
		indexWriter.close();
	}
	
	@Test
	public void testSearchIndex() throws Exception{
		/**
		 * 1、创建一个 IndexSearch对象
		 * 2、调用search方法进行检索
		 * 3、输出内容
		 */
		//创建一个 IndexSearch对象
		Directory directory = FSDirectory.open(new File("./indexDir"));
		IndexSearcher indexSearcher = new IndexSearcher(directory);
		//调用search方法进行检索
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
		QueryParser queryParser = new QueryParser(Version.LUCENE_30,"content",analyzer);
		Query query = queryParser.parse("google");//关键词
		TopDocs topDocs = indexSearcher.search(query, 2);
		int count = topDocs.totalHits;//根据关键词查询出来的总的记录数
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		List<Aticle> AticleList = new ArrayList<Aticle>();
		for(ScoreDoc scoreDoc:scoreDocs){
			float score = scoreDoc.score;//关键词得分
			int index = scoreDoc.doc;//索引的下标
			Document document = indexSearcher.doc(index);
			//把document转化成Aticle
			Aticle Aticle = new  Aticle();
			Aticle.setId(Long.parseLong(document.get("id")));//document.getField("id").stringValue()
			Aticle.setTitle(document.get("title"));
			Aticle.setContent(document.get("content"));
			AticleList.add(Aticle);
		}
		for(Aticle Aticle:AticleList){
			System.out.println(Aticle.getId());
			System.out.println(Aticle.getTitle());
			System.out.println(Aticle.getContent());
  		}
		System.out.println("总记录数有" + count + "个");
	}
	
	/*@Before
	public void test(){
		System.out.println("测试开始");
	}*/
}

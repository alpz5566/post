package com.youngball.index;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;
import org.junit.Test;

import com.youngball.bean.Aticle;
import com.youngball.util.DocumentUtils;
import com.youngball.util.luceneUtils;

public class AticleIndex {
	
	@Test
	public void testCreateAticle() throws Exception{
		Aticle aticle = new Aticle();
		aticle.setId(1L);
		aticle.setTitle("做的是lucene的测试");
		aticle.setContent("google,baidu都是搜索引擎");
		IndexWriter indexWriter = new IndexWriter(luceneUtils.directory, luceneUtils.analyzer, MaxFieldLength.LIMITED);
		indexWriter.addDocument(DocumentUtils.aticle2Document(aticle));
		indexWriter.close();
	}
	
	@Test
	public void testSearchAticle () throws Exception{
		IndexSearcher indexSearcher = new IndexSearcher(luceneUtils.directory);
		QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_30, new String[]{"title","content"}, luceneUtils.analyzer);
		Query query = queryParser.parse("lucene");
		TopDocs topDocs = indexSearcher.search(query, 2);
		int count = topDocs.totalHits; //查询计数器
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		List<Aticle> aticleList = new ArrayList<Aticle>();
		for(ScoreDoc scoreDoc:scoreDocs){
			float score = scoreDoc.score;
			int index = scoreDoc.doc;
			Document document = indexSearcher.doc(index);
			Aticle aticle = DocumentUtils.document2Aticle(document);
			aticleList.add(aticle);
		}
		
		for(Aticle aticle:aticleList){
			System.out.println(aticle.getId());
			System.out.println(aticle.getContent());
			System.out.println(aticle.getTitle());
			System.out.println(count);
		}
	}
	
	@Test
	public void testDelete() throws Exception{
		IndexWriter indexWriter = new IndexWriter(luceneUtils.directory, luceneUtils.analyzer, MaxFieldLength.LIMITED);
		/**
		 * term为关键词对象
		 */
		Term term = new Term("title", "lucene");
		indexWriter.deleteDocuments(term);
		indexWriter.close();
	}

	@Test
	public void testUpdate() throws Exception{
		IndexWriter indexWriter = new IndexWriter(luceneUtils.directory, luceneUtils.analyzer, MaxFieldLength.LIMITED);
		Aticle aticle = new Aticle();
		aticle.setId(2L);
		aticle.setTitle("做的是lucene的测试");
		aticle.setContent("修改后的对象");
		Document doc = DocumentUtils.aticle2Document(aticle);
		Term term = new Term("title", "lucene");
		indexWriter.updateDocument(term, doc);
		indexWriter.close();
	}
	
}

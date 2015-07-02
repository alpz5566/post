package com.youngball.Direction;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

import com.youngball.bean.Aticle;
import com.youngball.util.DocumentUtils;
import com.youngball.util.luceneUtils;

public class DirectionTest {
	
	@Test
	public void testRam() throws Exception{
		Directory directory = new RAMDirectory();
		IndexWriter indexWriter = new IndexWriter(directory, luceneUtils.analyzer, MaxFieldLength.LIMITED);
		Aticle aticle = new Aticle();
		aticle.setId(2L);
		aticle.setTitle("lucene");
		aticle.setContent("baidu google都是很好的搜索引擎");
		indexWriter.addDocument(DocumentUtils.aticle2Document(aticle));
		indexWriter.close();
		this.showdate(directory);
	}

	private void showdate(Directory directory) throws Exception{
		IndexSearcher indexSearcher = new IndexSearcher(directory);
		QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_30, new String[]{"title","content"}, luceneUtils.analyzer);
		Query query = queryParser.parse("lucene");
		TopDocs topDocs = indexSearcher.search(query, 10);
		ScoreDoc[] docs = topDocs.scoreDocs;
		List<Aticle> aticleList = new ArrayList<Aticle>();
		for(ScoreDoc scoreDoc : docs){
			Document document = indexSearcher.doc(scoreDoc.doc);
			aticleList.add(DocumentUtils.document2Aticle(document));
		}
		for(Aticle aticle : aticleList){
			System.out.println(aticle.getId());
			System.out.println(aticle.getTitle());
			System.out.println(aticle.getContent());
		}
	}
	
}

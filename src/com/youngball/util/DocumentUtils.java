package com.youngball.util;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;

import com.youngball.bean.Aticle;

public class DocumentUtils {
	public static Document aticle2Document(Aticle aticle){
		Document document = new Document();
		Field idField = new Field("id", aticle.getId().toString(), Store.YES,Index.NOT_ANALYZED);
		Field titleField = new Field("title", aticle.getTitle(), Store.YES,Index.ANALYZED);
		Field contentField = new Field("content", aticle.getContent(), Store.YES,Index.ANALYZED);
		document.add(idField);
		document.add(titleField);
		document.add(contentField);
		return document;
	}
	
	public static Aticle document2Aticle(Document document){
		Aticle aticle = new Aticle();
		aticle.setId(Long.parseLong(document.get("id")));
		aticle.setTitle(document.get("title"));
		aticle.setContent(document.get("content"));
		return aticle;
	}
}

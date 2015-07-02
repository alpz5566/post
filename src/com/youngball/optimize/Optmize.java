package com.youngball.optimize;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.junit.Test;

import com.youngball.util.luceneUtils;

public class Optmize {
	
	@Test
	public void optmizeTest() throws Exception{
		IndexWriter indexWriter = new IndexWriter(luceneUtils.directory, luceneUtils.analyzer, MaxFieldLength.LIMITED);
		//一条代码实现优化
		indexWriter.optimize();
		indexWriter.close();
		
	}
}

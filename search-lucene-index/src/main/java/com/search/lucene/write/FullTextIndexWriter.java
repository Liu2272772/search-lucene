package com.search.lucene.write;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.search.lucene.beans.FullTextBean;

public class FullTextIndexWriter {
	
	private Logger log=Logger.getLogger(this.getClass());
	
	private Directory directory;
	private Analyzer analyzer;
	private IndexWriterConfig config;
	private IndexWriter indexWriter;
	
	public void init() throws IOException {
		this.directory=FSDirectory.open(new File("F:\\Eclipse8\\Lucene\\TestResource\\INDEXDB\\fileIndex1"));
		this.analyzer = new IKAnalyzer();
		this.config = new IndexWriterConfig(Version.LATEST, analyzer);
		this.indexWriter = new IndexWriter(directory, config);
		this.indexWriter.addIndexes(FSDirectory.open(new File("F:\\Eclipse8\\Lucene\\TestResource\\INDEXDB\\fileIndex2")));
		
	}
	public void destory() {
		try {
			this.indexWriter.close();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
	public boolean addIndex(FullTextBean fullTextBean) {
		try {
			init();
			Document document = new Document();
			Field contentIndexField =new TextField("fullTextContent",fullTextBean.getContent(),Store.NO);
			Field idField = new LongField("id", fullTextBean.getId(), Store.YES);
			document.add(contentIndexField);
			document.add(idField);
			indexWriter.addDocument(document);
			return true;
		} catch (IOException e) {
			
			log.error(e.getMessage());
			return false;
		}finally {
			destory();
		}
	}
	
	public boolean updateIndex(FullTextBean fullTextBean) {
		try {
			init();
			Document document = new Document();
			Field contentIndexField =new TextField("fullTextContent",fullTextBean.getContent(),Store.NO);
			Field idField = new LongField("id", fullTextBean.getId(), Store.YES);
			document.add(contentIndexField);
			document.add(idField);
			 indexWriter.updateDocument(new Term("id", String.valueOf(fullTextBean.getId())), document);
			return true;
		} catch (IOException e) {
			
			log.error(e.getMessage());
			return false;
		}finally {
			destory();
		}
	}
	public boolean deleteIndex(FullTextBean fullTextBean) {
		try {
			init();
			Query query = new TermQuery(new Term("id", String.valueOf(fullTextBean.getId())));
			 indexWriter.deleteDocuments(query);
			return true;
		} catch (IOException e) {
			
			log.error(e.getMessage());
			return false;
		}finally {
			destory();
		}
	}

}

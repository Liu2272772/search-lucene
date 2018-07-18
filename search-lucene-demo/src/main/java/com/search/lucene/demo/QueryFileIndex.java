package com.search.lucene.demo;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class QueryFileIndex {
	public static void queryAllIndexContext() throws IOException {
		// ����һ��Directory����ָ���������ŵ�·��
		Directory directory = FSDirectory.open(new File("F:\\Eclipse8\\Lucene\\TestResource\\INDEXDB\\fileIndex"));
		// ����IndexReader������Ҫָ��Directory����
		IndexReader indexReader = DirectoryReader.open(directory);
		// ����Indexsearcher������Ҫָ��IndexReader����
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);

		// ������ѯ����
		// ʹ��MatchAllDocsQuery��ѯ����Ŀ¼�е������ĵ�
		Query query = new MatchAllDocsQuery();
		// ִ�в�ѯ
		// ��һ�������ǲ�ѯ���󣬵ڶ��������ǲ�ѯ������ص����ֵ
		TopDocs topDocs = indexSearcher.search(query, 10);

		// ��ѯ�����������
		System.out.println("��ѯ�������������" + topDocs.totalHits);
		// ������ѯ���
		// topDocs.scoreDocs�洢��document�����id
		// ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
			// scoreDoc.doc���Ծ���document�����id
			// int doc = scoreDoc.doc;
			// ����document��id�ҵ�document����
			Document document = indexSearcher.doc(scoreDoc.doc);
			// �ļ�����
			System.out.println("fileName:" + document.get("fileName"));
			// �ļ�����
			// System.out.println(document.get("fileContent"));
			// �ļ���С
			System.out.println(document.get("fileSize"));
			// �ļ�·��
			System.out.println(document.get("filePath"));
			System.out.println("----------------------------------");
		}
		// �ر�indexreader����
		indexReader.close();
	}

	public static void searchJinZhunIndex() throws IOException {
		// ����һ��Directory����ָ���������ŵ�·��
		Directory directory = FSDirectory.open(new File("F:\\Eclipse8\\Lucene\\TestResource\\INDEXDB\\fileIndex"));
		// ����IndexReader������Ҫָ��Directory����
		IndexReader indexReader = DirectoryReader.open(directory);
		// ����Indexsearcher������Ҫָ��IndexReader����
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		// ����һ��TermQuery����׼��ѯ������ָ����ѯ�������ѯ�Ĺؼ���
		// ������ѯ
		Query query = new TermQuery(new Term("fileName", "1111.txt"));
		// ִ�в�ѯ
		// ��һ�������ǲ�ѯ���󣬵ڶ��������ǲ�ѯ������ص����ֵ
		TopDocs topDocs = indexSearcher.search(query, 10);
		// ��ѯ�����������
		System.out.println("��ѯ�������������" + topDocs.totalHits);
		// ������ѯ���
		// topDocs.scoreDocs�洢��document�����id
		// ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
			// scoreDoc.doc���Ծ���document�����id
			// int doc = scoreDoc.doc;
			// ����document��id�ҵ�document����
			Document document = indexSearcher.doc(scoreDoc.doc);
			// �ļ�����
			System.out.println("fileName:" + document.get("fileName"));
			// �ļ�����
			System.out.println(document.get("fileContent"));
			// �ļ���С
			System.out.println(document.get("fileSize"));
			// �ļ�·��
			System.out.println(document.get("filePath"));
			System.out.println("----------------------------------");
		}
		// �ر�indexreader����
		indexReader.close();

	}

	public static void queryParserIndex() throws IOException, ParseException {
		// ����һ��Directory����ָ���������ŵ�·��
		Directory directory = FSDirectory.open(new File("F:\\Eclipse8\\Lucene\\TestResource\\INDEXDB\\fileIndex"));
		// ����IndexReader������Ҫָ��Directory����
		IndexReader indexReader = DirectoryReader.open(directory);
		// ����Indexsearcher������Ҫָ��IndexReader����
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		Analyzer analyzer=new IKAnalyzer();
		// ����queryparser����
		// ��һ������Ĭ����������
		// �ڶ����������Ƿ���������
		QueryParser queryParser = new QueryParser("fileContent", analyzer);
		// ʹ��Ĭ�ϵ���,�����õ����﷨���������ϸ����һ��
		Query query = queryParser.parse("����һ������");
		// ��ʹ��Ĭ�ϵ��򣬿����Լ�ָ����
		// Query query = queryParser.parse("fileContent:apache");
		// ִ�в�ѯ

		// ��һ�������ǲ�ѯ���󣬵ڶ��������ǲ�ѯ������ص����ֵ
		TopDocs topDocs = indexSearcher.search(query, 10);

		// ��ѯ�����������
		System.out.println("��ѯ�������������" + topDocs.totalHits);
		// ������ѯ���
		// topDocs.scoreDocs�洢��document�����id
		// ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
			// scoreDoc.doc���Ծ���document�����id
			// int doc = scoreDoc.doc;
			// ����document��id�ҵ�document����
			Document document = indexSearcher.doc(scoreDoc.doc);
			// �ļ�����
			System.out.println(document.get("fileName"));
			// �ļ�����
			// System.out.println(document.get("fileContent"));
			// �ļ���С
			System.out.println(document.get("fileSize"));
			// �ļ�·��
			System.out.println(document.get("filePath"));
			System.out.println("----------------------------------");
		}
		// �ر�indexreader����
		indexReader.close();
	}

	public static void queryParserHightLightIndex() throws InvalidTokenOffsetsException, IOException {
		// ����һ��Directory����ָ���������ŵ�·��
		Directory directory = FSDirectory.open(new File("F:\\Eclipse8\\Lucene\\TestResource\\INDEXDB\\fileIndex"));
		// ����IndexReader������Ҫָ��Directory����
		IndexReader indexReader = DirectoryReader.open(directory);
		// ����Indexsearcher������Ҫָ��IndexReader����
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		Analyzer analyzer = new IKAnalyzer();
		// ʹ��QueryParser��ѯ����������Query����
		QueryParser qp = new QueryParser("fileContent", analyzer);
		// �������Ч����
		qp.setDefaultOperator(QueryParser.OR_OPERATOR);
		try {
			ScoreDoc[] hits;
			Query query = qp.parse("����һ������");
			// ע��searcher�ļ�������
			hits = indexSearcher.search(query,null, 10).scoreDocs;

			// �ؼ��ָ�����ʾ��html��ǩ����Ҫ����lucene-highlighter-xxx.jar
			SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<span style='color:red'>", "</span>");
			Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));

			for (int i = 0; i < hits.length; i++) {
				Document doc = indexSearcher.doc(hits[i].doc);
				// �������Ӹ�����ʾ
				TokenStream tokenStream = analyzer.tokenStream("fileContent", new StringReader(doc.get("fileContent")));
				String content = highlighter.getBestFragment(tokenStream, doc.get("fileContent"));
				// �ļ�����
				System.out.println(content);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException, ParseException, InvalidTokenOffsetsException {
		// queryAllIndexContext();
		// searchJinZhunIndex();
		// queryParserIndex();
		//queryParserHightLightIndex();
		searchByTerm();
	}

	public static void searchByTerm() throws InvalidTokenOffsetsException, IOException {
		// ����һ��Directory����ָ���������ŵ�·��
		Directory directory = FSDirectory.open(new File("F:\\Eclipse8\\Lucene\\TestResource\\INDEXDB\\fileIndex"));
		// ����IndexReader������Ҫָ��Directory����
		IndexReader indexReader = DirectoryReader.open(directory);
		// ����Indexsearcher������Ҫָ��IndexReader����
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		Analyzer analyzer=new IKAnalyzer();
		// ʹ��QueryParser��ѯ����������Query����
		QueryParser qp = new QueryParser( "fileContent", analyzer);
		// �������Ч����
		qp.setDefaultOperator(QueryParser.OR_OPERATOR);
		try {
			Query query = qp.parse("����һ������");
			ScoreDoc[] hits;

			// ע��searcher�ļ�������
			hits = indexSearcher.search(query, 10).scoreDocs;

			// �ؼ��ָ�����ʾ��html��ǩ����Ҫ����lucene-highlighter-xxx.jar
			SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<span style='color:red'>", "</span>");
			Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));

			for (int i = 0; i < hits.length; i++) {
				Document doc = indexSearcher.doc(hits[i].doc);
				// �������Ӹ�����ʾ
				TokenStream tokenStream = analyzer.tokenStream("fileContent", new StringReader(doc.get("fileContent")));
				String content = highlighter.getBestFragment(tokenStream, doc.get("fileContent"));
				System.out.println(content);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}

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
		// 创建一个Directory对象，指定索引库存放的路径
		Directory directory = FSDirectory.open(new File("F:\\Eclipse8\\Lucene\\TestResource\\INDEXDB\\fileIndex"));
		// 创建IndexReader对象，需要指定Directory对象
		IndexReader indexReader = DirectoryReader.open(directory);
		// 创建Indexsearcher对象，需要指定IndexReader对象
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);

		// 创建查询条件
		// 使用MatchAllDocsQuery查询索引目录中的所有文档
		Query query = new MatchAllDocsQuery();
		// 执行查询
		// 第一个参数是查询对象，第二个参数是查询结果返回的最大值
		TopDocs topDocs = indexSearcher.search(query, 10);

		// 查询结果的总条数
		System.out.println("查询结果的总条数：" + topDocs.totalHits);
		// 遍历查询结果
		// topDocs.scoreDocs存储了document对象的id
		// ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
			// scoreDoc.doc属性就是document对象的id
			// int doc = scoreDoc.doc;
			// 根据document的id找到document对象
			Document document = indexSearcher.doc(scoreDoc.doc);
			// 文件名称
			System.out.println("fileName:" + document.get("fileName"));
			// 文件内容
			// System.out.println(document.get("fileContent"));
			// 文件大小
			System.out.println(document.get("fileSize"));
			// 文件路径
			System.out.println(document.get("filePath"));
			System.out.println("----------------------------------");
		}
		// 关闭indexreader对象
		indexReader.close();
	}

	public static void searchJinZhunIndex() throws IOException {
		// 创建一个Directory对象，指定索引库存放的路径
		Directory directory = FSDirectory.open(new File("F:\\Eclipse8\\Lucene\\TestResource\\INDEXDB\\fileIndex"));
		// 创建IndexReader对象，需要指定Directory对象
		IndexReader indexReader = DirectoryReader.open(directory);
		// 创建Indexsearcher对象，需要指定IndexReader对象
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		// 创建一个TermQuery（精准查询）对象，指定查询的域与查询的关键词
		// 创建查询
		Query query = new TermQuery(new Term("fileName", "1111.txt"));
		// 执行查询
		// 第一个参数是查询对象，第二个参数是查询结果返回的最大值
		TopDocs topDocs = indexSearcher.search(query, 10);
		// 查询结果的总条数
		System.out.println("查询结果的总条数：" + topDocs.totalHits);
		// 遍历查询结果
		// topDocs.scoreDocs存储了document对象的id
		// ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
			// scoreDoc.doc属性就是document对象的id
			// int doc = scoreDoc.doc;
			// 根据document的id找到document对象
			Document document = indexSearcher.doc(scoreDoc.doc);
			// 文件名称
			System.out.println("fileName:" + document.get("fileName"));
			// 文件内容
			System.out.println(document.get("fileContent"));
			// 文件大小
			System.out.println(document.get("fileSize"));
			// 文件路径
			System.out.println(document.get("filePath"));
			System.out.println("----------------------------------");
		}
		// 关闭indexreader对象
		indexReader.close();

	}

	public static void queryParserIndex() throws IOException, ParseException {
		// 创建一个Directory对象，指定索引库存放的路径
		Directory directory = FSDirectory.open(new File("F:\\Eclipse8\\Lucene\\TestResource\\INDEXDB\\fileIndex"));
		// 创建IndexReader对象，需要指定Directory对象
		IndexReader indexReader = DirectoryReader.open(directory);
		// 创建Indexsearcher对象，需要指定IndexReader对象
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		Analyzer analyzer=new IKAnalyzer();
		// 创建queryparser对象
		// 第一个参数默认搜索的域
		// 第二个参数就是分析器对象
		QueryParser queryParser = new QueryParser("fileContent", analyzer);
		// 使用默认的域,这里用的是语法，下面会详细讲解一下
		Query query = queryParser.parse("创建一个对象");
		// 不使用默认的域，可以自己指定域
		// Query query = queryParser.parse("fileContent:apache");
		// 执行查询

		// 第一个参数是查询对象，第二个参数是查询结果返回的最大值
		TopDocs topDocs = indexSearcher.search(query, 10);

		// 查询结果的总条数
		System.out.println("查询结果的总条数：" + topDocs.totalHits);
		// 遍历查询结果
		// topDocs.scoreDocs存储了document对象的id
		// ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
			// scoreDoc.doc属性就是document对象的id
			// int doc = scoreDoc.doc;
			// 根据document的id找到document对象
			Document document = indexSearcher.doc(scoreDoc.doc);
			// 文件名称
			System.out.println(document.get("fileName"));
			// 文件内容
			// System.out.println(document.get("fileContent"));
			// 文件大小
			System.out.println(document.get("fileSize"));
			// 文件路径
			System.out.println(document.get("filePath"));
			System.out.println("----------------------------------");
		}
		// 关闭indexreader对象
		indexReader.close();
	}

	public static void queryParserHightLightIndex() throws InvalidTokenOffsetsException, IOException {
		// 创建一个Directory对象，指定索引库存放的路径
		Directory directory = FSDirectory.open(new File("F:\\Eclipse8\\Lucene\\TestResource\\INDEXDB\\fileIndex"));
		// 创建IndexReader对象，需要指定Directory对象
		IndexReader indexReader = DirectoryReader.open(directory);
		// 创建Indexsearcher对象，需要指定IndexReader对象
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		Analyzer analyzer = new IKAnalyzer();
		// 使用QueryParser查询分析器构造Query对象
		QueryParser qp = new QueryParser("fileContent", analyzer);
		// 这句所起效果？
		qp.setDefaultOperator(QueryParser.OR_OPERATOR);
		try {
			ScoreDoc[] hits;
			Query query = qp.parse("创建一个对象");
			// 注意searcher的几个方法
			hits = indexSearcher.search(query,null, 10).scoreDocs;

			// 关键字高亮显示的html标签，需要导入lucene-highlighter-xxx.jar
			SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<span style='color:red'>", "</span>");
			Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));

			for (int i = 0; i < hits.length; i++) {
				Document doc = indexSearcher.doc(hits[i].doc);
				// 内容增加高亮显示
				TokenStream tokenStream = analyzer.tokenStream("fileContent", new StringReader(doc.get("fileContent")));
				String content = highlighter.getBestFragment(tokenStream, doc.get("fileContent"));
				// 文件名称
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
		// 创建一个Directory对象，指定索引库存放的路径
		Directory directory = FSDirectory.open(new File("F:\\Eclipse8\\Lucene\\TestResource\\INDEXDB\\fileIndex"));
		// 创建IndexReader对象，需要指定Directory对象
		IndexReader indexReader = DirectoryReader.open(directory);
		// 创建Indexsearcher对象，需要指定IndexReader对象
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		Analyzer analyzer=new IKAnalyzer();
		// 使用QueryParser查询分析器构造Query对象
		QueryParser qp = new QueryParser( "fileContent", analyzer);
		// 这句所起效果？
		qp.setDefaultOperator(QueryParser.OR_OPERATOR);
		try {
			Query query = qp.parse("创建一个对象");
			ScoreDoc[] hits;

			// 注意searcher的几个方法
			hits = indexSearcher.search(query, 10).scoreDocs;

			// 关键字高亮显示的html标签，需要导入lucene-highlighter-xxx.jar
			SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<span style='color:red'>", "</span>");
			Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));

			for (int i = 0; i < hits.length; i++) {
				Document doc = indexSearcher.doc(hits[i].doc);
				// 内容增加高亮显示
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

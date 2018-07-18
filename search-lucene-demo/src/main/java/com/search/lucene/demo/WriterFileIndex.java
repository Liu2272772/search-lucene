package com.search.lucene.demo;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class WriterFileIndex {
	public static void createIndex() throws IOException {
		//指定Directory对象 用于存放索印的文件
		Directory directory = FSDirectory.open(new File("F:\\Eclipse8\\Lucene\\TestResource\\INDEXDB\\fileIndex"));
		// 指定一个标准分析器，对文档内容进行分析
		//Analyzer analyzer = new StandardAnalyzer();
		Analyzer analyzer = new IKAnalyzer();
		// 创建indexwriterCofig对象
		// 第一个参数： Lucene的版本信息，可以选择对应的lucene版本也可以使用LATEST
		// 第二根参数：分析器对象
		IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, analyzer);
		// 创建一个IndexWriter对象
		IndexWriter indexWriter = new IndexWriter(directory, config);
		// 原始文档的路径
		File file = new File("F:\\Eclipse8\\Lucene\\TestResource\\doc");
		File[] fileList = file.listFiles();
		for (File file2 : fileList) {
			// 创建document对象
			Document document = new Document();
			// 创建field对象，将field添加到document对象中
			// 文件名称
			String fileName=file2.getName();
			// 创建文件名域
			// 第一个参数：域的名称
			// 第二个参数：域的内容
			// 第三个参数：是否存储
			Field fileNameField =new TextField("fileName",fileName,Store.YES);

			// 文件的大小
			long fileSize = FileUtils.sizeOf(file2);
			// 文件大小域
			Field fileSizeField = new LongField("fileSize", fileSize, Store.YES);

			// 文件路径
			String filePath = file2.getPath();
			// 文件路径域（不分析、不索引、只存储）
			Field filePathField = new StoredField("filePath", filePath);

			// 文件内容
			String fileContent = FileUtils.readFileToString(file2);
			// String fileContent = FileUtils.readFileToString(file2, "utf-8");
			// 文件内容域
			Field fileContentField = new TextField("fileContent", fileContent, Store.YES);

			document.add(fileNameField);
			document.add(fileSizeField);
			document.add(filePathField);
			document.add(fileContentField);
			// 使用indexwriter对象将document对象写入索引库，此过程进行索引创建。并将索引和document对象写入索引库。
			indexWriter.addDocument(document);
		}
		// 关闭IndexWriter对象。
		indexWriter.close();
	}
	public static void main(String[] args) throws IOException {
		createIndex();
	}

	//删除全部索引
	public static void deleteAllIndex() throws Exception {
		Directory directory = FSDirectory.open(new File("F:\\Eclipse8\\Lucene\\TestResource\\INDEXDB\\fileIndex"));
		Analyzer analyzer = new IKAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, analyzer);
		IndexWriter indexWriter = new IndexWriter(directory, config);
		//删除全部索引
		indexWriter.deleteAll();
		//关闭indexwriter
		indexWriter.close();
	}
	//根据查询条件删除索引
    public static void deleteIndexByQuery() throws Exception {
    	Directory directory = FSDirectory.open(new File("F:\\Eclipse8\\Lucene\\TestResource\\INDEXDB\\fileIndex"));
        Analyzer analyzer = new IKAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, config);
        //创建一个查询条件
        Query query = new TermQuery(new Term("fileContent", "apache"));
        //根据查询条件删除
        indexWriter.deleteDocuments(query);
        //关闭indexwriter
        indexWriter.close();
    }
    //修改索引库
    public static void updateIndex() throws Exception {
    	Directory directory = FSDirectory.open(new File("F:\\Eclipse8\\Lucene\\TestResource\\INDEXDB\\fileIndex"));
        Analyzer analyzer = new IKAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, config);
        //创建一个Document对象
        Document document = new Document();
        //向document对象中添加域。
        //不同的document可以有不同的域，同一个document可以有相同的域。
        document.add(new TextField("fileXXX", "要更新的文档", Store.YES));
        document.add(new TextField("contentYYY", "简介 Lucene 是一个基于 Java 的全文信息检索工具包。", Store.YES));
        indexWriter.updateDocument(new Term("fileName", "apache"), document);
        //关闭indexWriter
        indexWriter.close();
    }
}

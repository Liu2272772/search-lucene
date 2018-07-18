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
		//ָ��Directory���� ���ڴ����ӡ���ļ�
		Directory directory = FSDirectory.open(new File("F:\\Eclipse8\\Lucene\\TestResource\\INDEXDB\\fileIndex"));
		// ָ��һ����׼�����������ĵ����ݽ��з���
		//Analyzer analyzer = new StandardAnalyzer();
		Analyzer analyzer = new IKAnalyzer();
		// ����indexwriterCofig����
		// ��һ�������� Lucene�İ汾��Ϣ������ѡ���Ӧ��lucene�汾Ҳ����ʹ��LATEST
		// �ڶ�������������������
		IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, analyzer);
		// ����һ��IndexWriter����
		IndexWriter indexWriter = new IndexWriter(directory, config);
		// ԭʼ�ĵ���·��
		File file = new File("F:\\Eclipse8\\Lucene\\TestResource\\doc");
		File[] fileList = file.listFiles();
		for (File file2 : fileList) {
			// ����document����
			Document document = new Document();
			// ����field���󣬽�field��ӵ�document������
			// �ļ�����
			String fileName=file2.getName();
			// �����ļ�����
			// ��һ���������������
			// �ڶ����������������
			// �������������Ƿ�洢
			Field fileNameField =new TextField("fileName",fileName,Store.YES);

			// �ļ��Ĵ�С
			long fileSize = FileUtils.sizeOf(file2);
			// �ļ���С��
			Field fileSizeField = new LongField("fileSize", fileSize, Store.YES);

			// �ļ�·��
			String filePath = file2.getPath();
			// �ļ�·���򣨲���������������ֻ�洢��
			Field filePathField = new StoredField("filePath", filePath);

			// �ļ�����
			String fileContent = FileUtils.readFileToString(file2);
			// String fileContent = FileUtils.readFileToString(file2, "utf-8");
			// �ļ�������
			Field fileContentField = new TextField("fileContent", fileContent, Store.YES);

			document.add(fileNameField);
			document.add(fileSizeField);
			document.add(filePathField);
			document.add(fileContentField);
			// ʹ��indexwriter����document����д�������⣬�˹��̽�����������������������document����д�������⡣
			indexWriter.addDocument(document);
		}
		// �ر�IndexWriter����
		indexWriter.close();
	}
	public static void main(String[] args) throws IOException {
		createIndex();
	}

	//ɾ��ȫ������
	public static void deleteAllIndex() throws Exception {
		Directory directory = FSDirectory.open(new File("F:\\Eclipse8\\Lucene\\TestResource\\INDEXDB\\fileIndex"));
		Analyzer analyzer = new IKAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, analyzer);
		IndexWriter indexWriter = new IndexWriter(directory, config);
		//ɾ��ȫ������
		indexWriter.deleteAll();
		//�ر�indexwriter
		indexWriter.close();
	}
	//���ݲ�ѯ����ɾ������
    public static void deleteIndexByQuery() throws Exception {
    	Directory directory = FSDirectory.open(new File("F:\\Eclipse8\\Lucene\\TestResource\\INDEXDB\\fileIndex"));
        Analyzer analyzer = new IKAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, config);
        //����һ����ѯ����
        Query query = new TermQuery(new Term("fileContent", "apache"));
        //���ݲ�ѯ����ɾ��
        indexWriter.deleteDocuments(query);
        //�ر�indexwriter
        indexWriter.close();
    }
    //�޸�������
    public static void updateIndex() throws Exception {
    	Directory directory = FSDirectory.open(new File("F:\\Eclipse8\\Lucene\\TestResource\\INDEXDB\\fileIndex"));
        Analyzer analyzer = new IKAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, config);
        //����һ��Document����
        Document document = new Document();
        //��document�����������
        //��ͬ��document�����в�ͬ����ͬһ��document��������ͬ����
        document.add(new TextField("fileXXX", "Ҫ���µ��ĵ�", Store.YES));
        document.add(new TextField("contentYYY", "��� Lucene ��һ������ Java ��ȫ����Ϣ�������߰���", Store.YES));
        indexWriter.updateDocument(new Term("fileName", "apache"), document);
        //�ر�indexWriter
        indexWriter.close();
    }
}

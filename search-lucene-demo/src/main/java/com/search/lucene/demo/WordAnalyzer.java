package com.search.lucene.demo;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class WordAnalyzer {
    public static void main(String[] args) throws IOException {
        // 1������һ������������
        Analyzer analyzer = new IKAnalyzer(); // �������ķ�����
        // 2���ӷ����������л��tokenStream����
        // ����1��������ƣ�����Ϊnull��������""
        // ����2��Ҫ�������ı�
        TokenStream tokenStream = analyzer.tokenStream("", "���ݿ��д洢�������ǽṹ�����ݸ߸�˧����������java�������ö�ά��ṹ���߼����ʵ�ֵ����ݡ����ֹ��Ǻ��书");

        // 3������һ������(�൱��ָ��)��������ÿ����Ƕ������ͣ������ǹؼ��ʵ����ã�ƫ���������õȵ�
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class); // charTermAttribute�������ǰ�Ĺؼ���
        // ƫ����(��ʵ���ǹؼ������ĵ��г��ֵ�λ�ã��õ����λ����ʲô���أ���Ϊ���ǽ�������Ҫ�Ըùؼ��ʽ��и�����ʾ�����и�����ʾҪ֪������ؼ������ģ�)
        OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
        // 4������tokenStream��reset�����������ø÷��������׳�һ���쳣
        tokenStream.reset();
        // 5��ʹ��whileѭ�������������б�
        while (tokenStream.incrementToken()) {
            System.out.println("start��" + offsetAttribute.startOffset()); // �ؼ�����ʼλ��
            // 6����ӡ����
            System.out.println(charTermAttribute);
            System.out.println("end��" + offsetAttribute.endOffset()); // �ؼ��ʽ���λ��
        }
        // 7���ر�tokenStream����
        tokenStream.close();
    }

}

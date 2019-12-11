package com.lucenegrpc.server;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;

public class Indexer {
    public Indexer() {
    }

    public void index(String indexPath) throws IOException {
        Path indexDirPath = Paths.get(indexPath);
        Directory indexDir = NIOFSDirectory.open(indexDirPath);
        StandardAnalyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        config.setRAMBufferSizeMB(512.0);
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        IndexWriter indexWriter = new IndexWriter(indexDir,config);

        long startTime = System.currentTimeMillis();

        indexFiles(indexWriter, "name","tony",true);

        System.out.println("\n* Total time taken is "+(System.currentTimeMillis() - startTime)+"ms.");
        System.out.println("* Total document index contains is "+indexWriter.getDocStats().numDocs+".");
        System.out.println("* Closing Index Writer.");
        indexWriter.close();
        System.out.println("* Index Writer Closed Successfully.");
    }

    public void indexFiles(IndexWriter writer, String fieldName, String data, boolean isStore) throws IOException {
        Document doc = new Document();
        TextField stringField;

        if(isStore){
            stringField = new TextField(fieldName, data, Field.Store.YES);
        }
        else {
            stringField = new TextField(fieldName, data, Field.Store.NO);
        }

        doc.add(stringField);
        writer.addDocument(doc);
    }

    public static void main(String[] args){
        Indexer indexer = new Indexer();
        String indexPath = "/Users/tony/Desktop/Workspace/lucene/indexes/demo_index";

        try {
            indexer.index(indexPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

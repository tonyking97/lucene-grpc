package com.github.tonyking97.lucenegrpc.lucene;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Searcher {
    IndexSearcher indexSearcher;
    IndexReader reader;
    QueryParser queryParser;
    Query query;

    public Searcher(){
    }

    public static void main(String[] args) throws IOException, ParseException {
        Searcher searcher = new Searcher();
        String indexPath = "/Users/tony/Desktop/Workspace/lucene/indexes/demo_index";
        searcher.searcher(indexPath);
        Scanner scan = new Scanner(System.in);
        String query = "";
        while(true) {
            System.out.print("Enter the word to search :");
            query = scan.nextLine();
            if(query.equals("!q")) {
                System.out.println("Program terminated");
                searcher.close();
                scan.close();
                System.exit(0);
            }
            searcher.search(query);
        }
    }

    private void searcher(String indexDirPath) throws IOException {
        Directory indexDir = NIOFSDirectory.open(Paths.get(indexDirPath));
        reader = DirectoryReader.open(indexDir);
        indexSearcher = new IndexSearcher(reader);
        queryParser = new QueryParser("name",new StandardAnalyzer());
    }

    private TopDocs searchIndex(String searchQuery) throws ParseException, IOException {
        query = queryParser.parse(searchQuery);
        return indexSearcher.search(query, 10);
    }

    private Document getDocument(ScoreDoc scoreDoc) throws IOException {
        return indexSearcher.doc(scoreDoc.doc);
    }

    private void search(String query) throws ParseException, IOException {
        long startTime = System.currentTimeMillis();
        TopDocs hits = searchIndex(query);
        long endTime = System.currentTimeMillis();

        System.out.println(hits.totalHits + " Documents found in "+(endTime - startTime)+"ms.");

        startTime = System.nanoTime();
        for(ScoreDoc scoreDoc: hits.scoreDocs) {
            Document doc = getDocument(scoreDoc);
            System.out.println("name -> "+doc.get("name"));
        }
        endTime = System.nanoTime();
        long time = ((endTime - startTime)/1000);
        System.out.println("\n\nTime taken to retrive data is "+(time/1000)+"ms ("+time+" us).\n");
    }

    private void close() throws IOException {
        reader.close();
    }
}

package com.quertle.demo.service.lucene;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.quertle.demo.dto.SearchDto;
import com.quertle.demo.model.FierceNews;

@Service
public class LuceneSearchService {

	private static final Logger LOG = LoggerFactory.getLogger(LuceneSearchService.class);

	private static final String INDEX_DIR = "D:/lucene/index";

	/**
	 * This method searches for documents from Lucene index
	 * 
	 */
	public SearchDto serchFromIndex(String titleToSearch) {
		LOG.info("Searching Records");
		IndexSearcher searcher = createSearcher();
		try {
			// Search by ID
			TopDocs foundDocs = searchById(1, searcher);
			LOG.info("SearchByID Total Results: {}", foundDocs.totalHits);
			for (ScoreDoc sd : foundDocs.scoreDocs) {
				Document document = searcher.doc(sd.doc);
				LOG.info("ID Results: {}", String.format(document.get("id")));
			}

			// Search by Title
			// String titleToSearch = "News of Note—GlaxoSmithKline, Ebola and SK Bioscience
			// ";
			TopDocs foundDocs2 = searchByTitle(titleToSearch, searcher);
			LOG.info("SearchByTitle : {}", titleToSearch);
			LOG.info("SearchByTitle Total Results: {}", foundDocs2.totalHits);
			List<FierceNews> news = new ArrayList<>();
			for (ScoreDoc sd : foundDocs2.scoreDocs) {
				Document document = searcher.doc(sd.doc);
				// LOG.info("Title Results: {}", document.get("title"));
				FierceNews fierceNews = FierceNews.getFierceNews(document);
				news.add(fierceNews);
				LOG.info("Title Results: {}", fierceNews.getTitle());
			}
			return new SearchDto(titleToSearch, news);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This method searches the index by taking the ID value passed as parameter
	 * 
	 * @param id
	 * @param searcher
	 * @return
	 * @throws Exception
	 */
	private TopDocs searchById(Integer id, IndexSearcher searcher) throws Exception {
		QueryParser qp = new QueryParser("id", new StandardAnalyzer());
		Query idQuery = qp.parse(id.toString());
		TopDocs hits = searcher.search(idQuery, 10);
		return hits;
	}

	/**
	 * This method searches the index by taking the Title value passed as parameter
	 * 
	 * @param title
	 * @param searcher
	 * @return
	 * @throws Exception
	 */
	private TopDocs searchByTitle(String title, IndexSearcher searcher) throws Exception {
		QueryParser qp = new QueryParser("title", new StandardAnalyzer());
		Query titleQuery = qp.parse(title);
		TopDocs hits = searcher.search(titleQuery, 10);
		return hits;
	}

	/**
	 * This method creates Index Searcher
	 * 
	 * @return
	 */
	private IndexSearcher createSearcher() {
		Directory dir;
		IndexSearcher searcher = null;
		try {
			dir = FSDirectory.open(Paths.get(INDEX_DIR));
			IndexReader reader = DirectoryReader.open(dir);
			searcher = new IndexSearcher(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return searcher;
	}

}

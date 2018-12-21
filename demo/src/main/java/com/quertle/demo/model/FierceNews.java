package com.quertle.demo.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.lucene.document.Document;

@Entity
public class FierceNews {

	private static final String DATE_PUBLISHED = "datePublished";

	private static final String URL_LINK = "urlLink";

	private static final String TITLE = "title";
	
	private static final String FULL_TEXT = "fullText";
	
	private static final String ABSTRACT = "abstractContent";
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String title;

	/*
	 * @JoinTable(name = "fierce_author", joinColumns = @JoinColumn(name =
	 * "fierce_id", referencedColumnName = "id"), inverseJoinColumns
	 * = @JoinColumn(name = "author_id", referencedColumnName = "id"))
	 */
	@OneToMany(mappedBy = "fierceNews", fetch = FetchType.EAGER)
	private List<Author> authors;

	private Date datePublished;
	private String abstractContent;

	@Column(columnDefinition = "text")
	private String fullText;
	private String urlLink;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	public Date getDatePublished() {
		return datePublished;
	}

	public void setDatePublished(Date datePublished) {
		this.datePublished = datePublished;
	}

	public String getAbstractContent() {
		return abstractContent;
	}

	public void setAbstractContent(String abstractContent) {
		this.abstractContent = abstractContent;
	}

	public String getFullText() {
		return fullText;
	}

	public void setFullText(String fullText) {
		this.fullText = fullText;
	}

	public String getUrlLink() {
		return urlLink;
	}

	public void setUrlLink(String urlLink) {
		this.urlLink = urlLink;
	}

	public static FierceNews getFierceNews(Document document) throws ParseException {
		FierceNews n = new FierceNews();
		n.setTitle(document.get(TITLE));
		String strDate = document.get(DATE_PUBLISHED);
		n.setDatePublished(new SimpleDateFormat("YYYY-MM-dd").parse(strDate.substring(0, strDate.indexOf(" "))));
		n.setUrlLink(document.get(URL_LINK));
		return n;
	}
	
	public String[] getFields() {
		String[] fields = {TITLE, ABSTRACT, FULL_TEXT, URL_LINK, DATE_PUBLISHED };
		return fields;
	}
}

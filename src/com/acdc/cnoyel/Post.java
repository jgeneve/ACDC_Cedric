package com.acdc.cnoyel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * The class Post is used interact with a post(or news) that you want to create
 * @author Cedric NOYEL
 */
public class Post {
	private String title;
	private String text;
	private String category;
	private String author;
	private List<String> linkList = new ArrayList<>();
	private List<String> imgList = new ArrayList<>();

	public Post(String title, String category, String author, String text, List<String> linkList, List<String> imgLinkList) {
		this.title = title;
		this.category = category;
		this.author = author;
		this.text = text;
		this.linkList = linkList;
		this.imgList = imgLinkList;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}	
	
	public List<String> getLinkList() {
		return linkList;
	}

	public void setLinkList(List<String> linkList) {
		this.linkList = linkList;
	}

	public List<String> getImgList() {
		return imgList;
	}

	public void setImgList(List<String> imgList) {
		this.imgList = imgList;
	}

	/** Method returning a markdown String corresponding to the current post
	 * @return
	 * 		postMarkdown - aString containing the formatted markdown of the current post
	 */
	public String toMarkdown() {
		StringBuffer txt = new StringBuffer("");
		String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		txt.append("---\n"
				+ "layout: post" + "\n"
				+ "title: \"" + this.title + "\"\n"
				+ "date: " + date + "\n"
				+ "categories: " + this.category + "\n"
				+ "---" + "\n\n");
		txt.append("*By " + this.author + "*" + "\n\n");
		txt.append(this.text);

		if (!imgList.isEmpty()) {
			txt.append("<br /><br />Images:");
			for (int i=0; i<this.imgList.size(); i++) {
				txt.append("<br />![" + " Image " + i + "](" + this.imgList.get(i) + " \"Image" + i + "\")");
			}
		}
		
		if (!linkList.isEmpty()) {
			txt.append("<br /><br />Links:");
			for (int i=0; i<this.linkList.size(); i++) {
				txt.append("<br />[" + this.linkList.get(i) + "](" + this.linkList.get(i) + ")");
			}
		}
		
		String postMarkdown = txt.toString();
		return postMarkdown;
	}
	
	/**
	 * @return le nom du fichier markdown genere. Exemple: 2018-11-22-Le-titre.markdown
	 */
	public String getMarkdownFileName() {
		return new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()) + "-" + this.getTitle().replace(" ", "-") + ".markdown";
	}

	
}

package com.acdc.ihm;

import java.io.File;
import java.util.List;

import com.acdc.cnoyel.Categories;
import com.acdc.cnoyel.Post;
import com.acdc.cnoyel.Tools;


public class Main {

	public static void main(String[] args) {
		
		Post newPost = askPostData();

		String githubDirectory = "https://github.com/jgeneve/web_inria.git";
		String gitDirectory = "C:\\Users\\jgeneve\\Documents\\IMTA1\\ACDC\\web-master\\BLOG";
		String markdownFilePath = gitDirectory + File.separator + "_posts" + File.separator + newPost.getMarkdownFileName();
		
		Categories.categoriesFile = new File(gitDirectory + File.separator + "category" + File.separator + "category.txt");
		
		Categories.addCategory(newPost.getCategory());
		
        Tools.createMarkdownFile(newPost.toMarkdown(), markdownFilePath);
		Tools.executeCmd("bundle exec jekyll serve -o", gitDirectory, true);
		gitCommands(githubDirectory, gitDirectory);
		
		System.out.println("- Commandes git effectuées\r - End -");
	}

	/**
	 * Method that runs git add, commit and push
	 * @param githubDirectory - String of the distant git repository
	 * @param gitDirectory- String of the local git repository
	 */
	private static void gitCommands(String githubDirectory, String gitDirectory) {
		Tools.executeCmd("git add .", gitDirectory);
		Tools.executeCmd("git commit -m \"Add markdown file\"", gitDirectory);
		Tools.executeCmd("git push", gitDirectory);
	}

	private static Post askPostData() {
		System.out.println("Titre de la publication");
		String title = Tools.getStringUserInput();
		System.out.println("Catégorie de la publication");
		String category = Tools.getStringUserInput();
		System.out.println("Auteur de la publication");
		String author = Tools.getStringUserInput();
		System.out.println("Texte de la publication");
		String bodyText = Tools.getStringUserInput();
		System.out.println("Liens de la publication (séparés par un espace si plusieurs ou laisser vide si aucun)");
		List<String> linkList = Tools.stringToList(Tools.getStringUserInput(), " ");
		System.out.println("Liens des images (séparés par un espace si plusieurs images ou laisser vide si aucune)");
		List<String> imgLinkList = Tools.stringToList(Tools.getStringUserInput(), " ");
		
		return new Post(title, category, author, bodyText, linkList, imgLinkList);
	}
	
}

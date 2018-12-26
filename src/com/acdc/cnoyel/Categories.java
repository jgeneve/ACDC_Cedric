package com.acdc.cnoyel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is used to interact with elements concerning post's categories
 * (categories are stored in a text file, categories in the textfile are always lowercase)
 * 
 * @author Cedric NOYEL
 *
 */
public class Categories {

	public static File categoriesFile;
	
	/**
	 * add a category into 'category/categories.txt' file
	 * if the category already exist, it does not add it
	 * @param category - String name of the category to add
	 * @throws IOException
	 */
	public static void addCategory(String category) {
		try {	
			List<String> categoriesList = getCategories();
			if (!categoriesList.contains(category.toLowerCase())) {
				BufferedWriter writer = new BufferedWriter(new FileWriter(categoriesFile.getPath(), true));
				writer.append(category.toLowerCase());
				writer.newLine();
				writer.close();
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/** Method used to remove a category of the categories text file
	 * @param
	 * 		category - String of the category to remove
	 * @throws IOException 
	 */
	public static void removeCategory(String category) {
		try {
			List<String> out = Files.lines(categoriesFile.toPath())
					.filter(line -> !line.contains(category))
					.collect(Collectors.toList());
			Files.write(categoriesFile.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** Method returning a list of categories in the categories.txt file
	 * @return
	 * 		categoryList - List<String> containing each category from categories.txt file
	 */
	public static List<String> getCategories() {
		List<String> categoryList = new ArrayList<>();
		try {
			if (!categoriesFile.exists()) {
				categoriesFile.createNewFile();
			}
			BufferedReader reader = new BufferedReader(new FileReader(categoriesFile));
			String line;
			while ((line = reader.readLine()) != null) {
				categoryList.add(line);
			}
			reader.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return categoryList;
	}
}

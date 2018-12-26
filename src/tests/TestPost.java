package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.acdc.cnoyel.Post;

class TestPost {

	@Test
	void testPostGetters() {
		
		ArrayList<String> linkList = new ArrayList<String>();
		linkList.add("http://facebook.com");
		linkList.add("http://youtube.com");
		linkList.add("http://twiter.com");
		
		ArrayList<String> imgList = new ArrayList<String>();
		imgList.add("https://yt3.ggpht.com/-zpy4bN8SAzI/AAAAAAAAAAI/AAAAAAAAA9g/keBIWJp-O0Y/s48-c-k-c0xffffffff-no-nd-rj/photo.jpg");
		
		Post p = new Post("testTitre", "testCategorie", "testAuteur", "this is my body text", linkList, imgList);
		
		assertEquals("testTitre", p.getTitle());
		assertEquals("testCategorie", p.getCategory());
		assertEquals("testAuteur", p.getAuthor());
		assertEquals("this is my body text", p.getText());

		for(int i=0; i<linkList.size(); i++) {
			assertEquals(linkList.get(i), p.getLinkList().get(i));
		}
		
		for(int i=0; i<imgList.size(); i++) {
			assertEquals(imgList.get(i), p.getImgList().get(i));
		}
	}

}

/**
 * TestIbooksHandler 31.10.2013
 *
 * @author Philipp Haussleiter
 *
 */
package de.javastream.aibe;

import java.io.File;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class TestIbooksHandler extends GenericTest {

    private final static String FILENAME = "test.ibooks";
    private final static String FOLDER = "target" + File.separator + "test-classes";

    @Test
    public void testBooksScan() {
        IbooksHandler handler = new IbooksHandler(FOLDER);
        List<String> books = handler.getFiles();
        Assert.assertTrue(books.size() == 1);
        Assert.assertNotNull(books.get(0));
        Assert.assertTrue(books.get(0).endsWith(FILENAME));
    }

    @Test
    public void testForNullBookContent() {
        IbooksHandler handler = new IbooksHandler(FOLDER);
        List<String> contentList = handler.getContent(null);
        Assert.assertTrue(contentList.isEmpty());
    }

    @Test
    public void testGetBookContent() {
        IbooksHandler handler = new IbooksHandler(FOLDER);
        List<String> books = handler.getFiles();
        String outputDir = handler.extract(books.get(0));
        List<String> contentList = handler.getContent(outputDir);
        Assert.assertTrue(contentList.size() > 0);
    }

    @Test
    public void testGetBookImages() {
        IbooksHandler handler = new IbooksHandler(FOLDER);
        List<String> books = handler.getFiles();
        String outputDir = handler.extract(books.get(0));
        List<String> contentList = handler.getImages(outputDir);
        for (String contentFile : contentList) {
            File f = new File(contentFile);
            Assert.assertTrue(checkIfImage(f));
        }
    }
}

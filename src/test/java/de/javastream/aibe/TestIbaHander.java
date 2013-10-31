/**
 * TestIbaHander 31.10.2013
 *
 * @author Philipp Haussleiter
 *
 */
package de.javastream.aibe;

import java.io.File;
import java.util.List;
import javax.activation.MimetypesFileTypeMap;
import org.junit.Assert;
import org.junit.Test;

public class TestIbaHander {

    private final static String FILENAME = "test.iba";
    private final static String FOLDER = "target" + File.separator + "test-classes";

    @Test
    public void testBooksScan() {
        IbaHander handler = new IbaHander(FOLDER);
        List<String> books = handler.getFiles();
        Assert.assertTrue(books.size() == 1);
        Assert.assertNotNull(books.get(0));
        Assert.assertTrue(books.get(0).endsWith(FILENAME));
    }

    @Test
    public void testForNullBookContent() {
        IbaHander handler = new IbaHander(FOLDER);
        List<String> contentList = handler.getContent(null);
        Assert.assertTrue(contentList.isEmpty());
    }

    @Test
    public void testGetBookContent() {
        IbaHander handler = new IbaHander(FOLDER);
        List<String> books = handler.getFiles();
        String outputDir = handler.extract(books.get(0));
        List<String> contentList = handler.getContent(outputDir);
        Assert.assertTrue(contentList.size() > 0);
    }

    @Test
    public void testGetBookContentStructure() {
        IbaHander handler = new IbaHander(FOLDER);
        List<String> books = handler.getFiles();
        String outputDir = handler.extract(books.get(0));
        handler.extractStructure(outputDir);
    }

    @Test
    public void testGetBookImages() {
        IbaHander handler = new IbaHander(FOLDER);
        List<String> books = handler.getFiles();
        String outputDir = handler.extract(books.get(0));
        List<String> contentList = handler.getImages(outputDir);
        for (String contentFile : contentList) {
            File f = new File(contentFile);
            String mimetype = new MimetypesFileTypeMap().getContentType(f);
            String type = mimetype.split("/")[0];
            Assert.assertTrue("File: " + contentFile, type.equals("image"));
        }
    }
}

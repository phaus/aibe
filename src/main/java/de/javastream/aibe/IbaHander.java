/**
 * IbaHander 31.10.2013
 *
 * @author Philipp Haussleiter
 *
 */
package de.javastream.aibe;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

public class IbaHander extends BooksHandler {

    private static final Logger LOGGER = Logger.getLogger(IbaHander.class.getName());

    public IbaHander(String folderName) {
        super(folderName);
    }

    public List<String> getFiles() {
        return scan(new IbaFilter());
    }

    public String extractStructure(String outputDir) {
        File outputDirStructure = new File(outputDir + ".structure");
        File indexFile = new File(outputDir + File.separator + "index.xml");
        if (indexFile.exists()) {
            if (!outputDirStructure.exists()) {
                outputDirStructure.mkdirs();
            }
            extractXML2Folder(indexFile, outputDirStructure);
            return outputDirStructure.getAbsolutePath();
        }
        return null;
    }

    private void extractXML2Folder(File indexFile, File outputDir) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(indexFile, new IbaIndexHandler(outputDir));
        } catch (ParserConfigurationException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private class IbaFilter implements FileFilter {

        public boolean accept(File pathname) {
            return (pathname != null && pathname.getName().endsWith(".iba"));
        }

    }
}

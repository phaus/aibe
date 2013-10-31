/**
 * IbaIndexHandler 31.10.2013
 *
 * @author Philipp Haussleiter
 *
 */
package de.javastream.aibe;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * This Class Extracts the Structure of the index.xml File to the File System.
 * @author Philipp Haußleiter
 */
public class IbaIndexHandler extends DefaultHandler {

    private static final Logger LOGGER = Logger.getLogger(IbaIndexHandler.class.getName());
    private File currentDir;
    private String name;
    private StringBuilder sb = new StringBuilder();
    private Properties properties = new Properties();
    private boolean inElement = false;

    public IbaIndexHandler(File outputDirStructure) {
        currentDir = outputDirStructure;
    }

    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        inElement = true;
        properties.setProperty("qName", qName);
        name = getCleanName(qName);
        currentDir = new File(currentDir.getAbsolutePath() + File.separator + name);
        currentDir.mkdirs();
        for (int i = 0; i < attributes.getLength(); i++) {
            properties.setProperty(attributes.getLocalName(i), attributes.getValue(i));
        }
        writeProperties(properties, new File(currentDir + File.separator + "info.properties"));
        properties = new Properties();
        LOGGER.log(Level.FINER, "Start Element {0}", name);
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (inElement) {
            sb.append(new String(ch, start, length));
        }
    }

    @Override
    public void endElement(String uri, String localName,
            String qName) throws SAXException {
        inElement = false;
        name = getCleanName(qName);
        if (!sb.toString().isEmpty()) {
            writeContent(sb.toString(), new File(currentDir + File.separator + "content.txt"));
        }
        LOGGER.log(Level.FINER, "End Element {0}", name);
        currentDir = currentDir.getParentFile();
        sb = new StringBuilder();
    }

    private String getCleanName(String qName) {
        return qName.replace("_", "__").replace(":", "_");
    }

    private void writeContent(String string, File file) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedWriter writer = getWriter(file);
            writer.write(string);
            writer.close();
        } catch (UnsupportedEncodingException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private void writeProperties(Properties props, File file) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            String keyString, valueString;
            BufferedWriter writer = getWriter(file);

            for (Object key : props.keySet()) {
                keyString = (String) key;
                valueString = props.getProperty((String) key);
                writer.write(keyString + "=" + valueString);
                writer.write("\n");
            }
            writer.close();
        } catch (UnsupportedEncodingException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private BufferedWriter getWriter(File file) throws FileNotFoundException, UnsupportedEncodingException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath()), "UTF-8"));
        return writer;
    }
}

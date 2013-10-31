/**
 * StaticBooksFileds
 * 31.10.2013
 * @author Philipp Haussleiter
 *
 */
package de.javastream.aibe;

import java.io.File;

public class StaticBooksFileds {
    public static final String JAVA_VERSION = System.getProperty("java.version");
    public static final boolean NEWER_JDK = !JAVA_VERSION.startsWith("1.6");
    public static final String[] IMAGE_EXTENSIONS = {".jpeg", ".png", ".tiff", ".jpg", ".svg"};
    protected static final String TMP_DIR = System.getProperty("java.io.tmpdir") + File.separator + "aibe";
    private StaticBooksFileds(){}
}

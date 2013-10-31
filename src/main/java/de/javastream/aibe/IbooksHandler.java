/**
 * IbooksHandler 31.10.2013
 *
 * @author Philipp Haussleiter
 *
 */
package de.javastream.aibe;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

public class IbooksHandler extends BooksHandler {

    public IbooksHandler(String folderName) {
        super(folderName);
    }

    public List<String> getFiles() {
        return scan(new IbooksFilter());
    }

    class IbooksFilter implements FileFilter {

        public boolean accept(File pathname) {
            return (pathname != null && pathname.getName().endsWith(".ibooks"));
        }
    }
}

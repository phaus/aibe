/**
 * GenericTest 31.10.2013
 *
 * @author Philipp Haussleiter
 *
 */
package de.javastream.aibe;

import java.io.File;
import javax.activation.MimetypesFileTypeMap;

public class GenericTest {

    protected boolean checkIfImage(File contentFile) {
        String mimetype = new MimetypesFileTypeMap().getContentType(contentFile);
        String type = mimetype.split("/")[0];
        return type.equals("image");
    }
}

/**
 * BooksHandler 31.10.2013
 *
 * @author Philipp Haussleiter
 *
 */
package de.javastream.aibe;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class BooksHandler {

    private static final Logger LOGGER = Logger.getLogger(BooksHandler.class.getName());

    protected File folder;

    public BooksHandler(String folderName) {
        checkDirs();
        folder = new File(folderName);
    }

    public List<String> getContent(String outputDirName) {
        List<String> files = new ArrayList<String>();
        if (outputDirName != null) {
            File outputDirFolder = new File(outputDirName);
            if (outputDirFolder.exists()) {
                for (File f : outputDirFolder.listFiles()) {
                    files.add(f.getAbsolutePath());
                }
            }
        }
        return files;
    }

    public List<String> getImages(String outputDirName) {
        List<String> files = new ArrayList<String>();
        if (outputDirName != null) {
            File outputDirFolder = new File(outputDirName);
            if (outputDirFolder.exists()) {
                for (File f : outputDirFolder.listFiles(new ImagesFilter())) {
                    files.add(f.getAbsolutePath());
                }
            }
        }
        return files;
    }

    protected List<String> scan(FileFilter filer) {
        List<String> files = new ArrayList<String>();
        if (folder.exists() && folder.isDirectory()) {
            for (File f : folder.listFiles(filer)) {
                files.add(f.getAbsolutePath());
            }
        }
        return files;
    }

    public String extract(String path) {
        if (path == null) {
            return null;
        }
        ZipInputStream zis = null;
        byte[] buffer = new byte[1024];
        try {
            File file = new File(path);
            File outputDir = new File(StaticBooksFileds.TMP_DIR + File.separator + file.getName());
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }
            zis = new ZipInputStream(new FileInputStream(file));
            ZipEntry ze = zis.getNextEntry();
            while (ze != null) {

                String fileName = ze.getName();
                File newFile = new File(outputDir + File.separator + fileName);
                LOGGER.log(Level.FINER, "file unzip : {0}", newFile.getAbsoluteFile());
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }

                fos.close();
                ze = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();
            return outputDir.getAbsolutePath();
        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (zis != null) {
                    zis.close();
                }
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    private class ImagesFilter implements FileFilter {

        public boolean accept(File pathname) {
            for (String ext : StaticBooksFileds.IMAGE_EXTENSIONS) {
                if (pathname.getName().toLowerCase().endsWith(ext)) {
                    return true;
                }
            }
            return false;
        }
    }

    private void checkDirs() {
        String[] dirs = {StaticBooksFileds.TMP_DIR};
        File f;
        for (String dir : dirs) {
            f = new File(dir);
            if (!f.exists()) {
                f.mkdirs();
            }
        }
    }

}

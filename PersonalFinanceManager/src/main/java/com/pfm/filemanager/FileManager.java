/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.filemanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.ServletContext;

/**
 *
 * @author mihail
 */
public class FileManager {

    public static String createDirectory(String dirToCreate, ServletContext context) {
        String contextPath = context.getRealPath("/");
        Path finalPath = Paths.get(contextPath, dirToCreate);
        File dir = new File(finalPath.toString());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir.getAbsolutePath();
    }

    public static String createFile(String targetDir, String fileName, ServletContext context, String content) {
        createDirectory(targetDir, context);
        String contextPath = context.getRealPath("/");
        Path finalPath = Paths.get(contextPath, targetDir, fileName);
        File file = new File(finalPath.toString());
        try (FileOutputStream os = new FileOutputStream(file)) {
            os.write(content.getBytes());
            if (!file.exists()) {
                file.createNewFile();
            }
            os.close();
            return file.getAbsolutePath();
        } catch (IOException exception) {
            return "";
        }
    }

    public static String getFileContent(String targetFile, ServletContext context) {
        String content = "";
        String contextPath = context.getRealPath("/");
        Path finalPath = Paths.get(contextPath, targetFile);
        File fileToRead = new File(finalPath.toString());
        try (FileReader fileStream = new FileReader(fileToRead);
                BufferedReader bufferedReader = new BufferedReader(fileStream)) {
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                content += line;
            }
            fileStream.close();
            bufferedReader.close();
            return content;
        } catch (FileNotFoundException ex) {
            return content;
        } catch (IOException ex) {
            return content;
        }

    }
}

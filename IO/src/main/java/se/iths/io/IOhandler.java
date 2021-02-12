package se.iths.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class IOhandler {

    public static byte[] readFromFile(File file) {
        byte[] content = new byte[0];
        if (file.exists() && file.canRead()) {
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                content = new byte[(int) file.length()];
                int count = fileInputStream.read(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;
    }

    //Skriver till listan title.txt
    public static void FileWriter(String url, String result) {
        try {
            FileWriter myWriter = new FileWriter(".." + File.separator + "web" + url);
            myWriter.write(result);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

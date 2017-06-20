package com.chenxx.lib.java;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by asdgbc on 20/6/2017.
 */
public class StreamUtils {

    /**
     *
     * @param fileName
     * @return
     */
    public static byte[] readBytesFromFile(String fileName) {
        FileInputStream fis = null;
        ByteOutputStream bos = null;
        try {
            fis = new FileInputStream(fileName);
            bos = new ByteOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            return bos.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeIfNotNull(fis);
            closeIfNotNull(bos);
        }
        return null;
    }

    public static void closeIfNotNull(Closeable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

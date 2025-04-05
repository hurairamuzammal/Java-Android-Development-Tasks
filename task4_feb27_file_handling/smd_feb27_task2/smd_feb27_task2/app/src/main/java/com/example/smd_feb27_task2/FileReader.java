
package com.example.smd_feb27_task2;
import android.content.Context;
import java.io.InputStream;

import java.io.IOException;
public class FileReader {
    //hello
    public static String readFileFromAssets(Context context, String fileName)
    {
        StringBuilder content = new StringBuilder();
        try {
            InputStream inputStream = context.getAssets().open("inputFile.txt");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            content.append(new String(buffer));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
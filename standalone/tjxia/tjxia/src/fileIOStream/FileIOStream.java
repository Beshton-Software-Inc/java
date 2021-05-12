package fileIOStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class FileIOStream {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("./standalone/cool/tjxia/resource/input.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("./standalone/cool/tjxia/resource/output.txt");
        byte[] buffer = new byte[1024];
        int c;
        while((c = fileInputStream.read(buffer, 0, buffer.length)) != -1){
            fileOutputStream.write(buffer, 0, c);
        }

        fileOutputStream.close();
        fileInputStream.close();

    }
}

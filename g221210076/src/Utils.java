/**
*
* @author Cengizhan Keyfli-cengizhan.keyfli@ogr.sakarya.edu.tr
* @since 29.03.2024
* <p>
* dosya tarama
* </p>
*/
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Stack;

public class Utils {

    static void scanFolder(String folderPath, Stack<String> javaFileContentStack, Stack<String> javaFileNameStack) throws IOException {

        File folder = new File(folderPath);
        for (File file : folder.listFiles()) {
            if (file.getName().endsWith(".java")) {
                javaFileNameStack.push(file.getName());
                Path filePath = file.toPath();
                String fileContent = Files.readString(filePath);
                javaFileContentStack.push(fileContent);
            }
            if (file.isDirectory()) {
                scanFolder(file.getAbsolutePath(), javaFileContentStack, javaFileNameStack);
            }
        }
    }

}

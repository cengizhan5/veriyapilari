/**
*
* @author Cengizhan Keyfli-cengizhan.keyfli@ogr.sakarya.edu.tr

* @since 04.04.2024
* <p>
* Genel olarak ekrana yazma işlemleri-clonlama işlemleri-clonlanacak github ve dosya adları alma
* </p>
*/
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    
	public static boolean cloneRepository(String repositoryUrl, String localPath) {
		 Path path = Paths.get(localPath);
		 
		 if (Files.exists(path) && Files.isDirectory(path))
		 {
			 System.out.println("\"" + localPath + "\"" + "isminde klasör mevcut!");
			 System.out.println("Lütfen farklı bir isimlendirme deneyiniz!");
			 return false;
		 }
		 
		try {
            String command = "git clone " + repositoryUrl + " " + localPath;
            Process process = Runtime.getRuntime().exec(command);

            int exitCode = process.waitFor();

            if (exitCode == 0) {
                //System.out.println("Repository cloned successfully.");
            } else {
                System.err.println("Error occurred while cloning repository.");
                return false;
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error occurred while cloning repository: " + e.getMessage());
            System.out.print("CATCHfgaefa: ");
        }
		return true;
    }
	
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        
        System.out.print("İndirilecek projenin github linkini girin: ");
        String githubLink = scanner.nextLine();
        System.out.print("Projenin indirileceği klasör için isim giriniz: ");
        String folderPath = scanner.nextLine();
        if (!cloneRepository(githubLink, folderPath))
        {
        	return ;
        }
        Stack<String> javaFileContentStack = new Stack<>();
        Stack<String> javaFileNameStack = new Stack<>();

        Utils.scanFolder(folderPath, javaFileContentStack, javaFileNameStack);
        displayContentInformation(javaFileContentStack, javaFileNameStack);
    }
    public static void displayContentInformation(Stack<String> javaFileContentStack, Stack<String> javaFileNameStack)
    {
        while (!javaFileNameStack.isEmpty() && !javaFileContentStack.isEmpty())
        {
            String currentContent = javaFileContentStack.pop();
            String currentFileName = javaFileNameStack.pop();
            if (isClass(currentContent))
            {
                System.out.println("Sınıf: " + currentFileName);
                System.out.println("Javadoc Satır Sayısı: " + stringHandler.getJavadocNumber(currentContent));
                System.out.println("Yarum Satır Sayısı: " + stringHandler.getCommentNumber(currentContent));
                System.out.println("Kod Satır Sayısı: " + stringHandler.getCodeNumber(currentContent));
                System.out.println("LOC: " + stringHandler.getFileLineNumber(currentContent));
                System.out.println("Fonksiyon Sayısı: " + stringHandler.getFunctionNumber(currentContent));
                System.out.printf("Yorum Sapma Yüzdesi: %% %.2f\n", stringHandler.getCommantPercantage(currentContent));
                System.out.println("-----------------------------------------");
            }

        }

    }
    public static boolean isClass(String content) {
        String classPattern = "(?s)\\bclass\\s+(\\w\\s*)+\\{";

        Pattern pattern = Pattern.compile(classPattern);
        Matcher matcher = pattern.matcher(content);

        return matcher.find();
    }


}
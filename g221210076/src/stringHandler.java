/**
*
* @author Cengizhan Keyfli-cengizhan.keyfli@ogr.sakarya.edu.tr
* @since 01.04.2024
* <p>
* sapmalar ve sayısal degerleri bulma
* </p>
*/
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class stringHandler {

    public static int getJavadocNumber(String arg) {
        int res = 0;

        for (int i = 0; i < arg.length() - 1; i++) {
            if (i < arg.length() - 3 && arg.charAt(i) == '/' && arg.charAt(i + 1) == '*' && arg.charAt(i + 2) == '*' && arg.charAt(i+3) != '/') {
                i += 3;
                while (i < arg.length()) {
                    if (arg.charAt(i) == '*' && arg.charAt(i + 1) == '/')
                    {
                        res--;
                        break;
                    }
                    if (arg.charAt(i) == '\n')
                        res++;
                    i++;
                }
            }
        }
        return res;
    }
    public static int getCommentNumber(String arg) {
        int res = 0;

        for (int i = 0; i < arg.length() - 1; i++) {
            if (arg.charAt(i) == '/' && arg.charAt(i + 1) == '/') {
                res++;
                while (i < arg.length() - 1 && arg.charAt(i) != '\n')
                    i++;
                i--;
            } else if (i < arg.length() - 3 && arg.charAt(i) == '/' && arg.charAt(i + 1) == '*' && arg.charAt(i + 2) != '*') {// /**/ buna ne olucak
                i += 2;
                boolean flag = false;
                while (i < arg.length()) {
                    if (arg.charAt(i) == '*' && arg.charAt(i + 1) == '/')
                    {
                        if (flag)
                            res--;
                        break;
                    }
                    if (arg.charAt(i) == '\n')
                    {
                        flag = true;
                        res++;
                    }
                    i++;
                }
            }
        }
        return res;
    }
    public static int getCodeNumber(String arg){
        int res = 0;
        int i = 0;
        while (i < arg.length())
        {
            while (arg.charAt(i) == ' ' || arg.charAt(i) == '\t' ||arg.charAt(i) == '\n')
                i++;
            if (i+1 < arg.length() && arg.charAt(i) == '/' && arg.charAt(i+1) == '/'){
                while (i<arg.length() && arg.charAt(i) != '\n')
                    i++;
                if (i == arg.length())
                    return res;
                if (arg.charAt(i) == '\n')
                    i++;
            }
            else if (i+1 < arg.length() && arg.charAt(i) == '/' && arg.charAt(i+1) == '*')
            {
                i+=2;
                while (i+1<arg.length() && !(arg.charAt(i) == '*' && arg.charAt(i+1) == '/'))
                {
                    i++;
                }
                if (i+1<arg.length() && arg.charAt(i) == '*' && arg.charAt(i+1) == '/'){
                    i += 2;
                }
                if (i == arg.length())
                    return res;
                if (arg.charAt(i) == '\n')
                    i++;
            }
            else{
                res++;
                while (i<arg.length() && arg.charAt(i) != '\n')
                    i++;
                if (i == arg.length())
                    return res;
                if (arg.charAt(i) == '\n')
                    i++;
            }
        }

        return res;
    }
    public static int getFileLineNumber(String arg) {
        int res = 0;
        int i=0;
        while (i<arg.length())
        {
            if (arg.charAt(i) == '\n')
            {
                res++;
                i++;
            }
            else
                i++;
        }
        return res;
    }
    public static int getFunctionNumber(String arg){
        int res = 0;
        String functionPattern = "(public|private)\\s+(\\w+\\s*)+\\([^)]*\\)\\s*\\{";

        Pattern pattern = Pattern.compile(functionPattern);
        Matcher matcher = pattern.matcher(arg);
        while (matcher.find()) {
            res++;
        }
        return res;
    }
    public static double getCommantPercantage(String arg) {
        double __yg__ = (((double)getJavadocNumber(arg) + (double) getCommentNumber(arg)) * 0.8) / (double) getFunctionNumber(arg);
        double __yh__ = ((double) getCodeNumber(arg) / (double) getFunctionNumber(arg)) * 0.3;
        double res = ((100 * __yg__) / __yh__) - 100;
        return res;
    }
/*
YG=[(Javadoc_Satır_Sayısı + Diğer_yorumlar_satır_sayısı)*0.8]/Fonksiyon_Sayisi
YH= (Kod_satir_sayisi/Fonksiyon_Sayisi)*0.3
Yorum Sapma Yüzdesinin Hesabı: [(100*YG)/YH]-100
 */


}


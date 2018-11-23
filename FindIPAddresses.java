import java.io.*;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
Find ip addresses that are in any folder/file under root

*/
class FindIPAddresses {
    public static void main(String[] args) throws IOException {
        File root = new File("/root");
        Queue<File> files = new LinkedList<>();
        files.add(root);
        StringBuilder sb = new  StringBuilder();
        while(!files.isEmpty()) {
            File currentFile = files.poll();
            for(File file : currentFile.listFiles()) {
                if(file.isDirectory()) {
                    files.add(file);
                } else {
                    sb.append(new String(Files.readAllBytes(file.toPath()),"UTF-8"));
                }
            }
        }
        Pattern p = Pattern.compile("\\b(?:(?:[0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\.){3}(?:[0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\b",Pattern.MULTILINE);
        Matcher m = p.matcher(sb.toString());
        
        StringBuilder response = new StringBuilder();
        Set<String> ips = new TreeSet();
        
        while(m.find()) {
            //response.append(m.group()+"\n");
            ips.add(m.group());
        }
        for(String ip :  ips) {
            System.out.println(ip);
        }
    }
}

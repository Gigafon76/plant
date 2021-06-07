package space.example.myapplication.Util;

import java.io.File;
import java.util.ArrayList;

public class FileSearch {
    /**
     * Search a directory and return a list of all **directories** container inside
     * @param directory
     * @return
     */
    public static ArrayList <String> getDirectoryPaths(String directory){
        ArrayList<String> pathArray= new ArrayList<>();
        File file = new File(directory);
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (int i = 0; i<listFiles.length;i++){
                if (listFiles[i].isDirectory()){
                    pathArray.add(listFiles[i].getAbsolutePath());
                }
            }
        }
        return pathArray;
    }
    /**
     * Search a directory and return a list of all **files** container inside
     * @param directory
     * @return
     */
    public static ArrayList<String> getFilePaths (String directory){
        ArrayList<String> pathArray= new ArrayList<>();
        File file = new File(directory);
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (int i = 0; i<listFiles.length;i++){
                if (listFiles[i].isFile()){
                    pathArray.add(listFiles[i].getAbsolutePath());
                }
            }
        }
        return pathArray;
    }
}

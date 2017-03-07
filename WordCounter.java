
import java.io.*;
import java.util.*;

/*
    Name: Ajevan Mahadaya and Saijeeshan Keetheswaran
    Date : 3/7/2017
    Name of Program: Spam Master 3000
*/

public class WordCounter {
    private Map<String,Integer> wordCounts;

    public WordCounter() {
        wordCounts = new TreeMap<>();
    }
    /*
        The processFile method is a method that finds the words in the file
        required
        @param file- the file name and the file info
     */
    public void processFile(File file) throws IOException {
        if (file.isDirectory()) {
            // for directories, recursively call
            File[] filesInDir = file.listFiles();
            for (int i = 0; i < filesInDir.length; i++) {
                processFile(filesInDir[i]);
            }
        } else {
            // for single files, load the words and count
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String word = scanner.next();
                if (isWord(word)) {
                    countWord(word);
                }
            }
        }
    }
    /*
       The countWord method is a method that counts the words in a file
       required
       @param word- a single word
    */
    private void countWord(String word) {
        if (wordCounts.containsKey(word)) {
            // increment the count
            int oldCount = wordCounts.get(word);
            wordCounts.put(word, oldCount+1);
        } else {
            // add the word with count of 1
            wordCounts.put(word, 1);
        }
    }
    /*
       The isWord method is a method that checks to see if the word is a word
       @param token- a single word token
    */
    private boolean isWord(String token) {
        String pattern = "^[a-zA-Z]*$";
        if (token.matches(pattern)) {
            return true;
        } else {
            return false;
        }
    }
    /*
       The printWordCounts method is a method that prints the word count
       @return map this returns the wordCount map which contains a string and a number
    */
    public Map<String,Integer> printWordCounts() throws IOException {
        return wordCounts;
    }
}

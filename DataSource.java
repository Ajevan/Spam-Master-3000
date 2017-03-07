


import javafx.collections.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/*
    Name: Ajevan Mahadaya and Saijeeshan Keetheswaran
    Date : 3/7/2017
    Name of Program: Spam Master 3000
*/

public class DataSource {
    private File mainDir;
    private File [] f;
    private HashMap<String,Integer> spam=new HashMap<>();
    private HashMap<String,Integer> ham=new HashMap<>();
    private HashMap<String,Double> SWi = new HashMap<>();
    private int hamCount=0;
    private int spamCount=0;
    private double n=0.0;
    private double SF = 0.0;
    /*
        The dataSource method is a method that sets up the main directory names and information
        @param mainDir- the main directory name and information
     */
    public DataSource(File mainDir){
        this.mainDir = mainDir;
    }
    /*
        The ObservableList method is a method that sets up the initial table
     */
    public ObservableList<TestFile> getAllMail() throws IOException{
        ObservableList<TestFile> mails = FXCollections.observableArrayList();

        f = mainDir.listFiles();
        for (int i = 1; i >= 0 ; i=i-1) {
            if (f[i].getName().equals("train")){
                calculateTrain(f[i]);
            }
            else if (f[i].getName().equals("test")){
                mails=calculateTest(f[i]);
            }
        }
        return mails;
    }
    /*
        The calculateTrain method is a method that calculates the spam probabilities
        required
        @param file- the file name and the file info
     */
    public void calculateTrain(File file){
        File[] files=file.listFiles();
        HashMap<String,Integer> totalMap=new HashMap<>();
        for (int i= 0; i < files.length; i++) {

            if (files[i].isFile()) {
                try {
                    if (files[i].getParent().split("/")[files[i].getParent().split("/").length - 1].charAt(0) == 'h') {
                        hamCount += 1;
                    } else if (files[i].getParent().split("/")[files[i].getParent().split("/").length - 1].charAt(0) == 's') {
                        spamCount += 1;
                    }
                    WordCounter wordCounter = new WordCounter();
                    wordCounter.processFile(files[i]);
                    Map<String, Integer> map = wordCounter.printWordCounts();
                    Iterator it = map.entrySet().iterator();


                    while (it.hasNext()) {
                        String item = it.next().toString();
                        String word = item.split("=")[0];
                        int count = Integer.parseInt(item.split("=")[1]);

                        if (files[i].getParent().split("/")[files[i].getParent().split("/").length - 1].charAt(0) == 'h') {
                            if (ham.containsKey(word)!=true)
                                ham.put(word, 0);
                            ham.put(word,ham.get(word)+1);
                        } else {
                            if (spam.containsKey(word)!=true)
                                spam.put(word, 0);
                            spam.put(word,spam.get(word)+1);
                        }

                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            else if (files[i].isDirectory()) {
                    calculateTrain(files[i]);
            }
        }
        HashMap<String,Double> WiS=new HashMap<>();
        HashMap<String,Double> HiS = new HashMap<>();
        Double SF=0.0;

        for (Map.Entry<String, Integer> item:spam.entrySet()){
            WiS.put(item.getKey(),((double)item.getValue())/((double)spamCount));
        }
        for (Map.Entry<String, Integer> item:ham.entrySet()){
            HiS.put(item.getKey(),((double)item.getValue())/((double)hamCount));
        }
        for (Map.Entry<String, Integer> item:spam.entrySet())
        {
            if(HiS.containsKey(item.getKey())) {
                SWi.put(item.getKey(), WiS.get(item.getKey())/(WiS.get(item.getKey()) + HiS.get(item.getKey())));
            }
        }
    }
    /*
        The calculateTest method is a method that calculates the spam probabilities
        required
        @param file- the file name and the file info
        @return double This returns the spam probability
     */
    public ObservableList <TestFile> calculateTest(File file){
        ObservableList<TestFile> mails=FXCollections.observableArrayList();
        File[] files=file.listFiles();
        for (int x=0;x<files.length;x++) {
            if (files[x].isFile()) {
                try {
                    FileReader fr = new FileReader(files[x].getPath());
                    BufferedReader br = new BufferedReader(fr);
                    String line;
                    while ((line = br.readLine()) != null) {
                        for (String word : line.split(" ")) {
                            if (SWi.containsKey(word)) {
                                n += Math.log(1 - SWi.get(word)) - Math.log(SWi.get(word));
                            }
                        }
                    }
                    SF = 1 /(1 + Math.exp(n));

                    n=0;
                    mails.add((new TestFile(files[x].getName(),
                            (new StringTokenizer(files[x].getParent().split("/")[files[x].getParent().split("/").length-1],"1234567890").nextToken()),
                            SF)));
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            else if (files[x].isDirectory())
                mails.addAll(calculateTest(files[x]));
        }

        return mails;
    }
}


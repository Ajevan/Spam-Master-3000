/**
 * Created by ajevan on 26/02/17.
 */

import javafx.collections.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.StringTokenizer;


public class DataSource {
    private File mainDir;
    private File [] f;
    private Map<String,Integer> wordCounts;
    public DataSource(File mainDir){
        this.mainDir = mainDir;
    }

    public ObservableList<TestFile> getAllMail() throws IOException{
        ObservableList<TestFile> mails = FXCollections.observableArrayList();

            f = mainDir.listFiles();

        for (int i= 0; i < f.length; i++) {
            if (f[i].isFile())
            {
                mails.add(new TestFile(f[i].getName(),
                        (new StringTokenizer(f[i].getParent().split("/")[f[i].getParent().split("/").length-1],"1234567890").nextToken()),
                        0));
            }
            else if(f[i].isDirectory()){
                mails.addAll(new DataSource(new File(mainDir.getPath()+"/"+f[i].getName())).getAllMail());
            }
        }

        return mails;
    }

}


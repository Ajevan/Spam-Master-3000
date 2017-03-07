
import java.text.DecimalFormat;

/*
    Name: Ajevan Mahadaya and Saijeeshan Keetheswaran
    Date : 3/7/2017
    Name of Program: Spam Master 3000
*/

public class TestFile {

    private String filename;
    private double spamProbability;
    private String actualClass;

    public TestFile(String filename,
                    String actualClass,
                    double spamProbability
                    ) {
        this.filename = filename;
        this.spamProbability = spamProbability;
        this.actualClass = actualClass;
    }

    public String getFilename() { return this.filename; }
    public double getSpamProbability() { return this.spamProbability; }
    public String getSpamProbRounded() {
        DecimalFormat df = new DecimalFormat("0.00000");
        return df.format(this.spamProbability);
    }
    public String getActualClass() { return this.actualClass; }

    public void setFilename(String value) { this.filename = value; }
    public void setSpamProbability(double val) { this.spamProbability = val; }
    public void setActualClass(String value) { this.actualClass = value; }
}
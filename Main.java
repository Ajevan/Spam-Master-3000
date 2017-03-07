
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.image.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.*;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
/*

    Name: Ajevan Mahadaya and Saijeeshan Keetheswaran
    Date : 3/7/2017
    Name of Program: Spam Master 3000

    This is the Spam Master 3000, this program is designed to detect
    spam e-mail in an folder full of e-mails, it will print out a UI
    that will show the filename, the actual class, and the probability
    that the e-mail is spam

 */
public class Main extends Application {
    private Stage window;
    private BorderPane layout;
    private TableView<TestFile> table;
    private double scg=0;
    private double hcg=0;
    private double g=0;
    private double nt=0;
    private double nf=0;
    private double accuracy;
    private double precision;

    @Override
    /*
        The start method is a method that is used to set up the UI
        @param primaryStage - the main stage that is set up to place the
        table, the label, and does the accuracy and precision of the detection
     */
    public void start(Stage primaryStage) throws Exception {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File mainDirectory = directoryChooser.showDialog(primaryStage);

        primaryStage.setTitle("Spam Master 3000");

        /* create the table (for the center of the user interface) */
        table = new TableView<>();
        DataSource dataSource = new DataSource(mainDirectory);
        ObservableList<TestFile> mails=dataSource.getAllMail();
        table.setItems(mails);
        table.setEditable(true);

        /* create the table's columns */
        TableColumn<TestFile, String> fileColumn = null;
        fileColumn = new TableColumn<>("File");
        fileColumn.setMinWidth(250);
        fileColumn.setCellValueFactory(new PropertyValueFactory<>("filename"));

        TableColumn<TestFile, String> classColumn = null;
        classColumn = new TableColumn<>("Actual Class");
        classColumn.setMinWidth(150);
        classColumn.setCellValueFactory(new PropertyValueFactory<>("actualClass"));

        TableColumn<TestFile, Double> spamColumn = null;
        spamColumn = new TableColumn<>("Spam Probability");
        spamColumn.setMinWidth(250);
        spamColumn.setCellValueFactory(new PropertyValueFactory<>("spamProbRounded"));

        table.getColumns().add(fileColumn);
        table.getColumns().add(classColumn);
        table.getColumns().add(spamColumn);

        GridPane displayArea = new GridPane();
        displayArea.setPadding(new Insets(10, 10, 10, 10));
        displayArea.setVgap(10);
        displayArea.setHgap(10);

        mails.forEach((mail)->{
            if (mail.getSpamProbability() >=0.5 && mail.getSpamProbability() <=1.0&& mail.getActualClass().equalsIgnoreCase("spam"))
            {
                scg +=1;
            }
            if (mail.getSpamProbability() >=0 && mail.getSpamProbability() <0.5 && mail.getActualClass().equalsIgnoreCase("ham"))
            {
                hcg +=1;
            }
            if (mail.getSpamProbability()>0.5 && mail.getActualClass().equalsIgnoreCase("spam"))
            {
                nt +=1;
            }
            if(mail.getActualClass().equalsIgnoreCase("spam"))
            {
                nf +=1;
            }
            g +=1;
        });
        accuracy = (scg + hcg)/g;
        precision = nt / (nf);
        Label accuracyLabel = new Label("Accuracy:");
        displayArea.add(accuracyLabel, 0, 0);
        TextField accuracyField = new TextField(accuracy+"");
        displayArea.add(accuracyField, 1, 0);
        accuracyField.setEditable(false);

        Label precisionLabel = new Label("Precision:");
        displayArea.add(precisionLabel, 0, 1);
        TextField precisionField = new TextField(String.valueOf(precision));
        displayArea.add(precisionField, 1, 1);
        precisionField.setEditable(false);

        layout = new BorderPane();
        layout.setCenter(table);
        layout.setBottom(displayArea);
        Scene scene = new Scene(layout, 650, 400);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    /*
        This start method is used to launch the program with the UI
        @param args - the arguements required to run the program
     */
    public static void main(String[] args) {
        launch(args);
    }
}
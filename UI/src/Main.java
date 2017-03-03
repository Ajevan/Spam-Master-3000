/**
 * Created by ajevan on 25/02/17.
 */
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

public class Main extends Application {
    private Stage window;
    private BorderPane layout;
    private TableView<TestFile> table;

    @Override
    public void start(Stage primaryStage) throws Exception {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File mainDirectory = directoryChooser.showDialog(primaryStage);

        primaryStage.setTitle("Spam Master 3000");

        /* create the table (for the center of the user interface) */
        table = new TableView<>();
        DataSource dataSource = new DataSource(mainDirectory);
        table.setItems(dataSource.getAllMail());
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
        spamColumn.setCellValueFactory(new PropertyValueFactory<>("spamProbability"));

        table.getColumns().add(fileColumn);
        table.getColumns().add(classColumn);
        table.getColumns().add(spamColumn);

        GridPane displayArea = new GridPane();
        displayArea.setPadding(new Insets(10, 10, 10, 10));
        displayArea.setVgap(10);
        displayArea.setHgap(10);

        Label accuracyLabel = new Label("Accuracy:");
        displayArea.add(accuracyLabel, 0, 0);
        TextField accuracyField = new TextField();
        displayArea.add(accuracyField, 1, 0);
        accuracyField.setEditable(false);

        Label precisionLabel = new Label("Precision:");
        displayArea.add(precisionLabel, 0, 1);
        TextField precisionField = new TextField();
        displayArea.add(precisionField, 1, 1);
        precisionField.setEditable(false);

        layout = new BorderPane();
        layout.setCenter(table);
        layout.setBottom(displayArea);
        Scene scene = new Scene(layout, 650, 400);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
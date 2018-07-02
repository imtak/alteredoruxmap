package tak.oruxmap.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.xml.sax.SAXException;
import tak.oruxmap.OruxDataLoader;
import tak.oruxmap.domain.Tile;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class MainPane extends BorderPane{

    private final Stage primaryStage;
    private final OruxDataLoader dataLoader = new OruxDataLoader();


    public MainPane(Stage primaryStage) {
        //BorderPane pane = new BorderPane();
        this.primaryStage = primaryStage;
        this.getStylesheets().add("style.css");
        HBox hbox = addHBox();
        setTop(hbox);

        File descriptorFile = new File("D:\\home\\peter\\dev\\tak\\src\\test\\resources\\tile-metadata.xml");
        File dbFile = new File("D:\\home\\peter\\dev\\tak\\src\\test\\resources\\tiles.db");
        TilePane tilePane = null;

//        Tile[][] tiles = new Tile[8][8];
//        for(int i = 0; i < 8; i++){
//            for(int j = 0; j < 8; j++){
//                tiles[i][j] = new Tile(i, j, 16);
//            }
//        }
//
//        tilePane = new TilePane(tiles);

        try {
            Tile[][] tiles = dataLoader.load(descriptorFile, dbFile);
            tilePane = new TilePane(tiles);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }


        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(tilePane);
        setCenter(scrollPane);
    }

    private HBox addHBox() {

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);   // Gap between nodes
        hbox.setStyle("-fx-background-color: #336699;");

        final Button btnOpenDirectoryChooser = new Button();
        btnOpenDirectoryChooser.setPrefSize(180, 20);

        final CheckBox checkBoxInclSubFolders = new CheckBox("Incl. sub folders");
        checkBoxInclSubFolders.setPrefSize(180, 20);

        final Button btnOpen = new Button("Open");
        btnOpen.setPrefSize(60, 20);

        btnOpen.setOnAction(openHandle);

        final Label labelSelectedDirectory = new Label();

        buildDirectoryChooser(btnOpenDirectoryChooser, labelSelectedDirectory);

        hbox.getChildren().addAll(btnOpenDirectoryChooser, checkBoxInclSubFolders, btnOpen, labelSelectedDirectory);

        return hbox;
    }

    private void buildDirectoryChooser(final Button btnOpenDirectoryChooser, final Label labelSelectedDirectory){
        btnOpenDirectoryChooser.setText("Choose map directory");
        btnOpenDirectoryChooser.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                DirectoryChooser directoryChooser = new DirectoryChooser();
                File selectedDirectory =
                        directoryChooser.showDialog(primaryStage);

                if(selectedDirectory == null){
                    labelSelectedDirectory.setText("No Directory selected");
                }else{
                    labelSelectedDirectory.setText(selectedDirectory.getAbsolutePath());
                }
            }
        });
    }

    private final EventHandler<ActionEvent> openHandle = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
            System.out.println("Open called");
        }
    };
}

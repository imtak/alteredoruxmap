package tak.oruxmap.ui;

import javafx.geometry.Insets;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tak.oruxmap.OruxDataLoader;
import tak.oruxmap.domain.Tile;

import java.io.ByteArrayInputStream;

public class TileButton extends ToggleButton {

    private static final Logger log = LoggerFactory.getLogger(OruxDataLoader.class);

    final private TilePane tilePane;
    final private Tile tile;


    public TileButton(TilePane tilePane,  Tile tile){
        if(tile.imageBytes != null){
            Image image = new Image(new ByteArrayInputStream(tile.imageBytes));
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(64);
            imageView.setFitHeight(64);
            this.setGraphic(imageView);
            setPadding(new Insets(2));
        }
        this.tile = tile;
        this.tilePane = tilePane;
        getStyleClass().add("tileButton");

        this.setOnAction(e -> {
                if(isSelected()){
                    tilePane.tileSelected(this);
                }else{
                    tilePane.tileUnselected(this);
                }
        });
    }

}

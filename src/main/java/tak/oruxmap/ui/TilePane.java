package tak.oruxmap.ui;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tak.oruxmap.OruxDataLoader;
import tak.oruxmap.domain.Tile;

public class TilePane extends GridPane {

    private static final Logger log = LoggerFactory.getLogger(OruxDataLoader.class);
    private int selectedTilesCount = 0;

    public TilePane(Tile[][] tiles){
        for(int i = 0; i < tiles.length; i++){
            for(int j = 0; j < tiles[0].length; j++){
                add(new TileButton(this, tiles[i][j]), i, j);
            }
        }

        setPadding(new Insets(1));
    }

    void tileSelected(TileButton tileButton){
        selectedTilesCount++;
        if(2 == selectedTilesCount)
            disableUnselectedTiles();
    }

    void tileUnselected(TileButton tileButton){
        selectedTilesCount--;
        if(2 > selectedTilesCount)
            enableTiles();
    }

    private void disableUnselectedTiles(){
        log.debug("disableUnselectedTiles is called");
        this.getChildren().forEach( node -> {
            TileButton b = ((TileButton)node);
            if(!b.isSelected()){
                b.setDisable(true);
            }
        });
    }

    private void enableTiles(){
        log.debug("disableUnselectedTiles is called");
        this.getChildren().forEach( node -> {
            TileButton b = ((TileButton)node);
            if(b.isDisable()){
                b.setDisable(false);
            }
        });
    }





}

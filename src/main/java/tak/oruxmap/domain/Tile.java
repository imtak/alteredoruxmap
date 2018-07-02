package tak.oruxmap.domain;

public class Tile {

    public TileDescriptor tileDescriptor;
    public int x;
    public int y;
    public int z;
    public byte[] imageBytes;

    public Tile(){}

    public Tile(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}

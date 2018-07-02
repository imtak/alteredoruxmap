package tak.oruxmap.domain;

public class TileDescriptor {

    public String mapName;
    public String fileName;

    public Integer mapChunksXMax;
    public Integer mapChunksYMax;

    public Integer mapChunksImageHeight;
    public Integer mapChunksImageWidth;

    public Integer mapDimensionsHeight;
    public Integer mapDimensionsWidth;

    public Double mapBoundsMinLat;
    public Double mapBoundsMaxLat;
    public Double mapBoundsMinLon;
    public Double mapBoundsMaxLon;

    public Double calibrationPointTopLeftLat;
    public Double calibrationPointTopLeftLon;

    public Double calibrationPointBottomRightLat;
    public Double calibrationPointBottomRightLon;

    public Double calibrationPointTopRightLat;
    public Double calibrationPointTopRightLon;

    public Double calibrationPointBottomLeftLat;
    public Double calibrationPointBottomLeftLon;

    @Override
    public String toString() {
        return "TileDescriptor{" +
                "mapName='" + mapName + '\'' +
                ", fileName='" + fileName + '\'' +
                ", mapChunksXMax=" + mapChunksXMax +
                ", mapChunksYMax=" + mapChunksYMax +
                ", mapChunksImageHeight=" + mapChunksImageHeight +
                ", mapChunksImageWidth=" + mapChunksImageWidth +
                ", mapDimensionsHeight=" + mapDimensionsHeight +
                ", mapDimensionsWidth=" + mapDimensionsWidth +
                ", mapBoundsMinLat=" + mapBoundsMinLat +
                ", mapBoundsMaxLat=" + mapBoundsMaxLat +
                ", mapBoundsMinLon=" + mapBoundsMinLon +
                ", mapBoundsMaxLon=" + mapBoundsMaxLon +
                ", calibrationPointTopLeftLat=" + calibrationPointTopLeftLat +
                ", calibrationPointTopLeftLon=" + calibrationPointTopLeftLon +
                ", calibrationPointBottomRightLat=" + calibrationPointBottomRightLat +
                ", calibrationPointBottomRightLon=" + calibrationPointBottomRightLon +
                ", calibrationPointTopRightLat=" + calibrationPointTopRightLat +
                ", calibrationPointTopRightLon=" + calibrationPointTopRightLon +
                ", calibrationPointBottomLeftLat=" + calibrationPointBottomLeftLat +
                ", calibrationPointBottomLeftLon=" + calibrationPointBottomLeftLon +
                '}';
    }
}

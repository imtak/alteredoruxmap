package tak.oruxmap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import sun.util.locale.provider.LocaleServiceProviderPool;
import tak.oruxmap.db.Sqlite;
import tak.oruxmap.domain.Tile;
import tak.oruxmap.domain.TileDescriptor;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OruxDataLoader {

    private static final Logger log = LoggerFactory.getLogger(OruxDataLoader.class);

    public Tile[][] load(File descriptorFile, File dbFile) throws ParserConfigurationException, IOException, SAXException {
        TileDescriptor descriptor = parseDescriptor(descriptorFile);
        return loadTiles(descriptor, dbFile);
    }

    protected TileDescriptor parseDescriptor(File descriptorFile) throws ParserConfigurationException, IOException, SAXException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        TileDescriptor descriptor = new TileDescriptor();
        saxParser.parse(descriptorFile, new DescriptorHandler(descriptor));
        log.debug(descriptor.toString());
        return descriptor;
    }

    private static class DescriptorHandler extends DefaultHandler{
        private int oruxTrackerLevel = 0;
        private TileDescriptor descriptor;

        private String currentElementName = "";

        public DescriptorHandler(TileDescriptor descriptor) {
            this.descriptor = descriptor;
        }

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            currentElementName = qName;

            switch(qName){
                case "OruxTracker":
                    oruxTrackerLevel++;
                    break;

                case "MapChunks":
                    descriptor.mapChunksXMax = Integer.valueOf(attributes.getValue("xMax"));
                    descriptor.mapChunksYMax = Integer.valueOf(attributes.getValue("yMax"));

                    descriptor.mapChunksImageHeight = Integer.valueOf(attributes.getValue("img_height"));
                    descriptor.mapChunksImageWidth = Integer.valueOf(attributes.getValue("img_width"));
                    break;

                case "MapDimensions":
                    descriptor.mapDimensionsHeight = Integer.valueOf(attributes.getValue("height"));
                    descriptor.mapDimensionsWidth = Integer.valueOf(attributes.getValue("width"));
                    break;

                case "MapBounds":
                    descriptor.mapBoundsMinLat = Double.valueOf(attributes.getValue("minLat"));
                    descriptor.mapBoundsMaxLat = Double.valueOf(attributes.getValue("maxLat"));
                    descriptor.mapBoundsMinLon = Double.valueOf(attributes.getValue("minLon"));
                    descriptor.mapBoundsMaxLon = Double.valueOf(attributes.getValue("maxLon"));
                    break;

                case "CalibrationPoint":
                    String corner = attributes.getValue("corner");

                    switch(corner){
                        case "TL":
                            descriptor.calibrationPointTopLeftLat = Double.valueOf(attributes.getValue("lat"));
                            descriptor.calibrationPointTopLeftLon = Double.valueOf(attributes.getValue("lon"));
                            break;

                        case "BR":
                            descriptor.calibrationPointBottomRightLat = Double.valueOf(attributes.getValue("lat"));
                            descriptor.calibrationPointBottomRightLon = Double.valueOf(attributes.getValue("lon"));
                            break;

                        case "TR":
                            descriptor.calibrationPointTopRightLat = Double.valueOf(attributes.getValue("lat"));
                            descriptor.calibrationPointTopRightLon = Double.valueOf(attributes.getValue("lon"));
                            break;

                        case "BL":
                            descriptor.calibrationPointBottomLeftLat = Double.valueOf(attributes.getValue("lat"));
                            descriptor.calibrationPointBottomLeftLon = Double.valueOf(attributes.getValue("lon"));
                            break;
                    }
                    break;

                default:
                    break;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            currentElementName = "";
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            switch(currentElementName){
                case "MapName":
                    if(oruxTrackerLevel == 1)
                        descriptor.mapName = new String(ch, start, length);
                    break;

                default:
                    break;
            }
        }
    }

    protected Tile[][] loadTiles(TileDescriptor tileDescriptor, File dbFile){

        Tile[][] tiles = new Tile[tileDescriptor.mapChunksXMax][tileDescriptor.mapChunksYMax];

        Sqlite conn = new Sqlite() {
            @Override
            public void perform(Connection conn) {
                String selectSQL = "SELECT x, y, z, image FROM tiles order by x asc, y asc, z asc";
                ResultSet rs;
                PreparedStatement pstmt;

                try {
                    pstmt = conn.prepareStatement(selectSQL);
//                    pstmt.setInt(1, 0);
//                    pstmt.setInt(2, 0);
//                    pstmt.setInt(3, 16);
                    rs = pstmt.executeQuery();
                    int i = 0;
                    while (rs.next()) {
                        Tile tile = new Tile();
                        tile.tileDescriptor = tileDescriptor;
                        tile.x = rs.getInt("x");
                        tile.y = rs.getInt("y");
                        tile.z = rs.getInt("z");

                        InputStream inputStream = rs.getBinaryStream("image");
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int read = 0;
                        while ((read = inputStream.read(buffer, 0, buffer.length)) != -1) {
                            baos.write(buffer, 0, read);
                        }
                        baos.flush();

                        tile.imageBytes = baos.toByteArray();

                        try (FileOutputStream fos = new FileOutputStream(
                                "D:/tmp/tile_" + tile.x + "_" + tile.y + ".jpg")) {
                            fos.write(tile.imageBytes);
                            //fos.close(); There is no more need for this line since you had created the instance of "fos" inside the try. And this will automatically close the OutputStream
                        }

                        tiles[tile.x][tile.y] = tile;

                        baos.close();
                        inputStream.close();
                        i++;
                    }
                    System.out.println(String.format("Successfully loaded %s tiles from %s", dbFile, Integer.valueOf(i).toString()));
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        };

        conn.connect(dbFile.getAbsolutePath());
        return tiles;
    }




}

package tak.oruxmap;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;
import tak.oruxmap.domain.TileDescriptor;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class OruxDataLoaderTest {


    private static final OruxDataLoader dataLoader = new OruxDataLoader();
    private static String tileMetadataFile = "tile-metadata.xml";
    private static TileDescriptor tileDescriptor;

    @BeforeClass
    public static void setUp(){
        File file = getFile(tileMetadataFile);
        try {
            tileDescriptor = dataLoader.parseDescriptor(file);
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void descriptorParsingTest() {
        Assert.assertEquals("LALLEY TOP25 vue au 25000eme", tileDescriptor.mapName);
        Assert.assertTrue(8 == tileDescriptor.mapChunksXMax);
        Assert.assertTrue(8 == tileDescriptor.mapChunksYMax);

        Assert.assertTrue(512 == tileDescriptor.mapChunksImageHeight);
        Assert.assertTrue(512 == tileDescriptor.mapChunksImageWidth);

        Assert.assertTrue(4000 == tileDescriptor.mapDimensionsHeight);
        Assert.assertTrue(4000 == tileDescriptor.mapDimensionsWidth);

        double delta = 0.00000000000001;

        Assert.assertEquals(44.67823609999999, tileDescriptor.mapBoundsMinLat, delta);
        Assert.assertEquals(44.77191957, tileDescriptor.mapBoundsMaxLat, delta);
        Assert.assertEquals(5.61513163, tileDescriptor.mapBoundsMinLon, delta);
        Assert.assertEquals(5.74654196, tileDescriptor.mapBoundsMaxLon, delta);

        Assert.assertEquals(5.62039593, tileDescriptor.calibrationPointTopLeftLon, delta);
        Assert.assertEquals(44.77191957, tileDescriptor.calibrationPointTopLeftLat, delta);
        Assert.assertEquals(5.741075890000001, tileDescriptor.calibrationPointBottomRightLon, delta);
        Assert.assertEquals(44.67823609999999, tileDescriptor.calibrationPointBottomRightLat, delta);
        Assert.assertEquals(5.74654196, tileDescriptor.calibrationPointTopRightLon, delta);
        Assert.assertEquals(44.768091530000014, tileDescriptor.calibrationPointTopRightLat, delta);
        Assert.assertEquals(5.61513163, tileDescriptor.calibrationPointBottomLeftLon, delta);
        Assert.assertEquals(44.68205785000001, tileDescriptor.calibrationPointBottomLeftLat, delta);

    }



    private static File getFile(String relativePath){
        ClassLoader classLoader = OruxDataLoaderTest.class.getClassLoader();
        File file = new File(classLoader.getResource(relativePath).getFile());
        return file;
    }

}

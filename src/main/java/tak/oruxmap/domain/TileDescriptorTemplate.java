package tak.oruxmap.domain;

import java.nio.file.Path;

public class TileDescriptorTemplate {

    public void writeTileDescriptor(Path path, TileDescriptor desc){
        String tileXml = template
                .replace("${mapName}", desc.mapName)
                .replace("${fileName}", desc.fileName)

                .replace("${mapChunksXMax}", desc.mapChunksXMax.toString())
                .replace("${mapChunksYMax}", desc.mapChunksYMax.toString())

                .replace("${imgHeight}", desc.mapChunksImageHeight.toString())
                .replace("${imgWidth}", desc.mapChunksImageWidth.toString())

                .replace("${mapDimensionsHeight}", desc.mapDimensionsHeight.toString())
                .replace("${mapDimensionsWidth}", desc.mapDimensionsHeight.toString())

                .replace("${mapBoundsMinLat}", desc.mapBoundsMinLat.toString())
                .replace("${mapBoundsMaxLat}", desc.mapBoundsMaxLat.toString())
                .replace("${mapBoundsMinLon}", desc.mapBoundsMinLon.toString())
                .replace("${mapBoundsMaxLon}", desc.mapBoundsMaxLon.toString())

                .replace("${tlLon}", desc.calibrationPointTopLeftLon.toString())
                .replace("${tlLat}", desc.calibrationPointTopLeftLat.toString())
                .replace("${brLon}", desc.calibrationPointBottomRightLon.toString())
                .replace("${brLat}", desc.calibrationPointBottomRightLat.toString())
                .replace("${trLon}", desc.calibrationPointTopRightLon.toString())
                .replace("${trLat}", desc.calibrationPointTopRightLat.toString())
                .replace("${blLon}", desc.calibrationPointBottomLeftLon.toString())
                .replace("${blLat}", desc.calibrationPointBottomLeftLat.toString());
    }

    String template = "" +
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<OruxTracker xmlns:orux=\"http://oruxtracker.com/app/res/calibration\"\n" +
            " versionCode=\"3.0\">\n" +
            "<MapCalibration layers=\"true\" layerLevel=\"0\">\n" +
            "<MapName><![CDATA[${mapName}]]></MapName>\n" +
            "<OruxTracker versionCode=\"2.1\">\n" +
            "<MapCalibration layers=\"false\" layerLevel=\"16\">\n" +
            "<MapName><![CDATA[${mapName} 16]]></MapName>\n" +
            "<MapChunks xMax=\"${mapChunksXMax}\" yMax=\"${mapChunksYMax}\" datum=\"WGS 1984:Global Definition@WGS 1984:Global Definition\" projection=\"Latitude/Longitude,\" img_height=\"${imgHeight}\" img_width=\"${imgWidth}\" file_name=\"${fileName} 16\" />\n" +
            "<MapDimensions height=\"${mapDimensionsHeight}\" width=\"${mapDimensionsWidth}\" />\n" +
            "<MapBounds minLat=\"${mapBoundsMinLat}\" maxLat=\"${mapBoundsMaxLat}\" minLon=\"${mapBoundsMinLon}\" maxLon=\"${mapBoundsMaxLon}\" />\n" +
            "<CalibrationPoints>\n" +
            "<CalibrationPoint corner=\"TL\" lon=\"${tlLon}\" lat=\"${tlLat}\" />\n" +
            "<CalibrationPoint corner=\"BR\" lon=\"${brLon}\" lat=\"${brLat}\" />\n" +
            "<CalibrationPoint corner=\"TR\" lon=\"${trLon}\" lat=\"${trLat}\" />\n" +
            "<CalibrationPoint corner=\"BL\" lon=\"${blLon}\" lat=\"${blLst}\" />\n" +
            "</CalibrationPoints>\n" +
            "</MapCalibration>\n" +
            "</OruxTracker>\n" +
            "</MapCalibration>\n" +
            "</OruxTracker>";



}

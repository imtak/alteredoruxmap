package tak.oruxmap.db;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConnectionTest {

    private String testfile = "D:/tak/src/test/resources/tiles.db";

    private String outfile = "D:/tmp/tileimg.jpg";

    @Test
    public void extractTileTest(){
        Sqlite conn = new Sqlite() {
            @Override
            public void perform(Connection conn) {
                System.out.println("perform is called");
                String selectSQL = "SELECT image FROM tiles WHERE x=? and y=? and z=?";
                ResultSet rs = null;
                FileOutputStream fos = null;
                PreparedStatement pstmt = null;

                try {
                    pstmt = conn.prepareStatement(selectSQL);
                    pstmt.setInt(1, 0);
                    pstmt.setInt(2, 0);
                    pstmt.setInt(3, 16);
                    rs = pstmt.executeQuery();

                    // write binary stream into file
                    File file = new File(outfile);
                    fos = new FileOutputStream(file);

                    System.out.println("Writing BLOB to file " + file.getAbsolutePath());
                    while (rs.next()) {
                        InputStream input = rs.getBinaryStream("image");
                        byte[] buffer = new byte[1024];
                        while (input.read(buffer) > 0) {
                            fos.write(buffer);
                        }
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        };

        conn.connect(testfile);

    }
}

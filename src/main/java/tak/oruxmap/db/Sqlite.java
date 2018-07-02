package tak.oruxmap.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class Sqlite {

    /**
     * Connect to a sample database
     */
    public void connect(String pathToDbFile) {
        Connection conn = null;
        try {
            String driver = "org.sqlite.JDBC";
            Class.forName(driver);
            //DriverManager.registerDriver(org.sqlite.core.);
            // db parameters
            String url = "jdbc:sqlite:"+pathToDbFile;
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");

            perform(conn);


        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public abstract void perform(Connection conn);

}

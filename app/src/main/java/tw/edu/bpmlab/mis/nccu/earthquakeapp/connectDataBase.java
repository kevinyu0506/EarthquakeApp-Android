package tw.edu.bpmlab.mis.nccu.earthquakeapp;

/**
 * Created by willy on 2017/3/30.
 */
import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
public class connectDataBase {
    private static final String IP ="maotry.database.windows.net:1433";
    private static final String DB = "maodatabase";
    private static final String USER = "willy";
    private static final String PASS = "Password123";

    public static Connection CONN(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            String ConnURL = "jdbc:jtds:sqlserver://" + IP + ";"
                    + "databaseName=" + DB + ";user=" + USER + ";password="
                    + PASS + ";";
            conn = DriverManager.getConnection(ConnURL);
        }catch (Exception e){
            conn = null;
            e.printStackTrace();
        }
        return conn;
    }
}

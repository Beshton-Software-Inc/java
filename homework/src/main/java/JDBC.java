import java.sql.*;

public class JDBC {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/schema1";

    static final String USER = "root";
    static final String PASS = "123456";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try{

            Class.forName(JDBC_DRIVER);


            conn = DriverManager.getConnection(DB_URL,USER,PASS);


            stmt = conn.createStatement();
            String sql;
            sql = "SELECT id, name, url FROM websites";
            ResultSet rs = stmt.executeQuery(sql);


            while(rs.next()){

                int id  = rs.getInt("id");
                String name = rs.getString("name");


                System.out.print("ID: " + id);
                System.out.print("name: " + name);
                System.out.print("\n");
            }

            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){

            se.printStackTrace();
        }catch(Exception e){

            e.printStackTrace();
        }finally{

            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){

            }
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }

}

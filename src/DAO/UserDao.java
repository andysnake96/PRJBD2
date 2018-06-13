package DAO;

import ENTITY.User;
import feauture1.Bean.UserBean;

import java.sql.*;
import java.sql.Connection;

public class UserDao {

    private final static String finduser = "finduser";
    private final static String adduser = "adduser";


    public static User findUser(String username, String password) {

        PreparedStatement stmt = null;
        DAO.Connection connection = DAO.Connection.getIstance();
        Connection conn = connection.getConn();
        User user = null;
        try {




            String sql = connection.getSqlString(finduser);
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            System.out.println(stmt);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) { // rs is empty

                return null;
            }

            String name = rs.getString("name");  // non se perhcè mastrofini riprendeva username, password se già lo sappiamo
            String uname = rs.getString("username");
            String pword = rs.getString("password");
            String type = rs.getString("type");
            String email = rs.getString("email");
            String sname = rs.getString("surname");


            user = new User(name, sname, uname, pword, email, type);

            connection.closeConn(conn);
            stmt.close();
        } catch (SQLException se2) {
            se2.printStackTrace();
        }
        return user;

    }

    public static String addUser(UserBean user) {
        Statement stmt = null;
        DAO.Connection connection = DAO.Connection.getIstance();
        Connection conn = connection.getConn();
        String msx = null;
        try {
            String sql = connection.getSqlString(adduser);
            sql = sql + "'"+ user.getName() +"','" + user.getSurname() +"','" + user.getUsername() + "','"
                    + user.getPassword() +"','" + user.getEmail() +"','" + user.getType() +"')";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);

            connection.closeConn(conn);
            stmt.close();
        } catch (SQLException se2) {
            msx = se2.getMessage();
            return msx;
        }
        msx = "insert valid";
        return msx;

        }

    }


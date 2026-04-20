package dao;

import java.sql.*;

public class UserDAO {

    public void addUser(String name) {
        String sql = "insert into users(name) values (?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.executeUpdate();

            System.out.println("User added!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
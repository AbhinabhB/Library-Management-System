package dao;

import java.sql.*;

public class UserDAO {

    public int addUser(String name) {
        String sql = "insert into users(name) values (?)";
        int userId = -1;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, name);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                userId = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return userId;
    }
}
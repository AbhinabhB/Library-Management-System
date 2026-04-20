package dao;

import model.Book;
import java.sql.*;

public class BookDAO {

    public void addBook(Book book) {
        String sql = "insert into books(title, author, available) values (?, ?, true)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.executeUpdate();

            System.out.println("Book added successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewBooks() {
        String sql = "select * from books";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                                rs.getString("title") + " | " +
                                rs.getString("author") + " | " +
                                rs.getBoolean("available")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateAvailability(int bookId, boolean status) {
        String sql = "update books set available=? where id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setBoolean(1, status);
            ps.setInt(2, bookId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
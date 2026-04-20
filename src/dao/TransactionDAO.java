package dao;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class TransactionDAO {

    public void issueBook(int bookId, int userId) {
        String sql = "insert into transactions(book_id, user_id, issue_date, fine) values (?, ?, ?, 0)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, bookId);
            ps.setInt(2, userId);
            ps.setDate(3, Date.valueOf(LocalDate.now()));

            ps.executeUpdate();

            new BookDAO().updateAvailability(bookId, false);

            System.out.println("Book issued!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void returnBook(int transactionId) {
        String fetch = "select issue_date, book_id from transactions where id=?";
        String update = "update transactions set return_date=?, fine=? where id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps1 = con.prepareStatement(fetch)) {

            ps1.setInt(1, transactionId);
            ResultSet rs = ps1.executeQuery();

            if (rs.next()) {
                LocalDate issueDate = rs.getDate("issue_date").toLocalDate();
                LocalDate returnDate = LocalDate.now();

                long days = ChronoUnit.DAYS.between(issueDate, returnDate);

                double fine = days > 7 ? (days - 7) * 10 : 0;

                PreparedStatement ps2 = con.prepareStatement(update);
                ps2.setDate(1, Date.valueOf(returnDate));
                ps2.setDouble(2, fine);
                ps2.setInt(3, transactionId);
                ps2.executeUpdate();

                int bookId = rs.getInt("book_id");
                new BookDAO().updateAvailability(bookId, true);

                System.out.println("Returned! Fine: " + fine);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
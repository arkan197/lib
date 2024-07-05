package dao;

import dto.BorrowedBookDTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BorrowedBookDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/LibUmm";
    private String jdbcUsername = "root";
    private String jdbcPassword = "root";

    private static final String SELECT_ALL_BORROWED_BOOKS = "SELECT * FROM borrowed_books";

    // Method to get a database connection
    protected Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // Method to get all borrowed books
    public List<BorrowedBookDTO> selectAllBorrowedBooks() {
        List<BorrowedBookDTO> borrowedBooks = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BORROWED_BOOKS);) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int studentId = rs.getInt("user_id");
                int bookId = rs.getInt("book_id");
                java.sql.Date borrowDate = rs.getDate("borrow_date");
                java.sql.Date returnDate = rs.getDate("return_date");

                BorrowedBookDTO borrowedBook = new BorrowedBookDTO();
                borrowedBook.setId(id);
                borrowedBook.setStudentId(studentId);
                borrowedBook.setBookId(bookId);
                borrowedBook.setBorrowDate(new java.util.Date(borrowDate.getTime()));
                if (returnDate != null) {
                    borrowedBook.setReturnDate(new java.util.Date(returnDate.getTime()));
                }

                borrowedBooks.add(borrowedBook);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return borrowedBooks;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}

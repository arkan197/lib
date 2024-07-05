package application;

import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import dto.BookDTO;
import dto.BorrowedBookDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class CardController {

    @FXML
    private Label bookCategory_card;

    @FXML
    private ImageView bookCover_card;

    @FXML
    private Label bookTitle_card;

    @FXML
    private Label bookWriter_card;

    @FXML
    private Label borrowed_date;

    @FXML
    private HBox box;

    @FXML
    private Label loanStatus_card;

    @FXML
    private Label return_date;
    
    private String[] colors = {"#ff3333", "#3399ff", "#33cc33"}; //red, blue, green
    
    public void setData(BookDTO book, BorrowedBookDTO borrowedBook) {
    	Image image = new Image(getClass().getResourceAsStream(book.getImagePath()));
    	String color = "";
    	bookCover_card.setImage(image);
    	
    	bookTitle_card.setText(book.getTitle());
    	bookWriter_card.setText(book.getWriter());
    	  // Format borrowed date
        borrowed_date.setText(dateFormatted(borrowedBook.getBorrowDate()));
        
        // Calculate return deadline
        LocalDate borrowDate = convertToLocalDate(borrowedBook.getBorrowDate());
        LocalDate returnDeadline = borrowDate.plusDays(borrowedBook.getBorrowDuration());
        return_date.setText(dateFormatted(returnDeadline));
        if (borrowedBook.getReturnDate() != null) {
            loanStatus_card.setText("Returned");
            color = colors[2];
        } else {
            LocalDate currentDate = LocalDate.now();
            if (currentDate.isAfter(returnDeadline)) {
                loanStatus_card.setText("Passed the return deadline");
                color = colors[0];
            } else {
                loanStatus_card.setText("Ongoing");
                color = colors[1];
            }
        }
        
        box.setStyle("-fx-bakcground-color: "+ color + ";" + 
        "-fx-background-radius: 15;" + 
        "-fx effect: dropShadown(three-pass-box, rgba(0,0,0,0), 10, 0, 0, 10);");
        
    }
    
    private String dateFormatted(Date date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = convertToLocalDate(date);
        return formatter.format(localDate);
    }

    private String dateFormatted(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return formatter.format(date);
    }
    
    private LocalDate convertToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}

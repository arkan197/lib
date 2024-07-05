package application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dao.BookDAO;
import dao.BorrowedBookDAO;
import dto.BookDTO;
import dto.BorrowedBookDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

public class StudentController implements Initializable {

    @FXML
    private HBox cardLayout;
    private BookDAO bookDAO = new BookDAO();
	private BookDTO bookDTO = new BookDTO();
	private BorrowedBookDAO borrowedBookDAO = new BorrowedBookDAO();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		List<BorrowedBookDTO> borrowedBookList = borrowedBookDAO.selectAllBorrowedBooks();
		try {
			for (BorrowedBookDTO borrowedBookDTO: borrowedBookList) {
				bookDTO = bookDAO.selectBookById(bookDTO.getBookId());
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(getClass().getResource("/fxml/borrowedBooks_card.fxml"));
				HBox cardBox = fxmlLoader.load();
				CardController cardController = fxmlLoader.getController();
				cardController.setData(bookDTO, borrowedBookDTO);
				cardLayout.getChildren().add(cardBox);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}

package application;

import java.net.URL;
import java.util.ResourceBundle;

import connexion.Persistance;
import dao.factory.DAOFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class ClientController  implements Initializable {

	
	
	DAOFactory dao = DAOFactory.getDAOFactory(Persistance.MYSQL);
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

	@FXML
	public void click_btn() {
		
	}
	
	@FXML
	public void click_btn_modifier() {
		
	}

	@FXML
	public void click_btn_supprimer() {
		
	}
}

package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import connexion.Persistance;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuItem;

public class AccueilController implements Initializable {

	@FXML private MenuItem mnu_categorie;
	@FXML private MenuItem mnu_client;
	@FXML private MenuItem mnu_commande;
	@FXML private MenuItem mnu_produit;
	@FXML private MenuItem mnu_quitter;
	@FXML private MenuItem mnu_info;
	@FXML private ChoiceBox<Persistance> cbxPersistance;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			ArrayList<Persistance> list=new ArrayList<>();
			for(Persistance p:Persistance.values() ){
				list.add(p);
			}
			this.cbxPersistance.setItems(FXCollections.observableArrayList(list));
	    	
		} catch (Exception e) {
		}
	}
	
	@FXML
	public void click_btn() {
		Persistance p=cbxPersistance.getSelectionModel().getSelectedItem(); 
	}
	
	@FXML
	public void click_mnu_categorie() {
		
	}
	
	@FXML
	public void click_mnu_client() {
		
	}
	
	@FXML
	public void click_mnu_commande() {
		
	}
}

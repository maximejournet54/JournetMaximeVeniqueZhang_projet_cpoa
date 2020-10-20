package application;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import connexion.Persistance;
import dao.factory.DAOFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import pojo.Categorie;
import pojo.Client;
import pojo.Commande;
import pojo.Produit;

public class CommandeController  implements Initializable {
	@FXML private DatePicker dtpDate;
	@FXML private Label lblAffichage;
	@FXML private ChoiceBox<Client> cbxClient;
	
	DAOFactory dao = DAOFactory.getDAOFactory(Persistance.MYSQL);
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
	    	this.lblAffichage.setText("");
	    	this.cbxClient.setItems(FXCollections.observableArrayList(dao.getClientDAO().findAll()));
	    	

		} catch (Exception e) {
		}
		
	}
	
	@FXML
	public void click_btn() {
		//fuseau horaire par defaut
		ZoneId defaultZoneId = ZoneId.systemDefault();
		LocalDate d= dtpDate.getValue();
		String erreur="";
		Client selectItem = cbxClient.getSelectionModel().getSelectedItem(); 
		
		//gestion des erreurs
		if (selectItem == null) {
			erreur = erreur + "\nVeuillez selectionner un client";
		}
			
		else {
			//on creer une instance de produit 
			Commande c1 = new Commande(1, d, selectItem.getId(),);
			
			try {
				DAOFactory.getDAOFactory(Persistance.MYSQL).getCommandeDAO().create(c1);
				DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getCommandeDAO().create(c1); 
				
				
			} catch (Exception e) {
				e.printStackTrace();
			} 
			initialize(null, null);
			this.lblAffichage.setTextFill(Color.web("#000000"));
				this.lblAffichage.setText(c1.toString());
		}
	}
	
	@FXML
	public void click_btn_modifier() {
		
	}

	@FXML
	public void click_btn_supprimer() {
		
	}

}
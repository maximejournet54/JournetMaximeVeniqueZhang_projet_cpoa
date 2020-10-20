package application;

import java.net.URL;
import java.util.ResourceBundle;

import connexion.Persistance;
import dao.factory.DAOFactory;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import pojo.Categorie;

public class CategorieController  implements Initializable {
	@FXML private Label lblAffichage;
	@FXML private TextField editTitre;
	@FXML private TextField editVisuel;

	
	DAOFactory dao = DAOFactory.getDAOFactory(Persistance.MYSQL);
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
	    	this.lblAffichage.setText("");
	    	this.editTitre.setText("");
	    	this.editVisuel.setText("");

		} catch (Exception e) {
		}
		
	}
	
	@FXML
	public void click_btn() {
		String titre = editTitre.getText().trim();
		String visuel = editVisuel.getText().trim(); 
		String erreur="";
		
		if (titre.isEmpty()) {
			erreur = erreur + "\nLe titre est vide";
		}
		if (visuel.isEmpty()) {
			erreur = erreur + "\nLe visuel est vide";
		}
		if (erreur != "") {
			this.lblAffichage.setTextFill(Color.web("#bb0b0b"));
			this.lblAffichage.setText(erreur);
		}
		else {
			//on cree une instance de categorie
			Categorie c1 = new Categorie(1, titre, visuel);
			
			try {
				DAOFactory.getDAOFactory(Persistance.MYSQL).getCategorieDAO().create(c1);
				DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getCategorieDAO().create(c1); 	
				
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



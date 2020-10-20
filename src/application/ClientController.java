package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import connexion.ConnexionMYSQL;
import connexion.Persistance;
import dao.factory.DAOFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import pojo.Client;

public class ClientController  implements Initializable {
	@FXML private Label lblAffichage;
	@FXML private TextField editNom;
	@FXML private TextField editPrenom;
	@FXML private TableColumn<Client, String> nom;
	@FXML private TableView<Client> tab;
	
	
	DAOFactory dao = DAOFactory.getDAOFactory(Persistance.MYSQL);
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.lblAffichage.setText("");
		this.editNom.setText("");
    	this.editPrenom.setText("");
    	/*
    	Connection laConnexion = ConnexionMYSQL.creeConnexion();		
		PreparedStatement requete;
		try {
			requete = laConnexion.prepareStatement("select * from `Client`");
			ResultSet res = requete.executeQuery();
			int id=res.getInt(1);
			String nom=res.getString(2);
			String prenom=res.getString(3);
		    tab.getItems().add(new Client(id,nom, prenom));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		
		
	}

	@FXML
	public void click_btn() {
		String nom = editNom.getText().trim();
		String prenom = editPrenom.getText().trim(); 
		String erreur="";
		
		if (nom.isEmpty()) {
			erreur = erreur + "\nLe nom est vide ";
		}
		if (prenom.isEmpty()) {
			erreur = erreur + "Le prenom est vide";
		}
		if (erreur != "") {
			this.lblAffichage.setTextFill(Color.web("#bb0b0b"));
			this.lblAffichage.setText(erreur);
		}
		else {
			//on cree une instance de categorie
			Client c1 = new Client(1, nom, prenom);
			try {
				DAOFactory.getDAOFactory(Persistance.MYSQL).getClientDAO().create(c1);
				DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getClientDAO().create(c1); 	
				
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

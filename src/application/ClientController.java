package application;

import java.net.URL;
import java.util.ResourceBundle;

import connexion.Persistance;
import dao.factory.DAOFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pojo.Client;

public class ClientController  implements Initializable {
	@FXML private Label lblAffichage;
	@FXML private TextField editNom;
	@FXML private TextField editPrenom;
	@FXML private TableView<Client> editView;
	@FXML private TableColumn<Client, String> editViewNom;
	@FXML private TableColumn<Client, String> editViewPrenom;
	
	DAOFactory dao = DAOFactory.getDAOFactory(Persistance.MYSQL);
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			this.lblAffichage.setText("");
			this.editNom.setText("");
	    	this.editPrenom.setText("");
	    	this.editView.setItems(FXCollections.observableArrayList(dao.getClientDAO().findAll()));
	    	this.editViewNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
	        this.editViewPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
		} catch (Exception e) {
			e.printStackTrace();
		}	
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
	public void click_btn_modifier()  throws Exception {
		try {
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/ClientUpdate.fxml"));
	        Parent root1 = (Parent) fxmlLoader.load();
	        Stage stage = new Stage();
	        stage.setScene(new Scene(root1)); 
	        stage.setTitle("Modifier une categorie");
	        stage.show();
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
		
		String nom = editNom.getText().trim();
		String prenom = editPrenom.getText().trim(); 
		String erreur="";
		
		if (nom.isEmpty()) {
			erreur = erreur + "Le nom est vide\n";
		}
		if (prenom.isEmpty()) {
			erreur = erreur + "Le prenom est vide\n";
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
		//dao.getClientDAO().update(dao.getClientDAO().getById(this.editView.getSelectionModel().getSelectedItems().get(0).getId()));
	}

	@FXML
	public void click_btn_supprimer() throws Exception {
		dao.getClientDAO().delete(dao.getClientDAO().getById(this.editView.getSelectionModel().getSelectedItems().get(0).getId()));
		initialize(null, null);
		
	}
}

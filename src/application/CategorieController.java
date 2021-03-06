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
import pojo.Categorie;

public class CategorieController  implements Initializable {
	@FXML private Label lblAffichage;
	@FXML private TextField editTitre;
	@FXML private TextField editVisuel;
	@FXML private TextField editTri;
	@FXML private TableView<Categorie> editView;
	@FXML private TableColumn<Categorie, String> editViewCategorie;
	@FXML private TableColumn<Categorie, String> editViewTitre;
	@FXML private TableColumn<Categorie, String> editViewvisuel;
	
	DAOFactory dao = DAOFactory.getDAOFactory(Persistance.MYSQL);
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
	    	this.lblAffichage.setText("");
	    	this.editTitre.setText("");
	    	this.editVisuel.setText("");
	    	this.editView.setItems(FXCollections.observableArrayList(dao.getCategorieDAO().findAll()));
	    	this.editViewCategorie.setCellValueFactory(new PropertyValueFactory<>("id"));
	    	this.editViewTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
	        this.editViewvisuel.setCellValueFactory(new PropertyValueFactory<>("visuel"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void click_btn() {
		String titre = editTitre.getText().trim();
		String visuel = editVisuel.getText().trim(); 
		String erreur="";
		
		if (titre.isEmpty()) {
			erreur = erreur + "Le titre est vide\n";
		}
		if (visuel.isEmpty()) {
			erreur = erreur + "Le visuel est vide\n";
		}
		if (erreur != "") {
			this.lblAffichage.setTextFill(Color.web("#bb0b0b"));
			this.lblAffichage.setText(erreur);
		}
		else {
			//on cree une instance de categorie
			Categorie c1 = new Categorie(1, titre, visuel);
			
			try {
				DAOFactory.getDAOFactory(AccueilController.Peri).getCategorieDAO().create(c1);	
				
			} catch (Exception e) {
				e.printStackTrace();
			} 
			initialize(null, null);
			this.lblAffichage.setTextFill(Color.web("#000000"));
			this.lblAffichage.setText(c1.toString());
		}
	}
	
	
	
	@FXML
	public void click_btn_modifier() throws Exception  {
		try {
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/CategorieUpdate.fxml"));
	        Parent root1 = (Parent) fxmlLoader.load();
	        Stage stage = new Stage();
	        stage.setScene(new Scene(root1)); 
	        stage.setTitle("Modifier une categorie");
	        stage.show();
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
		
		String titre = editTitre.getText().trim();
		String visuel = editVisuel.getText().trim(); 
		String erreur="";
		
		if (titre.isEmpty()) {
			erreur = erreur + "Le titre est vide\n";
		}
		if (visuel.isEmpty()) {
			erreur = erreur + "Le visuel est vide\n";
		}
		if (erreur != "") {
			this.lblAffichage.setTextFill(Color.web("#bb0b0b"));
			this.lblAffichage.setText(erreur);
		}
		else {
			//on cree une instance de categorie
			Categorie c1 = new Categorie(1, titre, visuel);
			
			try {
				DAOFactory.getDAOFactory(AccueilController.Peri).getCategorieDAO().create(c1);	 	
				
			} catch (Exception e) {
				e.printStackTrace();
			} 
			initialize(null, null);
			this.lblAffichage.setTextFill(Color.web("#000000"));
			this.lblAffichage.setText(c1.toString());
		}
		//dao.getCategorieDAO().update(dao.getCategorieDAO().getById(this.editView.getSelectionModel().getSelectedItems().get(0).getId()));
	}

	@FXML
	public void click_btn_supprimer() throws Exception {
		dao.getCategorieDAO().delete(dao.getCategorieDAO().getById(this.editView.getSelectionModel().getSelectedItems().get(0).getId()));
		initialize(null, null);
		
	}
	
}



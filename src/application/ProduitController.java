package application;

import java.net.URL;
import java.util.ResourceBundle;

import connexion.Persistance;
import dao.factory.DAOFactory;

import pojo.Categorie;
import pojo.Produit;


import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ProduitController implements Initializable {
	@FXML private Label lblAffichage;
	@FXML private TextField editNom;
	@FXML private TextField editTri;
	@FXML private TextField editTarif;
	@FXML private TextArea editDesc; 
	@FXML private ChoiceBox<Categorie> cbxCategorie;
	@FXML private TableView<Produit> editView;
	@FXML private TableColumn<Produit, String> editViewNom;
	@FXML private TableColumn<Produit, String> editViewDescription;
	@FXML private TableColumn<Produit, String> editViewTarif;
	@FXML private TableColumn<Produit, String> editViewCategorie;

	DAOFactory dao = DAOFactory.getDAOFactory(Persistance.MYSQL);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
	    	this.lblAffichage.setText("");
	    	this.editNom.setText("");
	    	this.editDesc.setText("");
	    	this.editTarif.setText("");
	    	//this.cbxCategorie.setSelectionModel(null);
			this.cbxCategorie.setItems(FXCollections.observableArrayList(dao.getCategorieDAO().findAll()));
			this.editView.setItems(FXCollections.observableArrayList(dao.getProduitDAO().findAll()));
	    	this.editViewNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
	        this.editViewDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
	        this.editViewTarif.setCellValueFactory(new PropertyValueFactory<>("tarif"));
	        this.editViewCategorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));
	        
	        //tri a faire
	        
		} catch (Exception e) {
		}
	}

	@FXML
	public void click_btn() {
		String nom = editNom.getText().trim();
		String desc = editDesc.getText().trim(); 
		float tarif = 0;
		String erreur="";
		//Objet de type Categorie qui correspond a l'objet selectionne dans le choice box
		Categorie selectItem = cbxCategorie.getSelectionModel().getSelectedItem(); 
		
		//on convertit le tarif qui est en String en int 
		try {
			tarif = Float.parseFloat(editTarif.getText().trim());
		}
		catch (NumberFormatException e) {
		}
		
		if (nom.isEmpty()) {
			erreur = erreur + "\nLe nom est vide";
		}
		if (desc.isEmpty()) {
			erreur = erreur + "\nLa description est vide";
		}
		if (tarif <= 0) {
			erreur = erreur + "\nVeuillez saisir un tarif raisonnable";
		}
		if (selectItem == null) {
			erreur = erreur + "\nVeuillez selectionner une categorie";
		}
		if (erreur != "") {
			this.lblAffichage.setTextFill(Color.web("#bb0b0b"));
			this.lblAffichage.setText(erreur);
		}
		else {
			//on creer une instance de produit 
			Produit p1 = new Produit(1, nom, desc, tarif, nom.concat(".png"), selectItem.getId());
			try {
				DAOFactory.getDAOFactory(Persistance.MYSQL).getProduitDAO().create(p1);
				DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getProduitDAO().create(p1); 	
			} catch (Exception e) {
				e.printStackTrace();
			} 
			initialize(null, null);
			this.lblAffichage.setTextFill(Color.web("#000000"));
			if(tarif > 1) 
				this.lblAffichage.setText(p1.toStringUtilisateur() + "s");
			else 
				this.lblAffichage.setText(p1.toStringUtilisateur());
		}
	}
	
	@FXML
	public void click_btn_modifier() {
		try {
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/ProduitUpdate.fxml"));
	        Parent root1 = (Parent) fxmlLoader.load();
	        Stage stage = new Stage();
	        stage.setScene(new Scene(root1));  
	        stage.setTitle("Modifier un produit");
	        stage.show();
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
		
		String nom = editNom.getText().trim();
		String desc = editDesc.getText().trim(); 
		float tarif = 0;
		String erreur="";
		//Objet de type Categorie qui correspond a l'objet selectionne dans le choice box
		Categorie selectItem = cbxCategorie.getSelectionModel().getSelectedItem(); 
		//on convertit le tarif qui est en String en int 
		try {
			tarif = Float.parseFloat(editTarif.getText().trim());
		}
		catch (NumberFormatException e) {
		}
		
		if (nom.isEmpty()) {
			erreur = erreur + "\nLe nom est vide";
		}
		if (desc.isEmpty()) {
			erreur = erreur + "\nLa description est vide";
		}
		if (tarif <= 0) {
			erreur = erreur + "\nVeuillez saisir un tarif raisonnable";
		}
		if (selectItem == null) {
			erreur = erreur + "\nVeuillez selectionner une categorie";
		}
		if (erreur != "") {
			this.lblAffichage.setTextFill(Color.web("#bb0b0b"));
			this.lblAffichage.setText(erreur);
		}
		else {
			//on creer une instance de produit 
			Produit p1 = new Produit(1, nom, desc, tarif, nom.concat(".png"), selectItem.getId());
			try {
				DAOFactory.getDAOFactory(Persistance.MYSQL).getProduitDAO().create(p1);
				DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getProduitDAO().create(p1); 	
			} catch (Exception e) {
				e.printStackTrace();
			} 
			initialize(null, null);
			this.lblAffichage.setTextFill(Color.web("#000000"));
			if(tarif > 1) 
				this.lblAffichage.setText(p1.toStringUtilisateur() + "s");
			else 
				this.lblAffichage.setText(p1.toStringUtilisateur());
		}
	}

	@FXML
	public void click_btn_supprimer() throws Exception{
		dao.getProduitDAO().delete(dao.getProduitDAO().getById(this.editView.getSelectionModel().getSelectedItems().get(0).getId()));
		initialize(null, null);
	}

}


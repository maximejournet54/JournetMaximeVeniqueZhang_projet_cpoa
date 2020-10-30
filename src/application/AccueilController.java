package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import connexion.Persistance;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class AccueilController implements Initializable {

	@FXML private MenuItem mnu_categorie;
	@FXML private MenuItem mnu_client;
	@FXML private MenuItem mnu_commande;
	@FXML private MenuItem mnu_produit;
	@FXML private MenuItem mnu_quitter;
	@FXML private MenuItem mnu_info;
	@FXML private ChoiceBox<Persistance> cbxPersistance;
	
	@Override
	public void initialize(URL location, ResourceBundle resources)  {
		
		//ne fonctionne pas
		try {
			/*
			ArrayList<Persistance> list=new ArrayList<>();
			for(Persistance p:Persistance.values() ){
				list.add(p);
			}
			*/
			ChoiceBox<Persistance> cbxPersistance = new ChoiceBox<>();
			cbxPersistance.getItems().setAll(Persistance.values());
			
			
	    	
		} catch (Exception e) {
		}
		
	}
	
	@FXML
	public void click_btn() {
		Persistance p=cbxPersistance.getSelectionModel().getSelectedItem(); 
		//a fixer
	}
	
	@FXML
	public void click_mnu_categorie() {
		try {
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/Categorie.fxml"));
	        Parent root1 = (Parent) fxmlLoader.load();
	        Stage stage = new Stage();
	        stage.setScene(new Scene(root1)); 
	        stage.setTitle("Menu Categorie");
	        stage.show();
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@FXML
	public void click_mnu_client() {
		try {
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/Client.fxml"));
	        Parent root1 = (Parent) fxmlLoader.load();
	        Stage stage = new Stage();
	        stage.setScene(new Scene(root1));  
	        stage.setTitle("Menu Client");
	        stage.show();
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@FXML
	public void click_mnu_commande() {
		try {
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/Commande.fxml"));
	        Parent root1 = (Parent) fxmlLoader.load();
	        Stage stage = new Stage();
	        stage.setScene(new Scene(root1));  
	        stage.setTitle("Menu Commande");
	        stage.show();
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@FXML
	public void click_mnu_produit() {
		 try {
		        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/Produit.fxml"));
		        Parent root1 = (Parent) fxmlLoader.load();
		        Stage stage = new Stage();
		        stage.setScene(new Scene(root1));  
		        stage.setTitle("Menu Produit");
		        stage.show();
		    } catch(Exception e) {
		        e.printStackTrace();
		    }
	}
	
	@FXML
	public void click_mnu_quitter() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Attention!");
		alert.setHeaderText("Attention, vous allez fermer l'application");
		alert.setContentText("Voulez-vous vraiment fermer l'application?");
		ButtonType btnOui = new ButtonType("Oui");
		ButtonType btnNon = new ButtonType("Non", ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(btnOui,btnNon);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == btnOui){
			Platform.exit();
		}
	}
}

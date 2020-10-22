package application;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.ResourceBundle;

import connexion.Persistance;
import dao.factory.DAOFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pojo.Client;
import pojo.Commande;
import pojo.LigneCommande;
import pojo.Produit;

public class CommandeController  implements Initializable {
	@FXML private DatePicker dtpDate;
	@FXML private Label lblAffichage;
	@FXML private ChoiceBox<Client> cbxClient;
	@FXML private TableView<Commande> editView;
	@FXML private TableColumn<Commande, String> editViewDate;
	@FXML private TableColumn<Commande, String> editViewClient;
	@FXML private TableColumn<Commande, String> editViewProduit;
	@FXML private TableColumn<Commande, String> editViewQuantite;
	@FXML private TableColumn<Commande, String> editViewPrix;
	
	DAOFactory dao = DAOFactory.getDAOFactory(Persistance.MYSQL);
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
	    	this.lblAffichage.setText("");
	    	this.cbxClient.setItems(FXCollections.observableArrayList(dao.getClientDAO().findAll()));
	    	this.editView.setItems(FXCollections.observableArrayList(dao.getCommandeDAO().findAll()));
	    	this.editViewDate.setCellValueFactory(new PropertyValueFactory<>("date"));
	        this.editViewClient.setCellValueFactory(new PropertyValueFactory<>("client"));
	        this.editViewProduit.setCellValueFactory(new PropertyValueFactory<>("produit"));
	        this.editViewQuantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
	        this.editViewPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML
	public void click_btn() {
		//fuseau horaire par defaut
		ZoneId defaultZoneId = ZoneId.systemDefault();
		LocalDate d= dtpDate.getValue();
		String erreur="";
		Client selectItem = cbxClient.getSelectionModel().getSelectedItem(); 
		HashMap lignecommande=new HashMap<Produit, LigneCommande>(); 
		//a completer et modifier
		
		//gestion des erreurs
		if(d == null) {
			erreur = erreur + "\nVeuillez selectionner une date";
		}
		if (selectItem == null) {
			erreur = erreur + "\nVeuillez selectionner un client";
		}
		
		/*	
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
		*/
	}
	
	@FXML
	public void click_btn_modifier() throws Exception {
		//meme code que pour click_btn + update
	}

	@FXML
	public void click_btn_supprimer() throws Exception {
		dao.getCommandeDAO().delete(dao.getCommandeDAO().getById(this.editView.getSelectionModel().getSelectedItems().get(0).getId()));
		initialize(null, null);
	}

}
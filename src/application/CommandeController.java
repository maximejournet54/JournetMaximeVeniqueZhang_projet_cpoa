package application;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import connexion.Persistance;
import dao.factory.DAOFactory;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pojo.Client;
import pojo.Commande;
import pojo.LigneCommande;
import pojo.Produit;

public class CommandeController  implements Initializable {
	@FXML private TextField editTri;
	@FXML private TextField editDate;
	@FXML private TextField editQuantite;
	@FXML private TextField editRecherche;
	
	@FXML private Label lblAffichage1;
	@FXML private Label lblAffichage2;
	
	@FXML private ChoiceBox<Commande> cbxTri;
	@FXML private ChoiceBox<Client> cbxClient;
	@FXML private ChoiceBox<Produit> cbxProduit;
	
	@FXML private TableView<Commande> editViewCommande;	
	@FXML private TableView<LigneCommande> editViewLigneCommande;
	
	@FXML private TableColumn<Commande, String> editViewIdCommande1;
	@FXML private TableColumn<Commande, String> editViewDateCommande;
	@FXML private TableColumn<Commande, String> editViewIdClient;

	@FXML private TableColumn<LigneCommande, String> editViewIdCommande2;
	@FXML private TableColumn<LigneCommande, String> editViewIdProduit;
	@FXML private TableColumn<LigneCommande, String> editViewQuantite;
	@FXML private TableColumn<LigneCommande, String> editViewPrix;
	
	DAOFactory dao = DAOFactory.getDAOFactory(Persistance.MYSQL);
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
	    	this.lblAffichage1.setText("");
	    	this.lblAffichage2.setText("");
	    	this.cbxClient.setItems(FXCollections.observableArrayList(dao.getClientDAO().findAll()));
	    	this.cbxProduit.setItems(FXCollections.observableArrayList(dao.getProduitDAO().findAll()));
	    	
	    	//commande
	    	this.editViewCommande.setItems(FXCollections.observableArrayList(dao.getCommandeDAO().findAll()));
	    	this.editViewCommande.getSelectionModel().selectFirst();
	    	this.editViewIdCommande1.setCellValueFactory(new PropertyValueFactory<>("id"));
	        this.editViewDateCommande.setCellValueFactory(new PropertyValueFactory<>("date")); 
	        this.editViewIdClient.setCellValueFactory(new PropertyValueFactory<>("id"));
	        
	        //ligne commande
	     	this.editViewLigneCommande.setItems(FXCollections.observableArrayList(dao.getLigneCommandeDAO().findAll()));
	        this.editViewIdCommande2.setCellValueFactory(new PropertyValueFactory<>("idCommande"));
	        this.editViewIdProduit.setCellValueFactory(new PropertyValueFactory<>("idProduit"));
	        this.editViewQuantite.setCellValueFactory(new PropertyValueFactory<>("quantite")); 
	        this.editViewPrix.setCellValueFactory(new PropertyValueFactory<>("prixUnitaire")); 

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML
	public void click_btn_ajouter1() {
		//conversion de la date de String a LocalDate
		String d= editDate.getText();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate d1=LocalDate.parse(d, dateTimeFormatter); 
		
		Client selectItem1 = cbxClient.getSelectionModel().getSelectedItem(); 
		String erreur="";
		
		//gestion des erreurs
		
		if (selectItem1 == null) {
			erreur = erreur + "\nVeuillez selectionner un client";
		}
		
		if (d.isEmpty()) {
			erreur = erreur + "\nVeuillez saisir une date";
		}
			
		else  {
			
			//on creer une instance de commande
			Commande c1 = new Commande(1, d1, selectItem1.getId());
			
			try {
				DAOFactory.getDAOFactory(Persistance.MYSQL).getCommandeDAO().create(c1);
				DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getCommandeDAO().create(c1); 
			} catch (Exception e) {
				e.printStackTrace();
			} 
			initialize(null, null);
			this.lblAffichage1.setTextFill(Color.web("#000000"));
			this.lblAffichage1.setText(c1.toString());
		}
		
		
	}
	
	@FXML
	public void click_btn_ajouter2(){
		Commande c=this.editViewCommande.getSelectionModel().getSelectedItem();
		Produit p = cbxProduit.getSelectionModel().getSelectedItem(); 
		String qte= editQuantite.getText();
		int q=Integer.parseInt(qte);
		String erreur="";
		
		//gestion des erreurs
		
		if (p == null) {
			erreur = erreur + "\nVeuillez selectionner un produit";
		}
		
		if (qte.isEmpty()) {
			erreur = erreur + "\nVeuillez saisir une quantite";
		}
		
		if (q<=0) {
			erreur = erreur + "\nVeuillez saisir une quantite > 0";
		}
		
		
		else  {
			
			
			//on creer une instance de ligne commande
			LigneCommande c1 = new LigneCommande(c.getId(), p.getId(), q, p.getTarif());
			
			try {
				DAOFactory.getDAOFactory(Persistance.MYSQL).getLigneCommandeDAO().create(c1);
				DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getLigneCommandeDAO().create(c1); 
			} catch (Exception e) {
				e.printStackTrace();
			} 
			initialize(null, null);
			this.lblAffichage2.setTextFill(Color.web("#000000"));
			this.lblAffichage2.setText(c1.toString());
		}
		
		
	}
	
	@FXML
	public void click_btn_modifier1() throws Exception {
		//meme code que pour click_btn + update
		try {
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/CommandeUpdate.fxml"));
	        Parent root1 = (Parent) fxmlLoader.load();
	        Stage stage = new Stage();
	        stage.setScene(new Scene(root1)); 
	        stage.setTitle("Modifier une commande");
	        stage.show();
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
		
		
	}
	
	@FXML
	public void click_btn_modifier2() throws Exception {
		//meme code que pour click_btn + update
		try {
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/LigneCommandeUpdate.fxml"));
	        Parent root1 = (Parent) fxmlLoader.load();
	        Stage stage = new Stage();
	        stage.setScene(new Scene(root1)); 
	        stage.setTitle("Modifier une ligne de commande");
	        stage.show();
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
		
		
	}

	@FXML
	public void click_btn_supprimer1() throws Exception {
		dao.getCommandeDAO().delete(dao.getCommandeDAO().getById(this.editViewCommande.getSelectionModel().getSelectedItems().get(0).getId()));
		initialize(null, null);
	}
	
	@FXML
	public void click_btn_supprimer2() throws Exception {
		dao.getLigneCommandeDAO().delete(dao.getLigneCommandeDAO().getById(this.editViewLigneCommande.getSelectionModel().getSelectedItems().get(0).getIdCommande(), this.editViewLigneCommande.getSelectionModel().getSelectedItems().get(1).getIdProduit()));
		initialize(null, null);
	}

}
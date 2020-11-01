package application;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import dao.factory.DAOFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
	
	@FXML private TableColumn<Commande, Integer> editViewIdCommande1;
	@FXML private TableColumn<Commande, Date> editViewDateCommande;
	@FXML private TableColumn<Commande, Integer> editViewIdClient;

	@FXML private TableColumn<LigneCommande, Integer> editViewIdCommande2;
	@FXML private TableColumn<LigneCommande, Integer> editViewIdProduit;
	@FXML private TableColumn<LigneCommande, Integer> editViewQuantite;
	@FXML private TableColumn<LigneCommande, Double> editViewPrix;
	
	private ObservableList<Commande> olC;
	private ObservableList<LigneCommande> olLC;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			olC=FXCollections.observableArrayList(DAOFactory.getDAOFactory(AccueilController.Peri).getCommandeDAO().findAll());
			olLC=FXCollections.observableArrayList(DAOFactory.getDAOFactory(AccueilController.Peri).getLigneCommandeDAO().findAll());
			
	    	this.lblAffichage1.setText("");
	    	this.lblAffichage2.setText("");
	    	this.cbxClient.setItems(FXCollections.observableArrayList(DAOFactory.getDAOFactory(AccueilController.Peri).getClientDAO().findAll()));
	    	this.cbxProduit.setItems(FXCollections.observableArrayList(DAOFactory.getDAOFactory(AccueilController.Peri).getProduitDAO().findAll()));
	    	
	    	//commande
	    	FilteredList<Commande> filteredDataC=new FilteredList<>(olC,p->true);
	    	SortedList<Commande> sortedDataC=new SortedList<>(filteredDataC);
	    	sortedDataC.comparatorProperty().bind(editViewCommande.comparatorProperty());
	    	this.editViewCommande.setItems(sortedDataC);
	    	
	    	editViewCommande.getSelectionModel().selectFirst();
	    	editViewIdCommande1.setCellValueFactory(new PropertyValueFactory<Commande, Integer>("id"));
	        editViewDateCommande.setCellValueFactory(new PropertyValueFactory<Commande, Date>("date")); 
	        editViewIdClient.setCellValueFactory(new PropertyValueFactory<Commande, Integer>("idClient"));
	        
	        //ligne commande
	        FilteredList<LigneCommande> filteredDataLC=new FilteredList<>(olLC,p->true);
	    	SortedList<LigneCommande> sortedDataLC=new SortedList<>(filteredDataLC);
	    	sortedDataLC.comparatorProperty().bind(editViewLigneCommande.comparatorProperty());
	    	this.editViewLigneCommande.setItems(sortedDataLC);
	    	
	     	editViewLigneCommande.setItems(FXCollections.observableArrayList(DAOFactory.getDAOFactory(AccueilController.Peri).getLigneCommandeDAO().findAll()));
	        editViewIdCommande2.setCellValueFactory(new PropertyValueFactory<LigneCommande, Integer>("idCommande"));
	        editViewIdProduit.setCellValueFactory(new PropertyValueFactory<LigneCommande, Integer>("idProduit"));
	        editViewQuantite.setCellValueFactory(new PropertyValueFactory<LigneCommande, Integer>("quantite")); 
	        editViewPrix.setCellValueFactory(new PropertyValueFactory<LigneCommande, Double>("prixUnitaire"));        
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML
	public void click_btn_ajouter1() {
		String d= editDate.getText();		
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
			try {
				int c=DAOFactory.getDAOFactory(AccueilController.Peri).getClientDAO().getById(selectItem1.getId()).getId();
				Commande c1 = new Commande(editDate.getText(), c);
				DAOFactory.getDAOFactory(AccueilController.Peri).getCommandeDAO().create(c1);
				this.lblAffichage1.setTextFill(Color.web("#000000"));
				this.lblAffichage1.setText(c1.toString());
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}	
	}
	
	@FXML
	public void click_btn_ajouter2(){
		String qte= editQuantite.getText();
		int q=Integer.parseInt(qte);
		String erreur="";
		Produit selectItem2=cbxProduit.getSelectionModel().getSelectedItem();
		Produit p = null;

			try {
				p = DAOFactory.getDAOFactory(AccueilController.Peri).getProduitDAO().getById((selectItem2.getId()));
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
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
			//on creer une instance de ligne commande et de commande
			try {
				Commande c=this.editViewCommande.getSelectionModel().getSelectedItem();	
				LigneCommande c1 = new LigneCommande(c.getId(), p.getId(), q, p.getTarif());
				DAOFactory.getDAOFactory(AccueilController.Peri).getLigneCommandeDAO().create(c1);
				this.lblAffichage2.setTextFill(Color.web("#000000"));
				this.lblAffichage2.setText(c1.toString());
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
		}
		
		
	}
	
	@FXML
	public void click_btn_modifier1(){
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
	public void click_btn_modifier2() {
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
	public void click_btn_supprimer1() {
		Commande c=this.editViewCommande.getSelectionModel().getSelectedItem();
		try {
			olC.remove(c);
			DAOFactory.getDAOFactory(AccueilController.Peri).getCommandeDAO().delete(c);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML
	public void click_btn_supprimer2() {
		LigneCommande c=this.editViewLigneCommande.getSelectionModel().getSelectedItem();
		try {
			olLC.remove(c);
			DAOFactory.getDAOFactory(AccueilController.Peri).getLigneCommandeDAO().delete(c);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
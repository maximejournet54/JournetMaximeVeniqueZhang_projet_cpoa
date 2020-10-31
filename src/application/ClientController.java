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
import javafx.scene.control.ChoiceBox;
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
	@FXML private TextField editId;
	@FXML private TextField editMdp;
	@FXML private TextField editNum;
	@FXML private TextField editVoie;
	@FXML private TextField editCp;
	@FXML private TextField editVille;
	@FXML private TextField editPays;
	
	@FXML private ChoiceBox<Client> cbxTri;
	
	@FXML private TableView<Client> editView;
	
	@FXML private TableColumn<Client, String> editViewNom;
	@FXML private TableColumn<Client, String> editViewPrenom;
	@FXML private TableColumn<Client, String> editViewId;
	@FXML private TableColumn<Client, String> editViewMdp;
	@FXML private TableColumn<Client, String> editViewNum;
	@FXML private TableColumn<Client, String> editViewVoie;
	@FXML private TableColumn<Client, String> editViewCp;
	@FXML private TableColumn<Client, String> editViewVille;
	@FXML private TableColumn<Client, String> editViewPays;
	
	DAOFactory dao = DAOFactory.getDAOFactory(Persistance.MYSQL);
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			this.lblAffichage.setText("");
			this.editNom.setText("");
	    	this.editPrenom.setText("");
	    	this.editId.setText("");
	    	this.editMdp.setText("");
	    	this.editNum.setText("");
	    	this.editVoie.setText("");
	    	this.editCp.setText("");
	    	this.editVille.setText("");
	    	this.editPays.setText("");
	    	
	    	this.editView.setItems(FXCollections.observableArrayList(dao.getClientDAO().findAll()));
	    	this.editViewNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
	        this.editViewPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
	        this.editViewId.setCellValueFactory(new PropertyValueFactory<>("identifiant"));
	        this.editViewMdp.setCellValueFactory(new PropertyValueFactory<>("mdp"));
	        this.editViewNum.setCellValueFactory(new PropertyValueFactory<>("numero"));
	        this.editViewVoie.setCellValueFactory(new PropertyValueFactory<>("rue"));
	        this.editViewCp.setCellValueFactory(new PropertyValueFactory<>("cp"));
	        this.editViewVille.setCellValueFactory(new PropertyValueFactory<>("ville"));
	        this.editViewPays.setCellValueFactory(new PropertyValueFactory<>("pays"));
	        
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	@FXML
	public void click_btn() {
		String nom = editNom.getText().trim();
		String prenom = editPrenom.getText().trim(); 
		String id = editId.getText().trim(); 
		String mdp = editMdp.getText().trim(); 
		String num = editNum.getText().trim();
		int num1=Integer.parseInt(num);
		String voie = editVoie.getText().trim(); 
		String cp = editCp.getText().trim(); 
		int cp1=Integer.parseInt(cp);
		String ville = editVille.getText().trim(); 
		String pays = editPays.getText().trim(); 
		String erreur="";
		
		if (nom.isEmpty()) {
			erreur = erreur + "\nLe nom est vide ";
		}
		if (prenom.isEmpty()) {
			erreur = erreur + "Le prenom est vide";
		}
		if (id.isEmpty()) {
			erreur = erreur + "L'identifiant est vide";
		}
		if (mdp.isEmpty()) {
			erreur = erreur + "Le mot de passe est vide";
		}
		if (num.isEmpty()) {
			erreur = erreur + "Le numero est vide";
		}
		if (voie.isEmpty()) {
			erreur = erreur + "La voie est vide";
		}
		if (cp.isEmpty()) {
			erreur = erreur + "Le code postal est vide";
		}
		if (ville.isEmpty()) {
			erreur = erreur + "La ville est vide";
		}
		if (pays.isEmpty()) {
			erreur = erreur + "Le pays est vide";
		}	
		
		if (erreur != "") {
			this.lblAffichage.setTextFill(Color.web("#bb0b0b"));
			this.lblAffichage.setText(erreur);
		}
		else {
			//on cree une instance de categorie
			Client c1 = new Client(1, nom, prenom, id, mdp, num1, voie, cp1, ville, pays);
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
		String id = editId.getText().trim(); 
		String mdp = editMdp.getText().trim(); 
		String num = editNum.getText().trim();
		int num1=Integer.parseInt(num);
		String voie = editVoie.getText().trim(); 
		String cp = editCp.getText().trim(); 
		int cp1=Integer.parseInt(cp);
		String ville = editVille.getText().trim(); 
		String pays = editPays.getText().trim(); 
		String erreur="";
		
		if (nom.isEmpty()) {
			erreur = erreur + "\nLe nom est vide ";
		}
		if (prenom.isEmpty()) {
			erreur = erreur + "Le prenom est vide";
		}
		if (id.isEmpty()) {
			erreur = erreur + "L'identifiant est vide";
		}
		if (mdp.isEmpty()) {
			erreur = erreur + "Le mot de passe est vide";
		}
		if (num.isEmpty()) {
			erreur = erreur + "Le numero est vide";
		}
		if (voie.isEmpty()) {
			erreur = erreur + "La voie est vide";
		}
		if (cp.isEmpty()) {
			erreur = erreur + "Le code postal est vide";
		}
		if (ville.isEmpty()) {
			erreur = erreur + "La ville est vide";
		}
		if (pays.isEmpty()) {
			erreur = erreur + "Le pays est vide";
		}	
		
		if (erreur != "") {
			this.lblAffichage.setTextFill(Color.web("#bb0b0b"));
			this.lblAffichage.setText(erreur);
		}
		else {
			//on cree une instance de categorie
			Client c1 = new Client(1, nom, prenom, id, mdp, num1, voie, cp1, ville, pays);
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
	public void click_btn_supprimer() throws Exception {
		dao.getClientDAO().delete(dao.getClientDAO().getById(this.editView.getSelectionModel().getSelectedItems().get(0).getId()));
		initialize(null, null);
		
	}
}

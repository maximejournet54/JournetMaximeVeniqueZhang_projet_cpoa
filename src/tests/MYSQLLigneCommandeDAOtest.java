package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import connexion.Persistance;
import dao.factory.DAOFactory;
import dao.modele.CommandeDAO;
import dao.modele.LigneCommandeDAO;
import pojo.Commande;
import pojo.LigneCommande;
import pojo.Produit;

public class MYSQLLigneCommandeDAOtest {

	DateTimeFormatter formatage = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	LigneCommandeDAO<LigneCommande> ligneCommande = DAOFactory.getDAOFactory(Persistance.MYSQL).getLigneCommandeDAO();
	
	//Fonction qui supprime toutes les lignes contenues dans la table SAUF la premiere
	@BeforeEach
	void testDeleteAll() {
		ArrayList<LigneCommande> listeLigneCommande = new ArrayList<LigneCommande>();
		
		try {
			listeLigneCommande = ligneCommande.findAll();
			
			int i = 1;
			
			while(i < listeLigneCommande.size()) {
				ligneCommande.delete(listeLigneCommande.get(i++));
			}
		} 
		catch (Exception e) {
		}
	}
	
	
	//Fonction qui permet de tester tous les cas ou une CREATION de ligneCommande renvoie VRAI
	//Peut importe l'id rentre, le ligneCommande est toujours cree avec succes
	@Test
	void testCreateTrue() {
		
		try {
			assertFalse(ligneCommande.create(new LigneCommande(1, 6, 1, 1)));
		} 
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	//Fonction qui permet de tester tous les cas ou une MODIFICATION de ligneCommande renvoie VRAI
	@Test
	void testUpdateTrue() {
		
		try {
			//L'unique cas ou la fonction renvoi vrai est quand on selectionne un id qui existe, ici le cas 2 garde grace a la fonction testDeleteAll()
			assertTrue(ligneCommande.update(new LigneCommande(ligneCommande.findAll().get(ligneCommande.findAll().size()-1).getIdCommande()
					, ligneCommande.findAll().get(ligneCommande.findAll().size()-1).getIdProduit()
					, 10, 10)));
		} 
		catch (Exception e) {
			fail("exception lancee mais id ligneCommande existant");
		}
	}
	
	
	//Fonction qui permet de tester tous les cas ou une MODIFICATION de ligneCommande renvoie FAUX
	@Test
	void testUpdateFalse() {
		
		try {
			//La fonctionne renvoie soit VRAI soit une exception donc on on recupere l'exception est fait assertFalse car l'exception ne sera pas vide
			ligneCommande.update(new LigneCommande(-1, 6, 1, 1));
			fail("exception lancee mais id ligneCommande non existant");
			} 
		catch (Exception e) {
			assertFalse( e.getMessage().isEmpty() );
		}
	}
	
	//Fonction qui permet de tester tous les cas ou une SUPPRESSION de ligneCommande renvoie FAUX
	@Test
	void testDeleteTrue() {
		try {
			HashMap<Produit, LigneCommande> ligne = new HashMap<Produit, LigneCommande>();
			
			//On creer une nouvelle commande pour la supprimer ensuite
			CommandeDAO commandeDAO = DAOFactory.getDAOFactory(Persistance.MYSQL).getCommandeDAO();
			commandeDAO.create(new Commande(2, LocalDate.parse("01/01/2020", formatage), 2, ligne));
			
			//on recupere l'id de la commande creee
			ArrayList<Commande> listeCommande = commandeDAO.findAll();
			Commande commande = listeCommande.get( listeCommande.size()-1 );
			
			LigneCommande ligneCommandeTest = new LigneCommande(commande.getId(), 6, 1, 1);
			ligneCommande.create(ligneCommandeTest);
			
			//On cree une liste de tous les clients
			ArrayList<LigneCommande> listeLigneCommande = ligneCommande.findAll();
			
			//on recupere le dernier item de la liste et on le supprime
			LigneCommande ligneCommandeDelete = listeLigneCommande.get(listeLigneCommande.size()-1);
			assertTrue(ligneCommande.delete(ligneCommandeDelete));
			
			DAOFactory.getDAOFactory(Persistance.MYSQL).getCommandeDAO().delete(new Commande(commande.getId(), LocalDate.parse("01/01/2020", formatage), 2, ligne));
		}
		catch (Exception e) {
			fail("Exception lancee mais id ligneCommande existant");
		}
	}
	
	@Test
	void testDeleteFalse() {
		
		try {
			//La fonctionne renvoie soit VRAI soit une exception donc on on recupere l'exception est fait assertFalse car l'exception ne sera pas vide
			ligneCommande.delete(new LigneCommande(-1, 6, 1, 1));
			fail("exception non lancee mais id ligneCommande non existant");
			} 
		catch (Exception e) {
			assertFalse( e.getMessage().isEmpty() );
		}
	}
	
	@Test
	void testGetByIdTrue() {
		
		try {
			//La seule valeur qui reste apres la methode DeleteAll 
			ligneCommande.getById(ligneCommande.findAll().get(ligneCommande.findAll().size()-1).getIdCommande()
					, ligneCommande.findAll().get(ligneCommande.findAll().size()-1).getIdProduit());
		} 
		catch (Exception e) {
			fail("Exception lancee mais id ligneCommande existant");
		}
	}
	
	@Test
	void testGetByIdTrueFalse() {
		
		try {
			//On met une valeur qui n'existera jamais 
			ligneCommande.getById(-1, 6);
			fail("Exception lancee mais id ligneCommande inexistant");
		} 
		catch (Exception e) {
			assertFalse( e.getMessage().isEmpty() );
		}
	}
	
	
	@Test
	void testFindAll() {
		try {
			ArrayList<LigneCommande> listeLigneCommande = ligneCommande.findAll();
			assertTrue(!listeLigneCommande.isEmpty());
		} catch (Exception e) {
			fail("La liste de ligneCommande ne doit pas etre vide");
		}
	}

}


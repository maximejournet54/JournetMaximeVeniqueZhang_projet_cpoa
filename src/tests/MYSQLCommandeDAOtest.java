package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import connexion.Persistance;
import dao.factory.DAOFactory;
import dao.modele.CommandeDAO;
import pojo.Commande;
import pojo.LigneCommande;
import pojo.Produit;

public class MYSQLCommandeDAOtest {

	CommandeDAO commande = DAOFactory.getDAOFactory(Persistance.MYSQL).getCommandeDAO();
	DateTimeFormatter formatage = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	//Fonction qui supprime toutes les lignes contenues dans la table SAUF la premiere
	@BeforeEach
	void testDeleteAll() {
		ArrayList<Commande> listeCommande = new ArrayList<Commande>();
		
		try {
			listeCommande = commande.findAll();
			
			int i = 1;
			
			while(i < listeCommande.size()) {
				commande.delete(listeCommande.get(i++));
			}
		} 
		catch (Exception e) {
		}
	}
	
	
	//Fonction qui permet de tester tous les cas ou une CREATION de commande renvoie VRAI
	//Peut importe l'id rentre, le commande est toujours cree avec succes
	@Test
	void testCreateTrue() {
		HashMap<Produit, LigneCommande> ligneCommande = new HashMap<Produit, LigneCommande>();
		
		try {
			assertTrue(commande.create(new Commande(1, LocalDate.parse("01/01/2020", formatage), 2, ligneCommande)));
			
		} 
		catch (Exception e) {
			fail("Erreur : exception lancee");
		}
	}
	
	
	//Fonction qui permet de tester tous les cas ou une MODIFICATION de commande renvoie VRAI
	@Test
	void testUpdateTrue() {
		HashMap<Produit, LigneCommande> ligneCommande = new HashMap<Produit, LigneCommande>();
		
		try {
			//L'unique cas ou la fonction renvoi vrai est quand on selectionne un id qui existe, ici le cas 2 garde grace a la fonction testDeleteAll()
			assertTrue(commande.update(new Commande( commande.findAll().get(commande.findAll().size()-1).getId()
					, LocalDate.parse("02/02/2020", formatage), 2, ligneCommande)));
		} 
		catch (Exception e) {
			fail("exception lancee mais id commande existant");
		}
	}
	
	
	//Fonction qui permet de tester tous les cas ou une MODIFICATION de commande renvoie FAUX
	@Test
	void testUpdateFalse() {
		HashMap<Produit, LigneCommande> ligneCommande = new HashMap<Produit, LigneCommande>();
		
		try {
			//La fonctionne renvoie soit VRAI soit une exception donc on on recupere l'exception est fait assertFalse car l'exception ne sera pas vide
			commande.update(new Commande(-1, LocalDate.parse("02/02/2020", formatage), 2, ligneCommande));
			fail("exception lancee mais id commande non existant");
			} 
		catch (Exception e) {
			assertFalse( e.getMessage().isEmpty() );
		}
	}
	
	//Fonction qui permet de tester tous les cas ou une SUPPRESSION de commande renvoie FAUX
	@Test
	void testDeleteTrue() {
		HashMap<Produit, LigneCommande> ligneCommande = new HashMap<Produit, LigneCommande>();
		
		try {
			//On creee un nouveau client
			Commande commandeTest = new Commande(commande.findAll().get(commande.findAll().size()-1).getId()
					, LocalDate.parse("03/03/2020", formatage), 2, ligneCommande);
			commande.create(commandeTest);
			
			//On cree une liste de tous les clients
			ArrayList<Commande> listeCommande = commande.findAll();
			
			//on recupere le dernier item de la liste et on le supprime
			Commande commandeDelete = listeCommande.get(listeCommande.size()-1);
			assertTrue(commande.delete(commandeDelete));
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	void testDeleteFalse() {
		HashMap<Produit, LigneCommande> ligneCommande = new HashMap<Produit, LigneCommande>();
		
		try {
			//La fonctionne renvoie soit VRAI soit une exception donc on on recupere l'exception est fait assertFalse car l'exception ne sera pas vide
			commande.delete(new Commande(-1, LocalDate.parse("02/02/2020", formatage), 2, ligneCommande));
			fail("exception non lancee mais id commande non existant");
			} 
		catch (Exception e) {
			assertFalse( e.getMessage().isEmpty() );
		}
	}
	
	@Test
	void testGetByIdTrue() {
		
		try {
			//La seule valeur qui reste apres la methode DeleteAll 
			commande.getById(commande.findAll().get(commande.findAll().size()-1).getId());
		} 
		catch (Exception e) {
			fail("Exception lancee mais id commande existant");
		}
	}
	
	@Test
	void testGetByIdFalse() {
		
		try {
			//On met une valeur qui n'existera jamais 
			commande.getById(-1);
			fail("Exception lancee mais id commande inexistant");
		} 
		catch (Exception e) {
			assertFalse( e.getMessage().isEmpty() );
		}
	}
	
	
	@Test
	void testFindAll() {
		try {
			ArrayList<Commande> listeCommande = commande.findAll();
			assertTrue(!listeCommande.isEmpty());
		} catch (Exception e) {
			fail("La liste de commande ne doit pas etre vide");
		}
	}

}

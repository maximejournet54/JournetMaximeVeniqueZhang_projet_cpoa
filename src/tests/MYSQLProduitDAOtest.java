package tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import connexion.Persistance;
import dao.factory.DAOFactory;
import dao.modele.ProduitDAO;
import pojo.Produit;

public class MYSQLProduitDAOtest {

	ProduitDAO produit = DAOFactory.getDAOFactory(Persistance.MYSQL).getProduitDAO();
	
	//Fonction qui supprime toutes les lignes contenues dans la table SAUF la premiere
	@BeforeEach
	void testDeleteAll() {
		ArrayList<Produit> listeProduit = new ArrayList<Produit>();
		
		try {
			listeProduit = produit.findAll();
			
			int i = 1;
			
			while(i < listeProduit.size()) {
				produit.delete(listeProduit.get(i++));
			}
		} 
		catch (Exception e) {
		}
	}
	
	//Fonction qui permet de tester tous les cas ou une CREATION de produit renvoie VRAI
	//Peut importe l'id rentre, le produit est toujours cree avec succes
	@Test
	void testCreateTrue() {
		
		try {
			assertTrue(produit.create(new Produit(-1, "test1", "test1.png", 1, "test1", 1)));
			assertTrue(produit.create(new Produit(0, "test2", "test2.png", 1, "test1", 1)));
			assertTrue(produit.create(new Produit(1, "test3", "test3.png", 1, "test1", 1)));
		} 
		catch (Exception e) {
			fail("Erreur : exception lancee");
		}
	}


	//Fonction qui permet de tester tous les cas ou une MODIFICATION de produit renvoie VRAI
	@Test
	void testUpdateTrue() {
		
		try {
			//L'unique cas ou la fonction renvoi vrai est quand on selectionne un id qui existe, ici le cas 2 garde grace a la fonction testDeleteAll()
			assertTrue(produit.update(new Produit( produit.findAll().get(produit.findAll().size()-1).getId()
					, "TestUpdateR", 
					"TestUpdateR", 
					1, 
					"test1", 
					1)));
		} 
		catch (Exception e) {
			fail("exception lancee mais id produit existant");
		}
	}


	//Fonction qui permet de tester tous les cas ou une MODIFICATION de produit renvoie FAUX
	@Test
	void testUpdateFalse() {
		
		try {
			//La fonctionne renvoie soit VRAI soit une exception donc on on recupere l'exception est fait assertFalse car l'exception ne sera pas vide
			produit.update(new Produit(-1, "TestNomEchec", "TestPrenomEchec", 1, "test1", 1));
			fail("exception lancee mais id produit non existant");
			} 
		catch (Exception e) {
			assertFalse( e.getMessage().isEmpty() );
		}
	}

	//Fonction qui permet de tester tous les cas ou une SUPPRESSION de produit renvoie FAUX
	@Test
	void testDeleteTrue() {
		try {
			//On creee un nouveau client
			Produit produitTest = new Produit(produit.findAll().get(produit.findAll().size()-1).getId()
					, "delete", "delete.png", 1, "test1", 1);
			produit.create(produitTest);
			
			//On cree une liste de tous les clients
			ArrayList<Produit> listeProduit = produit.findAll();
			
			//on recupere le dernier item de la liste et on le supprime 
			Produit produitDelete = listeProduit.get(listeProduit.size()-1);
			assertTrue(produit.delete(produitDelete));
		}
		catch (Exception e) {
			fail("Exception lancee mais id produit existant");
		}
	}

	@Test
	void testDeleteFalse() {
		
		try {
			//La fonctionne renvoie soit VRAI soit une exception donc on on recupere l'exception est fait assertFalse car l'exception ne sera pas vide
			produit.delete(new Produit(-1, "TestEchec", "TestEchec", 1, "test1", 1));
			fail("exception non lancee mais id produit non existant");
			} 
		catch (Exception e) {
			assertFalse( e.getMessage().isEmpty() );
		}
	}

	@Test
	void testGetByIdTrue() {
		
		try {
			//La seule valeur qui reste apres la methode DeleteAll 
			produit.getById(produit.findAll().get(produit.findAll().size()-1).getId());
		} 
		catch (Exception e) {
			fail("Exception lancee mais id produit existant");
		}
	}

	@Test
	void testGetByIdFalse() {
		
		try {
			//On met une valeur qui n'existera jamais 
			produit.getById(-1);
			fail("Exception lancee mais id produit inexistant");
		} 
		catch (Exception e) {
			assertFalse( e.getMessage().isEmpty() );
		}
	}


	@Test
	void testFindAll() {
		try {
			ArrayList<Produit> listeProduit = produit.findAll();
			assertTrue(!listeProduit.isEmpty());
		} catch (Exception e) {
			fail("La liste de produit ne doit pas etre vide");
		}
	}

}

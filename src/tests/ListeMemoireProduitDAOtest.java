package tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import connexion.Persistance;
import dao.factory.DAOFactory;
import dao.modele.ProduitDAO;
import pojo.Categorie;
import pojo.Produit;

public class ListeMemoireProduitDAOtest {
	ProduitDAO produit = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getProduitDAO();
	ArrayList<Produit> listeProduit = new ArrayList<Produit>();
	
	//on initialise les objets dont on aura besoin dans les tests 
	@BeforeAll
	static void initialize() {
		try {
			DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getCategorieDAO().create(new Categorie(3,"test", "test.png"));
		} catch (Exception e) {
		}
	}
	
	//Fonction qui supprime toutes les lignes contenues dans la table 
	@BeforeEach
	void testDeleteAll() {
		
		try {
			listeProduit = produit.findAll();
			int i = 0; 
				
			while(i < listeProduit.size()) {
				produit.delete(listeProduit.get(i++)); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	
	@AfterEach
	void Reinitialize() {
		testDeleteAll(); 
	}
	
	//on supprime les objets qu'on a cree au debut 
	@AfterAll
	static void ReinitializeEnd() {
		try {
			DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getCategorieDAO().delete(new Categorie(3,"test", "test.png"));
		} catch (Exception e) {
		}
	}
	
	
	//Fonction qui permet de tester tous les cas ou une CREATION de produit renvoie VRAI
	//Peut importe l'id rentre, le client est toujours cree avec succes, si la categorie existe
	@Test
	void testCreateTrue() {			
		try {
			
			assertTrue(produit.create(new Produit(-1, "test", "testDescription", 0, "testVisuel.png",3)));
			assertTrue(produit.create(new Produit(0, "test", "testDescription", 0, "testVisuel.png",3)));
			assertTrue(produit.create(new Produit(1, "test", "testDescription", 0, "testVisuel.png",3)));
			
		} catch (Exception e) {
			fail("Erreur : exception lancee\"");
		} 
	}	
	
	
	//Fonction qui permet de tester tous les cas ou une CREATION de produit renvoie FAUX, cad quand la categorie existe pas 
	@Test
	void testCreateFalse() {			
		try {
			produit.create(new Produit(1, "test", "testDescription", 0, "testVisuel.png",-10));
			fail("aucune categorie ne possede cet identifiant");
		} catch (Exception e) {
			assertFalse( e.getMessage().isEmpty() );
		} 
	}	
	
	
	
	//Fonction qui permet de tester tous les cas ou une MODIFICATION de produit renvoie VRAI
	@Test	
	void testUpdateTrue() {		
			
		try {
			
			//on cree une categorie qui aura comme id automatique 13(definit dans ListeMemoire)
			produit.create(new Produit(13, "test", "testDescription", 0, "testVisuel.png",3));
			
			//L'unique cas ou la fonction renvoi vrai est quand on selectionne un id qui existe
			assertTrue(produit.update(new Produit(13, "testReussi", "testDescriptionReussi", 0, "testVisuelReussi.png",3)));
			
		} catch (Exception e) {
			fail("exception lancee mais id produit existant");
		} 		
	}
	
	
	//Fonction qui permet de tester tous les cas ou une MODIFICATION de produit renvoie FAUX
	@Test	
	void testUpdateFalse() {		
			
		try {
			produit.update(new Produit(-1, "testEchec", "testDescriptionEchec", 0, "testVisuelEchec.png",1));
			produit.update(new Produit(1500, "testEchec", "testDescriptionEchec", 0, "testVisuelEchec.png",1));//aucun produit a un id=1500
			fail("IdProduit inexistant"); 
		} catch (Exception e) {
			assertFalse( e.getMessage().isEmpty() );
		} 		
	}
	
	//Fonction qui permet de tester tous les cas ou une SUPPRESSION de produit renvoie VRAI = quand l'id existe
	@Test
	void testDeleteTrue() {
		
		try  {
			
			//comme on a vide la table avant le test, on cree un produit  qui aura automatiquement 13 comme id 
			produit.create(new Produit(13, "test", "testDescription", 0, "testVisuel.png",3));
			
			assertTrue(produit.delete(new Produit(13, "test", "testDescription", 0, "testVisuel.png",3)));
			
		} catch (Exception e) {
			fail("Exception lancee mais id produit existant");
		} 
	}
	 
	
	//Fonction qui permet de tester tous les cas ou une SUPPRESSION de produit renvoie FAUX
	@Test
	void testDeleteFalse() {
		
		try {
			produit.delete(new Produit(-1, "testEchec", "testDescriptionEchec", 0, "testVisuelEchec.png",3));
			produit.delete(new Produit(1500, "testEchec", "testDescriptionEchec", 0, "testVisuelEchec.png",3));//aucun produit a un id=1500
			fail("pas d'exception alors qu'id inexistant");
		} catch (Exception e) {
			assertFalse( e.getMessage().isEmpty() );
		}
		
	}
	
	
	
	@Test
	void testGetByIdTrue() {
		
		try {
			
			//comme on a vide la table avant le test, on cree un produit qui aura automatiquement 13 comme id 
			produit.create(new Produit(13, "test", "testDescription", 0, "testVisuel.png",3));
			
			produit.getById(13);
		} 
		catch (Exception e) {
			fail("Exception lancee mais id produit existant");
		}
	}
	
	
	@Test
	void testGetByIdFalse() {
		
		try {
			//On met une valeur qui n'existera jamais comme nos id commence a 1 et que la table est vide
			produit.getById(-1);
			produit.getById(1500);//aucun client a un id=1500
			fail("Exception non lancee mais id Client inexistant");
		} 
		catch (Exception e) {
			assertFalse( e.getMessage().isEmpty() );
		}
	}
	
	
	@Test
	void testFindAllTrue() {
		try {
			produit.create(new Produit(13, "test", "testDescription", 0, "testVisuel.png",3));//on cree le produit
			ArrayList<Produit> listeProduit = produit.findAll();
			assertTrue(!listeProduit.isEmpty());
		} catch (Exception e) {
			fail("La liste de produit ne doit pas etre vide");
		}
	}

}

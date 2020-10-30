package tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import connexion.Persistance;
import dao.factory.DAOFactory;
import dao.modele.CategorieDAO;
import pojo.Categorie;


public class ListeMemoireCategorieDAOtest {
	
	CategorieDAO categorie = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getCategorieDAO();
	ArrayList<Categorie> listeCategorie = new ArrayList<Categorie>();
	
	//Fonction qui supprime toutes les lignes contenues dans la table 
	@BeforeEach
	void testDeleteAll() {
		
		try {
			listeCategorie = categorie.findAll();
			int i = 0; 
				
			while(i < listeCategorie.size()) {
				categorie.delete(listeCategorie.get(i++)); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	@AfterEach 
	void Reinitialize() {
		testDeleteAll(); 
	}
	
	//Fonction qui permet de tester tous les cas ou une CREATION de categorie renvoie VRAI
	//Peut importe l'id rentre, le client est toujours cree avec succes
	@Test
	void testCreateTrue() {			
		try {
			assertTrue(categorie.create(new Categorie(-1, "test", "test.png")));
			assertTrue(categorie.create(new Categorie(0, "test", "test.png")));
			assertTrue(categorie.create(new Categorie(1, "test", "test.png")));
		} catch (Exception e) {
			fail("Erreur : exception lancee\"");
		} 
	}	
	
	//Il n'existe aucun cas ou une CREATION de categorie renvoie FAUX
	
	//Fonction qui permet de tester tous les cas ou une MODIFICATION de categorie renvoie VRAI
	@Test	
	void testUpdateTrue() {		
			
		try {
			//on cree une categorie qui aura comme id 3
			categorie.create(new Categorie(3, "test", "test.png"));
			
			//L'unique cas ou la fonction renvoi vrai est quand on selectionne un id qui existe
			assertTrue(categorie.update(new Categorie(3,"testReussi","testReussi.png")));
		} catch (Exception e) {
			fail("exception lancee mais id client existant");
		} 		
	}
	
		
	//Fonction qui permet de tester tous les cas ou une MODIFICATION de categorie renvoie FAUX
	@Test
	void testUpdateFalse() {

		try {
			categorie.update(new Categorie(1000,"testEchec","testEchec.png"));//id 1000 n'existe pas
			fail("pas d'exception alors qu'Id inexistant");
		} catch (Exception e) {
			assertFalse(e.getMessage().isEmpty());

		}
	}
		
		
	//Fonction qui permet de tester tous les cas ou une SUPPRESION de categorie renvoie VRAI,= quand la cat�gorie existe (id)	
	@Test
	void testDeleteTrue() {
			
		try  {
		//comme on a vide la table avant le test, on cree une categorie  qui aura automatiquement 3 comme id 
			categorie.create(new Categorie(3,"test","test.png"));
			assertTrue(categorie.delete(new Categorie(3,"test","test.png")));
		} catch (Exception e) {
			fail("Exception lancee mais id categorie existant");
		} 
	}
		 
	
	//Fonction qui permet de tester tous les cas ou une SUPPRESSION de categorie renvoie FAUX = quand la categorie n'existe pas (id)	
	@Test
	void testDeleteFalse() {
			
		try {
			categorie.delete(new Categorie(-1000,"test","test.png")); //aucun id est negatif
			categorie.delete(new Categorie(150,"test","test.png")); //aucune categorie n'a un id=150
			fail("pas d'exception alors qu'id inexistant");			
		} catch (Exception e) {
			assertFalse( e.getMessage().isEmpty() );
		}
			
	}
		
		
	@Test
	void testGetByIdTrue() {
		
		try {
			//comme on a vide la table avant le test, on cree une categorie  qui aura automatiquement 3 comme id 
			categorie.create(new Categorie(3,"test","test.png"));
			categorie.getById(3);
		} 
		catch (Exception e) {
			fail("Exception lancee mais id categorie existant");
		}
	}
		
		
	@Test
	void testGetByIdFalse() {
		
		try {
			//On met une valeur qui n'existera jamais comme nos id sont >=1. de plus la table est vide
			categorie.getById(0); //aucun id est nul
			categorie.getById(-1000); //aucun id est negatif
			categorie.getById(150); //aucune categorie n'a un id=150
			fail("Exception non lancee mais id Categorie inexistant");
		} 
		catch (Exception e) {
			assertFalse( e.getMessage().isEmpty() );
		}
	}
	
		
	@Test
	void testFindAllTrue() {
		try {
			categorie.create(new Categorie(3,"test","test.png"));
			ArrayList<Categorie> listeCategorie = categorie.findAll();
			assertTrue(!listeCategorie.isEmpty());
		} catch (Exception e) {
			fail("La liste de categorie ne doit pas etre vide");
		}
	}
	
}


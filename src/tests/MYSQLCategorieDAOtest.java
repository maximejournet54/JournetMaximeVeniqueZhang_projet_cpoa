package tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import connexion.Persistance;
import dao.factory.DAOFactory;
import dao.modele.CategorieDAO;
import pojo.Categorie;

public class MYSQLCategorieDAOtest {
	CategorieDAO categorie = DAOFactory.getDAOFactory(Persistance.MYSQL).getCategorieDAO();
	
	//Fonction qui supprime toutes les lignes contenues dans la table SAUF la premiere
	@BeforeEach
	void testDeleteAll() {
		ArrayList<Categorie> listeCategorie = new ArrayList<Categorie>();
		
		try {
			listeCategorie = categorie.findAll();
			
			int i = 1;
			
			while(i < listeCategorie.size()) {
				categorie.delete(listeCategorie.get(i++));
			}
		} 
		catch (Exception e) {
		}
	}
	
	
	//Fonction qui permet de tester tous les cas ou une CREATION de categorie renvoie VRAI
	//Peut importe l'id rentre, le categorie est toujours cree avec succes
	@Test
	void testCreateTrue() {
		
		try {
			assertTrue(categorie.create(new Categorie(-1, "test1", "test1.png")));
			assertTrue(categorie.create(new Categorie(0, "test2", "test2.png")));
			assertTrue(categorie.create(new Categorie(1, "test3", "test3.png")));
		} 
		catch (Exception e) {
			fail("Erreur : exception lancee");
		}
	}
	
	
	//Fonction qui permet de tester tous les cas ou une MODIFICATION de categorie renvoie VRAI
	@Test
	void testUpdateTrue() {
		
		try {
			//L'unique cas ou la fonction renvoi vrai est quand on selectionne un id qui existe, ici le cas 2 garde grace a la fonction testDeleteAll()
			assertTrue(categorie.update(new Categorie(
					categorie.findAll().get(0).getId(), 
					"TestUpdateR", 
					"TestUpdateR")));
		} 
		catch (Exception e) {
			fail("exception lancee mais id categorie existant");
		}
	}
	
	
	//Fonction qui permet de tester tous les cas ou une MODIFICATION de categorie renvoie FAUX
	@Test
	void testUpdateFalse() {
		
		try {
			//La fonctionne renvoie soit VRAI soit une exception donc on on recupere l'exception est fait assertFalse car l'exception ne sera pas vide
			categorie.update(new Categorie(-1, "TestNomEchec", "TestPrenomEchec"));
			fail("exception lancee mais id categorie non existant");
			} 
		catch (Exception e) {
			assertFalse( e.getMessage().isEmpty() );
		}
	}
	
	//Fonction qui permet de tester tous les cas ou une SUPPRESSION de categorie renvoie FAUX
	@Test
	void testDeleteTrue() {
		try {
			//On cree un nouveau client
			Categorie categorieTest = new Categorie(2, "delete", "delete.png");
			categorie.create(categorieTest);
			
			//On cree une liste de tous les clients
			ArrayList<Categorie> listeCategorie = categorie.findAll();
			
			//on recupere le dernier item de la liste et on le supprime 
			Categorie categorieDelete = listeCategorie.get(listeCategorie.size()-1);
			assertTrue(categorie.delete(categorieDelete));
		}
		catch (Exception e) {
			fail("Exception lancee mais id categorie existant");
		}
	}
	
	@Test
	void testDeleteFalse() {
		
		try {
			//La fonctionne renvoie soit VRAI soit une exception donc on on recupere l'exception est fait assertFalse car l'exception ne sera pas vide
			categorie.delete(new Categorie(-1, "TestEchec", "TestEchec"));
			fail("exception non lancee mais id categorie non existant");
			} 
		catch (Exception e) {
			assertFalse( e.getMessage().isEmpty() );
		}
	}
	
	@Test
	void testGetByIdTrue() {
		
		try {
			//La seule valeur qui reste apres la methode DeleteAll 
			categorie.getById( categorie.findAll().get(categorie.findAll().size()-1).getId());
		} 
		catch (Exception e) {
			fail("Exception lancee mais id categorie existant");
		}
	}
	
	@Test
	void testGetByIdFalse() {
		
		try {
			//On met une valeur qui n'existera jamais 
			categorie.getById(-1);
			fail("Exception lancee mais id categorie inexistant");
		} 
		catch (Exception e) {
			assertFalse( e.getMessage().isEmpty() );
		}
	}
	
	
	@Test
	void testFindAll() {
		try {
			ArrayList<Categorie> listeCategorie = categorie.findAll();
			assertTrue(!listeCategorie.isEmpty());
		} catch (Exception e) {
			fail("La liste de categorie ne doit pas etre vide");
		}
	}
}

package tests;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import connexion.Persistance;
import dao.factory.DAOFactory;
import dao.modele.ClientDAO;
import pojo.Client;

public class MYSQLClientDAOtest {
	
	ClientDAO client = DAOFactory.getDAOFactory(Persistance.MYSQL).getClientDAO();
	
	//Fonction qui supprime toutes les lignes contenues dans la table SAUF la premiere
	@BeforeEach
	void testDeleteAll() {
		ArrayList<Client> listeClient = new ArrayList<Client>();
		
		try {
			listeClient = client.findAll();
			
			int i = 1;
			
			while(i < listeClient.size()) {
				client.delete(listeClient.get(i++));
			}
			
		} 
		catch (Exception e) {
		}
	}
	
	//Fonction qui permet de tester tous les cas ou une CREATION de client renvoie VRAI
	//Peut importe l'id rentre, le client est toujours cree avec succes
	@Test
	void testCreateTrue() {
		
		try {
			assertTrue(client.create(new Client(-1, "test1", "Luc")));
			assertTrue(client.create(new Client(0, "test2", "Luc")));
			assertTrue(client.create(new Client(1, "test3", "Luc")));
			assertTrue(client.create(new Client(1, "test4", "7894561230")));
		} 
		catch (Exception e) {
			fail("Erreur : exception lancee");
		}
	}
		
	//Il n'existe aucun cas ou une CREATION de client renvoie FAUX, sauf deconnection de la BDD
	
	//Fonction qui permet de tester tous les cas ou une MODIFICATION de client renvoie VRAI
	@Test
	void testUpdateTrue() {
		try {
			//L'unique cas ou la fonction renvoi vrai est quand on selectionne un id qui existe, ici le cas 2 garde grace a la fonction testDeleteAll()
			
			assertTrue(client.update(new Client( client.findAll().get(0).getId()
					, "TestUpdateNomReussi", 
					"TestUpdatePrenomReussi")));
		} 
		catch (Exception e) {
			fail("exception lancee mais id client existant");
		}
	}
	
	//Fonction qui permet de tester tous les cas ou une MODIFICATION de client renvoie FAUX
	@Test
	void testUpdateFalse() {
		
		try {
			//La fonctionne renvoie soit VRAI soit une exception donc on on recupere l'exception est fait assertFalse car l'exception ne sera pas vide
			client.update(new Client(-1, "TestNomEchec", "TestPrenomEchec"));
			fail("exception lancee mais id client non existant");
			} 
		catch (Exception e) {
			assertFalse( e.getMessage().isEmpty() );
		}
	}
	
	@Test
	void testDeleteTrue() {
		try {
			//On creer un nouveau client
			Client clientTest = new Client(1, "delete", "delete.png");
			client.create(clientTest);
			
			//On cree une liste de tous les clients
			ArrayList<Client> listeClient = client.findAll();
			
			//on recupere le dernier item de la liste et on le supprime 
			Client clientDelete = listeClient.get(listeClient.size()-1);
			assertTrue(client.delete(clientDelete));
		}
		catch (Exception e) {
			fail("Exception lancee mais id client existant");
		}
	}
	
	@Test
	void testDeleteFalse() {
		
		try {
			//La fonctionne renvoie soit VRAI soit une exception donc on on recupere l'exception est fait assertFalse car l'exception ne sera pas vide
			client.delete(new Client(-1, "TestNomEchec", "TestPrenomEchec"));
			fail("exception non lancee mais id client non existant");
			} 
		catch (Exception e) {
			assertFalse( e.getMessage().isEmpty() );
		}
	}
	
	@Test
	void testGetByIdTrue() {
		
		try {
			//La seule valeur qui reste apres la methode DeleteAll 
			client.getById( client.findAll().get(0).getId() );
		} 
		catch (Exception e) {
			fail("Exception lancee mais id Client existant");
		}
	}
	
	@Test
	void testGetByIdTrueFalse() {
		
		try {
			//On met une valeur qui n'existera jamais 
			client.getById(-1);
			fail("Exception lancee mais id Client inexistant");
		} 
		catch (Exception e) {
			assertFalse( e.getMessage().isEmpty() );
		}
	}
	
	
	@Test
	void testFindAll() {
		try {
			ArrayList<Client> listeClient = client.findAll();
			assertTrue(!listeClient.isEmpty());
		} catch (Exception e) {
			fail("La liste de client ne doit pas etre vide");
		}
	}
}
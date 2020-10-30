package tests;

import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import connexion.Persistance;
import dao.factory.DAOFactory;
import dao.modele.ClientDAO;
import pojo.Client;

class ClientTest {
	
	ClientDAO client = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getClientDAO(); 
	ArrayList<Client> listeClient = new ArrayList<Client>();
	
	//Fonction qui supprime toutes les lignes contenues dans la table avant chaque test 
	@BeforeEach
	void TestDeleteAll() { 
		try {
			listeClient = client.findAll();
			int i = 0; 
			
			while(i < listeClient.size()) {
				client.delete(listeClient.get(i++)); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	
	@AfterEach
	void Reinitialize() {
		TestDeleteAll(); 
	}

	
	//Fonction qui permet de tester tous les cas ou une CREATION de client renvoie VRAI
	//Peut importe l'id rentre, le client est toujours cree avec succes
	@Test
	void testCreateTrue() {
		
		try {
			assertTrue(client.create(new Client(-1, "THIL", "Claire")));
			assertTrue(client.create(new Client(1, "THIL", "Claire")));
			assertTrue(client.create(new Client(0, "THIL", "Claire")));
		} catch (Exception e) {
			fail("Erreur : exception lancee\"");
		} 
	}
	
	//Il n'existe aucun cas ou une CREATION de client renvoie FAUX
	
	//Fonction qui permet de tester tous les cas ou une MODIFICATION de client renvoie VRAI = id existe
	@Test
	void testUpdateTrue() {
		
		try {
			client.create(new Client(2, "THIL", "Claire"));
			//L'unique cas ou la fonction renvoi vrai est quand on selectionne un id qui existe
			assertTrue(client.update(new Client(2, "BRUNGARD", "Luc")));
			} catch (Exception e) {
				fail("exception lancee mais id client existant");
		} 		
	}
	
	
	@Test
	void testUpdateFalse() {
		
		try {
			client.update(new Client(1000, "BRUNGARD", "Luc"));//id 1000 n'existe pas
			fail("pas d'exception alors qu'Id inexistant");
		} catch (Exception e) {
			assertFalse(e.getMessage().isEmpty());

		}
	}
	
	
	
	@Test
	void testDeleteTrue() {
		
		try  {
		//comme on a vide la table avant le test, on cree un client  qui aura automatiquement 2 comme id 
		client.create(new Client(2,"test", "test"));
		assertTrue(client.delete(new Client(2, "THIL", "Claire")));
		} catch (Exception e) {
			fail("Exception lancee mais id client existant");
		} 
	}
	 
	
	@Test
	void testDeleteFalse() {
		
		try {
			client.delete(new Client(-1000, "THIL", "Claire")); //aucun client a un id=-1000
			fail("pas d'exception alors qu'id inexistant");
		} catch (Exception e) {
			assertFalse( e.getMessage().isEmpty() );
		}
		
	}
	
	
	@Test
	void testGetByIdTrue() {
		
		try {
			//comme on a vide la table avant le test, on cree un client  qui aura automatiquement 2 comme id 
			client.create(new Client(2,"THIL", "Claire"));
			client.getById(2);
		} 
		catch (Exception e) {
			fail("Exception lancee mais id Client existant");
		}
	}
	
	
	@Test
	void testGetByIdFalse() {
		
		try {
			//On met une valeur qui n'existera jamais comme nos id commence a 1 et que la table est vide
			client.getById(0);
			client.getById(150); //aucun client a un id = 150
			fail("Exception non lancee mais id Client inexistant");
		} 
		catch (Exception e) {
			assertFalse( e.getMessage().isEmpty() );
		}
	}
	
	
	@Test
	void testFindAllTrue() {
		try {
			client.create(new Client(2,"THIL", "Claire"));
			ArrayList<Client> listeClient = client.findAll();
			assertTrue(!listeClient.isEmpty());
		} catch (Exception e) {
			fail("La liste de client ne doit pas etre vide");
		}
	}
	
}
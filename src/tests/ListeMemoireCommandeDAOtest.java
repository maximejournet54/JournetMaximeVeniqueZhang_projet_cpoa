package tests;

import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import connexion.Persistance;
import dao.factory.DAOFactory;
import dao.modele.CommandeDAO;
import pojo.Client;
import pojo.Commande;
import pojo.LigneCommande;
import pojo.Produit;

public class ListeMemoireCommandeDAOtest {
	
	CommandeDAO commande = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getCommandeDAO();
	ArrayList<Commande> listeCommande = new ArrayList<Commande>(); 
	
	
	//on initialise les objets dont on aura besoin dans les tests 
		@BeforeAll
		static void initialize() {
			try {
				DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getClientDAO().create(new Client(2,"THIL", "Claire"));
			} catch (Exception e) {
			}
		}
		
	//Fonction qui supprime toutes les lignes contenues dans la table 
	@BeforeEach
	void testDeleteAll() {
		
		try {
			listeCommande = commande.findAll();
			int i = 0; 
				
			while(i < listeCommande.size()) {
				commande.delete(listeCommande.get(i++)); 
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
			DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getClientDAO().delete(new Client(2,"THIL", "Claire"));
		} catch (Exception e) {
		}
	}
	
	
	//Fonction qui permet de tester tous les cas ou une CREATION de commande renvoie VRAI
	//Peut importe l'id rentre, la commande est toujours cree avec succes
	@Test
	void testCreateTrue() {			
		
		//on cree d'abord la hashmap composee d'un produit et d'une ligne commande
		HashMap<Produit, LigneCommande> lc = new HashMap<Produit, LigneCommande>(); 
		lc.put(new Produit(12, "Dall", "Joyeux Noel avec nos petits lutins dansants !", (float)35, "bonnet1.png", 2), new LigneCommande(2,12,4,35));
			
		try {
			assertTrue(commande.create(new Commande(-1, LocalDate.of(2020, Month.OCTOBER, 8), 2,lc)));
			assertTrue(commande.create(new Commande(0, LocalDate.of(2020, Month.OCTOBER, 8), 2,lc)));
			assertTrue(commande.create(new Commande(1, LocalDate.of(2020, Month.OCTOBER, 8), 2,lc)));
		} catch (Exception e) {
			fail("Erreur : exception lancee\"");
		} 
	}	
	
	
	//On ne peut pas creer de commande si le client n'existe pas
	@Test
	void testCreateFalse() {	
		
		//on cree d'abord la hashmap composee d'un produit et d'une ligne commande
		HashMap<Produit, LigneCommande> lc = new HashMap<Produit, LigneCommande>(); 
		lc.put(new Produit(12, "Dall", "Joyeux Noel avec nos petits lutins dansants !", (float)35, "bonnet1.png", 2), new LigneCommande(2,12,4,35));
		
		try {
			commande.create(new Commande(-1, LocalDate.of(2020, Month.OCTOBER, 8), -100,lc));
			commande.create(new Commande(1, LocalDate.of(2020, Month.OCTOBER, 8), 1,lc));//aucun client a un id=1 apres TestDeleteAll(); 
		} catch (Exception e) {
			fail("id client inexistant");
			assertFalse(e.getMessage().isEmpty());
		} 
	}	
	
	
	//On peut modifier une commande seulement si elle existe 
	@Test
	void testUpdateTrue(){	
		
		//on cree d'abord la hashmap composee d'un produit et d'une ligne commande
		HashMap<Produit, LigneCommande> lc = new HashMap<Produit, LigneCommande>(); 
		lc.put(new Produit(12, "Dall", "Joyeux Noel avec nos petits lutins dansants !", (float)35, "bonnet1.png", 2), new LigneCommande(2,12,4,35));
		
		try {
			//on cree une commande qui aura 3 comme id (definit dans ListeMemoire)
			commande.create(new Commande(3, LocalDate.of(2020, Month.OCTOBER, 8), 2,lc));
			assertTrue(commande.update(new Commande(3, LocalDate.of(2020, Month.OCTOBER, 9), 2,lc))); //on modifie la date
		} catch (Exception e) {
			fail("exception lanc�e mais id client existant");
		} 
	}
	
	
	//On ne peut pas modifier la commande car id existe pas 
	@Test
	void testUpdateFalse(){	
		
		//on cree d'abord la hashmap composee d'un produit et d'une ligne commande
		HashMap<Produit, LigneCommande> lc = new HashMap<Produit, LigneCommande>(); 
		lc.put(new Produit(12, "Dall", "Joyeux Noel avec nos petits lutins dansants !", (float)35, "bonnet1.png", 2), new LigneCommande(2,12,4,35));
		
		try {
			//on cree une commande qui aura 3 comme id (definit dans ListeMemoire)
			commande.create(new Commande(3, LocalDate.of(2020, Month.OCTOBER, 8), 2,lc));
			commande.update(new Commande(150, LocalDate.of(2020, Month.OCTOBER, 9), 2,lc)); //on essaye de modifier la date d'une commande inexistante
			commande.update(new Commande(-1, LocalDate.of(2020, Month.OCTOBER, 9), 2,lc)); //on essaye de modifier la date d'une commande inexistante
			fail("commande inexistante");
		} catch (Exception e) {
			assertFalse(e.getMessage().isEmpty());
		} 
	}
	
	
	//Fonction qui permet de tester tous les cas ou une SUPPRESSION de commande renvoie VRAI = id existe
	@Test
	void testDeleteTrue() {
		
		//on cree d'abord la hashmap composee d'un produit et d'une ligne commande
		HashMap<Produit, LigneCommande> lc = new HashMap<Produit, LigneCommande>(); 
		lc.put(new Produit(12, "Dall", "Joyeux Noel avec nos petits lutins dansants !", (float)35, "bonnet1.png", 2), new LigneCommande(2,12,4,35));
		
		try  {
		//comme on a vide la table avant le test, on cree un produit  qui aura automatiquement 3 comme id 
		commande.create(new Commande(3, LocalDate.of(2020, Month.OCTOBER, 8), 2,lc));
		assertTrue(commande.delete(new Commande(3, LocalDate.of(2020, Month.OCTOBER, 8), 2,lc)));
		} catch (Exception e) {
			fail("Exception lancee mais id commande existant");
		} 
	}
	 
	
	//Fonction qui permet de tester tous les cas ou une SUPPRESSION de commande renvoie FAUX
	@Test
	void testDeleteFalse() {
		
		//on cree d'abord la hashmap composee d'un produit et d'une ligne commande
		HashMap<Produit, LigneCommande> lc = new HashMap<Produit, LigneCommande>(); 
		lc.put(new Produit(12, "Dall", "Joyeux Noel avec nos petits lutins dansants !", (float)35, "bonnet1.png", 2), new LigneCommande(2,12,4,35));
		
		try {
			commande.delete(new Commande(-1, LocalDate.of(2020, Month.OCTOBER, 9), 2,lc));
			fail("pas d'exception alors qu'id inexistant");
		} catch (Exception e) {
			assertFalse( e.getMessage().isEmpty() );
		}
		
	}
	
	
	@Test
	void testGetByIdTrue() {
		
		try {
			//comme on a vide la table avant le test, on cree une commande qui aura automatiquement 3 comme id 
			HashMap<Produit, LigneCommande> lc = new HashMap<Produit, LigneCommande>(); 
			lc.put(new Produit(12, "Dall", "Joyeux Noel avec nos petits lutins dansants !", (float)35, "bonnet1.png", 2), new LigneCommande(2,12,4,35));
			commande.create(new Commande(3, LocalDate.of(2020, Month.OCTOBER, 8), 2,lc));;
			commande.getById(3);
		} 
		catch (Exception e) {
			fail("Exception lancee mais id commande existant");
		}
	}
	
	
	@Test
	void testGetByIdFalse() {
		
		try {
			//On met une valeur qui n'existera jamais comme nos id commence a 1 et que la table est vide
			commande.getById(-1);
			commande.getById(150); //aucun id commande=150
			fail("id Commande inexistant");
		} 
		catch (Exception e) {
			assertFalse( e.getMessage().isEmpty() );
		}
	}
	
	@Test
	void testFindAllTrue() {
		
		//on cree d'abord la hashmap composee d'un produit et d'une ligne commande
				HashMap<Produit, LigneCommande> lc = new HashMap<Produit, LigneCommande>(); 
				lc.put(new Produit(12, "Dall", "Joyeux Noel avec nos petits lutins dansants !", (float)35, "bonnet1.png", 2), new LigneCommande(2,12,4,35));
				
		try {
			commande.create(new Commande(3, LocalDate.of(2020, Month.OCTOBER, 8), 2,lc));
			ArrayList<Commande> listeCommande = commande.findAll();
			assertTrue(!listeCommande.isEmpty());
		} catch (Exception e) {
			fail("La liste de commande ne doit pas etre vide");
		}
	}

}

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
import dao.modele.LigneCommandeDAO;
import pojo.Commande;
import pojo.LigneCommande;
import pojo.Produit;

public class ListeMemoireLigneComandeDAOtest {
	
	LigneCommandeDAO<LigneCommande> ligneCommande = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getLigneCommandeDAO();
	ArrayList<LigneCommande> listeLigneCommande = new ArrayList<LigneCommande>();
	
	//on initialise les objets dont on aura besoin dans les tests 
	@BeforeAll
	static void initialize() {
		
		try {
			//on cree nos produits avec comme id respectif 13,14,15
			DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getProduitDAO().create(new Produit(13, "Sonic te kiffe", "Inspire par la saga Saga (c''est plus fort que toi !), un pull 100% gamer qui te permettra de faire baver d''envie tes petits camarades de jeu.", (float)41.5, "pull1.png", 1));
			DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getProduitDAO().create(new Produit(14, "La chaleur des rennes", "Classique mais efficace, un bonnet dont l'elegance n''est pas a souligner, il vous grattera comme il faut !", (float)15, "bonnet0.png", 2)); 
			DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getProduitDAO().create(new Produit(15, "Dall", "Joyeux Noel avec nos petits lutins dansants !", (float)35, "bonnet1.png", 2)); 
			
			//on cree 2 commandes avec comme id respectif 3 et 4
			HashMap <Produit, LigneCommande> ligneCommande1 = new HashMap<Produit, LigneCommande>(); 
			ligneCommande1.put(new Produit(13, "Sonic te kiffe", "Inspire par la saga Saga (c''est plus fort que toi !), un pull 100% gamer qui te permettra de faire baver d''envie tes petits camarades de jeu.", (float)41.5, "pull1.png", 1), new LigneCommande(1,2,2,(float)41.5));
			ligneCommande1.put(new Produit(14, "La chaleur des rennes", "Classique mais efficace, un bonnet dont l'elegance n''est pas a souligner, il vous grattera comme il faut !", (float)15, "bonnet0.png", 2), new LigneCommande(1,6,1,15));
			DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getCommandeDAO().create(new Commande(3, LocalDate.of(2020, Month.SEPTEMBER, 02),1,ligneCommande1)); 
			
			HashMap <Produit, LigneCommande> ligneCommande2 = new HashMap<Produit, LigneCommande>(); 
			ligneCommande2.put(new Produit(15, "Dall", "Joyeux Noel avec nos petits lutins dansants !", (float)35, "bonnet1.png", 2), new LigneCommande(4,15,4,35));
			DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getCommandeDAO().create(new Commande(4, LocalDate.of(2020, Month.AUGUST, 30),1,ligneCommande2)); 
		} catch (Exception e) {
		}
	}
	
	//Fonction qui supprime toutes les lignes contenues dans la table 
	@BeforeEach
	void testDeleteAll() {
		
		try {
			listeLigneCommande = ligneCommande.findAll();
			int i = 0; 
				
			while(i < listeLigneCommande.size()) {
				ligneCommande.delete(listeLigneCommande.get(i++)); 
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
			DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getProduitDAO().delete(new Produit(13, "Sonic te kiffe", "Inspire par la saga Saga (c''est plus fort que toi !), un pull 100% gamer qui te permettra de faire baver d''envie tes petits camarades de jeu.", (float)41.5, "pull1.png", 1));
			DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getProduitDAO().delete(new Produit(14, "La chaleur des rennes", "Classique mais efficace, un bonnet dont l'elegance n''est pas a souligner, il vous grattera comme il faut !", (float)15, "bonnet0.png", 2)); 
			DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getProduitDAO().delete(new Produit(15, "Dall", "Joyeux Noel avec nos petits lutins dansants !", (float)35, "bonnet1.png", 2)); 
			
			//on cree 2 commandes avec comme id respectif 3 et 4
			HashMap <Produit, LigneCommande> ligneCommande1 = new HashMap<Produit, LigneCommande>(); 
			ligneCommande1.put(new Produit(13, "Sonic te kiffe", "Inspire par la saga Saga (c''est plus fort que toi !), un pull 100% gamer qui te permettra de faire baver d''envie tes petits camarades de jeu.", (float)41.5, "pull1.png", 1), new LigneCommande(1,2,2,(float)41.5));
			ligneCommande1.put(new Produit(14, "La chaleur des rennes", "Classique mais efficace, un bonnet dont l'elegance n''est pas a souligner, il vous grattera comme il faut !", (float)15, "bonnet0.png", 2), new LigneCommande(1,6,1,15));
			DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getCommandeDAO().delete(new Commande(3, LocalDate.of(2020, Month.SEPTEMBER, 02),1,ligneCommande1)); 
			
			HashMap <Produit, LigneCommande> ligneCommande2 = new HashMap<Produit, LigneCommande>(); 
			ligneCommande2.put(new Produit(15, "Dall", "Joyeux Noel avec nos petits lutins dansants !", (float)35, "bonnet1.png", 2), new LigneCommande(4,15,4,35));
			DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getCommandeDAO().delete(new Commande(4, LocalDate.of(2020, Month.AUGUST, 30),1,ligneCommande2)); 
		} catch (Exception e) {
		}
	}
	
	
	
	//Fonction qui permet de tester tous les cas ou une CREATION de LigneCommande renvoie VRAI
	//Pour que la creation marche, il faut que la commande existe, que le produit existe et qu'aucune LigneCommande est deja composee de cet id commande et de cet id produit
	@Test
	void testCreateTrue() {			
		try {
			assertTrue(ligneCommande.create(new LigneCommande(3,13,1,15)));
		} catch (Exception e) {
			fail("Erreur : exception lancee\"");
		} 
	}	
	
	
	//Fonction qui permet de tester tous les cas ou une CREATION de LigneCommande renvoie FAUX
	@Test
	void testCreateFalse() {	
		
		try {
			ligneCommande.create(new LigneCommande(4,15,4,35));
			ligneCommande.create(new LigneCommande(4,15,4,35)); //ligne commande deja existante(cree juste au dessus)
			ligneCommande.create(new LigneCommande(100,15,4,35)); //id commande n'existe pas
			ligneCommande.create(new LigneCommande(4,100,4,35)); //produit n'existe pas
			fail("commande ou produit n'existe pas, ou ligne commande d�j� existante");
		} catch (Exception e) {
			assertFalse(e.getMessage().isEmpty());
		} 
	}
	
	
	//Fonction qui permet de tester tous les cas ou une MODIFICATION de LigneCommande renvoie VRAI = id existe
		@Test
		void testUpdateTrue() {	
			
			try {
				ligneCommande.create(new LigneCommande(4,14,4,15)); 
				ligneCommande.update(new LigneCommande(4,14,5,15)); //on modifie la quantite
			} catch (Exception e) {
				fail("Exception lanc�e mais ligne commande existante");
			} 
		}
		
	//Fonction qui permet de tester tous les cas ou une MODIFICATION de LigneCommande renvoie FAUX = id n'existe pas
	@Test
	void testUpdateFalse() {	
		
		try {
			ligneCommande.update(new LigneCommande(-10,-10,5,35));
			ligneCommande.update(new LigneCommande(5,18,5,35)); //aucune ligne commande n'a un idCommande=5 et un idProduit=18
			fail("aucune ligneCommande n'est associ� � cette commande et ce produit");
		} catch (Exception e) {
			assertFalse(e.getMessage().isEmpty());
		} 
	}
	
	
	//Fonction qui permet de tester tous les cas ou une SUPPRESSION de commande renvoie VRAI = quand l'id existe
		@Test
		void testDeleteTrue() {
					
			try  {
			//comme on a vide la table avant le test, on cree une ligne commande
			ligneCommande.create(new LigneCommande(4,14,4,15)); 
			assertTrue(ligneCommande.delete(new LigneCommande(4,14,4,15)));
			} catch (Exception e) {
				fail("Exception lancee mais id commande existant");
			} 
		}
		
	//Fonction qui permet de tester tous les cas ou une SUPPRESSION de commande renvoie FAUX = quand l'id existe pas
		@Test
		void testDeleteFalse() {
						
			try {
				ligneCommande.delete(new LigneCommande(-1,-1,4,15));
				ligneCommande.delete(new LigneCommande(5,18,5,35)); //aucune ligne commande n'a un idCommande=5 et un idProduit=18
				fail("pas d'exception alors qu'id inexistant");
			} catch (Exception e) {
				assertFalse( e.getMessage().isEmpty() );
			}
			
		}
	
	@Test
	void testGetByIdTrue() {
		
		try {
			//comme on a vide la table avant le test, on cree une LignCommande
			ligneCommande.create(new LigneCommande(4,15,4,35));;
			ligneCommande.getById(4,15);
		} 
		catch (Exception e) {
			fail("Exception lancee mais ligne commande existante");
		}
	}
	
	
	@Test
	void testGetByIdFalse() {
		
		try {
			//On met une valeur qui n'existera jamais comme nos id commence a 1 et que la table est vide
			ligneCommande.getById(-1,-1);
			ligneCommande.getById(5,18); //aucune ligne commande n'a un idCommande=5 et un idProduit=18
			fail("Ligne commande inexistante");
		} 
		catch (Exception e) {
			assertFalse( e.getMessage().isEmpty() );
		}
	}

	@Test
	void testFindAllTrue() {
		try {
			ligneCommande.create(new LigneCommande(4,15,4,35));
			ArrayList<LigneCommande> listeLigneCommande = ligneCommande.findAll();
			assertTrue(!listeLigneCommande.isEmpty());
		} catch (Exception e) {
			fail("La liste de ligneCommande ne doit pas etre vide");
		}
	}
	
}

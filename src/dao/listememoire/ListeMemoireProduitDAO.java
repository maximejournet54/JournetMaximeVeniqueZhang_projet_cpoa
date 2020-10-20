package dao.listememoire;

import java.util.ArrayList;
import java.util.List;

import connexion.Persistance;
import dao.factory.DAOFactory;
import dao.modele.ProduitDAO;
import pojo.Categorie;
import pojo.Produit;

public class ListeMemoireProduitDAO implements ProduitDAO {
    private static ListeMemoireProduitDAO instance;
	private List<Produit> donnees;

	public static ListeMemoireProduitDAO getInstance() {
		if (instance == null) {
			instance = new ListeMemoireProduitDAO();
		}
		return instance;
	}

	private ListeMemoireProduitDAO() {
		this.donnees = new ArrayList<Produit>();
		this.donnees.add(new Produit(2, "Sonic te kiffe", "Inspire par la saga Saga (c''est plus fort que toi !), un pull 100% gamer qui te permettra de faire baver d''envie tes petits camarades de jeu.", (float)41.5, "pull1.png", 1));
		this.donnees.add(new Produit(6, "La chaleur des rennes", "Classique mais efficace, un bonnet dont l'elegance n''est pas a souligner, il vous grattera comme il faut !", (float)15, "bonnet0.png", 2)); 
		this.donnees.add(new Produit(12, "Dall", "Joyeux Noel avec nos petits lutins dansants !", (float)35, "bonnet1.png", 2)); 
	}

	@Override
	public boolean create(Produit objet) {
		objet.setId(3);
		List<Categorie> listeCateg = new ArrayList<Categorie>();
		try {
			listeCateg = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getCategorieDAO().findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (listeCateg.contains(new Categorie(objet.getIdCateg(), "test", "test.png"))) {
			
			// Ne fonctionne que si l'objet metier est bien fait...
			while (this.donnees.contains(objet)) {

				objet.setId(objet.getId() + 1);
			}
			this.donnees.add(objet);
			
		} 
		else {
			throw new IllegalArgumentException("La categorie selectionnee n'existe pas");
		}
		
		return true;
	}

	@Override
	public boolean update(Produit objet) {
		List<Categorie> listeCateg = new ArrayList<Categorie>();
		try {
			listeCateg = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getCategorieDAO().findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (listeCateg.contains(new Categorie(objet.getIdCateg(), "test", "test.png"))) {
			
			// Ne fonctionne que si l'objet metier est bien fait...
			int idx = this.donnees.indexOf(objet);
			if (idx == -1) {
				throw new IllegalArgumentException("Tentative de modification d'un produit inexistant");
			} else {
				
				this.donnees.set(idx, objet);
			}
		}
		else {
			throw new IllegalArgumentException("La categorie selectionnee n'existe pas");
		}
		
		return true;
	}

	@Override
	public boolean delete(Produit objet) {
		// Ne fonctionne que si l'objet métier est bien fait...
		boolean idx = this.donnees.remove(objet);
		if (!idx) 
			throw new IllegalArgumentException("Tentative de suppression d'une commande inexistante");	
		return idx;
	}

	@Override
	public Produit getById(int id) {
		// Ne fonctionne que si l'objet métier est bien fait...
		int idx = this.donnees.indexOf(new Produit(id, "test", "test",1, "test.png", 1));
		if (idx == -1) {
			throw new IllegalArgumentException("Aucune produit ne possède cet identifiant");
		} else {
			return this.donnees.get(idx);
		}
	}

	@Override
	public ArrayList<Produit> findAll() {
		return (ArrayList<Produit>) this.donnees;
	}
}

package dao.modele;

import dao.DAO;
import pojo.Commande;

public interface CommandeDAO extends DAO<Commande>{
	public abstract Commande getById(int id) throws Exception;
}

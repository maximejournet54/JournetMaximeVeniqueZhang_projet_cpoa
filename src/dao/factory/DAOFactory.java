package dao.factory;

import connexion.Persistance;
import dao.modele.*;
import pojo.LigneCommande;


public abstract class DAOFactory {
    public static DAOFactory getDAOFactory(Persistance cible) {
        DAOFactory daoF = null;
        switch (cible) {
            case MYSQL:
                daoF = new MYSQLFactoryDAO();
                break;
            case LISTE_MEMOIRE:
                daoF = new ListeMemoireDAOFactory();
                break;
        }
        return daoF;
        }

    public abstract ClientDAO getClientDAO();
	public abstract ProduitDAO getProduitDAO();
	public abstract CategorieDAO getCategorieDAO();
	public abstract CommandeDAO getCommandeDAO();
	public abstract LigneCommandeDAO<LigneCommande> getLigneCommandeDAO();
    
}

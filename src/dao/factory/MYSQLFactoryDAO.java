package dao.factory;

import dao.modele.*;
import dao.modele.CategorieDAO;
import dao.modele.ClientDAO;
import dao.modele.ProduitDAO;
import dao.mysql.*;
import pojo.LigneCommande;

public class MYSQLFactoryDAO extends DAOFactory{

    @Override
	public ClientDAO getClientDAO() { 
        return MYSQLClientDAO.getInstance(); 
    }
	
	@Override
	public ProduitDAO getProduitDAO() { 
        return MYSQLProduitDAO.getInstance(); 
    }

	@Override
	public CategorieDAO getCategorieDAO() {	
        return MYSQLCategorieDAO.getInstance(); 
    }
	
	@Override
	public CommandeDAO getCommandeDAO() { 
        return MYSQLCommandeDAO.getInstance(); 
    }
	
	@Override
    public LigneCommandeDAO<LigneCommande> getLigneCommandeDAO() {	
        return MYSQLLigneCommandeDAO.getInstance();
    }
}



package org.ace.accounting.expense.DAO;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.accounting.expense.Entity.ExpenseUser;
import org.ace.accounting.expense.IDAO.IExpenseUserDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("ExpenseUserDAO")
public class ExpenseUserDAO extends BasicDAO implements IExpenseUserDAO{

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public ExpenseUser find(String name) throws DAOException {
		ExpenseUser result = null;
		try {
			Query q = em.createQuery("SELECT u FROM ExpenseUser u WHERE u.name = :name");
			q.setParameter("name", name);
			result = (ExpenseUser) q.getSingleResult();
			em.flush();
		} catch (NoResultException pe) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find User(Username = " + name + ")", pe);
		}
		return result;
	}
	

	
	
}

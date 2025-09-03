package org.ace.accounting.expense.DAO;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.accounting.expense.Entity.Category;
import org.ace.accounting.expense.Entity.Expense;
import org.ace.accounting.expense.IDAO.IExpenseDAO;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository(value = "ExpenseDAO")
public class ExpenseDAO extends BasicDAO implements IExpenseDAO{

	@Override
	public Boolean deleteExpenseDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Category> findCategoryList() throws DAOException{
		// TODO Auto-generated method stub
		try {
			Query q = em.createQuery("select * from Category c");
			return q.getResultList();
		} catch (PersistenceException e) {
			throw translate("Failed to get Category", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveCategory(Category c) {
		try {
			em.persist(c);
		} catch (PersistenceException e) {
			throw translate("Failed to save Category", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveExpense(Expense expense){
		// TODO Auto-generated method stub
		try {
			em.persist(expense);
		} catch (PersistenceException e) {
			// TODO: handle exception
			throw translate("Failed to save Expense", e);
		}
	}

}

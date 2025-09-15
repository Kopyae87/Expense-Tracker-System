package org.ace.accounting.expense.DAO;

import java.util.List;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import org.ace.accounting.expense.Entity.Category;
import org.ace.accounting.expense.Entity.Expense;
import org.ace.accounting.expense.IDAO.IExpenseDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository(value = "ExpenseDAO")
public class ExpenseDAO extends BasicDAO implements IExpenseDAO{

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Boolean deleteExpense(Expense expense) {
		Boolean deletesuccess = false;
		try {
			expense = em.merge(expense);
			em.remove(expense);
//			em.flush();
			deletesuccess = true;
		} catch (PersistenceException e) {
			throw translate("Failed to delete expense", e);
		}
		return deletesuccess;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Category> findCategoryList() throws DAOException{
		// TODO Auto-generated method stub
		List<Category> categoryresult = null;
		try {
			TypedQuery<Category> q = em.createQuery("select c from Category c", Category.class);
			categoryresult =  q.getResultList();
		} catch (PersistenceException e) {
			throw translate("Failed to get Category", e);
		}
		return categoryresult;
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

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Expense> findAllExpense(String userid) {
		// TODO Auto-generated method stub
		List<Expense> result = null;
		try {
			TypedQuery<Expense> q = em.createQuery("select e from Expense e LEFT JOIN FETCH e.category where e.user.id = :userid",Expense.class);
			q.setParameter("userid", userid);
			result = q.getResultList();
		} catch (PersistenceException e) {
			// TODO: handle exception
			throw translate("Failed to find Expenses", e);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Boolean updateExpense(Expense currentExpense) {
		try {
			em.merge(currentExpense);
		} catch (PersistenceException e) {
			// TODO: handle exception
			throw translate("Failed to update Expense", e);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Expense> findLatestTenExpenses(String userid) {
		List<Expense> expenseList = null;
		try {
			TypedQuery<Expense> q = em.createQuery("select e from Expense e where e.user.id = :userid order by e.basicEntity.createdDate desc",Expense.class);
			q.setParameter("userid", userid);
			expenseList = q.setMaxResults(10).getResultList();
		} catch (PersistenceException e) {
			throw translate("Failed to update Expense", e);
		}
		return expenseList;
	}

	
}

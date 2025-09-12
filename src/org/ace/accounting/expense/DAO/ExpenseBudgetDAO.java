package org.ace.accounting.expense.DAO;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.ace.accounting.expense.Entity.Budget;
import org.ace.accounting.expense.IDAO.IExpenseBudgetDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("ExpenseBudgetDAO")
public class ExpenseBudgetDAO extends BasicDAO implements IExpenseBudgetDAO {

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Budget> findAllBudgetsByUserId(String userid) {
		try {
			TypedQuery<Budget> q = em.createQuery("select b from Budget b where b.user.id = :userid", Budget.class);
			q.setParameter("userid", userid);
			return q.getResultList();
		} catch (PersistenceException e) {
			throw translate("Can't find Budgets", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveBudget(Budget currentbudget) {
		try {
			em.persist(currentbudget);
		} catch (PersistenceException e) {
			throw translate("Can't save Budget", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateBuget(Budget currentbudget) {
		try {
			Budget existBudget = em.find(Budget.class, currentbudget.getId());
			if(existBudget != null) {
				if (currentbudget.getMonthlyLimit() != null) {
					existBudget.setMonthlyLimit(currentbudget.getMonthlyLimit());
				}
				if (currentbudget.getYearlyLimit() != null) {
					existBudget.setYearlyLimit(currentbudget.getYearlyLimit());
				}
				existBudget.setDescription(currentbudget.getDescription());
				existBudget.setMonthScope(currentbudget.getMonthScope());
				existBudget.setYearScope(currentbudget.getYearScope());
				existBudget.setCategory(currentbudget.getCategory());
				existBudget.setUser(currentbudget.getUser());
				em.merge(existBudget);
			}
		} catch (PersistenceException e) {
			throw translate("Can't update Budget", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteBudget(Budget currentbudget) {
		try {
			em.remove(em.merge(currentbudget));
		} catch (PersistenceException e) {
			throw translate("Can't delete Budget", e);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Budget findBudget(Budget currentbudget) {
		try {
			StringBuilder str = new StringBuilder("select b from Budget b where b.category.id = :id"
					+ " and b.yearScope = :yearscope" + " and b.user.id = :userid");
			if (currentbudget.getMonthlyLimit() != null) {
				str.append(" and b.monthlyLimit is not null");
			}  
			if (currentbudget.getYearlyLimit() != null) {
				str.append(" and b.yearlyLimit is not null");
			}
			if(currentbudget.getMonthScope() != null) {
				str.append(" and b.monthScope = :monthscope");
			}
			TypedQuery<Budget> q = em.createQuery(str.toString(), Budget.class);
			q.setParameter("id", currentbudget.getCategory().getId());
			q.setParameter("yearscope", currentbudget.getYearScope());
			q.setParameter("userid", currentbudget.getUser().getId());
			
			if(currentbudget.getMonthScope() != null) {
				q.setParameter("monthscope", currentbudget.getMonthScope());
			}
			
			List<Budget> li = q.getResultList();
			if (!li.isEmpty()) {
				return li.get(0);
			}
			return null;
		} catch (PersistenceException e) {
			throw translate("Something went wrong with finding Budget with categroy id and period", e);
		}
	}

}

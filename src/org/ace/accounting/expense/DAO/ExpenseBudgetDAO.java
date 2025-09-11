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
	public Budget findCategory(String categoryid, String timevalue, String userid, Integer scope) {
		try {
			StringBuilder str = new StringBuilder("select b from Budget b where b.category.id = :id"
					+ " and b.yearScope = :scope" + " and b.user.id = :userid");
			if ("monthly".equals(timevalue)) {
				str.append(" and b.monthlyLimit is not null");
			} else {
				str.append(" and b.yearlyLimit is not null");
			}
			TypedQuery<Budget> q = em.createQuery(str.toString(), Budget.class);
			q.setParameter("id", categoryid);
			q.setParameter("scope", scope);
			q.setParameter("userid", userid);
			List<Budget> li = q.getResultList();
			if (!li.isEmpty()) {
				return li.get(0);
			}
			return null;
		} catch (PersistenceException e) {
			throw translate("Something went wrong with finding Budget with categroy id and period", e);
		}

	}

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
	public void saveBudget(Budget currentbudget, String timevalue) {
		try {
			Budget existBudget = findCategory(currentbudget.getCategory().getId(), timevalue,
					currentbudget.getUser().getId(), currentbudget.getYearScope());
			if (existBudget != null) {
				if ("monthly".equals(timevalue)) {
					existBudget.setMonthlyLimit(currentbudget.getMonthlyLimit());
				} else {
					existBudget.setYearlyLimit(currentbudget.getYearlyLimit());
				}
				existBudget.setDescription(currentbudget.getDescription());
				em.merge(existBudget);
			}else {
				em.persist(currentbudget);
			}
		} catch (PersistenceException e) {
			throw translate("Can't save Budget", e);
		}
	}

}

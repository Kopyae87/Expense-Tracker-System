package org.ace.accounting.expense.DAO;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.ace.accounting.expense.IDAO.IExpenseChartDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("ExpenseChartDAO")
public class ExpenseChartDAO extends BasicDAO implements IExpenseChartDAO {

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Object[]> getDailyExpense(String selectedCategoryId, String userId, int month, int year) {
		List<Object[]> result = null;
		try {
			String str = "select function('DAY', e.expenseDate), sum(e.amount) "
					+ "from Expense e where e.category.id = :categoryId and e.user.id = :userId "
					+ "and function('MONTH', e.expenseDate) = :month "
					+ "and function('YEAR', e.expenseDate) = :year "
					+ "group by function('DAY', e.expenseDate) "
					+ "order by function('DAY', e.expenseDate)";
			TypedQuery<Object[]> q = em.createQuery(str, Object[].class);
			q.setParameter("categoryId", selectedCategoryId);
			q.setParameter("userId", userId);
			q.setParameter("month", month);
			q.setParameter("year", year);
			result = q.getResultList();
		} catch (PersistenceException e) {
			throw translate("Failed to Daily expenses", e);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Object[]> getMonthlyExpenses(String selectedCategoryId, String userId, int year) {
		List<Object[]> result = null;
		try {
			String str = "select function('MONTH', e.expenseDate), sum(e.amount) "
					+ "from Expense e where e.category.id = :categoryId and e.user.id = :userId "
					+ "and function('YEAR', e.expenseDate) = :year "
					+ "group by function('MONTH', e.expenseDate) "
					+ "order by function('MONTH', e.expenseDate)";
			TypedQuery<Object[]> q = em.createQuery(str, Object[].class);
			q.setParameter("categoryId", selectedCategoryId);
			q.setParameter("userId", userId);
			q.setParameter("year", year);
			result = q.getResultList();
		} catch (PersistenceException e) {
			throw translate("Failed to get Monthly expenses", e);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Object[]> getYearlyExpenses(String selectedCategoryId, String userId) {
		List<Object[]> result = null;
		try {
			String str = "select function('YEAR', e.expenseDate), sum(e.amount) "
					+ "from Expense e where e.category.id = :categoryId and e.user.id = :userId group by function('YEAR', e.expenseDate) "
					+ "order by function('YEAR', e.expenseDate)";
			TypedQuery<Object[]> q = em.createQuery(str, Object[].class);
			q.setParameter("categoryId", selectedCategoryId);
			q.setParameter("userId", userId);
			result = q.getResultList();
		} catch (PersistenceException e) {
			throw translate("Failed to get Yearly expenses", e);
		}
		return result;
	}

}

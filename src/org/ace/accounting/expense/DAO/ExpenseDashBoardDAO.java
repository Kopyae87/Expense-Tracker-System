package org.ace.accounting.expense.DAO;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.ace.accounting.expense.IDAO.IExpenseDashBoardDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("ExpenseDashBoardDAO")
public class ExpenseDashBoardDAO extends BasicDAO implements IExpenseDashBoardDAO {

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public double findTotalExpenseForMonth(String userId, int currentmonth, int currentyear) {
		double result = 0.00;
		
		try {
			String str = "select COALESCE(sum(e.amount), 0) from Expense e where e.user.id = :userId "
					+ "and function('MONTH', e.expenseDate) = :month " + "and function('YEAR', e.expenseDate) = :year";
			Query q = em.createQuery(str);
			q.setParameter("userId", userId);
			q.setParameter("month", currentmonth);
			q.setParameter("year", currentyear);
			result = ((Number) q.getSingleResult()).doubleValue();
			System.out.println(result);
		} catch (PersistenceException e) {
			throw translate("Failed to find total expense for month", e);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public double findTotalExpenseForYear(String userId, int currentYear) {
		double result = 0.00;
		try {
			String str = "select coalesce(sum(e.amount), 0) from Expense e where e.user.id = :userId "
					+ "and function('YEAR', e.expenseDate) = :year";
			Query q = em.createQuery(str);
			q.setParameter("userId", userId);
			q.setParameter("year", currentYear);
			result = ((Number) q.getSingleResult()).doubleValue();
		} catch (PersistenceException e) {
			throw translate("Failed to find total expense for year", e);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public long countExpenses(String userId) {
		long result = 0;
		try {
			String str = "select coalesce(sum(e.amount), 0) from Expense e where e.user.id = :userId";
			Query q = em.createQuery(str);
			q.setParameter("userId", userId);
			result = ((Number) q.getSingleResult()).longValue();
		} catch (PersistenceException e) {
			throw translate("Failed to count expense", e);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public double findTotalExpenseByCategoryForMonth(String userId, String categoryid, int month) {
		double result = 0.00;
		try {
			String str = "select coalesce(sum(e.amount), 0) from Expense e where e.user.id = :userId "
					+ "and e.category.id = :categoryid " + "and function('MONTH', e.expenseDate) = :month";
			Query q = em.createQuery(str);
			q.setParameter("userId", userId);
			q.setParameter("categoryid", categoryid);
			q.setParameter("month", month);
			result = ((Number) q.getSingleResult()).doubleValue();
		} catch (PersistenceException e) {
			throw translate("Failed to find total expense by category for month", e);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Map<Integer, Double> findMonthlyTrend(String userId) {
		Map<Integer, Double> monthData = new LinkedHashMap<>();
		List<Object[]> results;
		// Initialize all months to 0
		for (int month = 1; month <= 12; month++) {
			monthData.put(month, 0.0);
		}

		String jpql = "select function('MONTH', e.expenseDate), coalesce(sum(e.amount), 0) " + "from Expense e "
				+ "where e.user.id = :userId " + "and function('YEAR', e.expenseDate) = :year "
				+ "group by function('MONTH', e.expenseDate)";

		TypedQuery<Object[]> q = em.createQuery(jpql, Object[].class);
		q.setParameter("userId", userId);
		q.setParameter("year", Calendar.getInstance().get(Calendar.YEAR));
		results = q.getResultList();

		for (Object[] row : results) {
			Integer month = ((Number) row[0]).intValue();
			Double total = ((Number) row[1]).doubleValue();
			monthData.put(month, total);
		}
		return monthData;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public double findBudgetByCategoryForMonth(String userId, String categoryid, int currentmonth, int currentyear) {
		try {
			String str = "select sum(b.monthlyLimit) from Budget b where b.user.id = :userId "
					+ "and b.category.id = :categoryid and b.monthScope = :month "
					+ "and b.yearScope = :year ";
					
			Query q = em.createQuery(str);
			q.setParameter("userId", userId);
			q.setParameter("categoryid", categoryid);
			q.setParameter("month", currentmonth);
			q.setParameter("year", currentyear);
			Number result = ((Number) q.getSingleResult());
			return result != null ? result.doubleValue() : 0.0;
		} catch (PersistenceException e) {
			throw translate("Failed to find budget by category for month", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public double findMonthlyBudget(String userId, int month, int year) {
		try {
			String str = "select b.monthlyLimit from GlobalBudget b where b.user.id = :userId "
					+ "and b.monthScope = :month "
					+ "and b.yearScope = :year ";
					
			Query q = em.createQuery(str);
			q.setParameter("userId", userId);
			q.setParameter("month", month);
			q.setParameter("year", year);
			List<Number> results = q.getResultList();
	        if (results.isEmpty() || results.get(0) == null) {
	            return 0.0;
	        }
	        return results.get(0).doubleValue();
		} catch (PersistenceException e) {
			throw translate("Failed to find global budget for month", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public double findYearlyBudget(String userId, int year) {
		try {
			String str = "select b.yearlyLimit from GlobalBudget b where b.user.id = :userId "
					+ "and b.yearScope = :year ";
			Query q = em.createQuery(str);
			q.setParameter("userId", userId);
			q.setParameter("year", year);
			List<Number> results = q.getResultList();
	        if (results.isEmpty() || results.get(0) == null) {
	            return 0.0;
	        }
	        return results.get(0).doubleValue();
		} catch (PersistenceException e) {
			throw translate("Failed to find global budget for year", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public double findTotalExpenseByCategoryForYear(String userId, String categoryid, int currentyear) {
		double result = 0.00;
		try {
			String str = "select coalesce(sum(e.amount), 0) from Expense e where e.user.id = :userId "
					+ "and e.category.id = :categoryid and function('YEAR', e.expenseDate) = :year";
			Query q = em.createQuery(str);
			q.setParameter("userId", userId);
			q.setParameter("categoryid", categoryid);
			q.setParameter("year", currentyear);
			result = ((Number) q.getSingleResult()).doubleValue();
		} catch (PersistenceException e) {
			throw translate("Failed to find total expense by category for month", e);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public double findBudgetByCategoryForYear(String userId, String categoryid, int currentyear) {
		try {
			String str = "select sum(b.monthlyLimit) from Budget b where b.user.id = :userId "
					+ "and b.category.id = :categoryid "
					+ "and b.yearScope = :year ";
					
			Query q = em.createQuery(str);
			q.setParameter("userId", userId);
			q.setParameter("categoryid", categoryid);
			q.setParameter("year", currentyear);
			Number result = ((Number) q.getSingleResult());
			return result != null ? result.doubleValue() : 0.0;
		} catch (PersistenceException e) {
			throw translate("Failed to find budget by category for month", e);
		}
	}

}

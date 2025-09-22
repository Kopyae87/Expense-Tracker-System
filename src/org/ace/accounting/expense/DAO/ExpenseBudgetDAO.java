package org.ace.accounting.expense.DAO;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.ace.accounting.expense.Entity.Budget;
import org.ace.accounting.expense.Entity.BudgetDTO;
import org.ace.accounting.expense.Entity.GlobalBudget;
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
			if (existBudget != null) {
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
			/*
			 * if (currentbudget.getMonthlyLimit() != null) {
			 * str.append(" and b.monthlyLimit is not null"); } if
			 * (currentbudget.getYearlyLimit() != null) {
			 * str.append(" and b.yearlyLimit is not null"); }
			 */
			if (currentbudget.getMonthScope() != null) {
				str.append(" and b.monthScope = :monthscope");
			}
			TypedQuery<Budget> q = em.createQuery(str.toString(), Budget.class);
			q.setParameter("id", currentbudget.getCategory().getId());
			q.setParameter("yearscope", currentbudget.getYearScope());
			q.setParameter("userid", currentbudget.getUser().getId());

			if (currentbudget.getMonthScope() != null) {
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

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<GlobalBudget> findAllGlobalBudgetsByUserId(String currentUserId) {
		try {
			TypedQuery<GlobalBudget> q = em.createQuery("select b from GlobalBudget b where b.user.id = :userid",
					GlobalBudget.class);
			q.setParameter("userid", currentUserId);
			return q.getResultList();
		} catch (PersistenceException e) {
			throw translate("Can't find Global Budgets", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public GlobalBudget findGlobalBudget(GlobalBudget globalBudget) {
		try {
			StringBuilder str = new StringBuilder(
					"select b from GlobalBudget b where b.yearScope = :yearscope " + " and b.user.id = :userid");

			if (globalBudget.getMonthScope() != null) {
				str.append(" and b.monthScope = :monthscope");
			}
			TypedQuery<GlobalBudget> q = em.createQuery(str.toString(), GlobalBudget.class);
			q.setParameter("yearscope", globalBudget.getYearScope());
			q.setParameter("userid", globalBudget.getUser().getId());

			if (globalBudget.getMonthScope() != null) {
				q.setParameter("monthscope", globalBudget.getMonthScope());
			}

			List<GlobalBudget> li = q.getResultList();
			if (!li.isEmpty()) {
				return li.get(0);
			}
			return null;
		} catch (PersistenceException e) {
			throw translate("Something went wrong with finding Global Budget with period", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteGlobalBudget(GlobalBudget globalBudget) {
		try {
			em.remove(em.merge(globalBudget));
		} catch (PersistenceException e) {
			throw translate("Can't delete global Budget", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateGlobalBuget(GlobalBudget globalBudget) {
		try {
			GlobalBudget existGlobalBudget = em.find(GlobalBudget.class, globalBudget.getId());
			if (existGlobalBudget != null) {
				if (globalBudget.getMonthlyLimit() != null) {
					existGlobalBudget.setMonthlyLimit(globalBudget.getMonthlyLimit());
				}
				if (globalBudget.getYearlyLimit() != null) {
					existGlobalBudget.setYearlyLimit(globalBudget.getYearlyLimit());
				}
				existGlobalBudget.setMonthScope(globalBudget.getMonthScope());
				existGlobalBudget.setYearScope(globalBudget.getYearScope());
				existGlobalBudget.setUser(globalBudget.getUser());
				em.merge(existGlobalBudget);
			}
		} catch (PersistenceException e) {
			throw translate("Can't update globalBudget", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveGlobalBudget(GlobalBudget globalBudget) {
		try {
			em.persist(globalBudget);
		} catch (PersistenceException e) {
			throw translate("Can't save globalBudget", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public BudgetDTO findBudgetByCategoryAndDate(String categoryId, Date expenseDate) {
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(expenseDate);
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1;

			Budget categoryBudget = em.createQuery("SELECT b FROM Budget b WHERE b.category.id = :categoryId "
							+ "AND b.yearScope = :year AND b.monthScope = :month", Budget.class)
					.setParameter("categoryId", categoryId).setParameter("year", year).setParameter("month", month)
					.getResultStream().findFirst().orElse(null);

			GlobalBudget globalBudget = em.createQuery("SELECT g FROM GlobalBudget g WHERE g.yearScope = :year " + "AND g.monthScope = :month",
							GlobalBudget.class)
					.setParameter("year", year).setParameter("month", month).getResultStream().findFirst().orElse(null);

			BudgetDTO dto = new BudgetDTO(
	                categoryBudget != null ? categoryBudget.getYearScope() : (globalBudget != null ? globalBudget.getYearScope() : null),
	                categoryBudget != null ? categoryBudget.getMonthScope() : (globalBudget != null ? globalBudget.getMonthScope() : null),
	                categoryBudget != null ? categoryBudget.getCategory().getId() : null,
	                categoryBudget != null ? categoryBudget.getCategory().getName() : null,
	                categoryBudget != null ? categoryBudget.getMonthlyLimit() : null,
	                categoryBudget != null ? categoryBudget.getYearlyLimit() : null,
	                globalBudget != null ? globalBudget.getMonthlyLimit() : null,
	                globalBudget != null ? globalBudget.getYearlyLimit() : null,
	                categoryBudget != null,
	                globalBudget != null
	        );
			return dto;
		} catch (PersistenceException e) {
			throw translate("Can't find budget by category and date", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public double findTotalCategoryBudgetForMonth(String userid, String categoryid, Integer monthScope, Integer yearScope) {
		 try {
		        TypedQuery<Double> query = em.createQuery(
		            "SELECT COALESCE(SUM(b.monthlyLimit), 0) " +
		            "FROM Budget b " +
		            "WHERE b.user.id = :userId " +
		            "AND b.category.id = :categoryId " +
		            "AND b.yearScope = :yearScope " +
		            "AND b.monthScope = :monthScope", Double.class);

		        query.setParameter("userId", userid);
		        query.setParameter("categoryId", categoryid);
		        query.setParameter("yearScope", yearScope);
		        query.setParameter("monthScope", monthScope);
		        Number result = (Number) query.getSingleResult();
		        return result == null ? 0.0 : result.doubleValue();
		    } catch (PersistenceException e) {
		        throw translate("Failed to sum category monthly budget", e);
		    }
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public double findTotalCategoryBudgetForYear(String userid, String categoryid, Integer yearScope) {
		try {
	        TypedQuery<Double> query = em.createQuery(
	            "SELECT COALESCE(SUM(b.yearlyLimit), 0) " +
	            "FROM Budget b " +
	            "WHERE b.user.id = :userId " +
	            "AND b.category.id = :categoryId " +
	            "AND b.yearScope = :yearScope", Double.class);

	        query.setParameter("userId", userid);
	        query.setParameter("categoryId", categoryid);
	        query.setParameter("yearScope", yearScope);
	        Number result = (Number) query.getSingleResult();
	        return result == null ? 0.0 : result.doubleValue();
	    } catch (PersistenceException e) {
	        throw translate("Failed to sum category yearly budget", e);
	    }
	}

}

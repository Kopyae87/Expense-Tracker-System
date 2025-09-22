package org.ace.accounting.expense.services;

import java.util.Map;

import javax.annotation.Resource;

import org.ace.accounting.expense.IDAO.IExpenseDashBoardDAO;
import org.ace.accounting.expense.Iservices.IExpenseDashBoardService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "ExpenseDashBoardService")
public class ExpenseDashBoardService implements IExpenseDashBoardService{

	@Resource(name = "ExpenseDashBoardDAO")
	private IExpenseDashBoardDAO dashBoardDAO;
	
	/* 
	 * 
	 * this is for home dashboard 
	 * 
	 * */

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public double findTotalExpenseForMonth(String userId, int currentmonth, int currentyear) {
		// TODO Auto-generated method stub
		try {
			System.out.println("this is in service"+ userId + currentmonth + currentyear);
			return dashBoardDAO.findTotalExpenseForMonth(userId , currentmonth, currentyear);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Something went wrong,Can't find total expense for month", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public double findTotalExpenseForYear(String userId, int currentYear) {
		try {
			return dashBoardDAO.findTotalExpenseForYear(userId, currentYear);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Something went wrong,Can't find total expense for year", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public long countExpenses(String userId) {
		try {
			return dashBoardDAO.countExpenses(userId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Something went wrong,Can't count expenses", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public double findTotalExpenseByCategoryForMonth(String userId, String categoryid, int month) {
		try {
			return dashBoardDAO.findTotalExpenseByCategoryForMonth(userId, categoryid, month);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Something went wrong,Can't find total expense by category for month", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public double findTotalExpenseByCategoryForYear(String userId, String id, int currentyear) {
		try {
			return dashBoardDAO.findTotalExpenseByCategoryForYear(userId, id, currentyear);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Something went wrong,Can't find total expense by category for month", e);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<Integer, Double> findMonthlyTrend(String userId, int currentYear) {
		try {
			return dashBoardDAO.findMonthlyTrend(userId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Something went wrong,Can't find monthly trend for month", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public double findBudgetByCategoryForMonth(String userId, String categoryid, int currentmonth, int currentyear) {
		try {
			return dashBoardDAO.findBudgetByCategoryForMonth(userId, categoryid, currentmonth, currentyear);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Something went wrong,Can't find total expense by category for month", e);
		}
	}



	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public double findBudgetByCategoryForYear(String userId, String id, int currentyear) {
		try {
			return dashBoardDAO.findBudgetByCategoryForYear(userId, id, currentyear);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Something went wrong,Can't find total expense by category for month", e);
		}
	}

	@Override
	public double findMonthlyBudget(String userId, int month, int year) {
		try {
			return dashBoardDAO.findMonthlyBudget(userId,month,year);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Something went wrong,Can't find total expense by category for month", e);
		}
	}

	@Override
	public double findYearlyBudget(String userId, int year) {
		try {
			return dashBoardDAO.findYearlyBudget(userId,year);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Something went wrong,Can't find total expense by category for month", e);
		}
	}
}

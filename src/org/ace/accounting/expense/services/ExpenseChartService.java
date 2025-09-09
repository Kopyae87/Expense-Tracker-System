package org.ace.accounting.expense.services;

import java.util.List;

import javax.annotation.Resource;

import org.ace.accounting.expense.IDAO.IExpenseChartDAO;
import org.ace.accounting.expense.Iservices.IExpenseChartService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "ExpenseChartService")
public class ExpenseChartService implements IExpenseChartService{

	@Resource(name = "ExpenseChartDAO")
	private IExpenseChartDAO expenseChartDAO;
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Object[]> getDailyExpenses(String selectedCategoryId, String userId, int month, int year) {
		List<Object[]> result = null;
		try {
			result = expenseChartDAO.getDailyExpense(selectedCategoryId, userId, month, year);
		} catch (DAOException e) {
			// TODO: handle exception
			throw new SystemException(e.getErrorCode(), "Something went wrong,Can't save Expense", e);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Object[]> getMonthlyExpenses(String selectedCategoryId, String userId, int year) {
		List<Object[]> result = null;
		try {
			result = expenseChartDAO.getMonthlyExpenses(selectedCategoryId, userId, year);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Something went wrong,Can't save Expense", e);
		}
		
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Object[]> getYearlyExpenses(String selectedCategoryId, String userId) {
		List<Object[]> result = null;
		try {
			result = expenseChartDAO.getYearlyExpenses(selectedCategoryId, userId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Something went wrong,Can't save Expense", e);
		}
		return result;
	}

	
}

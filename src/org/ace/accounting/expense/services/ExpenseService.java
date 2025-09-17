package org.ace.accounting.expense.services;

import java.util.List;

import javax.annotation.Resource;

import org.ace.accounting.expense.Entity.Category;
import org.ace.accounting.expense.Entity.Expense;
import org.ace.accounting.expense.IDAO.IExpenseDAO;
import org.ace.accounting.expense.Iservices.IExpenseService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "ExpenseService")
public class ExpenseService implements IExpenseService {

	@Resource(name = "ExpenseDAO")
	private IExpenseDAO expenseDAO;
	
	@Override
	public Boolean deleteExpense(Expense expense) {
		Boolean success = false;
		try {
			expenseDAO.deleteExpense(expense);
		} catch (DAOException e) {
			// TODO: handle exception
			throw new SystemException(e.getErrorCode(), "Cant find Category Names", e);
		}
		return success;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Category> findAllCategory() throws SystemException {
		List<Category> result = null;
		try {
			result = expenseDAO.findCategoryList();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Cant find Category Names", e);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveCategory(Category c) {
		try {
			expenseDAO.saveCategory(c);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Cant find Category Names", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveExpense(Expense expense) {
		// TODO Auto-generated method stub
		try {
			expenseDAO.saveExpense(expense);
		} catch (DAOException e) {
			// TODO: handle exception
			throw new SystemException(e.getErrorCode(), "Something went wrong,Can't save Expense", e);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Expense> findAllExpense(String userid) {
		try {
			return expenseDAO.findAllExpense(userid);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Something went wrong,Can't find expense", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Expense> findLatestTenExpenses(String userid) {
		try {
			return expenseDAO.findLatestTenExpenses(userid);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Something went wrong,Can't find expense", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Boolean updateExpense(Expense currentexpense) {
		// TODO Auto-generated method stub
		try {
			return expenseDAO.updateExpense(currentexpense);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Something went wrong,Can't find expense", e);
		}

	}



}

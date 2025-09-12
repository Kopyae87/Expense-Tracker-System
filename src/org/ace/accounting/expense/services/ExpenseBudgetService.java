package org.ace.accounting.expense.services;

import java.util.List;

import javax.annotation.Resource;

import org.ace.accounting.expense.Entity.Budget;
import org.ace.accounting.expense.IDAO.IExpenseBudgetDAO;
import org.ace.accounting.expense.Iservices.IExpenseBudgetService;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "ExpenseBudgetService")
public class ExpenseBudgetService implements IExpenseBudgetService {

	@Resource(name = "ExpenseBudgetDAO")
	private IExpenseBudgetDAO expenseBudgetDAO;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Budget> fineAllBudgets(String userid) {
		try {
			return expenseBudgetDAO.findAllBudgetsByUserId(userid);
		} catch (DAOException e) {
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveBudget(Budget currentbudget) {
		try {
			expenseBudgetDAO.saveBudget(currentbudget);
		} catch (DAOException e) {
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateBudget(Budget currentbudget) {
		try {
			expenseBudgetDAO.updateBuget(currentbudget);
		} catch (DAOException e) {
			// TODO: handle exception
		}
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteBudget(Budget currentbudget) {
		try {
			expenseBudgetDAO.deleteBudget(currentbudget);
		} catch (DAOException e) {
			// TODO: handle exception
		}
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Budget findIndenticalBudget(Budget currentbudget) {
		try {
			return expenseBudgetDAO.findBudget(currentbudget);
		} catch (DAOException e) {
			// TODO: handle exception
		}
		return null;
	}

}

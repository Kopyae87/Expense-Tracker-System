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
	public boolean findCategory(String id, String timevalue, String userid, Integer scope) {
		boolean exist = false;
		try {
			Budget b = expenseBudgetDAO.findCategory(id, timevalue, userid, scope);
			if(b != null) {
				exist = true;
			}
		} catch (DAOException e) {
			// TODO: handle exception
		}
		return exist;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Budget> fineAllBudgets(String userid) {

		try {
			return expenseBudgetDAO.findAllBudgetsByUserId(userid);
		} catch (DAOException e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveBudget(Budget currentbudget, String timevalue) {
		// TODO Auto-generated method stub
		try {
			expenseBudgetDAO.saveBudget(currentbudget, timevalue);
		} catch (DAOException e) {
			// TODO: handle exception
		}

	}

}

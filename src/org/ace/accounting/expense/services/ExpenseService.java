package org.ace.accounting.expense.services;

import javax.annotation.Resource;

import org.ace.accounting.expense.IDAO.IExpenseDAO;
import org.ace.accounting.expense.Iservices.IExpenseService;
import org.springframework.stereotype.Service;

@Service(value = "ExpenseService")
public class ExpenseService implements IExpenseService{

	@Resource(name = "ExpenseDAO")
	private IExpenseDAO expenseDAO;
	
	@Override
	public Boolean deleteExpense() {
		// TODO Auto-generated method stub
		return null;
	}
	
}

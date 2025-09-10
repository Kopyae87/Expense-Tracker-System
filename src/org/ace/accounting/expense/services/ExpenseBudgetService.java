package org.ace.accounting.expense.services;

import javax.annotation.Resource;

import org.ace.accounting.expense.IDAO.IExpenseBudgetDAO;
import org.ace.accounting.expense.Iservices.IExpenseBudgetService;
import org.springframework.stereotype.Service;

@Service(value = "ExpenseBudgetService")
public class ExpenseBudgetService implements IExpenseBudgetService{

	@Resource(name = "ExpenseBudgetDAO")
	private IExpenseBudgetDAO expenseBudgetDAO;
	
	
}

package org.ace.accounting.expense.Iservices;

import java.util.List;

import org.ace.accounting.expense.Entity.Category;
import org.ace.accounting.expense.Entity.Expense;

public interface IExpenseService {
	public Boolean deleteExpense();
	
	public List<Category> findAllCategory();

	public void saveCategory(Category c);
	
	public void saveExpense(Expense e);
}

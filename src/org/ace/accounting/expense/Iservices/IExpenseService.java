package org.ace.accounting.expense.Iservices;

import java.util.List;

import org.ace.accounting.expense.Entity.Category;
import org.ace.accounting.expense.Entity.Expense;
import org.ace.accounting.expense.Entity.ExpenseUser;

public interface IExpenseService {
	public Boolean deleteExpense(Expense e);
	
	public List<Category> findAllCategory();

	public void saveCategory(Category c);
	
	public void saveExpense(Expense e);
	
	public List<Expense> findAllExpense(String userid);

	public Boolean updateExpense(Expense currentexpense);

	List<Expense> findLatestTenExpenses(String userid);
}

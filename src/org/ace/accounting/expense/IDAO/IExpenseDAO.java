package org.ace.accounting.expense.IDAO;

import java.util.List;

import org.ace.accounting.expense.Entity.Category;
import org.ace.accounting.expense.Entity.Expense;
import org.ace.accounting.expense.Entity.ExpenseUser;

public interface IExpenseDAO {
	public Boolean deleteExpense();

	public List<Category> findCategoryList();

	public void saveCategory(Category c);

	public void saveExpense(Expense e);

	public List<Expense> findAllExpense(String userid);

	public Boolean updateExpense(Expense currentExpense);
}

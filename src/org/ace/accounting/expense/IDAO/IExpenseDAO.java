package org.ace.accounting.expense.IDAO;

import java.util.List;

import org.ace.accounting.expense.Entity.Category;
import org.ace.accounting.expense.Entity.Expense;

public interface IExpenseDAO {
	public Boolean deleteExpenseDAO();

	public List<Category> findCategoryList();

	public void saveCategory(Category c);

	public void saveExpense(Expense e);
}

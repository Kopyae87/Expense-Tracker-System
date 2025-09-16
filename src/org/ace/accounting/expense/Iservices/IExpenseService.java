package org.ace.accounting.expense.Iservices;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

	/*	
	 * below are for home dashboard.xhtml
	 * 
	*/	
	public double findTotalExpenseForMonth(String userId, int currentmonth);

	public double findTotalExpenseForYear(String userId, int currentYear);

	public long countExpenses(String userId);

	double findTotalExpenseByCategoryForMonth(String userId, String id, int month);

	public Map<Integer, Double> findMonthlyTrend(String userId, int currentYear);


}

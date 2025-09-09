package org.ace.accounting.expense.IDAO;

import java.util.List;

public interface IExpenseChartDAO {
	public List<Object[]> getMonthlyExpenses(String selectedCategoryId, String userId, int year);
	public List<Object[]> getYearlyExpenses(String selectedCategoryId, String userId);
	public List<Object[]> getDailyExpense(String selectedCategoryId, String userId, int month, int year);
}

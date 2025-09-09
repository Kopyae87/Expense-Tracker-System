package org.ace.accounting.expense.Iservices;

import java.util.List;

public interface IExpenseChartService {
	List<Object[]> getDailyExpenses(String selectedCategoryId, String userId, int month, int year);

	List<Object[]> getMonthlyExpenses(String selectedCategoryId, String userId, int year);

	List<Object[]> getYearlyExpenses(String selectedCategoryId, String userId);
}

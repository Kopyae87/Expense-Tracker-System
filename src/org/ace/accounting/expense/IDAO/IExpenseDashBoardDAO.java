package org.ace.accounting.expense.IDAO;

import java.util.Map;

public interface IExpenseDashBoardDAO {

	double findTotalExpenseForMonth(String userId, int currentmonth);

	double findTotalExpenseForYear(String userId);

	long countExpenses(String userId);

	double findTotalExpenseByCategoryForMonth(String userId);

	Map<Integer, Double> findMonthlyTrend(String userId);
	
}

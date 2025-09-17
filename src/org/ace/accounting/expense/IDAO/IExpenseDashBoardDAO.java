package org.ace.accounting.expense.IDAO;

import java.util.Map;

public interface IExpenseDashBoardDAO {

	double findTotalExpenseForMonth(String userId, int currentmonth, int currentyear);

	double findTotalExpenseForYear(String userId, int currentYear);

	long countExpenses(String userId);

	double findTotalExpenseByCategoryForMonth(String userId, String categoryid, int month);

	Map<Integer, Double> findMonthlyTrend(String userId);

	double findBudgetByCategoryForMonth(String userId, String categoryid, int currentmonth, int currentyear);
	
}

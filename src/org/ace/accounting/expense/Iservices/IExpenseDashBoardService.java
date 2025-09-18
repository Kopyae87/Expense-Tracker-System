package org.ace.accounting.expense.Iservices;

import java.util.Map;

public interface IExpenseDashBoardService {
	/*	
	 * below are for home dashboard.xhtml
	 * 
	*/	
	public double findTotalExpenseForMonth(String userId, int currentmonth, int currentyear);

	public double findTotalExpenseForYear(String userId, int currentYear);

	public long countExpenses(String userId);

	double findTotalExpenseByCategoryForMonth(String userId, String categoryid, int month);

	public Map<Integer, Double> findMonthlyTrend(String userId, int currentYear);

	public double findBudgetByCategoryForMonth(String userId, String categoryid, int currentmonth, int currentyear);

	public double findTotalExpenseByCategoryForYear(String userId, String id, int currentyear);

	public double findBudgetByCategoryForYear(String userId, String id, int currentyear);

	public double findMonthlyBudget(String userId, int month, int year);

	public double findYearlyBudget(String userId, int year);
}

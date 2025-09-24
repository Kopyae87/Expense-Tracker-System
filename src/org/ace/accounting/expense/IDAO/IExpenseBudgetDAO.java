package org.ace.accounting.expense.IDAO;

import java.util.Date;
import java.util.List;

import org.ace.accounting.expense.Entity.Budget;
import org.ace.accounting.expense.Entity.BudgetDTO;
import org.ace.accounting.expense.Entity.GlobalBudget;

public interface IExpenseBudgetDAO {

	List<Budget> findAllBudgetsByUserId(String userid);

	void saveBudget(Budget currentbudget);

	/*
	 * Budget findBudget(String id, String timevalue, String userid, Integer scope);
	 */

	void updateBudget(Budget currentbudget);

	void deleteBudget(Budget currentbudget);

	Budget findBudget(Budget currentbudget);

	/* 
	 * 
	 * global budget 
	 * 
	 * */
	
	List<GlobalBudget> findAllGlobalBudgetsByUserId(String currentUserId);

	GlobalBudget findGlobalBudget(GlobalBudget globalBudget);

	void deleteGlobalBudget(GlobalBudget globalBudget);

	void updateGlobalBuget(GlobalBudget globalBudget);

	void saveGlobalBudget(GlobalBudget globalBudget);
	

	BudgetDTO findBudgetByCategoryAndDate(String id, Date expenseDate, String userid);
	
	/*
	 * 
	 *  validator 
	 *  
	 *  */

	double findTotalCategoryBudgetForMonth(String userid, String id, Integer monthScope, Integer yearScope);

	double findTotalCategoryBudgetForYear(String userid, String id, Integer yearScope);

	double findTotalAllCategoryBudgetForMonth(String userid, Integer monthScope, Integer yearScope);

	double findTotalAllCategoryBudgetForYear(String userid, Integer yearScope);

	double findYearlyBudget(String userId, int year);

	double findTotalMonthlyGlobalBudgetForYear(String userid, Integer yearScope);

	double findTotalMonthlyGlobalBudgetForYearExcludingMonth(String userid, Integer yearScope, Integer monthScope);

	double findMonthlyGlobalBudgetById(String id);

}

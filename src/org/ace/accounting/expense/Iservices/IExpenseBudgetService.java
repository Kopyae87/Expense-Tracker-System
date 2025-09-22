package org.ace.accounting.expense.Iservices;

import java.util.Date;
import java.util.List;

import org.ace.accounting.expense.Entity.Budget;
import org.ace.accounting.expense.Entity.BudgetDTO;
import org.ace.accounting.expense.Entity.GlobalBudget;

public interface IExpenseBudgetService {

	List<Budget> fineAllBudgets(String userid);

	void updateBudget(Budget currentbudget);

	void deleteBudget(Budget currentbudget);

	void saveBudget(Budget currentbudget);

	Budget findIndenticalBudget(Budget currentbudget);

	/* global budget */
	List<GlobalBudget> fineAllGlobalBudgets(String currentUserId);
	
	GlobalBudget findIndenticalGlobalBudget(GlobalBudget globalBudget);

	void deleteGlobalBudget(GlobalBudget globalBudget);

	void updateGlobalBudget(GlobalBudget globalBudget);

	void saveGlobalBudget(GlobalBudget globalBudget);

	BudgetDTO findBudgetByCategoryAndDate(String id, Date expenseDate);
	
	/*
	 * validator
	 * 
	 */
	double findTotalCategoryBudgetForMonth(String userid, String id, Integer monthScope, Integer yearScope);

	double findTotalCategoryBudgetForYear(String userid, String id, Integer yearScope);

	double findTotalAllCategoryBudgetForMonth(String userid, Integer monthScope, Integer yearScope);

	double findTotalAllCategoryBudgetForYear(String userid, Integer yearScope);

}

package org.ace.accounting.expense.IDAO;

import java.util.List;

import org.ace.accounting.expense.Entity.Budget;
import org.ace.accounting.expense.Entity.GlobalBudget;

public interface IExpenseBudgetDAO {

	List<Budget> findAllBudgetsByUserId(String userid);

	void saveBudget(Budget currentbudget);

	/*
	 * Budget findBudget(String id, String timevalue, String userid, Integer scope);
	 */

	void updateBuget(Budget currentbudget);

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

}

package org.ace.accounting.expense.IDAO;

import java.util.List;

import org.ace.accounting.expense.Entity.Budget;

public interface IExpenseBudgetDAO {

	List<Budget> findAllBudgetsByUserId(String userid);

	void saveBudget(Budget currentbudget);

	/*
	 * Budget findBudget(String id, String timevalue, String userid, Integer scope);
	 */

	void updateBuget(Budget currentbudget);

	void deleteBudget(Budget currentbudget);

	Budget findBudget(Budget currentbudget);

}

package org.ace.accounting.expense.Iservices;

import java.util.List;

import org.ace.accounting.expense.Entity.Budget;

public interface IExpenseBudgetService {

	boolean findCategory(String id, String timevalue, String currentUserId, Integer integer);

	void saveBudget(Budget currentbudget, String timevalue);

	List<Budget> fineAllBudgets(String userid);

}

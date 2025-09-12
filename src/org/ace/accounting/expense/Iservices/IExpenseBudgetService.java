package org.ace.accounting.expense.Iservices;

import java.util.List;

import org.ace.accounting.expense.Entity.Budget;

public interface IExpenseBudgetService {

	List<Budget> fineAllBudgets(String userid);

	void updateBudget(Budget currentbudget);

	void deleteBudget(Budget currentbudget);

	void saveBudget(Budget currentbudget);

	Budget findIndenticalBudget(Budget currentbudget);

}

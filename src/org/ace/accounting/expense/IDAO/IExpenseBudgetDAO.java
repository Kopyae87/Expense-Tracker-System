package org.ace.accounting.expense.IDAO;

import java.util.List;

import org.ace.accounting.expense.Entity.Budget;

public interface IExpenseBudgetDAO {

	Budget findCategory(String id, String timevalue, String userid, Integer scope);

	List<Budget> findAllBudgetsByUserId(String userid);

	void saveBudget(Budget currentbudget, String timevalue);

}

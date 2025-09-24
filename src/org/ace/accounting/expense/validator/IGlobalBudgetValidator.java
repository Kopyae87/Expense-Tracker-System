package org.ace.accounting.expense.validator;

import org.ace.accounting.common.validation.ValidationResult;
import org.ace.accounting.expense.Entity.GlobalBudget;

public interface IGlobalBudgetValidator<T> {
	public ValidationResult validate(T obj, String timevalue , String userid);

	public ValidationResult validateForUpdate(GlobalBudget globalBudget, String timevalue, String userid);
	//ValidationResult validate1(GlobalBudget globalBudget, String timevalue, String userid);
}

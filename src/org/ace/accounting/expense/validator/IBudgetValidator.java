package org.ace.accounting.expense.validator;

import org.ace.accounting.common.validation.ValidationResult;
import org.ace.accounting.expense.Entity.Budget;

public interface IBudgetValidator<T> {
	public ValidationResult validate(T obj, String timevalue, String userid);

	ValidationResult validate1(Budget budget, String timevalue, String userid);
}

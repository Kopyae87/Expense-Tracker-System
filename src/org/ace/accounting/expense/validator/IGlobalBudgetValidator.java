package org.ace.accounting.expense.validator;

import org.ace.accounting.common.validation.ValidationResult;

public interface IGlobalBudgetValidator<T> {
	public ValidationResult validate(T obj, String timevalue , String userid);
}

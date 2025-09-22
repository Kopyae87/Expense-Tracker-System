package org.ace.accounting.expense.validator;

import org.ace.accounting.common.validation.ValidationResult;

public interface IBudgetValidator<T> {
	public ValidationResult validate(T obj, String timevalue);
}

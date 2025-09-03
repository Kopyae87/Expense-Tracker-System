package org.ace.accounting.expense.IDAO;

import org.ace.accounting.expense.Entity.ExpenseUser;
import org.ace.java.component.persistence.exception.DAOException;

public interface IExpenseUserDAO {
	public ExpenseUser find(String name) throws DAOException;
}

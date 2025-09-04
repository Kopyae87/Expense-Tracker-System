package org.ace.accounting.expense.Iservices;

import org.ace.accounting.expense.Entity.ExpenseUser;
import org.ace.java.component.SystemException;

public interface IExpenseUserService {
	
	public ExpenseUser findExpenseUser(String userCode);
	
	public Boolean loginCheck(String name , String password) throws SystemException;
}

package org.ace.accounting.expense.Iservices;

import org.ace.java.component.SystemException;

public interface IExpenseUserService {
	
	public Boolean loginCheck(String name , String password) throws SystemException;
}

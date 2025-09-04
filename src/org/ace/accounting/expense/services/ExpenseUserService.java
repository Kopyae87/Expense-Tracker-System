package org.ace.accounting.expense.services;

import javax.annotation.Resource;
import org.ace.accounting.expense.Entity.ExpenseUser;
import org.ace.accounting.expense.IDAO.IExpenseUserDAO;
import org.ace.accounting.expense.Iservices.IExpenseUserService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "ExpenseUserService")
public class ExpenseUserService implements IExpenseUserService{
	
	@Resource(name = "ExpenseUserDAO")
	private IExpenseUserDAO expenseUserDAO; 
	
//	@Resource(name = "PasswordCodecHandler")
//	private PasswordCodecHandler codecHandler;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Boolean loginCheck(String name, String password) throws SystemException {
		try {
			ExpenseUser user = expenseUserDAO.find(name);
			if (user != null) {
				if (user.getPassword().equals(password)){
					return true;
				}
			}
		} catch (DAOException e) {
			// TODO: handle exception
			throw new SystemException(e.getErrorCode(), "Failed to change passowrd", e);
		}
		return false;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ExpenseUser findExpenseUser(String userCode) throws SystemException {
		ExpenseUser user = expenseUserDAO.find(userCode);
		return user;
	}
}

package org.ace.accounting.expense.services;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ace.accounting.expense.Entity.Expense;
import org.ace.accounting.expense.IDAO.IEnquiryExpenseDAO;
import org.ace.accounting.expense.Iservices.IEnquiryExpenseService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "EnquiryExpenseService")
public class EnquiryExpenseService implements IEnquiryExpenseService{

	@Resource(name = "EnquiryExpenseDAO")
	private IEnquiryExpenseDAO enquiryExpenseDAO;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Expense> find(Date startDate, Date endDate, String categoryName, String paymentType , String userid) {
		// TODO Auto-generated method stub
		List<Expense> expenseList = null;
		try {
			expenseList = enquiryExpenseDAO.find(startDate, endDate, categoryName, paymentType, userid);
		} catch (DAOException e) {
			// TODO: handle exception
			throw new SystemException(e.getErrorCode(), "Cant find Expenses", e);
		}
		return expenseList;
	}

}

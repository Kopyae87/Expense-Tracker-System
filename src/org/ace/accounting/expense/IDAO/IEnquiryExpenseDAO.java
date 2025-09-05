package org.ace.accounting.expense.IDAO;

import java.util.Date;
import java.util.List;

import org.ace.accounting.expense.Entity.Expense;

public interface IEnquiryExpenseDAO {
	public List<Expense> find(Date startDate,Date endDate,String categoryName,String paymentType, String userid);
}

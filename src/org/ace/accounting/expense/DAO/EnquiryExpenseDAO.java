package org.ace.accounting.expense.DAO;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import org.ace.accounting.expense.Entity.Expense;
import org.ace.accounting.expense.IDAO.IEnquiryExpenseDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository(value = "EnquiryExpenseDAO")
public class EnquiryExpenseDAO extends BasicDAO implements IEnquiryExpenseDAO{

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Expense> find(Date startDate, Date endDate, String categoryId, String paymentType, String userid) {
		List<Expense> list = null;
		try {
			
			Map<String, Object> params = new HashMap<>();
			StringBuffer str = new StringBuffer();
			str.append("select e from Expense e where e.user.id = :userid");
			params.put("userid", userid);
			
			if(startDate != null) {
				System.out.println("in 1");
				str.append(" and e.expenseDate >= :startDate");
				params.put("startDate", startDate);
			}
			if(endDate != null) {
				System.out.println("in 2");
				str.append(" and e.expenseDate <= :endDate");
				params.put("endDate", endDate);
			}
			if(categoryId != null && !categoryId.isEmpty()) {
				System.out.println("in 3");
				str.append(" and e.category.id = :categoryId");
				params.put("categoryId", categoryId);
			}
			if(paymentType != null && !paymentType.isEmpty()) {
				System.out.println("searching with paymenttype " + paymentType);
				paymentType = paymentType.trim();
				str.append(" and e.paymentType = :paymentType");
				params.put("paymentType", paymentType);
			}
			System.out.println("Searching Expenses:");
			System.out.println("CategoryId: " + categoryId);
			System.out.println("PaymentType: " + paymentType);
			System.out.println("StartDate: " + startDate);
			System.out.println("EndDate: " + endDate);
			
			TypedQuery<Expense> q = em.createQuery(str.toString(), Expense.class);			
			for(Map.Entry<String, Object> m : params.entrySet()) {
				q.setParameter(m.getKey(), m.getValue());
			}
			list = q.getResultList();
			
		} catch (PersistenceException e) {
			throw translate("Failed to delete expense", e);
		}
		
		return list;
	}

}

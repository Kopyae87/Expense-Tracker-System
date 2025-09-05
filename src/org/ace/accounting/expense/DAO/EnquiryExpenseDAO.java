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
	public List<Expense> find(Date startDate, Date endDate, String categoryName, String paymentType, String userid) {
		List<Expense> list = null;
		try {
			
			Map<String, Object> paramMap = new HashMap<>();
			StringBuffer str = new StringBuffer();
			str.append("select e from Expense e where e.user.id = :userid");
			paramMap.put("userid", userid);
			
			if(startDate != null) {
				str.append(" and e.expense_date >= :startDate");
				paramMap.put("startDate", startDate);
			}
			if(endDate != null) {
				str.append(" and e.expense_date <= :endDate");
				paramMap.put("endDate", endDate);
			}
			if(categoryName != null && !categoryName.isEmpty()) {
				str.append(" and e.category.id = :categoryName");
				paramMap.put("categoryName", categoryName);
			}
			if(paymentType != null && !paymentType.isEmpty()) {
				str.append(" and e.paymenttype = :paymentType");
				paramMap.put("paymentType", paymentType);
			}
			
			TypedQuery<Expense> q = em.createQuery(str.toString(), Expense.class);			
			for(Map.Entry<String, Object> m : paramMap.entrySet()) {
				q.setParameter(m.getKey(), m.getValue());
			}
			list = q.getResultList();
			
		} catch (PersistenceException e) {
			throw translate("Failed to delete expense", e);
		}
		
		return list;
	}

}

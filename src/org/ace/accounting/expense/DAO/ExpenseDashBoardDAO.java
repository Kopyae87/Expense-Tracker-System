package org.ace.accounting.expense.DAO;

import java.util.LinkedHashMap;
import java.util.Map;

import org.ace.accounting.expense.IDAO.IExpenseDashBoardDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.springframework.stereotype.Repository;

@Repository("ExpenseDashBoardDAO")
public class ExpenseDashBoardDAO extends BasicDAO implements IExpenseDashBoardDAO{

	@Override
	public double findTotalExpenseForMonth(String userId, int currentmonth) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double findTotalExpenseForYear(String userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long countExpenses(String userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double findTotalExpenseByCategoryForMonth(String userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map<Integer, Double> findMonthlyTrend(String userId) {
	    Map<Integer, Double> monthData = new LinkedHashMap<>();
	    
	    // Initialize all months to 0
	    for (int month = 1; month <= 12; month++) {
	        monthData.put(month, 50.0);
	    }

	    return monthData;
	}
	
}

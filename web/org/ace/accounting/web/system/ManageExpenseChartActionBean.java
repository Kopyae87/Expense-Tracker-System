package org.ace.accounting.web.system;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.accounting.expense.Entity.Category;
import org.ace.accounting.expense.Iservices.IExpenseChartService;
import org.ace.accounting.expense.Iservices.IExpenseService;
import org.ace.accounting.user.User;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.ParamId;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

@ManagedBean(name = "ManageExpenseChartActionBean")
@ViewScoped
public class ManageExpenseChartActionBean extends BaseBean {

	@ManagedProperty(value = "#{ExpenseService}")
	private IExpenseService expenseService;

	@ManagedProperty(value = "#{ExpenseChartService}")
	private IExpenseChartService expenseChartService;

	public void setExpenseService(IExpenseService expenseService) {
		this.expenseService = expenseService;
	}

	public void setExpenseChartService(IExpenseChartService expenseChartService) {
		this.expenseChartService = expenseChartService;
	}

	private BarChartModel expenseChart;
	private String selectedCategoryId;
	private String selectedCategoryName;
	private String timeperiod;
	private Date selectedDate;
	private List<Category> categoryList;
	private String userid;
	private User currentUser;
	private List<Object[]> results;

	@PostConstruct
	public void init() {
		currentUser = (User) getParam(ParamId.LOGIN_USER);
		userid = currentUser.getId();
		loadCategories();
		createNewExpenseChart();
//		createTestChart();
//		selectedDate = new Date();
	}

	public void loadCategories() {
		categoryList = expenseService.findAllCategory();
	}

	public void createNewExpenseChart() {
		expenseChart = new BarChartModel();
	}

	public void updateChart() {
		if (selectedCategoryId == null || timeperiod == null || timeperiod.isEmpty()) {
			createNewExpenseChart();;
			return;
		}

		if (selectedDate == null) {
			selectedDate = new Date();
		}
		Map<Integer, Integer> daysExpenses = new LinkedHashMap<>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(selectedDate);
		System.out.println("timeperiod is: " + timeperiod);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		int daysOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		switch (timeperiod) {
		case "DAILY":
			results = expenseChartService.getDailyExpenses(selectedCategoryId, userid, month, year);
			for(int i = 1; i <= daysOfMonth ; i++) {
				daysExpenses.put(i, 0);
			}
			if (results != null) {
				System.out.println("in the result loop " + results.size());
				for (Object[] row : results) {
					int day = ((Number)row[0]).intValue();
					int amount = ((Number) row[1]).intValue();
					daysExpenses.put(day, amount);
				}
			}	
			break;
		case "MONTHLY":
			results = expenseChartService.getMonthlyExpenses(selectedCategoryId, userid, year);
			break;
		default:
			results = expenseChartService.getYearlyExpenses(selectedCategoryId, userid);
			break;
		}
		BarChartModel model = new BarChartModel();
		ChartSeries series = new ChartSeries();
		
		for(Map.Entry<Integer, Integer> e : daysExpenses.entrySet()) {
			series.set(String.valueOf(e.getKey()), e.getValue());
		}

//		if (results != null) {
//			System.out.println("in the result loop " + results.size());
//			for (Object[] row : results) {
//				System.out.println("This is "+ row[0].toString());
//				series.set(row[0], ((Number) row[1]).intValue());
//				
//			}
//		}
		series.setLabel(selectedCategoryName + "(" + timeperiod + ")");
		model.addSeries(series);
		expenseChart = model;
	    expenseChart.setTitle("Expenses");
	    expenseChart.setLegendPosition("ne");
	    expenseChart.setAnimate(true);
	    expenseChart.setShowDatatip(true);
	    expenseChart.setStacked(false);
	}
//	public void createTestChart() {
//	    expenseChart = new BarChartModel();
//
//	    ChartSeries series1 = new ChartSeries();
//	    series1.setLabel("Food");
//	    series1.set("Jan", 120);
//	    series1.set("Feb", 100);
//	    series1.set("Mar", 140);
//
//	    ChartSeries series2 = new ChartSeries();
//	    series2.setLabel("Transport");
//	    series2.set("Jan", 80);
//	    series2.set("Feb", 90);
//	    series2.set("Mar", 70);
//
//	    expenseChart.addSeries(series1);
//	    expenseChart.addSeries(series2);
//
//	    expenseChart.setTitle("Test Expenses");
//	    expenseChart.setLegendPosition("ne");
//	    expenseChart.setAnimate(true);
//	    expenseChart.setShowDatatip(true);
//	    expenseChart.setStacked(false);
//	}
	
	public void selectCategory(String categoryId,String categoryName) {
	    this.selectedCategoryId = categoryId;
	    this.selectedCategoryName = categoryName;
	    updateChart();
	}
	
	public BarChartModel getExpenseChart() {
		return expenseChart;
	}

	public void setExpenseChart(BarChartModel expenseChart) {
		this.expenseChart = expenseChart;
	}

	public String getSelectedCategory() {
		return selectedCategoryId;
	}

	public void setSelectedCategory(String selectedCategory) {
		this.selectedCategoryId = selectedCategory;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public IExpenseService getExpenseService() {
		return expenseService;
	}

	public IExpenseChartService getExpenseChartService() {
		return expenseChartService;
	}

	public String getTimeperiod() {
		return timeperiod;
	}

	public void setTimeperiod(String timeperiod) {
		this.timeperiod = timeperiod;
	}

	public Date getSelectedDate() {
		return selectedDate;
	}

	public void setSelectedDate(Date selectedDate) {
		this.selectedDate = selectedDate;
	}

	public String getSelectedCategoryName() {
		return selectedCategoryName;
	}

	public void setSelectedCategoryName(String selectedCategoryName) {
		this.selectedCategoryName = selectedCategoryName;
	}

}

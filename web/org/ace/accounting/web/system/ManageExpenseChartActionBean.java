package org.ace.accounting.web.system;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.accounting.expense.Entity.Category;
import org.ace.accounting.expense.Iservices.IExpenseChartService;
import org.ace.accounting.expense.Iservices.IExpenseService;
import org.ace.java.web.common.BaseBean;
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
	private String selectedCategory;
	private String timeperiod;
	private List<Category> categoryList;

	@PostConstruct
	public void init() {
		loadCategories();
		createNewExpenseChart();
	}

	public void loadCategories() {
		categoryList = expenseService.findAllCategory();
	}

	public void createNewExpenseChart() {
		expenseChart = new BarChartModel();
	}
	
	public void updateChart() {
		if(selectedCategory == null || timeperiod == null || timeperiod.isEmpty()) {
			expenseChart.clear();
			return;
		}
		
		expenseChart = new BarChartModel();
		ChartSeries series = new ChartSeries();
		
		series.setLabel(selectedCategory + "(" + timeperiod + ")");
        if(timeperiod.equals("DAILY")) {
            series.set("1 Jan", 100);
            series.set("2 Jan", 120);
        } else if(timeperiod.equals("MONTHLY")) {
            series.set("Jan", 500);
            series.set("Feb", 700);
        } else if(timeperiod.equals("YEARLY")) {
            series.set("2023", 6000);
            series.set("2024", 7200);
        }
        
        expenseChart.addSeries(series);
	}
	
//	public void setCategory(Category cat) {
//		this.selectedCategory = 
//	}

	public BarChartModel getExpenseChart() {
		return expenseChart;
	}

	public void setExpenseChart(BarChartModel expenseChart) {
		this.expenseChart = expenseChart;
	}

	public String getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(String selectedCategory) {
		this.selectedCategory = selectedCategory;
		updateChart();
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

}

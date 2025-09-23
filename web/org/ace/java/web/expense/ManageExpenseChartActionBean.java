package org.ace.java.web.expense;

import java.util.ArrayList;
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

	private Integer selectedYear;
	private List<Integer> yearsList;
	private BarChartModel expenseChart;
	private String selectedCategoryId;
	private String selectedCategoryName;
	private String timeperiod;
	private Date selectedDate;
	private List<Category> categoryList;
	private String userid;
	private User currentUser;
	private Map<String, String> colorsMap;
//	private LinkedHashMap<Integer, Integer> chosenTimeExpenses;

	@PostConstruct
	public void init() {
		currentUser = (User) getParam(ParamId.LOGIN_USER);
		userid = currentUser.getId();
		loadCategories();
		createNewExpenseChart();
		loadYearsList();
		selectedYear = Calendar.getInstance().get(Calendar.YEAR);
//		createTestChart();
		defaultStartView();
		setColorsForEachCategory();
		updateChart();
	}

	public void loadYearsList() {
		int currentyear = Calendar.getInstance().get(Calendar.YEAR);
		yearsList = new ArrayList<>();
		for (int i = (currentyear - 20); i <= currentyear; i++) {
			yearsList.add(i);
		}
	}

	public void loadCategories() {
		categoryList = expenseService.findAllCategory();
	}

	public void createNewExpenseChart() {
		expenseChart = new BarChartModel();
	}

	public void updateChart() {
		BarChartModel model = new BarChartModel();
		Calendar calendar = Calendar.getInstance();
		
//		if (selectedCategoryId == null || timeperiod == null || timeperiod.isEmpty()) {
//			createNewExpenseChart();
//			return;
//		}

		if (selectedDate != null) {
			calendar.setTime(selectedDate);
		} else {
			System.out.println("selectedDate is null → using today");
			selectedDate = new Date();
		}

//		Map<Integer, Integer> daysExpenses = new LinkedHashMap<>();
//		Map<Integer, Integer> monthsExpenses = new LinkedHashMap<>();

		System.out.println("timeperiod is: " + timeperiod);

		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		int daysOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		int monthsOfYear = 12;
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);

		System.out.println("month" + month + "year" + year + "daysofmonth" + daysOfMonth + "monthsofyear" + monthsOfYear
				+ "currentyear" + currentYear);
		

		if ("All".equals(selectedCategoryId)) {
			

			for (Category cat : categoryList) {
				System.out.println("in All categories");
				LinkedHashMap<Integer, Integer> chosenTimeExpenses = accordingToPeriod(timeperiod, cat.getId(), userid,
						month, year, daysOfMonth, monthsOfYear, currentYear);

				ChartSeries series = new ChartSeries();
				series.setLabel(cat.getName());

				for (Map.Entry<Integer, Integer> e : chosenTimeExpenses.entrySet()) {
					series.set(String.valueOf(e.getKey()), e.getValue());
				}
				model.addSeries(series);
				
			}
			
			model.setStacked(true);
			/* setting color for each categories */
			List<String> colors = new ArrayList<>();
			for(Category c:categoryList) {
				colors.add(colorsMap.get(c.getId()));
			}
			model.setSeriesColors(String.join(",", colors));
		} else {
			LinkedHashMap<Integer, Integer> chosenTimeExpenses = accordingToPeriod(timeperiod, selectedCategoryId,
					userid, month, year, daysOfMonth, monthsOfYear, currentYear);

			ChartSeries series = new ChartSeries();
			series.setLabel(selectedCategoryName + " (" + timeperiod + ")");

			for (Map.Entry<Integer, Integer> e : chosenTimeExpenses.entrySet()) {
				series.set(String.valueOf(e.getKey()), e.getValue());
			}
			
			model.addSeries(series);
			String color = "0000FF"; // fallback
			if (colorsMap != null && colorsMap.containsKey(selectedCategoryId)) {
				color = colorsMap.get(selectedCategoryId);
			}
			model.setSeriesColors(color);
		}
		expenseChart = model;
		expenseChart.setTitle("Expenses");
		expenseChart.setLegendPosition("ne");
		expenseChart.setAnimate(true);
		expenseChart.setShowDatatip(true);
//		expenseChart.setShowPointLabels(true);
		expenseChart.setBarWidth(15);

	}

	public LinkedHashMap<Integer, Integer> calculateActualLength(Map<Integer, Integer> map, List<Object[]> result,
			int maxTimePeriod, String period) {

		if ("DAILY".equals(period) || "MONTHLY".equals(period)) {
			for (int i = 1; i <= maxTimePeriod; i++) {
				map.put(i, 0);
			}
		} else if ("YEARLY".equals(period)) {
			for (int i = (maxTimePeriod - 20); i <= maxTimePeriod; i++) { // for year must be mininum year is last 20
																			// year up to
				map.put(i, 0);
			}
		}
		if (result != null) {
			System.out.println("in the result loop " + result.size());
			for (Object[] row : result) {
				int currentChosenTime = ((Number) row[0]).intValue();
				int amount = ((Number) row[1]).intValue();
				map.put(currentChosenTime, amount);
			}
		}

		return new LinkedHashMap<>(map);
	}

	public LinkedHashMap<Integer, Integer> accordingToPeriod(String period, String selectedCategory, String userid,
			int month, int year, int daysOfMonth, int monthsOfYear, int currentYear) {
		Map<Integer, Integer> map = new LinkedHashMap<>();
		List<Object[]> results = null;

		switch (period) {
		case "DAILY":
			results = expenseChartService.getDailyExpenses(selectedCategory, userid, month, year);
			return calculateActualLength(map, results, daysOfMonth, period);

		case "MONTHLY":
			int yearToUse = (selectedYear != null) ? selectedYear : Calendar.getInstance().get(Calendar.YEAR);
			results = expenseChartService.getMonthlyExpenses(selectedCategory, userid, yearToUse);
			return calculateActualLength(map, results, monthsOfYear, period);

		case "YEARLY":
			results = expenseChartService.getYearlyExpenses(selectedCategory, userid);
			return calculateActualLength(map, results, currentYear, period);

		default:
			return new LinkedHashMap<>();
		}

	}

	public void defaultStartView() {
	    selectedCategoryId = "All";
	    selectedCategoryName = "All Categories";
	    timeperiod = "DAILY";
	    selectedDate = new Date();
	}
	
	public void selectCategory(String categoryId, String categoryName) {
		this.selectedCategoryId = categoryId;
		this.selectedCategoryName = categoryName;
		updateChart();
	}

	public void setColorsForEachCategory() {
		colorsMap = new LinkedHashMap<>();
		int i = 0;
		/*
		 * no need to set '#' in front of color code, BarchartModel dont recognize when
		 * start with '#'
		 */
		String[] palette = { "FF6384", // pink/red
				"36A2EB", // blue
				"9966FF", // purple
				"FFCE56", // yellow
				"FF9F40", // orange
				"2ecc71", // green
				"e74c3c", // dark red
				"8E44AD", // violet
		        "1ABC9C", // turquoise
		        "F39C12", // amber
		        "3498DB", // sky blue
		        "C0392B"  // crimson
		};
		for (Category c : categoryList) {
			String color = palette[i % palette.length];
			colorsMap.put(c.getId(), color);
			i++;
		}
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
//	public void createTestChart() {
//    expenseChart = new BarChartModel();
//
//    ChartSeries series1 = new ChartSeries();
//    series1.setLabel("Food");
//    series1.set("Jan", 120);
//    series1.set("Feb", 100);
//    series1.set("Mar", 140);
//
//    ChartSeries series2 = new ChartSeries();
//    series2.setLabel("Transport");
//    series2.set("Jan", 80);
//    series2.set("Feb", 90);
//    series2.set("Mar", 70);
//
//    expenseChart.addSeries(series1);
//    expenseChart.addSeries(series2);
//
//    expenseChart.setTitle("Test Expenses");
//    expenseChart.setLegendPosition("ne");
//    expenseChart.setAnimate(true);
//    expenseChart.setShowDatatip(true);
//    expenseChart.setStacked(false);
//}

	public List<Integer> getYearsList() {
		return yearsList;
	}

	public void setYearsList(List<Integer> yearsList) {
		this.yearsList = yearsList;
	}

	public int getSelectedYear() {
		return selectedYear;
	}

	public void setSelectedYear(int selectedYear) {
		this.selectedYear = selectedYear;
	}
}

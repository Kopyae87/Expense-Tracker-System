package org.ace.accounting.expense.Entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import org.ace.accounting.common.BasicEntity;
import org.ace.accounting.user.User;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = "Expense")
@TableGenerator(name = "EXPENSE_GEN", table = "ID_GEN" , pkColumnName = "GEN_NAME" , valueColumnName = "GEN_VAL" , pkColumnValue = "EXPENSE_GEN" , allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class Expense {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "EXPENSE_GEN")
	private String id;
	
//	@ManyToOne
//	@JoinColumn(name = "user_id", nullable = true)// dont forget to change back to false for nullable
//	private ExpenseUser user;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)// dont forget to change back to false for nullable
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "category_id", nullable = true)
	private Category category;
	
	@Column(name = "expense_amount", nullable = false)
	private double amount;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "expense_date")
	private Date expenseDate;
	
	@Column(name = "description")
	private String description;
	
//	@Enumerated(EnumType.STRING)
//	private PaymentType paymenttype;
	@Column(name = "paymentType", nullable = false)
	private String paymentType;
	
	@Version
	private int version;

	@Embedded
	private BasicEntity basicEntity;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getExpense_date() {
		return expenseDate;
	}

	public void setExpense_date(Date expense_date) {
		this.expenseDate = expense_date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BasicEntity getBasicEntity() {
		return basicEntity;
	}

	public void setBasicEntity(BasicEntity basicEntity) {
		this.basicEntity = basicEntity;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

//	public PaymentType getPaymenttype() {
//		return paymenttype;
//	}
//
//	public void setPaymenttype(PaymentType paymenttype) {
//		this.paymenttype = paymenttype;
//	}
	
	
}

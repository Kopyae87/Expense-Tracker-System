package org.ace.accounting.expense.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.ace.accounting.common.BasicEntity;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = "Category")
@TableGenerator(name = "CATEGORY_GEN", table = "ID_GEN" , pkColumnName = "GEN_NAME" , valueColumnName = "GEN_VAL" , pkColumnValue = "CATEGORY_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "CATEGORY_GEN")
	private String id;
	
	@Column(name = "category_name", nullable = false)
	private String name;
	
	private String description;
	
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true ,fetch = FetchType.LAZY)
	private List<Budget> budgets = new ArrayList<>();
	
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true ,fetch = FetchType.LAZY)
    private List<Expense> expenses = new ArrayList<>();
	
	@Version
	private int version;
	
	@Embedded
	private BasicEntity basicEntity;
	
    public Category(String name, String description) {
		this.name = name;
		this.description = description;
	}
    
    public Category() {
    }

	public void addBudget(Budget budget) {
        budgets.add(budget);
        budget.setCategory(this);
    }

    public void removeBudget(Budget budget) {
        budgets.remove(budget);
        budget.setCategory(null);
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
        expense.setCategory(this);
    }

    public void removeExpense(Expense expense) {
        expenses.remove(expense);
        expense.setCategory(null);
    }
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public BasicEntity getBasicEntity() {
		return basicEntity;
	}

	public void setBasicEntity(BasicEntity basicEntity) {
		this.basicEntity = basicEntity;
	}
	
//	@ManyToOne
//	@JoinColumn(name = "user_id", nullable = true)
//	private ExpenseUser user;
	
//	@ManyToOne
//	@JoinColumn(name = "parent_category_id", nullable = true)
//	private Category parent;
//	
//	@OneToMany(mappedBy = "parent" , cascade = CascadeType.ALL )
//	@Column(name = "")
//	private List<Category> subCategories = new ArrayList<>();
	
}

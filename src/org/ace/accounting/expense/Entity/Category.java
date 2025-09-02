package org.ace.accounting.expense.Entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.ace.accounting.common.BasicEntity;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = "Categories")
@TableGenerator(name = "CATEGORY_GEN", table = "ID_GEN" ,pkColumnValue = "GEN_NAME" , valueColumnName = "GEN_VAL" , allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "CATEGORY_GEN")
	private String id;
	
	@Column(name = "category_name")
	private String name;
	
	private String description;
	
//	@ManyToOne
//	@JoinColumn(name = "user_id", nullable = true)
//	private User user;
	
//	@ManyToOne
//	@JoinColumn(name = "parent_category_id", nullable = true)
//	private Category parent;
//	
//	@OneToMany(mappedBy = "parent" , cascade = CascadeType.ALL )
//	@Column(name = "")
//	private List<Category> subCategories = new ArrayList<>();

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

//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}

//	public Category getParent() {
//		return parent;
//	}
//
//	public void setParent(Category parent) {
//		this.parent = parent;
//	}
//
//	public List<Category> getSubCategories() {
//		return subCategories;
//	}
//
//	public void setSubCategories(List<Category> subCategories) {
//		this.subCategories = subCategories;
//	}

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
	
	
	
}

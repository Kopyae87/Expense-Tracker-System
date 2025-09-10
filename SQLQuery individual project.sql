select * from dbo.ACCUSERS

use [fnigp-acc];

create table ExpenseUser(
	id varchar(50) primary key,
	user_name VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    version INT NOT NULL DEFAULT 0,
    createdUserId VARCHAR(50),
    createdDate DATETIME,
    updatedUserId VARCHAR(50),
    updatedDate DATETIME
);

insert into ExpenseUser(id,user_name,password) values(1,'admin','123');

create table Category(
	id varchar(50) primary key,
	category_name varchar(50) not null,
	description varchar(255),
	version int NOT NULL DEFAULT 0,
    createdUserId VARCHAR(50),
    createdDate DATETIME,
    updatedUserId VARCHAR(50),
    updatedDate DATETIME
);
create table Expense(
	id varchar(50) primary key,
	category_id varchar(50) null,
	--user_id varchar(50) not null,
	user_id char(36) not null,
	expense_amount decimal(12,2) not null,
	--expense_date date not null,
	expense_date datetime not null,
	paymenttype varchar(50),
	description varchar(255),
	version int NOT NULL DEFAULT 0,
    createdUserId VARCHAR(50),
    createdDate DATETIME,
    updatedUserId VARCHAR(50),
    updatedDate DATETIME,
	CONSTRAINT FK_Expense_Category FOREIGN KEY (category_id) REFERENCES Category(id) on delete set null,
	--CONSTRAINT FK_Expense_User foreign key (user_id) REFERENCES ExpenseUser(id)
	CONSTRAINT FK_Expense_User foreign key (user_id) REFERENCES dbo.ACCUSERS(id)
);
create table Budget(
	id varchar(50) primary key,
	category_id varchar(50) null,
	--user_id VARCHAR(50) NOT NULL,
	user_id char(36) not null,
	yearly int not null, 
	monthly int null,
	daily int null,
	amount_limit decimal(12,2) not null,
	version int NOT NULL DEFAULT 0,
    createdUserId VARCHAR(50),
    createdDate DATETIME,
    updatedUserId VARCHAR(50),
    updatedDate DATETIME,
	--CONSTRAINT FK_Budget_User foreign key (user_id) REFERENCES ExpenseUser(id),
	CONSTRAINT FK_Budget_User foreign key (user_id) REFERENCES  dbo.ACCUSERS(id),
	CONSTRAINT FK_Budget_Category foreign key (category_id) REFERENCES Category(id) on delete set null
)
select * from Budget
select * from Expense
select * from Category
select * from ExpenseUser

select * from ACCUSERS
SELECT 
    f.name AS FK_name,
    OBJECT_NAME(f.parent_object_id) AS TableName,
    COL_NAME(fc.parent_object_id, fc.parent_column_id) AS ColumnName,
    OBJECT_NAME (f.referenced_object_id) AS RefTableName
FROM sys.foreign_keys AS f
INNER JOIN sys.foreign_key_columns AS fc 
    ON f.object_id = fc.constraint_object_id;

ALTER TABLE Expense DROP CONSTRAINT FK_Expense_Category;
ALTER TABLE Budget DROP CONSTRAINT FK_Budget_Category;
ALTER TABLE Categories DROP CONSTRAINT FK_Categories_Parent;

ALTER TABLE Expense
ADD CONSTRAINT FK_Expense_Category 
FOREIGN KEY (category_id) REFERENCES Categories(category_id);

ALTER TABLE Budget
ADD CONSTRAINT FK_Budget_Category 
FOREIGN KEY (category_id) REFERENCES Categories(category_id);

ALTER TABLE Categories
ADD CONSTRAINT FK_Categories_Parent 
FOREIGN KEY (parent_category_id) REFERENCES Categories(category_id);

drop table Budget
drop table Category
drop table Expense
drop table ExpenseUser

-- Drop foreign keys on Expense
ALTER TABLE Expense DROP CONSTRAINT FK_Expense_User;
ALTER TABLE Expense DROP CONSTRAINT FK_Expense_Category;

-- Drop foreign keys on Budget
ALTER TABLE Budget DROP CONSTRAINT FK_Budget_User;
ALTER TABLE Budget DROP CONSTRAINT FK_Budget_Category;

-- Drop foreign keys on Categories
ALTER TABLE Categories DROP CONSTRAINT FK_Categories_User;
ALTER TABLE Categories DROP CONSTRAINT FK_Categories_Parent;



	--CONSTRAINT FK_Expense_User foreign key (user_id) REFERENCES dbo.ACCUSERS(id),
	--user_id char(36) null,
	--CONSTRAINT FK_Categories_User foreign key (user_id) REFERENCES dbo.ACCUSERS(id),
	--parent_category_id varchar(50) null,
	--CONSTRAINT FK_Categories_Parent foreign key (parent_category_id) REFERENCES Categories(category_id) on delete set null
	--user_id char(36) foreign key references dbo.ACCUSERS(id),
	--category_id varchar(50) foreign key references Categories(category_id),
	--user_id char(36) null foreign key references dbo.ACCUSERS(id),
	--parent_category_id varchar(50) null foreign key references Categories(category_id),

select * from ID_GEN where GEN_NAME='CATEGORY_GEN';

insert into ID_GEN(GEN_NAME, GEN_VAL) values('CATEGORY_GEN', 50);
insert into ID_GEN(GEN_NAME, GEN_VAL) values('EXPENSE_GEN', 50);

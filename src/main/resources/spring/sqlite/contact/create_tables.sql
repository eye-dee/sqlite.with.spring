Drop Table If Exists ContactPhoneDetail;
Drop table If Exists Contact;
Create table if Not exists Contact (
	id integer Primary Key Autoincrement,
	firstName varchar(60) Not Null,
	lastName varchar(40) Not Null,
	birthDate date,
	Constraint uq_contact unique (firstName, lastName)
);
Create Table If Not Exists ContactPhoneDetail (
	id integer Primary Key Autoincrement,
	contactId int NOT NULL,
	phoneType varchar(20) NOT NULL,
	phoneNumber varchar(20) NOT NULL,
	Constraint uq_contact_phone_detail unique (contactId,phoneType),
	Foreign Key (contactId) References Contact(id)
);

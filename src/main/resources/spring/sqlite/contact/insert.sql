delete from contact;
insert into contact (firstName, lastName, birthDate)
	values ('Igor', 'Vereninov', '1990-05-03');
insert into contact (firstName, lastName, birthDate)
	values ('Sergey', 'Popov', '1000-04-02');
insert into contact (firstName, lastName, birthDate)
	values ('Andrey', 'Tutygin', '1990-05-03');
insert into contact (firstName, lastName, birthDate)
	values ('Fedor', 'Fedorov', '1990-05-03');
insert into contact (firstName, lastName, birthDate)
	values ('Petr', 'Petrov', '1990-05-03');
insert into contact (firstName, lastName, birthDate)
	values ('Anrey', 'Andereev', '1990-05-03');
insert into contact (firstName, lastName, birthDate)
	values ('Semen', 'Semenov', '1990-05-03');
insert into contact (firstName, lastName, birthDate)
	values ('Sasha', 'Sasheva', '1990-05-03');

select * from contact;

delete from contactPhoneDetail;

insert into contactPhoneDetail(contactId, phoneType, phoneNumber)
	values (1, 'Mobile', '1234567890');
insert into contactPhoneDetail(contactId, phoneType, phoneNumber)
	values (1, 'Home', '1234567890');

insert into contactPhoneDetail(contactId, phoneType, phoneNumber)
	values (2, 'Mobile', '1234567890');
insert into contactPhoneDetail(contactId, phoneType, phoneNumber)
	values (2, 'Home', '1234567890');

insert into contactPhoneDetail(contactId, phoneType, phoneNumber)
	values (3, 'Mobile', '1234567890');
insert into contactPhoneDetail(contactId, phoneType, phoneNumber)
	values (3, 'Home', '1234567890');

insert into contactPhoneDetail(contactId, phoneType, phoneNumber)
	values (4, 'Mobile', '1234567890');

insert into contactPhoneDetail(contactId, phoneType, phoneNumber)
	values (5, 'Home', '1234567890');

select * from contactPhoneDetail;

select * from contactPhoneDetail
inner join contact on contact.id = contactPhoneDetail.contactId;
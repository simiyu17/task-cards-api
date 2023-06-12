-- Clear cards table as this script is used in more than one test class --
delete from cards;

insert into cards(id, name, color, card_status, date_created, created_by_id)
values(10001, 'Test1 task', '#000000', 'TODO', '2023-06-11', 1);

insert into cards(id, name, color, card_status, date_created, created_by_id)
values(10002, 'Test2 task', '#FFFFFF', 'IN_PROGRESS', '2023-05-12', 1);

insert into cards(id, name, color, card_status, date_created, created_by_id)
values(10003, 'Test3 task', '#C0C0C0', 'DONE', '2023-04-13', 1);

insert into cards(id, name, color, card_status, date_created, created_by_id)
values(10004, 'Test4 task', '#808080', 'DELETED', '2023-03-14', 1);

insert into cards(id, name, color, card_status, date_created, created_by_id)
values(10005, 'Test5 task', '#FF0000', 'TODO', '2023-06-12', 2);

insert into cards(id, name, color, card_status, date_created, created_by_id)
values(10006, 'Test6 task', '#800080', 'IN_PROGRESS', '2023-05-13', 2);

insert into cards(id, name, color, card_status, date_created, created_by_id)
values(10007, 'Test7 task', '#008000', 'DONE', '2023-04-12', 2);

insert into cards(id, name, color, card_status, date_created, created_by_id)
values(10008, 'Test8 task', '#00FFFF', 'DELETED', '2023-03-11', 2);
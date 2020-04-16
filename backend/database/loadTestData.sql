
INSERT INTO families (name)
VALUES ('Melon');
INSERT INTO families (name)
VALUES ('Stier');
INSERT INTO families (name)
VALUES ('Corrill');
INSERT INTO families (name)
VALUES ('Shell');
INSERT INTO families (name)
VALUES ('Lewis');

INSERT INTO users (family_id, username, password, salt, role) 
VALUES (1, 'Joe', 'joesPassword', '1234567890', 'parent');
INSERT INTO users (family_id, username, password, salt, role) 
VALUES (2, 'Steve', 'fred', '1234567890', 'parent');
INSERT INTO users (family_id, username, password, salt, role) 
VALUES (3, 'Trent', 'trentsPassword', '1234567890', 'parent');
INSERT INTO users (family_id, username, password, salt, role) 
VALUES (4, 'Maggie', 'maggiesPassword', '1234567890', 'parent');
INSERT INTO users (family_id, username, password, salt, role) 
VALUES (5, 'Krista', 'kristasPassword', '1234567890', 'parent');
INSERT INTO users (family_id, username, password, salt, role) 
VALUES (5, 'Child Krista', 'childKristasPassword', '1234567890', 'child');
INSERT INTO users (family_id, username, password, salt, role) 
VALUES (2, 'stevie', 'stevie', '1234567890', 'child');

INSERT INTO books (family_id, isbn, title, author, image, description)
VALUES(1, 1, 'Harry Potter', 'J.K. Rowling', 'img.png', 'The boy who lived.');
INSERT INTO books (family_id, isbn, title, author, image, description)
VALUES(2, 1, 'The Road', 'Cormac McCarthy', 'img.png', 'Post apocolyptic.');
INSERT INTO books (family_id, isbn, title, author, image, description)
VALUES(3, 1, 'The Shining', 'Stephen King', 'img.png', 'Made into a movie.');
INSERT INTO books (family_id, isbn, title, author, image, description)
VALUES(4, 1, 'The Long Walk', 'Stephen King', 'img.png', 'Post apocolyptic.');
INSERT INTO books (family_id, isbn, title, author, image, description)
VALUES(5, 1, 'Eragon', 'Christopher Paolini', 'img.png', 'Dragons and magic and stuff.');

INSERT INTO reading_log (user_id, book_id, reading_format, time_spent, notes, completed_book)
VALUES (1, 1, 'Paper', 34, 'NOTES',false);
INSERT INTO reading_log (user_id, book_id, reading_format, time_spent, notes, completed_book)
VALUES (2, 2, 'Audiobook', 30, 'Scary book.',false);
INSERT INTO reading_log (user_id, book_id, reading_format, time_spent, notes, completed_book)
VALUES (3, 3, 'Other', 45, 'I liked the movie better.',false);
INSERT INTO reading_log (user_id, book_id, reading_format, time_spent, notes, completed_book)
VALUES (4, 4, 'Digital', 10, 'Did not like it.',false);
INSERT INTO reading_log (user_id,  book_id, reading_format, time_spent, notes, completed_book)
VALUES (5, 5, 'Paper', 34, 'Love this book.',false);

INSERT INTO prizes(family_id, prize_name, prize_description, minutes_needed, user_role, max_prizes, begin_date, end_date)
VALUES(2,'prize 1','prize 1 desc', 10, 'child',1,'2020-04-01','2020-05-01');
INSERT INTO prizes(family_id, prize_name, prize_description, minutes_needed, user_role, max_prizes, begin_date, end_date)
VALUES(2,'prize 2','prize 2 desc', 20, 'parent',2,'2020-04-02','2020-05-02');
INSERT INTO prizes(family_id, prize_name, prize_description, minutes_needed, user_role, max_prizes, begin_date, end_date)
VALUES(2,'prize 3','prize 3 desc', 30, 'both',3,'2020-04-03','2020-05-03');

INSERT INTO prizes_users(prize_id, user_id)
VALUES(1,7);
INSERT INTO prizes_users(prize_id, user_id)
VALUES(3,7);
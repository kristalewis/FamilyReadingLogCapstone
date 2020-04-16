INSERT INTO books (family_id, isbn, title, author, image, description) 
VALUES (111, 1245, 'Joe Dirt', 'Joe Melon', 'j.jpeg', 'janitor to engineer');

SELECT isbn, title, author, image, description
FROM books;

SELECT id, username, role, family_id
FROM users;

log_id serial PRIMARY KEY,
user_id FOREIGN KEY NOT NULL,
book_id FOREIGN KEY NOT NULL,
reading_format varchar NOT NULL,
time_spent INTEGER NOT NULL,
notes varchar,

INSERT INTO families (name)
VALUES ('melon');

INSERT INTO books (family_id, isbn, title, author, image, description)
VALUES(1, 1, 'Harry Potter', 'J.K. Rowling', 'img.png', 'The boy who lived.');

INSERT INTO users (family_id, username, password, salt, role) 
VALUES (1, 'username', 'password', '1234567890', 'parent');

INSERT INTO reading_log (user_id, book_id, reading_format, time_spent, notes)
VALUES (1, 1, 'Paper', 34, 'NOTES');

INSERT INTO families (name)
VALUES ('melon');

SELECT * FROM families;

SELECT *
FROM users;

SELECT *
FROM reading_log;

SELECT *
FROM books;

SELECT * FROM families;

SELECT *
FROM users;

SELECT *
FROM reading_log;

SELECT * 
FROM books;

--  Gets all child logs for family
    SELECT reading_log.log_id, reading_log.user_id, users.username, books.book_id, books.title, reading_log.reading_format,
    reading_log.time_spent, reading_log.notes
    FROM families
    JOIN users ON families.family_id = users.family_id
    JOIN reading_log ON users.id = reading_log.user_id
    JOIN books ON reading_log.book_id = books.book_id
    WHERE families.family_id = 1 AND users.role = 'child';


SELECT book_id, family_id, isbn, title, author, image, description
FROM books 
WHERE family_id = 5
ORDER BY title;

SELECT prize_id, family_id, prize_name, prize_description, minutes_needed, user_role, max_prizes, begin_date, end_date
FROM prizes;

SELECT prize_id, family_id, prize_name, prize_description, minutes_needed, user_role, max_prizes, begin_date, end_date
FROM prizes
WHERE family_Id = 2;

SELECT prize_user_id, prize_id, user_id, awarded_date
FROM prizes_users;

SELECT username, Sum(minutes_needed) from users u
JOIN prizes_users pu ON  pu.user_id = u.id
JOIN prizes p ON p.prize_id = pu.prize_id
GROUP BY username;
--Gets the books someone is reading based on the books they have logged
-- SELECT books.book_id, books.family_id, isbn, title, author, image, description
-- FROM users
-- JOIN reading_log ON users.id = reading_log.user_id
-- JOIN books ON reading_log.book_id = books.book_id
-- WHERE users.id = 1 AND reading_log.completed_book = false 
-- ORDER BY title;

--Gets a list of the books a person has finished reading
-- SELECT DISTINCT books.book_id, books.family_id, isbn, title, author, image, description
-- FROM users
-- JOIN reading_log ON users.id = reading_log.user_id
-- JOIN books ON reading_log.book_id = books.book_id
-- WHERE users.id = 1 AND reading_log.completed_book = true
-- ORDER BY title;


--Removes a book from their personal bookshelf when they finish it
-- DELETE FROM currently_reading
-- WHERE user_id = 1 AND book_id = 2;

 SELECT * FROM currently_reading;

--Gets the books someone is currently reading
  SELECT books.book_id, books.family_id, isbn, title, author, image, description
  FROM currently_reading
  JOIN books ON currently_reading.book_id = books.book_id
  WHERE currently_reading.user_id = 2
  ORDER BY title;

--Adds a book to someones personal bookshelf
  INSERT INTO currently_reading (user_id, book_id)
  VALUES (1, 2);

-- Gets all the books the children are reading
SELECT DISTINCT books.book_id, books.family_id, isbn, title, author, image, description
FROM books
JOIN currently_reading ON books.book_id = currently_reading.book_id
JOIN users ON currently_reading.user_id = users.id
WHERE books.family_id = 1 AND users.role = 'child'
ORDER BY title;

--Gets all the prizes for a family
SELECT prize_id, family_id, prize_name, prize_description, minutes_needed,
user_role, max_prizes, begin_date, end_date
FROM prizes
WHERE family_id = 1
ORDER BY prize_name

SELECT * FROM prizes;

--Updates a prize
UPDATE prizes
SET family_id = 2, prize_name = 'EPIC PARTY Ice Cream Party', prize_description = 'sprinkles', 
minutes_needed = 20, user_role = 'parent', max_prizes = 8, begin_date = '2020-04-24', end_date = '2020-05-01'
WHERE prize_id = 1

--Deletes a prize
DELETE FROM prizes WHERE prize_id = 3

SELECT prize_user_id, prize_id, user_id, awarded_date
FROM prizes_users

SELECT prizes.prize_id, prizes.family_id, prize_name, prize_description, minutes_needed, user_role, max_prizes, begin_date, end_date 
FROM prizes
JOIN prizes_users ON prizes.prize_id = prizes_users.prize_id
JOIN users ON prizes_users.user_id = users.id
WHERE users.family_id = 2;
WHERE prizes_users.user_id = 7;

UPDATE prizes SET max_prizes = (max_prizes - 1) 
WHERE prize_id = 1;



--Removes prize from view, sets max_prizes to 0
UPDATE prizes SET max_prizes = 0
WHERE prize_id = 1;

SELECT * 
FROM prizes_users

INSERT INTO prizes_users (prize_id, user_id)
VALUES (6, 9),
(7, 10)



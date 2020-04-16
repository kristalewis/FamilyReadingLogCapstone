--------------------------------------------------------------------------------------
-- readinglog  Database Load Script
--------------------------------------------------------------------------------------
-- This Script will create the Required Database for the family Reading log (bookworm) 
-- Capstone  project- It is initially
-- written for loading into a Postgres(v11) database
--------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------
-- History
-- Initial Testing            	        0.0.1	      4/6/2020  Steven Stier
-- Added families Table and fk in Users 0.0.2       4/9/2020  Steven Stier
-- Added prizes and prizes_users table  0.0.3       4/14/2020 Steven Stier
-- Edited prizes and prizes_users table  0.0.3       4/15/2020 Steven Stier and Maggie Shell
--------------------------------------------------------------------------------------
--NOTES:
--This script is for new installations only because it drops database objects
--(tables, views, etc.) before creating them.
--------------------------------------------------------------------------------------
BEGIN TRANSACTION;

DROP TABLE IF EXISTS prizes_users;
DROP TABLE IF EXISTS prizes;
DROP TABLE IF EXISTS currently_reading;
DROP TABLE IF EXISTS reading_log;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS families;

CREATE TABLE families (
  family_id serial PRIMARY KEY,
  name varchar(255) 
);

CREATE TABLE users (
  id serial PRIMARY KEY,
  family_id INTEGER NOT NULL,  -- added to support families tracking
  username varchar(255) NOT NULL UNIQUE,     -- Username
  password varchar(32) NOT NULL,      -- Password
  salt varchar(256) NOT NULL,          -- Password Salt
  role varchar(255) NOT NULL default('user'),
  CONSTRAINT fk_users_family_id FOREIGN KEY (family_id) REFERENCES families (family_id)
);

CREATE TABLE books (
  book_id serial PRIMARY KEY,
  family_id INTEGER NOT NULL,  -- added to support families tracking
  isbn varchar NOT NULL ,     --ISBN
  title varchar(250) NOT NULL,      --BOOK TITLE
  author varchar(250) NOT NULL,     --AUTHOR
  image varchar(250),               --IMAGE NOT SURE IF REQUIRED
  description varchar,              --BOOK DESCRIPTION
  CONSTRAINT fk_books_family_id FOREIGN KEY (family_id) REFERENCES families (family_id)
);

CREATE TABLE prizes(
    prize_id serial PRIMARY KEY,
    family_id INTEGER NOT NULL,
    prize_name varchar NOT NULL, --Did not add unique constraint as the same prize name could be added more than once...But is this what we want? 
    prize_description varchar,
    minutes_needed int NOT NULL,
    user_role varchar NOT NULL,
    max_prizes int NOT NULL, --Do we want not null here? What if it's a small prize always available?
    begin_date date, --Date format should be yyyy-mm-dd by default, so we will need to account for that in how it interacts with the date formatter on the front end.
    end_date date,

    CONSTRAINT fk_family_prizes FOREIGN KEY (family_id) REFERENCES families (family_id),
    CONSTRAINT ck_user_role CHECK (user_role IN ('child', 'parent', 'both')),
    CONSTRAINT ck_max_prizes CHECK (max_prizes >= 0)
);

--M.Shell 4/15 Updated awarded_date to default to current_date/time of insertion into table.
CREATE TABLE prizes_users(
    prize_user_id serial,
    prize_id INTEGER,
    user_id INTEGER,
    awarded_date date DEFAULT(CURRENT_DATE),

    CONSTRAINT pk_prizes_linker PRIMARY KEY (prize_user_id),
    CONSTRAINT fk_linker_prizes FOREIGN KEY (prize_id) REFERENCES prizes (prize_id),
    CONSTRAINT fk_linker_user FOREIGN KEY (user_id) REFERENCES users (id)
);


CREATE TABLE reading_log (
  log_id serial PRIMARY KEY,
  user_id int NOT NULL,
  book_id int NOT NULL,
  reading_format varchar NOT NULL,
  time_spent INTEGER NOT NULL,
  notes varchar,
  completed_book boolean NOT NULL,
  CONSTRAINT fk_user_log FOREIGN KEY (user_id) REFERENCES users (id),
  CONSTRAINT fk_book_log FOREIGN KEY (book_id) REFERENCES books (book_id),
  CONSTRAINT ck_reading_log CHECK (reading_format IN ('Print', 'Digital', 'Audiobook', 'Read-Aloud(Reader)', 'Read-Aloud(Listener)','Other'))
);

CREATE TABLE currently_reading (
  user_id int NOT NULL,
  book_id int NOT NULL,
  CONSTRAINT fk_user_log FOREIGN KEY (user_id) REFERENCES users (id),
  CONSTRAINT fk_book_log FOREIGN KEY (book_id) REFERENCES books (book_id)
);

COMMIT TRANSACTION;







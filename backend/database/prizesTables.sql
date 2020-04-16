BEGIN TRANSACTION;


--Drops:
DROP TABLE IF EXISTS prizes_users;
DROP TABLE IF EXISTS prizes;
--Just needed to have users and families (as part of users) to create linker.
DROP TABLE IF EXISTS users;
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
  CONSTRAINT fk_users_family_id FOREIGN KEY (family_id)REFERENCES families (family_id)
);

--Add again here:

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
    CONSTRAINT ck_user_role CHECK (user_role IN ('Child', 'Parent', 'Both')) 
);

CREATE TABLE prizes_users(
    prize_user_id serial,
    prize_id INTEGER,
    user_id INTEGER,
    awarded_date date,

    CONSTRAINT pk_prizes_linker PRIMARY KEY (prize_user_id),
    CONSTRAINT fk_linker_prizes FOREIGN KEY (prize_id) REFERENCES prizes (prize_id),
    CONSTRAINT fk_linker_user FOREIGN KEY (user_id) REFERENCES users (id)
);

COMMIT TRANSACTION;
CREATE TABLE tasks (
   id SERIAL PRIMARY KEY,
   title VARCHAR NOT NULL,
   description TEXT,
   created TIMESTAMP,
   done BOOLEAN
);
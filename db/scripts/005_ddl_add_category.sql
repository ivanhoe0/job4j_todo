CREATE TABLE categories (
   id SERIAL PRIMARY KEY,
   name VARCHAR UNIQUE NOT NULL
);

CREATE TABLE tasks_categories (
   id SERIAL PRIMARY kEY,
   task_id INT NOT NULL REFERENCES tasks(id),
   category_id INT NOT NULL REFERENCES categories(id),
   UNIQUE(task_id, category_id)
);
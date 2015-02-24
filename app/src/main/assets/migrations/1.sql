CREATE TABLE restaurants (
  id INTEGER PRIMARY KEY,
  name TEXT NOT NULL UNIQUE,
  localized_name TEXT NOT NULL,
  logo_url TEXT,
  string TEXT NOT NULL,
  city TEXT NOT NULL,
  latitude REAL NOT NULL,
  longitude REAL NOT NULL
);

CREATE TABLE tags (
  id INTEGER PRIMARY KEY,
  name TEXT NOT NULL
);

CREATE TABLE lunches (
  id INTEGER PRIMARY KEY,
  date INTEGER NOT NULL
);

CREATE TABLE users (
  id INTEGER PRIMARY KEY,
  first_name TEXT,
  last_name TEXT,
  email TEXT UNIQUE,
  avatar_url TEXT
);

CREATE TABLE votes (
  id INTEGER PRIMARY KEY,
  lunch_id INTEGER NOT NULL,
  user_id INTEGER NOT NULL,
  restaurant_id INTEGER NOT NULL,
  FOREIGN KEY(lunch_id) REFERENCES lunches(id),
  FOREIGN KEY(user_id) REFERENCES users(id),
  FOREIGN KEY(restaurant_id) REFERENCES restaurants(id)
);

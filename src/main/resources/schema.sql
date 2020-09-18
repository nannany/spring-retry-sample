DROP TABLE if exists todo;
CREATE TABLE todo (
    id INTEGER
    ,title TEXT NOT NULL
    ,details TEXT
    ,finished BOOLEAN NOT NULL
);
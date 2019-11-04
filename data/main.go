package data

import (
	"database/sql"
	_ "github.com/mattn/go-sqlite3"
)

func ConnectDb() (*sql.DB, error) {
	// Connect to the database
	db, err := sql.Open("sqlite3", "freemoz.db")
	if err != nil {
		return nil, err
	}

	return db, nil
}

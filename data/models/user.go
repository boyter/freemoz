package models

import (
	"database/sql"
	"github.com/boyter/freemoz/data"
)
type UserModel struct {
	DB *sql.DB
}

func (m *UserModel) Delete(user data.User) error {
	stmt := `DELETE FROM	user 
				   WHERE 	id = ?`
	_, err := m.DB.Exec(stmt, user.Id)
	return err
}

func (m *UserModel) Insert(user data.User) (*data.User, error) {
	stmt := `
		INSERT INTO user(id, account_id, name, email, hashed_password, user_type, created, updated, active)
 		VALUES (NULL,?,?,?,?,1,UTC_TIMESTAMP(),UTC_TIMESTAMP(),1)
	`

	res, err := m.DB.Exec(stmt, user.AccountId, user.Name, user.Email, user.HashedPassword)
	if err != nil {
		return nil, err
	}

	lastId, err := res.LastInsertId()
	if err != nil {
		return nil, err
	}

	// NB potentially an overflow issue here
	usr, err := m.Get(int(lastId))
	if err != nil {
		return nil, err
	}

	return usr, nil
}

func (m *UserModel) Get(id int) (*data.User, error) {
	stmt := `
		SELECT	id,
				account_id,
             	name,
             	email,
             	user_type,
				created,
				updated,
				active
		FROM	user
		WHERE	id = ?
`
	row := m.DB.QueryRow(stmt, id)
	user := &data.User{}

	err := row.Scan(&user.Id, &user.AccountId, &user.Name, &user.Email, &user.UserType, &user.Created, &user.Updated, &user.Active)
	if err == sql.ErrNoRows {
		return nil, data.ErrNoRecord
	} else if err != nil {
		return nil, err
	}

	return user, nil
}

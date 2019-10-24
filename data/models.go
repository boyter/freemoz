package data

import (
	"errors"
	"golang.org/x/crypto/bcrypt"
	"time"
)

var ErrNoRecord = errors.New("data: no matching record found")
var ErrDuplicateEmail = errors.New("data: duplicate email")

type Project struct {
	Id      int
	Name    string
	Created time.Time
	Updated time.Time
}

type Account struct {
	Id      int
	Name    string
	Email   string
	Created time.Time
	Updated time.Time
	Active  bool
	Details string
}

type User struct {
	Id             int
	AccountId      int
	Name           string
	Email          string
	HashedPassword []byte
	UserType       int
	Created        time.Time
	Updated        time.Time
	Active         bool
}

func (u *User) HashPassword(password string) error {
	hashedPassword, err := bcrypt.GenerateFromPassword([]byte(password), 12)

	if err != nil {
		return err
	}

	u.HashedPassword = hashedPassword
	return nil
}

package data

import (
	"testing"
)

func TestUserHash(t *testing.T) {
	user := User{}
	_ = user.HashPassword("something")

	if user.HashedPassword == nil {
		t.Error("Password should now be hashed")
	}

	if len(user.HashedPassword) != 60 {
		t.Error("Password should be 60 characters")
	}
}

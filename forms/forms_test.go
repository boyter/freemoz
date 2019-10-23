package forms

import (
	"testing"
)

func TestEmptyFormInvalid(t *testing.T) {
	form := NewForm(map[string][]string{})

	form.Required("title")
	form.MaxLength("title", 10)
	if form.Valid() {
		t.Error("Expecting form to be invalid")
	}
}

func TestFormValidValueRequiered(t *testing.T) {
	values := map[string][]string{}
	values["title"] = []string{}
	values["title"] = append(values["title"], "something")

	form := NewForm(values)
	form.Required("title")

	if !form.Valid() {
		t.Error("Form should be valid")
	}
}

func TestFormInValidMaxLengthLimit(t *testing.T) {
	values := map[string][]string{}
	values["title"] = []string{}
	values["title"] = append(values["title"], "something")

	form := NewForm(values)
	form.Required("title")
	form.MaxLength("title", 2)

	if form.Valid() {
		t.Error("Form should be valid, error is", form.Errors["title"])
	}
}

package forms

import (
	"fmt"
	"net/url"
	"strings"
	"unicode/utf8"
)

type errors map[string][]string

func (e errors) Add(field string, message string) {
	e[field] = append(e[field], message)
}

func (e errors) Get(field string) string {
	es := e[field]

	if len(es) == 0 {
		return ""
	}

	return es[0]
}

type Form struct {
	url.Values
	Errors errors
}

func NewForm(data url.Values) *Form {
	return &Form{
		Values: data,
		Errors: errors(map[string][]string{}),
	}
}

func (f *Form) Required(fields ...string) {
	for _, field := range fields {
		value := f.Get(field)
		if strings.TrimSpace(value) == "" {
			f.Errors.Add(field, "This field cannot be blank")
		}
	}
}

func (f *Form) MaxLength(field string, d int) {
	value := f.Get(field)
	if value == "" {
		return
	}

	if utf8.RuneCountInString(value) > d {
		f.Errors.Add(field, fmt.Sprintf("This field is too long; maximum length is %d characters", d))
	}
}

func (f *Form) Valid() bool {
	return len(f.Errors) == 0
}

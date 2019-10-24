package models

import (
	"database/sql"
	"github.com/boyter/freemoz/data"
)

type ContentModel struct {
	DB *sql.DB
}


func (m *ContentModel) GetById(id int) (*data.Content, error) {
	stmt := `
		SELECT	id,
				parentid,
             	topic,
             	title,
             	description,
				url
		FROM	content
		WHERE	id = ?
`
	row := m.DB.QueryRow(stmt, id)
	content := &data.Content{}

	err := row.Scan(&content.Id, &content.ParentId, &content.Topic, &content.Title, &content.Description, &content.Url)
	if err == sql.ErrNoRows {
		return nil, data.ErrNoRecord
	} else if err != nil {
		return nil, err
	}

	return content, nil
}
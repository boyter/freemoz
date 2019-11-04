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
		WHERE	id = ?;
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

func (m *ContentModel) GetSitesByTopic(topic string) ([]*data.Content, error) {
	stmt := `
		SELECT id,parentid,topic,title,description,url 
		FROM content 
		WHERE parentid = (select id from structure where topic = ? limit 1) 
		ORDER BY title;
`

	rows, err := m.DB.Query(stmt, topic)
	if err != nil {
		return nil, err
	}
	defer rows.Close()

	structures := []*data.Content{}

	for rows.Next() {
		structure := &data.Content{}

		err := rows.Scan(&structure.Id, &structure.ParentId, &structure.Topic)
		if err != nil {
			return nil, err
		}

		structures = append(structures, structure)
	}

	if err = rows.Err(); err != nil {
		return nil, err
	}

	return structures, nil
}

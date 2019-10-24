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
		select id,parentid,topic,title,description,url 
		from content 
		where parentid = (select id from structure where topic = ? limit 1) 
		order by title;
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

//func (m *ContentModel) GetSites() (*data.Content, error) {
//	stmt := `
//		SELECT	id,
//             	name,
//				created,
//				updated
//		FROM	project
//		ORDER BY updated, id DESC
//		LIMIT ?, ?
//`
//
//	rows, err := m.DB.Query(stmt)
//	if err != nil {
//		return nil, err
//	}
//	defer rows.Close()
//
//	projects := []*data.Project{}
//
//	for rows.Next() {
//		project := &data.Project{}
//
//		err := rows.Scan(&project.Id, &project.Name, &project.Created, &project.Updated)
//		if err != nil {
//			return nil, err
//		}
//
//		projects = append(projects, project)
//	}
//
//	if err = rows.Err(); err != nil {
//		return nil, err
//	}
//
//	return projects, nil
//}

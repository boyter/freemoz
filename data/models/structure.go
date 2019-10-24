package models

import (
	"database/sql"
	"github.com/boyter/freemoz/data"
)

type StructureModel struct {
	DB *sql.DB
}

func (m *StructureModel) GetSubcategoriesByCategory(category string) ([]*data.Structure, error) {
	stmt := `
		select id,parentid,topic
		from structure
		where parentid = (select id from structure where topic = ? limit 1)
		order by topic desc;
`

	rows, err := m.DB.Query(stmt, category)
	if err != nil {
		return nil, err
	}
	defer rows.Close()

	structures := []*data.Structure{}

	for rows.Next() {
		structure := &data.Structure{}

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

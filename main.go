package main

import (
	"github.com/boyter/freemoz/common"
	"github.com/boyter/freemoz/data"
	"github.com/boyter/freemoz/data/models"
	"github.com/boyter/freemoz/handlers"
	"github.com/rs/zerolog/log"

	//"github.com/golangcollege/sessions"
	"net/http"
)

func main() {
	log.Info().Str(common.UNIQUE_CODE, "47b5ef60").Msg("attempting to connect to database")
	db, err := data.ConnectDb()
	if err != nil {
		log.Error().Str(common.UNIQUE_CODE, "aacf1d88").Err(err)
	}
	defer db.Close()

	//session := sessions.New([]byte(""))
	//session.Lifetime = 12 * time.Hour

	// Initialize a new instance of application containing dependencies.
	app := handlers.Application{
		UserModel:      &models.UserModel{DB: db},
		ContentModel:   &models.ContentModel{DB: db},
		StructureModel: &models.StructureModel{DB: db},
		//Session: session,
	}

	srv := &http.Server{
		Addr:     ":8080",
		Handler:  app.Routes(),
	}
	// Run background tasks assuming we have any
	log.Log().Str(common.UNIQUE_CODE, "2a72dd2f").Msg("triggering background jobs")
	//go service.RunBackground()

	log.Log().Str(common.UNIQUE_CODE, "d25e6037").Msg("starting server on 8080")
	err = srv.ListenAndServe()
	log.Error().Str(common.UNIQUE_CODE, "a28d3ee0").Err(err)
}

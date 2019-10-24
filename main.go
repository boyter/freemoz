package main

import (
	"github.com/boyter/freemoz/data"
	"github.com/boyter/freemoz/data/models"
	"github.com/boyter/freemoz/handlers"
	//"github.com/golangcollege/sessions"
	"log"
	"net/http"
	"os"
)

//go:generate go-bindata ./assets/public/...
func main() {
	infoLog := log.New(os.Stdout, "INFO\t", log.Ldate|log.Ltime)
	errorLog := log.New(os.Stderr, "ERROR\t", log.Ldate|log.Ltime|log.Lshortfile)


	//data, err := Asset("assets/public/js/script.js")
	//if err != nil {
	//	// Asset was not found.
	//	fmt.Println(err.Error())
	//}
	//
	//fmt.Println(string(data))

	db, err := data.ConnectDb()
	if err != nil {
		errorLog.Fatal(err)
	}
	defer db.Close()

	//session := sessions.New([]byte(""))
	//session.Lifetime = 12 * time.Hour

	// Initialize a new instance of application containing dependencies.
	app := handlers.Application{
		ErrorLog: errorLog,
		InfoLog:  infoLog,
		UserModel: &models.UserModel{DB: db},
		//Session: session,
	}

	srv := &http.Server{
		Addr:     ":8080",
		ErrorLog: errorLog,
		Handler:  app.Routes(),
	}

	// Run background tasks
	//go service.RunBackground()

	// Use the http.ListenAndServe() function to start a new web server. We pass in
	// two parameters: the TCP network address to listen on (in this case ":8080")
	// and the servemux we just created. If http.ListenAndServe() returns an error
	// we use the log.Fatal() function to log the error message and exit.
	infoLog.Println("Starting server on :8080")
	err = srv.ListenAndServe()
	errorLog.Fatal(err)
}

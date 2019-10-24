package handlers

import (
	"github.com/boyter/freemoz/data/models"

	//"github.com/golangcollege/sessions"
	"github.com/gorilla/mux"
	"log"
	"net/http"
)

// Define an application struct to hold the application-wide dependencies for the
// web application. For now we'll only include fields for the two custom loggers, but
// we'll add more to it as the build progresses.
type Application struct {
	ErrorLog     *log.Logger
	InfoLog      *log.Logger
	UserModel    *models.UserModel
	ContentModel *models.ContentModel
	//Session      *sessions.Session
}

func (app *Application) Routes() *mux.Router {

	// Cookie middleware
	//cookieMiddleware := alice.New(app.Session.Enable)

	// Use the http.NewServeMux() function to initialize a new servemux, then
	// register the home function as the handler for the "/" URL pattern.
	// It is good practice to create a new one to avoid the default global one
	// being polluted by imports
	router := mux.NewRouter()
	router.Handle("/", http.HandlerFunc(app.Home)).Methods("GET")
	router.Handle("/help/", http.HandlerFunc(app.Help)).Methods("GET")
	router.Handle("/health-check/", http.HandlerFunc(app.HealthCheck)).Methods("GET")

	// User login
	//router.Handle("/user/login/", cookieMiddleware.ThenFunc(app.LoginForm)).Methods("GET")
	//router.Handle("/user/login/", cookieMiddleware.ThenFunc(app.LoginUser)).Methods("POST")

	// Setup to serve files from the supplied directory
	fileServer := http.FileServer(http.Dir("./assets/ui/static/"))
	router.PathPrefix("/static/").Handler(http.StripPrefix("/static", fileServer))

	return router
}

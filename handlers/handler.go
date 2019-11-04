package handlers

import (
	"github.com/boyter/freemoz/forms"
	"html/template"
	"net/http"
)

type templateData struct {
	GroupName   string
	ProjectName string
	ProjectPath string
}

func (app *Application) LoginForm(w http.ResponseWriter, r *http.Request) {
	_, _ = w.Write([]byte("Show the login form here"))
}

func (app *Application) LoginUser(w http.ResponseWriter, r *http.Request) {
	form := forms.NewForm(r.PostForm)

	form.Required("username", "password")

	if !form.Valid() {
	}

	_, _ = w.Write([]byte("Attempt to login here"))
}

// Define a home handler function
func (app *Application) Home(w http.ResponseWriter, r *http.Request) {
	if r.URL.Path != "/" {
		http.NotFound(w, r)
		return
	}

	// Use the template.ParseFiles() function to read the template file into a
	// template set. If there's an error, we log the detailed error message and use
	// the http.Error() function to send a generic 500 Internal Server Error
	// response to the user.
	// TODO should cache these templates and better yet build them into the application

	// Initialize a slice containing the paths to the two files. Note that the
	// home.page.tmpl file must be the *first* file in the slice.
	files := []string{
		"./assets/public/html/home.page.tmpl",
		"./assets/public/html/base.layout.tmpl",
		//"./assets/public/html/footer.partial.tmpl",
	}

	ts, err := template.ParseFiles(files...)
	if err != nil {
		app.ErrorLog.Println(err.Error())
		http.Error(w, "Internal Server Error", 500)
		return
	}

	// We then use the Execute() method on the template set to write the template
	// content as the response body. The last parameter to Execute() represents any
	// dynamic data that we want to pass in, which for now we'll leave as nil.
	err = ts.Execute(w, templateData{})

	if err != nil {
		app.ErrorLog.Println(err.Error())
		http.Error(w, "Internal Server Error", 500)
	}
}

func (app *Application) Help(w http.ResponseWriter, r *http.Request) {
	_, _ = w.Write([]byte("Help"))
}

func (app *Application) HealthCheck(w http.ResponseWriter, r *http.Request) {
	_, _ = w.Write([]byte("health check"))
}

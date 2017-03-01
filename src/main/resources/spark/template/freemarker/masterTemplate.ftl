<#macro masterTemplate title="freemoz">
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Freemoz</title>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel='shortcut icon' type='image/x-icon' href='/img/favicon.ico' />
    <script language="javascript" type="text/javascript" src="/js/jquery-1.11.1.min.js"></script>
    <script language="javascript" type="text/javascript" src="/js/underscore-min.js"></script>
    <script language="javascript" type="text/javascript" src="/js/jquery.flot.min.js"></script>
    <script language="javascript" type="text/javascript" src="/js/typeahead.js"></script>
    <script language="javascript" type="text/javascript" src="/js/script.js"></script>
  </head>
  <body>

    <div class="container">
      <!-- Static navbar -->
      <nav class="navbar navbar-default">
        <div class="container-fluid">
          <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
              <span class="sr-only">Toggle navigation</span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/">Freemoz</a>
          </div>
          <form class="navbar-form navbar-left" method="GET" action="/search/">
            <div class="form-group">
              <input type="text" class="form-control" name="q" placeholder="Search Freemoz">
              <input class="hidden" type="submit" />
            </div>
            <button type="submit" class="btn btn-default">Search</button>
          </form>
          <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
              <li><a href="#">About</a></li>
              <li><a href="#">Become an Editor</a></li>
              <li><a href="#">Suggest a Site</a></li>
              <li><a href="#">Login</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div><!--/.container-fluid -->
      </nav>

      <#nested />


      <div>
      About Freemoz
      </div>
    </div> <!-- /container -->
  </body>
</html>
</#macro>
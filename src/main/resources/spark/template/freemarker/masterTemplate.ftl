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
              <li><a href="/login/">Login</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div><!--/.container-fluid -->
      </nav>

      <a href="https://github.com/boyter/freemoz"><img style="position: absolute; top: 0; right: 0; border: 0;" src="https://camo.githubusercontent.com/652c5b9acfaddf3a9c326fa6bde407b87f7be0f4/68747470733a2f2f73332e616d617a6f6e6177732e636f6d2f6769746875622f726962626f6e732f666f726b6d655f72696768745f6f72616e67655f6666373630302e706e67" alt="Fork me on GitHub" data-canonical-src="https://s3.amazonaws.com/github/ribbons/forkme_right_orange_ff7600.png"></a>

      <#nested />


      <div>
      About Freemoz
      </div>
    </div> <!-- /container -->
  </body>
</html>
</#macro>
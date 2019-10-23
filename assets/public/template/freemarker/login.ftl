<#import "masterTemplate.ftl" as layout />
<@layout.masterTemplate title="Admin">

    <h2>Login</h2>
    <p>If you are an editor you can login below using your supplied username and password.</p>

    <form action="/login/" method="post" class="form-inline">
      <div class="form-group">
        <label class="sr-only" for="loginUsername">Username</label>
        <input type="text" class="form-control" id="loginUsername" name="loginUsername" placeholder="Username">
      </div>
      <div class="form-group">
        <label class="sr-only" for="loginPassword">Password</label>
        <input type="password" class="form-control" id="loginPassword" name="loginPassword" placeholder="Password">
      </div>
      <button type="submit" class="btn btn-primary">Sign in</button>
    </form>
</@layout.masterTemplate>
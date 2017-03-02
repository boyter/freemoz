<#import "masterTemplate.ftl" as layout />
<@layout.masterTemplate title="Admin">

    <form action="/login/" method="post" class="form-inline">
      <div class="form-group">
        <label class="sr-only" for="exampleInputUsername3">Username</label>
        <input type="text" class="form-control" id="exampleInputUsername3" placeholder="Username">
      </div>
      <div class="form-group">
        <label class="sr-only" for="exampleInputPassword3">Password</label>
        <input type="password" class="form-control" id="exampleInputPassword3" placeholder="Password">
      </div>
      <button type="submit" class="btn btn-primary">Sign in</button>
    </form>
</@layout.masterTemplate>
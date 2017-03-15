<#import "masterTemplate.ftl" as layout />
<@layout.masterTemplate title="About">

<h1>Suggest</h1>
<p>Suggest a site to be added to the index here.</p>

<#if validationErrors??>
    <#list validationErrors>
        <#items as result>
            <div class="alert alert-danger" role="alert">${result?html}</div>
        </#items>
    </#list>
</#if>



<form method="post">
    <div class="form-group">
        <label for="siteUrl">Site URL</label>
        <input type="text" name="siteUrl" class="form-control" id="siteUrl" maxlength="1000" placeholder="https://mycoolsite.com/" <#if siteUrl??>value="${siteUrl?html}"</#if> >
        <p class="help-block">The URL to the site. HTTPS is preferred.</p>
    </div>

    <div class="form-group">
        <label for="siteCategory">Site Category</label>
        <select id="siteCategory" name="siteCategory" class="form-control">
          <option>Arts</option>
          <option>Business</option>
          <option>Computers</option>
          <option>Games</option>
          <option>Health</option>
          <option>Home</option>
          <option>News</option>
          <option>Recreation</option>
          <option>Reference</option>
          <option>Regional</option>
          <option>Science</option>
          <option>Shopping</option>
          <option>Society</option>
          <option>Sports</option>
        </select>
        <p class="help-block">Which category do you think this site should be under?.</p>
    </div>


    <div class="form-group">
        <label for="siteTitle">Title of Site</label>
        <input type="text" class="form-control" id="siteTitle" name="siteTitle" maxlength="255" placeholder="Bob's Cool Site" <#if siteTitle??>value="${siteTitle}"</#if>>
        <p class="help-block">A short site title. Keep this short and sweet.</p>
    </div>

    <div class="form-group">
        <label for="siteDescription">Description of Site</label>
        <textarea name="siteDescription" class="form-control" rows="3"><#if siteDescription??>${siteDescription}</#if></textarea>
        <p class="help-block">Keep the site description brief, no more than 30 words. The better the description the description the more likely your site is to be listed.</p>
    </div>

    <div class="form-group">
        <label for="siteTags">Site Tags</label>
        <input type="text" class="form-control" id="siteTags" name="siteTags" maxlength="50" placeholder="tag1 tag2 tag3" <#if siteTags??>value="${siteTags}"</#if>>
        <p class="help-block">A few words to used to tag against. Try to keep them relevant.</p>
    </div>

    <div class="form-group" style="display:none;">
        <label for="email">Your Email Address</label>
        <input type="text" class="form-control" id="emailAddress" name="emailAddress" maxlength="255" placeholder="my@email.com" <#if emailAddress??>value="${emailAddress}"</#if>>
        <p class="help-block">Do not fill in this. It's just here to keep out automated spambots.</p>
    </div>

    <div class="checkbox">
        <label>
            <input type="checkbox" id="haveConfirmed" name="haveConfirmed"> I confirm that all information submitted will be licenced under the <a href="https://creativecommons.org/licenses/by/3.0/">Creative Commons 3.0</a> licence.
        </label>
    </div>
    <button type="submit" class="btn btn-primary">Submit</button>
</form>

</@layout.masterTemplate>
<#import "masterTemplate.ftl" as layout />
<@layout.masterTemplate title="About">

<h1>Suggest</h1>
<p>Suggest a site to be added to the index here.</p>


<form method="post">

    <div class="form-group">
        <label for="siteUrl">Site URL</label>
        <input type="text" class="form-control" id="siteUrl" placeholder="https://mycoolsite.com/">
        <p class="help-block">The URL to the site. HTTPS is preferred.</p>
    </div>

    <div class="form-group">
        <label for="siteTitle">Title of Site</label>
        <input type="text" class="form-control" id="siteTitle" placeholder="Bob's Cool Site">
        <p class="help-block">A short site title. Keep this short and sweet.</p>
    </div>

    <div class="form-group">
        <label for="siteDescription">Description of Site</label>
        <textarea class="form-control" rows="3"></textarea>
        <p class="help-block">Keep the site description brief, no more than 30 words. The better the description the description the more likely your site is to be listed.</p>
    </div>

    <div class="form-group">
        <label for="siteTags">Site Tags</label>
        <input type="text" class="form-control" id="siteTags" placeholder="tag1 tag2 tag3">
        <p class="help-block">A few words to used to tag against. Try to keep them relevant.</p>
    </div>

    <div class="form-group" style="display:none;">
        <label for="email">Your Email Address</label>
        <input type="text" class="form-control" id="email" placeholder="my@email.com">
        <p class="help-block">Do not fill in this. It's just here to keep out automated spambots.</p>
    </div>

    <div class="checkbox">
        <label>
            <input type="checkbox"> I have confirmed that the above information appears to be correct
        </label>
    </div>
    <button type="submit" class="btn btn-default">Submit</button>
</form>


</@layout.masterTemplate>
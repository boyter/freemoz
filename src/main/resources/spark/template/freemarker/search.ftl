<#import "masterTemplate.ftl" as layout />
<@layout.masterTemplate title="Search results">

<form class="form-inline" method="GET" action="/search/">
<div class="form-group">
  <input type="text" name="q" class="form-control" placeholder="Search for..." value="${searchResult.query?html}">
</div>
<input class="btn btn-default" type="submit" value="Search" />
</form>

<h2>Results for: ${searchResult.query}</h2>

<#list searchResult.results>
    <dl>
    <#items as result>
        <dt><a href="${result.url}">${result.title}</a></dt>
        <dd>
        ${result.description}<br>
        <a href="${result.url}">${result.url}</a>
        </dd><br>
    </#items>
    </dl>
</#list>

</@layout.masterTemplate>
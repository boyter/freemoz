<#import "masterTemplate.ftl" as layout />
<@layout.masterTemplate title="Freemoz.org a spiritual sucessor to dmoz.org">

<h1><img src="/img/icons/${categoryName?lower_case}.png" alt="Recreation" height="50px" width="50px" /> ${categoryName}</h1>

<ol class="breadcrumb">
  <li><a href="/">Home</a></li>

  <#list breadCrumb>
    <#items as result>
      <li><a href="${result.path}/">${result.name}</a></li>
    </#items>
  </#list>
</ol>

<#list subCategories>
    <div class="page-header">
      <h2>Subcategories <small>(${subCategories?size})</small></h2>
    </div>

    <div class="row">
    <#items as result>
    <div class="col-md-3"><span class="glyphicon glyphicon-folder-open" aria-hidden="true"></span> <a href="/${result.topic}">${result.name?replace("_", " ")}</a></div>
    </#items>
    </div>
</#list>


<#list sites>
    <div class="page-header">
      <h2>Sites <small>(${sites?size})</small></h2>
    </div>

    <dl>
    <#items as result>
        <dt><span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span> <a href="${result.url}">${result.title}</a></dt>
        <dd>${result.description}</dd>
    </#items>
    </dl>
</#list>


</@layout.masterTemplate>
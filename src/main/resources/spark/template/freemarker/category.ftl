<#import "masterTemplate.ftl" as layout />
<@layout.masterTemplate title="Freemoz.org a spiritual sucessor to dmoz.org">

<h1>${categoryName}</h1>

<ol class="breadcrumb">
  <li><a href="/">Home</a></li>
  <li><a href="/${categoryName}/">${categoryName}</a></li>

  <#list breadCrumb>
    <#items as result>
      <li><a href="/${result}/">${result}</a></li>
    </#items>
  </#list>
</ol>

<#list subCategories>
    <div class="page-header">
      <h2>Subcategories <small>(${subCategories?size})</small></h2>
    </div>

    <div class="row">
    <#items as result>
    <div class="col-md-3"><span class="glyphicon glyphicon glyphicon-folder-open" aria-hidden="true"></span> <a href="/${result}">${result}</a></div>
    </#items>
    </div>
</#list>


<#list sites>
    <div class="page-header">
      <h2>Sites <small>(${sites?size})</small></h2>
    </div>

    <dl>
    <#items as result>
        <dt><a href="${result.url}">${result.title}</a></dt>
        <dd>${result.description}</dd>
    </#items>
    </dl>
</#list>


</@layout.masterTemplate>
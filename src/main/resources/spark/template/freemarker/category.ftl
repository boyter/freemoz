<#import "masterTemplate.ftl" as layout />
<@layout.masterTemplate title="Freemoz.org a spiritual sucessor to dmoz.org">

<h1>${categoryName}</h1>

<ol class="breadcrumb">
  <li><a href="/">Home</a></li>
  <li><a href="/${categoryName}/">${categoryName}</a></li>
  <li class="active">Data</li>
</ol>


<div class="page-header">
  <h2>Subcategories <small>(${subCategories?size})</small></h2>
</div>

<#list subCategories>
    <#items as result>
    <a href="/${result}">${result}</a><br />
    </#items>
</#list>

</@layout.masterTemplate>
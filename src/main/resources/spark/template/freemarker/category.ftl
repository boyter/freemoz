<#import "masterTemplate.ftl" as layout />
<@layout.masterTemplate title="Freemoz.org a spiritual sucessor to dmoz.org">

<h1>${categoryName}</h1>

<ol class="breadcrumb">
  <li><a href="/">Home</a></li>
  <li><a href="#">Library</a></li>
  <li class="active">Data</li>
</ol>

<#list subCategories>
    <#items as result>
    <a href="/${result}">${result}</a><br />
    </#items>
</#list>

</@layout.masterTemplate>
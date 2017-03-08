<#import "masterTemplate.ftl" as layout />
<@layout.masterTemplate title="Freemoz.org a spiritual sucessor to dmoz.org">

<h1>${categoryName}</h1>

<#list subCategories>
    <#items as result>
    ${result}<br />
    </#items>
</#list>

</@layout.masterTemplate>
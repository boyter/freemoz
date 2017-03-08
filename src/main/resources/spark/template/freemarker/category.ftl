<#import "masterTemplate.ftl" as layout />
<@layout.masterTemplate title="Freemoz.org a spiritual sucessor to dmoz.org">

<#list subCategories>
    <#items as result>
    ${result}<br />
    </#items>
</#list>

</@layout.masterTemplate>
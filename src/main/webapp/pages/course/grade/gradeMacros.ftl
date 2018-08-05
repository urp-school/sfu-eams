 <#macro emptyTd count>
    <#if (count>0)>
    <#list 1..count as i>
    <td>&nbsp;</td>
    </#list>
    </#if>
 </#macro>
 
 <#macro reserve2 gpa>
  ${gpa?string('0.00')}
 </#macro>
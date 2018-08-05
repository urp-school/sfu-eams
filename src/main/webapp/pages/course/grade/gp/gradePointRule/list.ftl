  <table width="100%" class="listTable" id="ruleListTable" >
   <tr align="center" class="darkColumn" style="font-size:12px" >
     <td>绩点映射列表</td>
   </tr>
   <#assign listSize = (gradePointRuleList?size)?default(0)/>
   <#list gradePointRuleList as rule>
   <tr align="center" height="20px"  class="infoTitle">         
     <td class="padding"  style="width:120px" bgcolor="#ffffff"  <#if rule_index=0>id="defaultRule"</#if>
       onclick="clearSelected(ruleListTable,this);setSelectedRow(ruleListTable,this);loadRule('${rule.id}')"
       onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
      ${rule.name}(<@i18nName rule.markStyle/>)
     </td>
   </tr>
   </#list>
  </table>
<#include "/templates/head.ftl"/>
 <body >  
 <table id="bar"></table>
 <#if RequestParameters['majorType.id']=="1">
   <#include "/pages/components/stdList1stTable.ftl"/>
 <#else>
   <#include "/pages/components/stdList2ndTable.ftl"/>
 </#if>
 <@htm.actionForm name="actionForm" action="degreeAudit.do" entity="std">
 <input type="hidden" name="standard.id" value="${RequestParameters['standard.id']}"/>
 <input type="hidden" name="isAudit" value="false"/>
 </@>
 <script>
   var bar = new ToolBar("bar","没有审核学生列表",null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("审核","multiAction('audit')");
   bar.addItem("<@msg.message key="action.export"/>", "exportData()");
   function exportData(){
      addInput(form,"keys","code,name,enrollYear,department.name,firstMajor.name");
      addInput(form,"titles","<@msg.message key="attr.stdNo"/>,<@msg.message key="attr.personName"/>,所在年级,院系,专业");
      addInput(form, "params", queryStr, "hidden");
      exportList();
    }
 </script>
 </body>   
<#include "/templates/foot.ftl"/> 
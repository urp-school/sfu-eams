<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/itemSelect.js"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="myBar"></table>
<table width="100%"  height="85%" class="frameTable">
    <tr>     
     <td style="width:20%" class="frameTable_view">
     <#include "list.ftl"/>
     </td>
     
     <td valign="top" width="80%">
     <iframe  src="#"
     id="contentFrame" name="contentFrame" 
     marginwidth="0" marginheight="0" scrolling="no"
     frameborder="0"  height="100%" width="100%">
     </iframe>
     </td>
    </tr>
  <table>
  <script>
  	  var listSize = ${listSize};
      function loadRule(ruleId){
          contentFrame.window.location="gradePointRule.do?method=info&gradePointRuleId="+ruleId;
      }
      <#if (gradePointRuleList?size>0)>
         defaultRule.onclick();
      </#if>
      function add(){
         contentFrame.window.location="gradePointRule.do?method=edit";
      }
      var bar = new ToolBar('myBar','绩点映射规则管理',null,true,true);
      bar.setMessage('<@getMessage/>');
      bar.addItem("<@bean.message key="action.new"/>",add,'new.gif');        
  </script>
<#include "/templates/foot.ftl"/>  
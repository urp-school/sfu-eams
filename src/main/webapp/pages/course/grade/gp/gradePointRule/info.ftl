<#include "/templates/head.ftl"/>
<body>
<table id="gradePointRuleInfoBar" width="100%"></table>
 <table class="infoTable">
   <tr>
       <td class="title" width="15%">名称</td>
       <td width="15%">${gradePointRule.name}</td>
       <td class="title" width="15%">适合学生类别</td>
       <td width="15%"><@i18nName gradePointRule.stdType?if_exists/></td>
       <td class="title" width="15%">成绩记录方式</td>
       <td width="15%"><@i18nName gradePointRule.markStyle/></td>
   </tr>
 </table>
  <@table.table width="100%">
   <@table.thead>
       <td width="10%">下限(包含)</td>
       <td width="10%">上限(包含)</td>
       <td width="15%">对应绩点</td>
     </@>
     <@table.tbody datas=gradePointRule.GPMappings?sort_by("maxScore");GPMapping>
      <td>${GPMapping.minScoreDisplay}</td>
      <td>${GPMapping.maxScoreDisplay}</td>
      <td>${GPMapping.gp?string("#.##")}</td>
     </@>
  </@>
  <form name="actionForm" method="post" action="gradePointRule.do" onsubmit="return false;">
    <input type="hidden" name="gradePointRuleId" value="${gradePointRule.id}">
  </form>
  <script>
     var action="gradePointRule.do";
     function update(){
        actionForm.target="_self";
        actionForm.action=action+"?method=edit";
        actionForm.submit();
     }
     function remove(){
     	//alert(parent.listSize);
        if(0==${gradePointRule.id}){
           alert("系统默认绩点规则,不能删除");
           return;
        }
        if(confirm("确定删除该绩点对照表?")){
          actionForm.target="_parent";
          actionForm.action=action+"?method=remove";
          actionForm.submit();
        }
     }
   var bar = new ToolBar('gradePointRuleInfoBar','绩点对照表   ${gradePointRule.name}(<#if gradePointRule.stdType?exists >适合${gradePointRule.stdType?if_exists.name?if_exists}<#else>系统默认</#if>)',null,true,true);
   bar.setMessage('<@getMessage/>');
   <#if inAuthority>
   bar.addItem('<@bean.message key="action.modify"/>',"javascript:update()",'update.gif');
   bar.addItem('<@bean.message key="action.delete"/>',"javascript:remove()",'delete.gif');
   </#if>
  </script>
</body>
<#include "/templates/foot.ftl"/>
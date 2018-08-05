<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="bar"></table>
<@table.table id="id" sortable="true" width="100%">
 <@table.thead>
  <@table.selectAllTd id="registerUserGroupId"/>
  <@table.sortTd id="registerUserGroup.name" text="组名"/>
  <@table.sortTd id="registerUserGroup.beginAt" text="开始时间"/>
  <@table.sortTd id="registerUserGroup.endAt" text="结束时间"/>
 </@>
 <@table.tbody datas=registerUserGroups;registerUserGroup>
  <@table.selectTd id="registerUserGroupId" value=registerUserGroup.id/>
  <td><a href="registerUserGroup.do?method=maintenance&id=${registerUserGroup.id}">${registerUserGroup.name?default("")}</a></td>
  <td>${(registerUserGroup.beginAt?string("yyyy-MM-dd HH:mm"))?default("")}</td>
  <td>${(registerUserGroup.endAt?string("yyyy-MM-dd HH:mm"))?default("")}</td>
 </@>
</@>
<@htm.actionForm name="actionForm" method="post" entity="registerUserGroup" action="registerUserGroup.do"/>
<script language="javascript">
   var bar=new ToolBar('bar','注册权限组',null,true,true);
   bar.addItem("人员维护","maintenance()");
   bar.addItem("新增","add()","new.gif"); 
   bar.addItem("修改","edit()","update.gif");
   bar.addItem("删除","remove()","delete.gif");
   bar.setMessage('<@getMessage/>');
   bar.addPrint("<@bean.message key="action.print"/>");
   function maintenance(){
   var id = getSelectIds("registerUserGroupId");
    if(id==""||isMultiId(id)){
        alert("请选择一项");
        return;
      }
   self.location="registerUserGroup.do?method=maintenance&id="+id;
   }
 </script>
</body>
<#include "/templates/foot.ftl"/>
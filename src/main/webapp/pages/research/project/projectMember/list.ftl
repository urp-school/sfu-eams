<#include "/templates/head.ftl"/>
 <body>
 <table id="projectMember"></table>
 <@table.table width="100%" id="sortTable">
   <@table.thead>
          <@table.selectAllTd id="projectMemberId"/>
          <td width="15%">成员姓名</td>
          <td width="10%">性别</td>
          <td width="20%">职称</td>
          <td width="15%">出生年月</td> 
          <td width="40%">个人简介</td>         
   		  </@>
   <@table.tbody datas=projectMembers?if_exists;projectMember>
      <@table.selectTd id="projectMemberId" value=projectMember.id/>
      <td>${(projectMember.name)?if_exists}</td>
	  <td>${(projectMember.gender.name)?if_exists}</td>
	  <td>${(projectMember.teacherTitle.name)?if_exists}</td>
	  <td>${(projectMember.birthday)?if_exists}</td>
	  <td>${(projectMember.resume)?if_exists}</td>
   </@>
 </@>
 
  <@htm.actionForm name="actionForm" action="projectMember.do" entity="projectMember">
  <input type="hidden" value="${teachProjectId?if_exists}" name="teachProjectId"/>
  <input type="hidden" value="${teachProjectId?if_exists}" name="params"/>
  </@>
 <script>
    var bar = new ToolBar('projectMember','&nbsp;项目成员列表',null,true,true);
    var form =document.actionForm;
    bar.setMessage('<@getMessage/>');
    bar.addItem("<@bean.message key="action.add"/>","add()");
    bar.addItem("<@bean.message key="action.modify"/>","edit()");
    bar.addItem("<@bean.message key="action.delete"/>","remove()");
    bar.addBack();
 </script>
 </body>
 <#include "/templates/foot.ftl"/>
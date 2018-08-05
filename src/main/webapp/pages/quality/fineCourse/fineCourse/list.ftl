<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
<table id="myBar" width="100%"></table>
  <@table.table width="100%" id="listTable" sortable="true">
    <@table.thead>
       <@table.selectAllTd id="fineCourseId"/>
       <@table.sortTd width="20%" name="attr.courseName" id="fineCourse.courseName"/>
       <@table.sortTd width="15%" text="等级" id="fineCourse.level.name"/>
       <@table.sortTd width="15%" name="entity.department"  id="fineCourse.department"/>
       <@table.td width="10%" text="负责人"/>
       <@table.sortTd width="10%" text="批准时间" id="fineCourse.passedYear"/>
    </@>
    <@table.tbody datas=fineCourses;fineCourse>
       <@table.selectTd  id="fineCourseId" value="${fineCourse.id}"/>
       <td>${fineCourse.courseName?html}</td>
       <td><@i18nName fineCourse.level?if_exists/></td>
       <td><@i18nName fineCourse.department?if_exists/></td>
       <td>${fineCourse.chargeNames?html}</td>
       <td>${fineCourse.passedYear?default("")}</td>
    </@>
  </@>
<script>
 var bar = new ToolBar("myBar","精品课程列表",null,true,true);
 bar.setMessage('<@getMessage/>');
 bar.addItem("<@msg.message key="action.info"/>","info()");
 bar.addItem("<@msg.message key="action.edit"/>","edit()");
 bar.addItem("<@msg.message key="action.new"/>","add()");
 bar.addItem("<@msg.message key="action.delete"/>","remove()","delete.gif");
 menu1 =bar.addMenu("导入","importData()");
 menu1.addItem("下载模板","downloadTemplate()","download.gif");

 function downloadTemplate(){
   self.location="dataTemplate.do?method=download&document.id=8";
 }
 function importData(){
   form.action="fineCourse.do?method=importForm&templateDocumentId=8";
   addInput(form,"importTitle","精品课程数据上传")
   form.submit();
 }
</script>
<@htm.actionForm name="actionForm" action="fineCourse.do" entity="fineCourse"></@>
</body>
<#include "/templates/foot.ftl"/>
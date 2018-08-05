<#include "/templates/head.ftl"/>
<body>
<table id="examinationPaperBar"></table>
<@table.table id="listTable" width="100%" sortable="true">
  <@table.thead>
      <@table.selectAllTd id="examinationPaperId"/>   
      <td width="15%">课程代码</td>
      <td width="15%">课程名称</td>
      <td width="30%">文档内容</td>
      <td width="30%">文档说明</td>
    </@>
    <@table.tbody datas=examinationPaperList;examinationPaper>
     <@table.selectTd id="examinationPaperId" value=examinationPaper.id/>
         <input type="hidden" name="${examinationPaper.id}" id="${examinationPaper.id}" />
     </td>
     <td>${(examinationPaper.course.code)?if_exists}</td>
     <td><@i18nName (examinationPaper.course)?if_exists/></td>
     <td><a href='examinationPaper.do?method=download&examinationPaperId=${(examinationPaper.id)?if_exists}'>${(examinationPaper.fileName)?if_exists}</a></td>
     <td>${(examinationPaper.remark)?if_exists}</td>
    </@>
  </@>
  </body>
  <@htm.actionForm name="actionForm" entity="examinationPaper" action="examinationPaper.do">
  	<input type="hidden" value="${calendar.id}" name="calendar.id"/>
  </@>
<script>
    var bar=new ToolBar("examinationPaperBar","试卷列表",null,true,true);
  	bar.setMessage('<@getMessage />');
    bar.addItem("<@bean.message key="action.add"/>","add()");
  	bar.addItem("<@bean.message key="action.modify"/>","edit()");
  	bar.addItem("<@bean.message key="action.delete"/>","remove()");
</script>
<#include "/templates/foot.ftl"/>
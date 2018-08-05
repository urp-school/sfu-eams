<#include "/templates/head.ftl"/>
 <BODY>	
 <table id="backBar" width="100%"></table>
 <@table.table   width="100%" id="listTable" sortable="true">
 	<@table.thead>
 		<@table.selectAllTd id="thesisId"/>
 		<@table.sortTd text="学号" id="thesis.student.code"/>
 		<@table.sortTd text="姓名" id="thesis.student.name" width="10%"/>
 		<@table.sortTd text="院系" id="thesis.student.department.name" width="10%"/>
 		<@table.sortTd text="专业" id="thesis.student.firstMajor.name" width="10%"/>
 		<@table.sortTd text="论文题目" width="22%" id="thesis.name"/>
 		<@table.sortTd text="开始时间" id="thesis.startOn"/>
 		<@table.sortTd text="结束时间" id="thesis.endOn"/>
 		<@table.sortTd text="提交时间" id="thesis.finishOn"/>
 		<@table.sortTd text="是否确认" id="thesis.affirm" width="8%"/>
 	</@>
 	<@table.tbody datas=thesiss;thesis>
 		<@table.selectTd id="thesisId" value="${thesis.id}"/>
 		<td>${thesis.student.code}</td>
 		<td><a href="thesisDownload.do?method=doDownLoad&thesisManageId=${thesis.thesisManage.id}&storeId=06">${thesis.student.name}</td>
 		<td><@i18nName (thesis.student.department)?if_exists/></td>
 		<td><@i18nName (thesis.student.firstMajor)?if_exists/></td>
 		<td>${(thesis.name)?if_exists}</td>
 		<td>${((thesis.startOn)?string("yyyy-MM-dd"))?if_exists}</td>
 		<td>${((thesis.endOn)?string("yyyy-MM-dd"))?if_exists}</td>
 		<td>${(thesis.finishOn?string("yyyy-MM-dd"))?if_exists}</td>
 		<td>${(thesis.affirm)?default(false)?string("已确认","未确认")}</td>
 	</@>
 </@>
    <@htm.actionForm name="actionForm" method="post" entity="thesis" action="thesisAdmin.do" onSubmit="return false;">
      <input type="hidden" id="keys" name="keys" value="student.type.name,student.department.name,student.code,student.name,<#if "1"==RequestParameters['majorType.id']?default("1")>student.teacher.name,student.teacher.title.name<#else>thesisManage.tutor.name,thesisManage.tutor.title.name</#if>,name,keyWords,startOn,endOn,thesisSource.name,affirm,remark,thesisManage.formalAnswer.formelMark">
      <input type="hidden" id="titles" name="titles" value="<@msg.message key="entity.studentType"/>,<@msg.message key="entity.department"/>,<@msg.message key="attr.stdNo"/>,<@msg.message key="attr.personName"/>,导师姓名,导师职称,论文题目,主题词,开始时间,结束时间,论文来源,是否确认,备注,论文答辩成绩">
    </@>
<script>
var bar = new ToolBar('backBar','查询结果列表',null,true,true);
bar.setMessage('<@getMessage/>');
bar.addItem("查看","detail()", "detail.gif");
var configMenue = bar.addMenu('设置确认',null,"detail.gif");
configMenue.addItem("确认","affirm('true')");
configMenue.addItem("取消确认","affirm('false')");
bar.addItem("导出","exportList()")

var form =document.actionForm;
var action="thesisAdmin.do";
function detail(){
	form.action=action+"?method=detail";
	submitId(form,"thesisId",false);
}

function affirm(value){
   addInput(form,"affirmValue",value);
   multiAction("affirm","你确认要提交吗");
}
</script>
</body>
<#include "/templates/foot.ftl"/>
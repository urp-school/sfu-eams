<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
	<table id="courseTakeBar"></table>
	<#include "courseTakeList.ftl"/>
	<form name="actionForm" method="post" action="" onsubmit="return false;">
	    <#--下面两行代码为显示课程详细信息而设，请勿更改或者同名-->
	    <input type="hidden" name="type" value="course"/>
	    <input type="hidden" name="id" value=""/>

	    <input name="calendar.id" type="hidden" value=${RequestParameters['courseTake.task.calendar.id']}/>
	</form>
	<script language="JavaScript" type="text/JavaScript" src="scripts/course/CourseTake.js"></script>
	<script>
	    var byWhat = '${RequestParameters['orderBy']?default("null")}';
	    var bar = new ToolBar("courseTakeBar","上课名单列表",null,true,true);
	    bar.setMessage('<@getMessage/>');
	    menu1=bar.addMenu("<@msg.message key="action.export"/>","exportData('courseTakeSearch')");
	    menu1.addItem("详细导出","exportData('courseTakeSearch',false)",'excel.png','导出项包括课程安排');
	    bar.addHelp("<@msg.message key="action.help"/>");
        
        var form = document.actionForm;
        function courseInfo(selectId, courseId) {
           if (null == courseId || "" == courseId || isMultiId(selectId) == true) {
               alert("请选择一条要操作的记录。");
               return;
           }
           form.action = "courseSearch.do?method=info";
           form[selectId].value = courseId;
           form.submit();
        }
	</script>
</body>
<#include "/templates/foot.ftl"/>


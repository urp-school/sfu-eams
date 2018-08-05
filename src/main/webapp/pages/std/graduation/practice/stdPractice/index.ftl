<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
	<table id="bar"></table>
	<#if (practices?size>0)>
 	<#assign graduatePractice=practices?first>
 	<#include "infoTable.ftl"/>
 	<#elseif !(calendar?exists)>
 	你还没有毕业实习数据，<br>
    但是该时间不再学期范围内，不能进行填写。
    <#else>
    你还没有毕业实习数据,如果你在这个学期${calendar.year} ${calendar.term}有毕业实习，可以单击工具栏中的添加按钮。 
    </#if>
    <form method="post" action="stdPractice.do?method=save" name="practiceForm" onsubmit="return false;">
      <#if calendar?exists><input name="graduatePractice.teachCalendar.id" type="hidden" value="${calendar.id}"/></#if>
    </form>
	<script>
		var bar = new ToolBar("bar", "学生毕业实习", null, true, true);
		bar.setMessage('<@getMessage/>');
		<#if graduatePractice?exists>
			bar.addItem("<@msg.message key="action.edit"/>","edit()");
		<#elseif calendar?exists>
		    bar.addItem("<@msg.message key="action.add"/>","edit()");
		</#if>
	  	bar.addHelp("<@msg.message key="action.help"/>");
	  	
		form = document.practiceForm;
		function edit() {
			form.action = "stdPractice.do?method=edit";
	     	form.submit();
		} 
	</script>
</body>
<#include "/templates/foot.ftl"/>

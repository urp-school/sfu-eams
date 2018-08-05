<#include "/templates/head.ftl"/>
<body>
	<table id="bar" width="100%"></table>
	<@table.table width="100%" sortable="true" id="listTable">
	   	<@table.thead>
	  	  	<@table.selectAllTd id="taskId"/>
	      	<@table.sortTd id="task.seqNo" width="8%" name="attr.taskNo"/>
	      	<@table.sortTd id="task.course.name" width="10%" name="attr.courseName"/>
	      	<@table.sortTd text="课程代码" width="8%" id="task.course.code"/>
	      	<@table.sortTd text="课程性质" width="15%" id="task.courseType.name"/>
	      	<@table.td name="task.arrangeInfo" width="20%"/>
	      	<@table.td text="教学班" width="5%"/>
	      	<@table.td width="10%" name="entity.teacher"/>
	      	<@table.sortTd text="双语" width="5%" id="task.requirement.teachLangType.name"/>
	      	<@table.sortTd text="挂牌" width="5%" id="task.requirement.isGuaPai"/>
	      	<@table.sortTd width="3%" id="task.teachClass.stdCount" name="attr.stdNum"/>
	      	<@table.sortTd width="3%" id="task.course.credits" name="attr.credit"/>
	      	<@table.sortTd width="4%" id="task.arrangeInfo.weekUnits" text="周时"/>
	      	<@table.sortTd width="4%" id="task.arrangeInfo.weeks" text="周数"/>
	      	<@table.sortTd text="人数上限" id="task.electInfo.maxStdCount"/>
	    </@>
	    <@table.tbody datas=tasks;task>
	      	<@table.selectTd id="taskId" value=task.id/>
	      	<td><A href="courseTable.do?method=taskTable&task.id=${task.id}" target="_blank" title="点击显示单个教学任务具体安排">${task.seqNo?if_exists}</a></td>
	      	<td><A href="collegeCourseTable.do?method=info&task.id=${task.id}" title="<@bean.message key="info.task.info"/>"><@i18nName task.course/></A></td>
	      	<td><A href="javascript:courseInfo('id', ${task.course.id})">${task.course.code}</A></td>
	      	<td title="<@i18nName task.courseType/>" nowrap><span style="display:block;width:80%;overflow:hidden;text-overflow:ellipsis;"><@i18nName task.courseType/></span></td>
	      	<td>${arrangeInfo[task.id?string]}</td>
	      	<td>${task.teachClass.name?html}</td>
	      	<td title="<@getTeacherNames task.arrangeInfo.teachers/>" nowrap><span style="display:block;width:90%;overflow:hidden;text-overflow:ellipsis;"><@getTeacherNames task.arrangeInfo.teachers/></span></td>
	      	<td><@i18nName task.requirement.teachLangType/></td>
	      	<td>${task.requirement.isGuaPai?string("是","否")}</td>
	      	<td>${task.teachClass.stdCount}</td>
	      	<td>${task.course.credits}</td>
	      	<td>${task.arrangeInfo.weekUnits}</td>
	      	<td>${task.arrangeInfo.weeks}</td>
	      	<td>${task.electInfo.maxStdCount}</td>
	    </@>
	</@>
	<@htm.actionForm name="actionForm" action="collegeCourseTable.do" entity="task">
        <#--下面两行代码为显示课程详细信息而设，请勿更改或者同名-->
        <input type="hidden" name="type" value="course"/>
        <input type="hidden" name="id" value=""/>
	</@>
	<script>
		var bar = new ToolBar("bar", "院系课表查询结果", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("查看课表", "courseTableInfo()");
		bar.addItem("<@msg.message key="action.look"/>", "info()");
		
		function courseTableInfo() {
			form.action = "courseTable.do?method=taskTable";
			submitId(form, "taskId", false);
		}
		
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
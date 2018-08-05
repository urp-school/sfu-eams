<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
    <table class="frameTable_title">
        <tr>
	        <td></td>
          <form name="calendarForm" method="post" action="">
          	<input type="hidden" name="studentIds" value="${RequestParameters["studentIds"]}"/>
            <#include "/pages/course/calendar.ftl"/>
          </form>
        </tr>
    </table>
    <p>学生选课列表（选择的记录是将要退课的记录）：</p>
    <@table.table id="stdCourseTake" width="100%">
    	<@table.thead>
    		<@table.selectAllTd id="takeId"/>
    		<@table.td text="学号"/>
    		<@table.td text="姓名"/>
    		<@table.td text="学生类别"/>
    		<@table.td text="双专业"/>
    		<@table.td text="双专业方向"/>
    		<@table.td text="所修课程"/>
    	</@>
    	<@table.tbody datas=stdCourseTakes;take>
    		<@table.selectTd id="takeId" value=take.id/>
    		<td>${take.student.code}</td>
    		<td><@i18nName take.student/></td>
    		<td><@i18nName take.student.type/></td>
    		<td><@i18nName (take.student.secondMajor)?if_exists/></td>
    		<td><@i18nName (take.student.secondAspect)?if_exists/></td>
    		<td><@i18nName take.task.course/><br>(${take.task.course.code})</td>
    	</@>
    </@>
    <p>是否要删除下面的学生所在班级：<form method="post" action="" name="actionForm"><input type="radio" name="isRemove" value="1" checked/>删除&nbsp;<input type="radio" name="isRemove" value="0"/>不删除</form></p>
    <@table.table id="stdAdminClass" width="100%">
    	<@table.thead>
    		<@table.td text="学号"/>
    		<@table.td text="姓名"/>
    		<@table.td text="学生类别"/>
    		<@table.td text="双专业"/>
    		<@table.td text="双专业方向"/>
    		<@table.td text="所在班级"/>
    	</@>
    	<@table.tbody datas=students;student>
	    	<#list student.adminClasses as adminClass>
	    		<#if (adminClass.speciality.majorType.id)?default(1) == 2>
		    		<td>${student.code}</td>
		    		<td><@i18nName student/></td>
		    		<td><@i18nName student.type/></td>
		    		<td><@i18nName (student.secondMajor)?if_exists/></td>
		    		<td><@i18nName (student.secondAspect)?if_exists/></td>
		    		<td><@i18nName adminClass/><br>(${adminClass.code})</td>
		    	</#if>
	    	</#list>
    	</@>
    </@>
	<script>
		var bar = new ToolBar("bar", "设为不就读的学生", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("提交", "settingSelected()");
		bar.addBack("<@msg.message key="action.back"/>");

		function selectCheckBox(box) {
			for (var i = 0; i < box.length; i ++) {
   				box[i].checked = true;
   			}
   		}
   		
		var form = document.actionForm;
		function settingSelected() {
			var takeIds = getSelectIds("takeId");
			var stdAdminClassIds = getSelectIds("stdAdminClassId");
			if (confirm("是否提交？")) {
				form.action = "speciality2ndStd.do?method=settingUnstudy";
				addInput(form, "takeIds", takeIds, "hidden");
				addInput(form, "studentIds", document.calendarForm["studentIds"].value, "hidden");
				addInput(form, "isRemove", document.getElementsByName("isRemove").value, "hidden");
				form.submit();
			}
		}
	</script>
</body>
<#include "/templates/foot.ftl"/>
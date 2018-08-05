<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	<table class="formTable" style="width:95%;" align="center">
		<tr>
			<td class="darkColumn" colspan="4">课程要求设置</td>
		</tr>
		<form method="post" action="" name="actionForm" onsubmit="return false;">
		<tr>
			<td class="title"> <input type="checkbox" name="HSKDegreeCheck" value="" onclick="checkState(this,'HSKDegreeSelect')"/> HSK级别要求</td>
			<td style="width:30%"><@htm.i18nSelect datas=(hskLevels?sort_by("degree"))?if_exists selected="" name="HSKDegreeId" id="HSKDegreeSelect"><option value="">请选择...</option></@></td>
			<td class="title"><input type="checkbox" name="courseCodeCheck" value="" onclick="checkState(this,'courseCodesInput')"/>添加先修课程代码串</td>
			<td style="width:30%"><input type="text" name="courseCodes" value="请输入课程代码,中间以半角逗号相隔" maxlength="30" id="courseCodesInput" onfocus="this.value=''"/></td>
		</tr>
		<@searchParams/>
		</form>
		<tr>
			<td class="darkColumn" align="center" colspan="4"><button onclick="save()"><@msg.message key="action.save"/></button></td>
		</tr>
	</table>
	<@table.table id="teachTaskId" width="95%" align="center">
  		<@table.thead>
      		<@table.td name="attr.taskNo"/>
      		<@table.td name="attr.courseName"/>
      		<@table.td name="entity.teachClass"/>
      		<@table.td name="entity.teacher"/>
      		<@table.td text="计划人数"/>
  			<@table.td name="attr.credit"/>
      		<@table.td text="周时"/>
      		<@table.td text="周数"/>
      		<@table.td name="attr.creditHour"/>
    	</@>
    	<@table.tbody datas=tasks;task>
    		<input type="hidden" name="taskId" value="${task.id}">
      		<td>${task.seqNo?if_exists}</td>
      		<td><@i18nName task.course/></td>      
      		<td width="35%">${(task.teachClass.name)?html}</td>
      		<td><@getTeacherNames task.arrangeInfo.teachers/></td>
      		<td>${task.teachClass.planStdCount}</td>
      		<td>${task.course.credits}</td>
      		<td>${task.arrangeInfo.weekUnits}</td>
      		<td>${task.arrangeInfo.weeks}</td>
      		<td>${task.arrangeInfo.overallUnits}</td>
    	</@>
  	</@>
	<script>
		var bar = new ToolBar("bar", "课程要求", null, true, true);
		bar.addItem("<@msg.message key="action.save"/>", "save()");
		bar.addBack();
		
		var form = document.actionForm;
        var info = new Object();
        //显示消息
        info["HSKDegreeIdSelect"] = "";
        info["courseCodesInput"] = "请输入课程代码,中间以半角逗号相隔";
        function initForm() {
            form.reset();
	        form['HSKDegreeId'].disabled = "disabled";
	        form['courseCodes'].disabled = "disabled";
        }
        initForm();
        function checkState(check, name) {
	        $(name).disabled=!check.checked;
	        if (!check.checked) {
	           $(name).value = info[name];
	           check.value = "";
	        } else {
	           check.value = name;
               $(name).focus();
	        }
        }
        
		var ids = document.getElementsByName("taskId");
		var taskIds = "";
		for (var i = 0; i < ids.length; i++) {
			taskIds += ids[i].value + ",";
		}
		function save() {
		    if (form["HSKDegreeCheck"].checked&&form['HSKDegreeId'].value == "") {
                alert("HSK级别要求不能为空！");
                return;
            }
            if (form["courseCodeCheck"].checked&& form['courseCodes'].value == "") {
                alert("先修课程代码串不能为空！");
                return;
            }
            if(!form["HSKDegreeCheck"].checked&&!form["courseCodeCheck"].checked){
               alert("至少选择其中一个");return;
            }
			form.action = "?method=batchUpdateElectInfo";
			addInput(form, "taskIds", taskIds);
			form.submit();
		}
	</script>
</body>
<#include "/templates/foot.ftl"/>
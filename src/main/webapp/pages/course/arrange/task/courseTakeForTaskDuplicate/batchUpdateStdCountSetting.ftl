<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
<body>
	<table id="bar"></table>
	<#assign actionPath="courseTakeForTaskDuplicate.do"/>
	<@htm.actionForm name="actionForm" action=actionPath entity="task" onsubmit="return false;">
		<@searchParams/>
		<table class="formTable" width="100%" align="center">
			<tr>
			    <td class="title" id="count"><input type="checkbox" name="planCountCheck" value="" onclick="selectItem(this, 'planStdCount')"/>计划人数：</td>
				<td><input type="text" name="planStdCount" value="" maxlength="5" style="width:100px" onfocus="this.value=''"/></td>
				<td class="title" id="count"><input type="checkbox" name="factCountCheck" value="" onclick="selectItem(this, 'stdCount')"/>实际人数：</td>
				<td><input type="text" name="stdCount" value="" maxlength="5" style="width:100px"  onfocus="this.value=''"/></td>
				<td class="title" id="count"><input type="checkbox" name="limitCountCheck" value="" onclick="selectItem(this, 'maxStdCount')"/>上限人数：</td>
				<td><input type="text" name="maxStdCount" value="" maxlength="5" style="width:100px"  onfocus="this.value=''"/></td>
			</tr>
		</table>
	</@>
	<@table.table id="teachTaskId" width="100%">
		<@table.thead>
      		<@table.td name="attr.index"/>
      		<@table.td name="attr.id"/>
      		<@table.td name="attr.courseName"/>
      		<@table.td name="entity.teacher"/>
      		<@table.td name="entity.courseType"/>
      		<@table.td text="计划"/>
      		<@table.td text="实际/上限"/>
		</@>
		<@table.tbody datas=tasks?sort_by(["seqNo"]);task>
			<input type="hidden" name="taskId" value="${task.id}"/>
      		<td>${(task.seqNo)?if_exists}</td>
      		<td>${task.course.code}</td>
      		<td><@i18nName task.course?if_exists/></td>
      		<td><@getTeacherNames task.arrangeInfo.teachers/></td>
      		<td><@i18nName task.courseType/></td>
      		<td>${task.teachClass.planStdCount}</td>
      		<td>${task.teachClass.stdCount}/${task.electInfo.maxStdCount}</td>
		</@>
	</@>
	<script>
		var bar = new ToolBar("bar", "修改学生人数（实践）", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("<@msg.message key="action.save"/>", "batchUpdateStdCount()");
		bar.addBack();
		
		var info = new Object();
		info["stdCount"] = "请输入学生人数。";
		info["planStdCount"] = "请输入学生人数。";
		info["maxStdCount"] = "请输入学生人数。";
		
		function initForm() {
			form.reset();
			form["stdCount"].disabled = "disabled";
			form["stdCount"].value = info["stdCount"];
			form["planStdCount"].disabled = "disabled";
			form["planStdCount"].value = info["planStdCount"];
			form["maxStdCount"].disabled = "disabled";
			form["maxStdCount"].value = info["maxStdCount"];
		}
		initForm();
		
		function selectItem(check, name) {
			$("count").className = "title";
			form[name].disabled=!check.checked;
	        if (!check.checked) {
	           form[name].value = info[name];
	           check.value = "";
	        } else {
	           check.value = name;
               form[name].focus();
	        }
		}
		
		var ids = document.getElementsByName("taskId");
		var taskIds = "";
		for (var i = 0; i < ids.length; i++) {
			taskIds += ids[i].value + ",";
		}
		function batchUpdateStdCount() {
			if (form["factCountCheck"].checked || form["planCountCheck"].checked  ||  form["limitCountCheck"].checked ) {
			  if(form["factCountCheck"].checked && !form["planCountCheck"].checked && !form["limitCountCheck"].checked){
			     var a_fields = {
					'stdCount':{'l':'指定人数', 'r':true, 't':'count', 'f':'unsigned'}
				};
			  }else if(form["planCountCheck"].checked && !form["factCountCheck"].checked && !form["limitCountCheck"].checked){
			     	var a_fields = {
					'planStdCount':{'l':'计划人数', 'r':true, 't':'count', 'f':'unsigned'}
				 };
			  }else if(form["limitCountCheck"].checked && !form["factCountCheck"].checked && !form["planCountCheck"].checked){
			    var a_fields = {
				    'maxStdCount':{'l':'上限人数', 'r':true, 't':'count', 'f':'unsigned'}
				 };
			  }else if(form["factCountCheck"].checked && form["planCountCheck"].checked && !form["limitCountCheck"].checked){
			    var a_fields = {
						'stdCount':{'l':'指定人数', 'r':true, 't':'count', 'f':'unsigned'},
						'planStdCount':{'l':'计划人数', 'r':true, 't':'count', 'f':'unsigned'}
					};
              }else if(form["factCountCheck"].checked && !form["planCountCheck"].checked && form["limitCountCheck"].checked){
                var a_fields = {
						'stdCount':{'l':'指定人数', 'r':true, 't':'count', 'f':'unsigned'},
						'maxStdCount':{'l':'上限人数', 'r':true, 't':'count', 'f':'unsigned'}
					};
              }else if(!form["factCountCheck"].checked && form["planCountCheck"].checked && form["limitCountCheck"].checked){
                var a_fields = {
						'planStdCount':{'l':'计划人数', 'r':true, 't':'count', 'f':'unsigned'},
						'maxStdCount':{'l':'上限人数', 'r':true, 't':'count', 'f':'unsigned'}
					};
              }else{
					var a_fields = {
						'stdCount':{'l':'指定人数', 'r':true, 't':'count', 'f':'unsigned'},
						'planStdCount':{'l':'计划人数', 'r':true, 't':'count', 'f':'unsigned'},
						'maxStdCount':{'l':'上限人数', 'r':true, 't':'count', 'f':'unsigned'}
					};
				}
				var v = new validator(form, a_fields, null);
				if (v.exec()) {
					form.action = "${actionPath}?method=batchUpdateStdCount";
					addInput(form, "taskIds", taskIds, "hidden");
					form.submit();
				}
			} else {
				alert("在保存之前，请填写要修改的选项。");
			}
		}
	</script>
</body>
<#include "/templates/foot.ftl"/>

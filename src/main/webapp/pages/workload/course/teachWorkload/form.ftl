<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
  <#include "/pages/evaluate/ealuateJs.ftl"/>
 <script>
 function doAction(form){     
     var a_fields = {
         'taskId':{'l':'选择教学任务', 'r':true, 't':'f_taskId'},
         'teacherId':{'l':'选择老师', 'r':true, 't':'f_teacherId'},
         'workloadValue':{'l':'该教师教学工作量','f':'real','r':true, 't':'f_workloadValues','mx':10},
         'remark':{'l':'备注','t':'f_remark','mx':1000}
     };
     var v = new validator(form, a_fields, null);
     if (v.exec()) {
        form.action="teachWorkload.do?method=save";
        setSearchParams(parent.form,form);
        form.submit();
     }
   }
   
   function setTeachTaskIdAndDescriptions(ids, descriptions){      
       	document.teachWorkloadForm.teachTeakId.value = ids;
	   	document.teachWorkloadForm.teachTeakName.value = descriptions;
   	 	}
   
   function displayDiv(divId){
       var div = document.getElementById(divId);

       if (div.style.display=="block"){
        div.style.display="none";
       }else{
         div.style.display="block";  
       }
   }
   function doSelectCourse(form){
   		var departmentSeq = form.departmentSeq.value;
   		var courseName = form.courseName.value;
   		var teachTaskSeq = form.courseSeq.value;
   		url="teachWorkload.do?method=taskList&departmentSeq="+departmentSeq
   			+"&courseName="+courseName
   			+"&courseSeq="+teachTaskSeq;
   		window.open(url, '', 'scrollbars=yes,width=600,height=550,status=yes');
   }
 </script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
 <table id="backBar"></table>
<script>
   var bar = new ToolBar('backBar','添加有任务的教学工作量',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addBack('<@bean.message key="action.back"/>');
</script>
  <table align="center" width="100%">
  <form name="teachWorkloadForm" method="post" action="" onsubmit="return false;">
	<tr>
	 <td>
		<table width="100%" align="center" class="listTable">
			<tr class="darkColumn" align="center">
				<td colspan="2">
					<B>添加单个教学工作量</B>
				</td>
			</tr>
			<tr>
				<td  id="f_taskId"class="grayStyle" align="right">选择教学任务<font color="red">*</font></td>
				<td class="brightStyle" align="left">
				   	<a href="javascript:displayDiv('seachBar');">查询条件:</a><br>
				   	<div id = "seachBar" style="display:block;">
				   		 选择开课院系:<select name="departmentSeq" style="300px;">
				   	 					<option value="">...</option>
				   	 				<#list departmentList as department>
				   	 					<option value="${department.id}">${department.name}</option>
				   	 				</#list>
				   	 			</select><br>
				   		<@msg.message key="attr.courseName"/>:<input name="courseName" style="width:100px;"> <@msg.message key="attr.taskNo"/>:<input name="courseSeq" style="width:100px;">
				   	</div>
					<input id="taskId" type="hidden" name="teachTeakId" value="">
					<input type="txet" name="teachTeakName" value="请选择教学任务" maxlength="50" style="width:300px;">
					<input type="button" name="button1" value="选择教学任务" onclick="doSelectCourse(this.form);" class="buttonStyle">
				</td>
			</tr>
			<tr>
				<td id="f_teacherId" class="grayStyle" align="right"> 选择老师<font color="red">*</font></td>
				<td>
					<select id="teacherId" name="teacherId" style="width:300px;">
						<option value="">请先选择教学任务</option>
					</select>
				</td>
			</tr>
			<tr>
				<td id="f_workloadValues" class="grayStyle" align="right">该教师教学工作量<font color="red">*</font></td>
				<td class="brightStyle" align="left">
					<input type="text" name="workloadValue" style="width:300px;" maxlength="5"/>
				</td>
			</tr>
			<tr>
				<td id="f_remark" class="grayStyle" align="right">备注</td>
				<td class="brightStyle" align="left">
					<textarea name="remark" style="width:300px;"></textarea>
				</td>
			</tr>
			<tr class="darkColumn">
				<td colspan="2" align="center">
					<input type="hidden" name="flag" value="save">
					<input type="button" name="button2" value="<@msg.message key="action.save"/>" onclick="doAction(this.form)" class="buttonStyle">
					<input type="reset" name="reset" value="重置" class="buttonStyle">
				</td>
			</tr>
			</table>
		</td>
	</tr>
	</form>
 <table>
 </body>
<#include "/templates/foot.ftl"/>
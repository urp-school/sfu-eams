<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<body>
	<table id="bar"></table>
	<table class="formTable" width="80%" align="center">
		<form name="actionForm" method="post" action="">
		<input type="hidden" name="gradeInputSwitch.id" value="${(gradeInputSwitch.id)?if_exists}"/>
		<tr>
			<td class="title"  width="30%"><font color="red">*</font>学年学期：</td>
			<td>
				<#if (gradeInputSwitch.calendar.id)?exists && (gradeInputSwitch.id)?exists>
					<input type="hidden" name="gradeInputSwitch.calendar.id" value="${gradeInputSwitch.calendar.id}"/>
		   			<@i18nName gradeInputSwitch.calendar.studentType/> ${gradeInputSwitch.calendar.year} ${gradeInputSwitch.calendar.term}
		  		<#else>
		      		<select id="stdType" name="calendar.studentType.id" style="width:100px;">
		        		<option value="${calendar.studentType.id}"></option>
		      		</select>
		    		<@bean.message key="attr.year2year"/> 
		     		<select id="year" name="calendar.year"  style="width:100px;">
		        		<option value="${calendar.year}"></option>
		      		</select>
		    		<@bean.message key="attr.term"/>
		     		<select id="term" name="calendar.term" style="width:60px;">
		        		<option value="${calendar.term}"></option>
		      		</select>
		   		</#if>
		   	</td>
		</tr>
	 	<tr>
	 		<td class="title" id="f_startAt"><font color="red">*</font>开始时间:</td>
	 		<td><input type="text" id="openDate" name="startDate" value="${(gradeInputSwitch.startAt?string("yyyy-MM-dd"))?default("")}" maxlength="10" onfocus="calendar();" style="width:100px;"/><input type="text" name="startTime" value="${(gradeInputSwitch.startAt?string("HH:mm"))?default("00:00")}" maxlength="5" style="width:50px"/></td>
	 	</tr>
	 	<tr>
	 		<td class="title" id="f_endAt"><font color="red">*</font>结束时间:</td>
	 		<td><input type="text" id="openDate" name="endDate" value="${(gradeInputSwitch.endAt?string("yyyy-MM-dd"))?default("")}" maxlength="10" onfocus="calendar();" style="width:100px;"/><input type="text" name="endTime" value="${(gradeInputSwitch.endAt?string("HH:mm"))?default("00:00")}" maxlength="5" style="width:50px"/></td>
	 	</tr>
	 	<tr>
			<td class="title" id="f_gradeTypes">成绩类型：</td>
		    <td >
		     <table>
		      <tr>
		       <td>
		        <select name="gradeTypes" MULTIPLE size="10" style="width:200px" onDblClick="JavaScript:moveSelectedOption(this.form['gradeTypes'], this.form['SelectedGradeType'])" >
		         <#list canInputTypes?sort_by('name') as gradeType>
		          <option value="${gradeType.id}">${gradeType.name}</option>
		         </#list>
		        </select>
		       </td>
		       <td  valign="middle">
		        <br><br>
		        <input OnClick="JavaScript:moveSelectedOption(this.form['gradeTypes'], this.form['SelectedGradeType'])" type="button" value="&gt;"> 
		        <br><br>
		        <input OnClick="JavaScript:moveSelectedOption(this.form['SelectedGradeType'], this.form['gradeTypes'])" type="button" value="&lt;"> 
		        <br>
		       </td> 
		       <td  class="normalTextStyle" id="f_SelectedGradeType">
		        <select name="SelectedGradeType" MULTIPLE size="10" style="width:200px;" onDblClick="JavaScript:moveSelectedOption(this.form['SelectedGradeType'], this.form['gradeTypes'])">
		         <#list mngGradeTypes?if_exists as mngGradeType>
		          <option value="${mngGradeType.id}">${mngGradeType.name}</option>
		         </#list>
		        </select>
		       </td>             
		      </tr>
		     </table>
		    </td>
	 	</tr>
	 	<tr>
	 		<td class="title"><font color="red">*</font>是否开放：</td>
	 		<td><@htm.radio2 name="gradeInputSwitch.isOpen" value=(gradeInputSwitch.isOpen)?default(false)/></td>
	 	</tr>
	 	<tr class="darkColumn">
	 		<td colspan="2" align="center">
				<input type="hidden" name="gradeTypeIds" />
	 			<button onClick="save(this.form)"><@msg.message key="action.save"/></button>
	 			<input type="reset"  name="reset1" value="<@msg.message key="system.button.reset"/>" class="buttonStyle"/>
	 		</td>
	 	</tr>
	 	</form>
	</table>
	<#list 1..10 as i><br></#list>
	<script>
		var bar = new ToolBar("bar", "设置录入开关", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("<@msg.message key="action.save"/>", "save()");
		bar.addBack();
		
		var form = document.actionForm;
		function save(form) {
			form.gradeTypeIds.value = getAllOptionValue(form.SelectedGradeType);
			if ("" == form.gradeTypeIds.value) {
				alert("可录入成绩类型设置不允许为空!");return;
			}
			var a_fields = {
				'startDate':{'l':'开始时间的日期', 'r':true, 't':'f_startAt'},
				'startTime':{'l':'开始时间的时间', 'r':true, 't':'f_startAt', 'f':'shortTime'},
				'endDate':{'l':'结束时间的日期', 'r':true, 't':'f_endAt'},
				'endTime':{'l':'结束时间的时间', 'r':true, 't':'f_endAt', 'f':'shortTime'}
			};
			var v = new validator(form, a_fields, null);
			if (v.exec()) {
				if(form['startDate'].value + " " + form['startTime'].value >= form['endDate'].value + " " + form['endTime'].value){
                   alert("开始结束日期时间设定不正确");return;
                }
                form.action = "gradeInputSwitch.do?method=save";
				form.submit();
			}
		}
	</script>
	<#if !((gradeInputSwitch.calendar.id)?exists && (gradeInputSwitch.id)?exists)>
   		<#include "/templates/calendarSelect.ftl"/>
	</#if>
</body>
<#include "/templates/foot.ftl"/>
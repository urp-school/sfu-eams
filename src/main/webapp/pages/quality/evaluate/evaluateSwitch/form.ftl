<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<BODY>
<#assign labInfo>评教开关信息</#assign>
  <#include "/templates/back.ftl"/>
  <table  width="80%" class="formTable" align="center">
   <form name="commonForm" method="post" action="" onsubmit="return false;">
    <@searchParams/>
     <input type="hidden" name="switch.id" value="${switch.id?default('')}"/>
        <tr>
          <td class="title" width="30%"><font color="red">*</font>学年学期:
		      <input type="hidden" name="switch.calendar.id" value="${(switch.calendar.id)?if_exists}"/>
		  </td>
		  <td>
		  <#if (switch.calendar.id)?exists>
		   <@i18nName switch.calendar.studentType/> ${switch.calendar.year} ${switch.calendar.term}
		  <#else>
		      <select id="stdType" name="calendar.studentType.id" style="width:100px;">
		        <option value="${calendar.studentType.id}"></option>
		      </select>
		    <@bean.message key="attr.year2year"/> 
		     <select id="year" name="calendar.year" style="width:100px;">
		        <option value="${calendar.year}"></option>
		      </select>
		    <@bean.message key="attr.term"/>
		     <select id="term" name="calendar.term" style="width:60px;">
		        <option value="${calendar.term}"></option>
		      </select>&nbsp;
		      <button onclick="isFreeCalendar()" title="检测可用此教学日历，添加评教开关。">是否可用</button> <label id="checkResult" style="color:red"></label>
		   </#if>
		   </td>
        </tr>
	 	<tr>
	 		<td class="title" width="30%"><font color="red">*</font>是否开放:</td>
	 		<td><@htm.radio2 name="switch.isOpen" value=(switch.isOpen)?default(false)/></td>
	 	</tr>
	 	<tr>
	 		<td class="title" id="f_openDate" width="30%"><font color="red">*</font>开始时间:</td>
	 		<td><input type="text" id="openDate" name="openDate" value="${(switch.openAt?string("yyyy-MM-dd"))?default("")}" maxlength="10" onfocus="calendar();" style="width:100px;"/><select name="openHour" id="openHour"><#list 0..23 as i><option value="<#if i<10>0${i}<#else>${i}</#if>"><#if i<10>0${i}<#else>${i}</#if></#list></select>:<select name="openMinute" id="openMinute"><#list 0..59 as i><#if i%5=0><option value="<#if i<10>0${i}<#else>${i}</#if>"><#if i<10>0${i}<#else>${i}</#if></#if></#list></select></td>
	 	</tr>
	 	<tr>
	 		<td class="title" id="f_closeDate"><font color="red">*</font>结束时间:</td>
	 		<td><input type="text" id="closeDate" name="closeDate" maxlength="10" onfocus="calendar();" value="${(switch.closeAt?string("yyyy-MM-dd"))?default("")}" style="width:100px;"><select name="closeHour" id="closeHour"><#list 0..23 as i><option value="<#if i<10>0${i}<#else>${i}</#if>"><#if i<10>0${i}<#else>${i}</#if></#list></select>:<select name="closeMinute" id="closeMinute"><#list 0..59 as i><#if i%5=0><option value="<#if i<10>0${i}<#else>${i}</#if>"><#if i<10>0${i}<#else>${i}</#if></#if></#list></select></td>
	 	</tr>
	   <tr>
	    <td class="title" id="f_studentType"><font color="red">*</font><@bean.message key="entity.studentType"/>：</td>
	    <td >
	     <table class="formTable">
	      <tr>
	       <td>
	        可选的学生类别
	        <select name="StudentTypes" MULTIPLE size="7" style="width:200px" onDblClick="JavaScript:moveSelectedOption(this.form['StudentTypes'], this.form['SelectedStudentType'])" >
	         <#list stdTypes?sort_by('code') as stdType>
	          <option value="${stdType.id}"><@i18nName stdType/></option>
	         </#list>
	        </select>
	       </td>
	       <td  valign="middle">
	        <br><br>
	        <input OnClick="JavaScript:moveSelectedOption(this.form['StudentTypes'], this.form['SelectedStudentType'])" type="button" value="&gt;"> 
	        <br><br>
	        <input OnClick="JavaScript:moveSelectedOption(this.form['SelectedStudentType'], this.form['StudentTypes'])" type="button" value="&lt;"> 
	        <br>
	       </td> 
	       <td  class="normalTextStyle">
	       已有的学生类别
	        <select name="SelectedStudentType" MULTIPLE size="7" style="width:200px;" onDblClick="JavaScript:moveSelectedOption(this.form['SelectedStudentType'], this.form['StudentTypes'])">
	         <#list switch.stdTypes?sort_by('code') as stdType>
	          <option value="${stdType.id}"><@i18nName stdType/></option>
	         </#list>
	        </select>
	       </td>
	      </tr>
	     </table>
	    </td>
	   </tr>
	   <tr>
	    <td class="title" id="f_department"><font color="red">*</font><@bean.message key="entity.department"/>：</td>
	    <td >
	     <table class="formTable">
	      <tr>
	       <td>
	        可选的院系所
	        <select name="Departments" MULTIPLE size="7" style="width:200px" onDblClick="JavaScript:moveSelectedOption(this.form['Departments'], this.form['SelectedDepartment'])" >
	         <#list departs?sort_by('name') as depart>
	          <option value="${depart.id}"><@i18nName depart/></option>
	         </#list>
	        </select>
	       </td>
	       <td  valign="middle">
	        <br><br>
	        <input OnClick="JavaScript:moveSelectedOption(this.form['Departments'], this.form['SelectedDepartment'])" type="button" value="&gt;"> 
	        <br><br>
	        <input OnClick="JavaScript:moveSelectedOption(this.form['SelectedDepartment'], this.form['Departments'])" type="button" value="&lt;"> 
	        <br>
	       </td> 
	       <td  class="normalTextStyle">
	        已选的院系所
	        <select name="SelectedDepartment" MULTIPLE size="7" style="width:200px;" onDblClick="JavaScript:moveSelectedOption(this.form['SelectedDepartment'], this.form['Departments'])">
	         <#list switch.departs?sort_by('name') as depart>
	          <option value="${depart.id}"><@i18nName depart/></option>
	         </#list>
	        </select>
	       </td>
	      </tr>
	     </table>
	    </td>
	   </tr>
	   <tr class="darkColumn">
	     <td colspan="4" align="center">
	       <button onClick="save()"><@msg.message key="system.button.submit"/></button>&nbsp;
	       <input type="hidden" name="stdTypeIdSeq" value=""/>
	       <input type="hidden" name="departmentIdSeq" value=""/>
	       <input type="reset"  name="reset1" value="<@msg.message key="system.button.reset"/>" class="buttonStyle"/>
	     </td>
	   </tr>
   </form>
  </table>
<#if !(switch.calendar.id)?exists>
   <#include "/templates/calendarSelect.ftl"/>
</#if>
   <script language="javascript">
   <#if switch.openAt?exists>
    setSelected(document.getElementById("openHour"),"${switch.openAt?string("HH")}");
    setSelected(document.getElementById("openMinute"),"${switch.openAt?string("mm")}");
   </#if>
   <#if switch.closeAt?exists>
    setSelected(document.getElementById("closeHour"),"${switch.closeAt?string("HH")}");
    setSelected(document.getElementById("closeMinute"),"${switch.closeAt?string("mm")}");
   </#if>
   
   var form = document.commonForm;
   
   function save(){
        form['stdTypeIdSeq'].value = getAllOptionValue(form.SelectedStudentType);  
        form['departmentIdSeq'].value = getAllOptionValue(form.SelectedDepartment);  
		var a_fields = {
			'openDate':{'l':'开始时间', 'r':true, 't':'f_openDate','f':'date'},
			'closeDate':{'l':'结束时间', 'r':true, 't':'f_closeDate','f':'date'}
		};
		var v = new validator(form, a_fields, null);
		if ((null == form["switch.id"].value || "" == form["switch.id"].value) && isFreeCalendar() && v.exec() || v.exec()) {
			var openTime = form['openDate'].value + "-" + form['openHour'].value + ":" + form['openMinute'].value;
			var closeTime = form['closeDate'].value + "-" + form['closeHour'].value + ":" + form['closeMinute'].value;
			if (openTime > closeTime) {
				alert("开始时间不能超过结束时间！");
				return; 
			}
			if (form["stdTypeIdSeq"].value=="") {
		     	alert("<@msg.message key="entity.studentType"/>没有选择！");
		     	return;
		    }
			if (form["departmentIdSeq"].value=="") {
		     	alert("<@msg.message key="entity.department"/>没有选择！");
		     	return;
		    }
		    addInput(form,"openTime",openTime);
		    addInput(form,"closeTime",closeTime);
		    form.action="evaluateSwitch.do?method=save";
		    form.submit();
	   }
   }
    function isFreeCalendar() {
        var stdTypeId = form["calendar.studentType.id"].value;
        var year = form["calendar.year"].value;
        var term = form["calendar.term"].value;
        <#list calendars as calendar>if (${calendar.studentType.id} == stdTypeId && "${calendar.year}".trim() == year && "${calendar.term}".trim() == term) {
            $("checkResult").style.color = "red";
            $("checkResult").innerHTML = "<br>当前教学的评教开关已经存在，不可用。";
            adaptFrameSize();
            alert("当前教学的评教开关已经存在，不可用。");
            return false;
        }<#if calendar_has_next> else </#if></#list>
        $("checkResult").style.color = "blue";
        $("checkResult").innerHTML = "可用";
        adaptFrameSize();
        return true;
    }
    </script>
</body>
<#include "/templates/foot.ftl"/>
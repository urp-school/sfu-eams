<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/common/Calendar.js"></script>
<body>
 <table id="bar"></table>
 <table width="90%" class="formTable" align="center" style="padding:0px;bolder-spacing:0px;">
 		<td class="darkColumn" colspan="4">
		    <B>转专业申请时间</B>
   		</td>
	<form name="actionForm" action="" method="post" onsubmit="return false;">
		<input type="hidden" name="plan.id" value="${(plan.id)?if_exists}"/>
		<input type="hidden" name="plan.startAt" id="applicationStart"/>
		<input type="hidden" name="plan.endAt" id="applicationEnd"/>
		<input type="hidden" name="plan.auditStartTime" id="auditStart"/>
		<input type="hidden" name="plan.auditEndTime" id="auditEnd"/>
		<input type="hidden" name="plan.calendar.id" value="${(plan.calendar.id)?if_exists}"/>
		<tr>
		<td class="title" width="20%" id="f_applicationStartDate">申请开始日期</td>
		<td width="30%">
		<input name="applicationStartDate" onfocus="calendar()" value="${(plan.startAt?string("yyyy-MM-dd"))?if_exists}" maxLength="10"/>
		</td>
		<td class="title" width="20%" id="f_startAt">申请开始时间</td>
		<td width="30%">
		<input name="startAt" value="${(plan.startAt?string("HH:mm"))?default("00:00")?if_exists}" style="width:50px" maxLength="5"/>(格式为 小时:分钟)
		</td>
		</tr>
		<tr>
		<td class="title" id="f_applicationEndDate">申请结束日期</td>
		<td width="30%">
		<input name="applicationEndDate" onfocus="calendar()" value="${(plan.endAt?string("yyyy-MM-dd"))?if_exists}" maxLength="10"/>
		</td>
		<td class="title" id="f_endAt">申请结束时间</td>
		<td width="30%">
		<input name="endAt" value="${(plan.endAt?string("HH:mm"))?default("00:00")?if_exists}" style="width:50px" maxLength="5"/>(格式为 小时:分钟)
		</td>
		</tr>
		<!--
		<tr>
		<td class="title" id="f_auditStartDate">审核开始日期</td>
		<td width="30%">
		<input name="auditStartDate" onfocus="calendar()" value="${(plan.auditStartTime?string("yyyy-MM-dd"))?if_exists}" maxLength="10"/>
		</td>
		<td class="title" id="f_auditStartTime">审核开始时间</td>
		<td width="30%">
		<input name="auditStartTime" value="${(plan.auditStartTime?string("HH:mm"))?default("00:00")?if_exists}" style="width:50px" maxLength="5"/>(格式为 小时:分钟)
		</td>
		</tr>
		<tr>
		<td class="title" id="f_auditEndDate">审核结束日期</td>
		<td width="30%">
		<input name="auditEndDate" onfocus="calendar()" value="${(plan.auditEndTime?string("yyyy-MM-dd"))?if_exists}" maxLength="10"/>
		</td>
		<td class="title" id="f_auditEndTime">申请结束时间</td>
		<td width="30%">
		<input name="auditEndTime" value="${(plan.auditEndTime?string("HH:mm"))?default("00:00")?if_exists}" style="width:50px" maxLength="5"/>(格式为 小时:分钟)
		</td>
		</tr>
		-->
		<tr>
		<td align="center" class="darkColumn" colspan="4">
		    <input type="button" onClick="save()" value="<@bean.message key="system.button.submit" />" name="button1" class="buttonStyle" />&nbsp;
		    <input type="reset" onClick="actionForm.reset()" value="<@bean.message key="system.button.reset" />" name="reset1"  class="buttonStyle" />
   		</td>
		</tr>
   </form>	
 </table>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>
<script language="javascript" >
    var bar=new ToolBar("bar","转专业招收计划",null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addItem("<@msg.message key="action.save"/>","save()");
    bar.addBack("<@msg.message key="action.back"/>");
    var form = document.actionForm;
    
    function save(){
     var fields = {
     	 'applicationStartDate':{'l':'申请开始日期', 'r':true, 't':'f_applicationStartDate','f':'date'},    
         'startAt':{'l':'申请开始时间', 'r':true, 't':'f_startAt','f':'shortTime'},
         'applicationEndDate':{'l':'申请结束日期', 'r':true, 't':'f_applicationEndDate','f':'date'},    
         'endAt':{'l':'申请结束时间', 'r':true, 't':'f_endAt','f':'shortTime'}
       //  'auditStartDate':{'l':'审核开始日期', 'r':true, 't':'f_auditStartDate','f':'date'},    
       //  'auditStartTime':{'l':'审核开始时间', 'r':true, 't':'f_auditStartTime','f':'shortTime'},
       // 'auditEndDate':{'l':'审核结束日期', 'r':true, 't':'f_auditEndDate','f':'date'},    
       //  'auditEndTime':{'l':'申请结束时间', 'r':true, 't':'f_auditEndTime','f':'shortTime'}
     };
     var v = new validator(form, fields, null);
     if (v.exec()) {
     	if (!v.exec()) return;
     	var applicationStartDate=form.applicationStartDate.value;
   		var applicationEndDate=form.applicationEndDate.value;
   		if(!validDateInterval(applicationStartDate,applicationEndDate)){
   		    alert("结束日期必须在申请开始日期之后");
   			retrun;
   		 }
   		if(form['applicationStartDate'].value==form['applicationEndDate'].value){
		       if(form['startAt'].value>=form['endAt'].value){
			  alert("申请结束日期应在结束时间之前");
			  return;
		       }
   		}
     	form.action="specialityAlerationAudit.do?method=savePlan";
     	var applicationStart=form.applicationStartDate.value+" "+form.startAt.value+":00";
   		var applicationEnd=form.applicationEndDate.value+" "+form.endAt.value+":00";
   		//var auditStart=form.auditStartDate.value+" "+form.auditStartTime.value+":00";
   		//var auditEnd=form.auditEndDate.value+" "+form.auditEndTime.value+":00";
   		document.getElementById("applicationStart").value=applicationStart;
   		document.getElementById("applicationEnd").value=applicationEnd;
   		//document.getElementById("auditStart").value=auditStart;
   		//document.getElementById("auditEnd").value=auditEnd;
        form.submit();
     }
 }
</script>
</body>
<#include "/templates/foot.ftl"/>
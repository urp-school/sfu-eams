<#include "/templates/head.ftl"/>
<script src='dwr/interface/teacherDAO.js'></script>
<body  onload="DWRUtil.useLoadingMessage();">
 <table id="batchEditBar"></table>
 <script>
    var bar= new ToolBar("batchEditBar","指定或修改监考学院",null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addItem("<@msg.message key="action.print"/>","print()");
    bar.addItem("<@bean.message key="action.save"/>","batchUpdate(document.taskListForm)");
    bar.addClose();
 </script>
 <table width="100%" border="0" class="listTable">
 <form name="taskListForm" method="post" action="" onsubmit="return false;">
    <tr align="center" class="darkColumn">
      <td >
        <input type="checkbox" onClick="toggleCheckBox(document.getElementsByName('examActivityId'),event);" checked>
      </td>
      <td >序号</td>
      <td width="6%"><@msg.message key="attr.taskNo"/></td>
      <td width="15%"><@bean.message key="attr.courseName"/></td>
      <td width="15%"><@bean.message key="entity.teachClass"/></td>
      <td width="10%">考试安排</td>
      <td width="9%">考试时间</td>
      <td width="8%">考试地点</td>
      <td width="8%">主考</td>
      <td width="30%">监考院系<br/>
       <select name="departmentIdAll" id="departmentAll" 
          onmouseover="displayDepartList(event);" style="width:60%">
       </select><button onclick="changeAll()">更换</button></td>
    </tr>
    <#list examActivities as activity>
   	  <#if activity_index%2==1 ><#assign class="grayStyle"/></#if>
	  <#if activity_index%2==0 ><#assign class="brightStyle"/></#if>
     <tr class="${class}" align="center">
      <td width="2%" class="select" title="只有选中的才保存被设置的信息">
        <input type="checkbox" name="examActivityId" value="${activity.id}" checked/>
      </td>
      <td  align="center">${activity_index+1}</td>
      <td  align="center">${activity.task.seqNo}</td>
      <td><@i18nName activity.task.course/></td>
      <td>${activity.task.teachClass.name}</td>
      <td>${activity.task.arrangeInfo.digestExam(activity.task.calendar,Request["org.apache.struts.action.MESSAGE"],Session["org.apache.struts.action.LOCALE"],activity.examType.id,"第:weeks周 :day :time")}</td>
      <td>${ activity.date?string("yyyy-MM-dd")}</td>
      <td><@i18nName activity.room?if_exists/></td>
      <td>${activity.examMonitor.examinerNames?if_exists}</td>
      <td><select name="departmentId${activity.id}" id="department${activity.id}" onmouseover="displayDepartList(event);" style="width:60%">
          <option value="${departMap[activity.id?string].id}"><@i18nName departMap[activity.id?string]/></option>
          </select>
      </td>
    </tr>
	</#list>
    <tr align="center" class="darkColumn">
       <td colspan="15">本次修改安排数量：${examActivities?size}。可以选择左侧的复选框，进行选择性的保存</td>
    </tr>
	</form>
	</table>
	<script language="JavaScript" type="text/JavaScript" src="scripts/course/DepartTeacher.js"></script>
    <script>
	    var departmentList= new Array();
		 <#list departmentList as department>
		   departmentList[${department_index}]={'id':'${department.id}','name':'<@i18nName department/>'};
		 </#list>	
	    function batchUpdate(form){
	        if(getCheckBoxValue(form.examActivityId)=="") {alert("请选择一个或多个进行保存设置");return;}
	        form.action="examiner.do?method=saveInvigilators&type=depart";
	        form.submit();
	    }
	    function changeAll(){
	    	var objItemv=document.getElementById("departmentAll").value;
	    	if(objItemv!=""&&objItemv!=null){
	    		<#list examActivities as activity>
	    		var objSelect=document.getElementById("department${activity.id}");
	    		jsSelectItem(objSelect,objItemv);
	    		</#list>
	    	}else{
	    		alert("请选择要更换的学院.");
	    	}
	    }
	    function jsSelectItem(objSelect,objItemv){
		    if(objItemv!=null&&objSelect!=null){
		    	for (var i = 0; i < objSelect.options.length; i++) {        
			        if (objSelect.options[i].value == objItemv) {        
			            objSelect.options[i].selected = true;        
			            break;        
			        }        
			    } 
			 }    
	    }
	</script>
</body>
<#include "/templates/foot.ftl"/>
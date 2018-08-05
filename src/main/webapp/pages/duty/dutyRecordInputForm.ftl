<#include "/templates/head.ftl"/>
<#include "/pages/system/calendar/timeFunction.ftl"/>
<#include "/pages/duty/checkRecordDetail.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<body leftmargin="0" topmargin="0" >
<table cellpadding="0" cellspacing="0" width="100%" border="0">
	<tr>
    	<td align="center" colspan="4" class="contentTableTitleTextStyle" bgcolor="#ffffff">
     		<B><@bean.message key="info.duty.studentList"/></B>
    		</td>
   	</tr>
	<tr>
		<table width="85%" align="center" class="listTable">
			<tr align="center" class="darkColumn">
	     		<td width="10%"><@bean.message key="attr.courseNo"/></td>
		     	<td width="20%"><@bean.message key="attr.courseName"/></td>
		     	<td width="14%"><@bean.message key="entity.courseType"/></td>
		     	<td width="11%"><@bean.message key="attr.year2year"/></td>
		     	<td width="10%"><@bean.message key="attr.term"/></td>
		     	<td width="10%"><@bean.message key="entity.teacher"/></td>
		     	<td width="19%"><@bean.message key="entity.teachClass"/></td>	     
	   		</tr>
	   		<#assign teachTask >${result.teachTask}</#assign>	      
	   		<tr class="brightStyle">
			    <td>&nbsp;${ result.teachTask.course.code?if_exists}</td>
			    <td>&nbsp;<@i18nName result.teachTask.course?if_exists/></td>
			    <td>&nbsp;<@i18nName result.teachTask.courseType?if_exists/></td>
			    <td>&nbsp;${result.teachTask.calendar.year}</td>
			    <td>&nbsp;${result.teachTask.calendar.term}</td>
			    <td>&nbsp;<@getTeacherNames result.teachTask.arrangeInfo.teachers?if_exists/><#--<@eraseComma teachTask.arrangeInfo.teacherNames?if_exists/>--></td>
			    <td>&nbsp;<@i18nName result.teachTask.teachClass?if_exists/></td>	    
	   		</tr>
     	</table>
	</tr>
   	<tr><td>&nbsp;</td></tr>
   	<div id="commonFormDiv" style="display:block">
	<tr>
		<td>
			<table width="85%" align="center" class="listTable">
				<form id="commonForm" name="commonForm" method="post" action="inputDutyRecord.do" onSubmit="return false;">
				<tr class="brightStyle">
					<td colspan="4" align="center">
						<@bean.message key="info.duty.dutyDate"/><input type="text" id="recordDetail.dutyDate" value="${today?string("yyyy-MM-dd")}" name="recordDetail.dutyDate" onfocus="calendar()" size="10"/>
						开始小节<select id="beginUnit" id="recordDetail.beginUnit.id" name="recordDetail.beginUnit.id" style="width:60px;" onchange="changeBeginTime();" ></select>开始时间：<span id="beginTime" ></span>&nbsp;
						结束小节<select id="endUnit" id=="recordDetail.endUnit.id" name="recordDetail.endUnit.id" style="width:60px;" onchange="changeEndTime();" ></select>结束时间：<span id="endTime" ></span>
	    			</td>
	   			</tr>
			   	<script>
			   		var dutyStatusArray=new Array();
			   	</script>
	   			<#assign dutyStatusSelectOption>
	   				<#list result.dutyStatusList?sort_by("id")?if_exists as dutyStatus>
	   					<option value="${dutyStatus.id}" style="background-color:#FFFFFF"><@i18nName dutyStatus/></option>
	   				</#list>
	   			</#assign>
	   			<tr align="center" class="darkColumn">
	     			<td align="center" width="5%">
			     	<select name="totalDutyStatus" style="width:60px" onchange="dutyStatusChange()">
			       		<option value="" style="background-color:#B0B0B0"></option>
		           		${dutyStatusSelectOption}
		        	</select></td>
	     			<td><@bean.message key="attr.stdNo"/></td>
	     			<td><@bean.message key="attr.personName"/></td>
	     			<td>修读类别</td>
	   			</tr>
	   			<#assign dutyStatusArray = {} />
	   			<#assign targetstdId = ","/>
	   			<#if result.teachTask?exists && result.teachTask.teachClass?exists && result.teachTask.teachClass.courseTakes?size!=0>
	   				<#list (result.teachTask.teachClass.courseTakes?sort_by(["student","code"]))?if_exists as courseTake>
	   					<#if courseTake.id==3||courseTake.id==4><#else>
		   					<#if courseTake_index%2==1 ><#assign class="grayStyle" ></#if>
		   					<#if courseTake_index%2==0 ><#assign class="brightStyle" ></#if>
		   					<#assign targetstdId = targetstdId + courseTake.student.id + ","/>
		   					<tr class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)">
		    					<td align="center"><#--><input type="checkBox" name="ids" value="${courseTake.student.stdNo}">-->
								    <#--<#assign dutyStatusArray>${dutyStatusArray}+"dutyStatus${courseTake.student.stdNo}"</#assign>
								    ${dutyStatusArray}-->
		    						<script>
		   								dutyStatusArray.push("dutyStatus${courseTake.student.id}");
		   							</script>
								    <select id="dutyStatus${courseTake.student.id}" name="dutyStatus${courseTake.student.id}"  style="width:60px" onchange="document.getElementById('totalDutyStatus')[0].selected=true;" >
								    	<option value="" style="background-color:#B0B0B0"></option>
							        	${dutyStatusSelectOption}
							        </select>
	        					</td>
		    					<td>&nbsp;${courseTake.student.code}</td>
		    					<td>&nbsp;<@i18nName courseTake.student?if_exists/></td>
		    					<td>&nbsp;<@i18nName courseTake.courseTakeType?if_exists/></td>
		   					</tr>
		   				</#if>
	   				</#list>
		   			<tr>
		    			<td colspan="4" align="center" class="darkColumn">
		     				<input type="hidden" name="courseTypeId" value="${RequestParameters["courseTypeId"]?if_exists}" />
					     	<input type="hidden" name="year" value="${RequestParameters['year']?if_exists}" />
					     	<input type="hidden" name="term" value="${RequestParameters['term']?if_exists}" />
					     	<input type="hidden" name="studentTypeId" value="${RequestParameters["studentTypeId"]?if_exists}" />
					     	<input type="hidden" name="studentTypeDescriptions" value="${RequestParameters["studentTypeDescriptions"]?if_exists}" />
					     	<input type="hidden" name="departmentId" value="${RequestParameters["departmentId"]?if_exists}" />
					     	<input type="hidden" name="departmentDescriptions" value="${RequestParameters["departmentDescriptions"]?if_exists}" />
					     	<input type="hidden" name="recordDetail.beginTime" />
					     	<input type="hidden" name="recordDetail.timeEnd" />
					     	<input type="hidden" name="targetstdId" />
					     	<input type="hidden" name="stdId" value="${targetstdId}" />	     
					     	<input type="hidden" name="recordId" />
					     	<input type="hidden" name="time0" />
					     	<input type="hidden" name="time1" />
					     	<input type="hidden" name="time2" />
					     	<input type="hidden" name="from" value="inputDutyRecord"/>
					     	<input type="hidden" id="method" name="method" value="input" />
					     	<input type="hidden" name="actionName" />
					     	<input type="hidden" name="teachTaskId" value="${RequestParameters["teachTaskId"]}" />
					     	<input type="button" onClick="search(this.form)" value="<@bean.message key="system.button.submit" />" name="button1" class="buttonStyle" />&nbsp;
					     	<input type="button"  onclick="this.form.reset();resetForm();" value="<@bean.message key="system.button.reset" />" name="reset1"  class="buttonStyle" />
	        			</td>
		   			</tr>
	   			</#if>
	   			</form>
     		</table>
    	</td>
   	</tr>
   	</div> 
   	<div id="chooseDiv" style="display:none">   
   		<tr>
   			<br>
   		</tr>   
   		<tr>
   			<table width="60%" align="center" class="listTable">
   				<tr class="darkColumn" align="center">
   					<td colspan="2" align="center">
   						<@bean.message key="info.duty.dutyExist"/>
   					</td>
   				</tr>
			   	<tr class="brightStyle">
			   		<td align="center"><input type="radio" name="chooseId" value="back" checked></td>
				    <td width="90%"><@bean.message key="attr.backPage"/></td>
			   	</tr>
			   	<tr class="brightStyle">
			   		<td align="center"><input type="radio" name="chooseId" value="history" ></td>
				    <td width="90%"><@bean.message key="action.goback"/></td>
			   	</tr>
			   	<tr class="brightStyle">
			   		<td align="center"><input type="radio" name="chooseId" value="modify" ></td>
				    <td width="90%"><@bean.message key="action.modify"/></td>
			   	</tr>
   				<tr class="darkColumn" align="center">
   					<td colspan="2" align="center">
   						<input type="button" onClick="choose()" value="<@bean.message key="system.button.submit" />" name="chooseButton" class="buttonStyle" />&nbsp;
   					</td>
   				</tr>
   			</table>
   		</tr>
   	</div>
</table><br><br>
</body>
<script> 	
	//returnTodayDate();
 	function dutyStatusChange(){
 		var totalDutyStatusValue = DWRUtil.getValue("totalDutyStatus");
 		if(totalDutyStatusValue==""){totalDutyStatusValue=0;}
 		for(i=0;i<dutyStatusArray.length;i++){
 			$(dutyStatusArray[i])[totalDutyStatusValue].selected=true;
 		} 		
 	}
 	
    function getIds(){
       return(getCheckBoxValue(document.getElementsByName("ids")));
    }
    
    function search(form){
    	var location = self.location.href;
        var url = location.split("/");
        var action = url[url.length-1].split("?");        
        form.targetstdId.value = getIds();        
        if(action[0]=="inputDutyRecordWithTeacher.do"){
        	document.getElementById("commonForm").action = "inputDutyRecordWithTeacher.do";
        }else{
        	document.getElementById("commonForm").action = "inputDutyRecord.do";
        }        
   		document.getElementById("method").value = "input";
        if (form["recordDetail.dutyDate"].value == ""){
            alert("<@bean.message key="info.duty.selectDate"/>");
            return false;
        }
        checkRecordDetail();
    }
    
    function choose(){
    	var chooseIdRadio = document.getElementsByName("chooseId");
    	var flag = "back";
    	for(var i =0; i < chooseIdRadio.length; i++){
    		if(chooseIdRadio[i].checked){
    			flag = chooseIdRadio[i].value;
    		}
    	}    	
    	if(flag=="back"){    	
    			document.getElementById("commonFormDiv").style.display = "block";
  	    		document.getElementById("chooseDiv").style.display = "none";
    		
    	}else if(flag=="modify"){
				var location = self.location.href;
        		var url = location.split("/");
        		var action = url[url.length-1].split("?");        		
        		if(action[0]=="inputDutyRecordWithTeacher.do"){
        			document.getElementById("commonForm").action = "dutyRecordManagerWithTeacher.do";
        		}else{
        			document.getElementById("commonForm").action = "dutyRecordManager.do";
        		}        		
  	    		document.getElementById("method").value = "updateDutyRecordForm";
  	    		document.getElementById("commonForm").submit();
    		
    	}else if(flag=="history"){    	
    			history.back();
    		
    	}
    }
    
    function returnTodayDate(){
    	var year=new Date().getFullYear();
    	var month=new Date().getMonth()+1;
    	var date=new Date().getDate();
    	var returnValue;
        returnValue=year+'-'+month+'-'+date;
        document.getElementById("recordDetail.dutyDate").value=returnValue;
    }
    
    function dutyStatistics(){
    	var dutyStatistics;
    }
    
    var courseUnitMap= new Object();    
    <#list result?if_exists.teachTask?if_exists.calendar?if_exists.timeSetting?if_exists.courseUnits?if_exists?sort_by("index") as courseUnit>    	
    	courseUnitMap[${courseUnit.id}]={'id':'${courseUnit.id}','name':'<@i18nName courseUnit/>','index':${courseUnit.index},'startTime':'<@getTimeStr courseUnit.startTime?if_exists/>','finishTime':'<@getTimeStr courseUnit.finishTime?if_exists/>'};
    </#list>    
    DWRUtil.removeAllOptions('beginUnit');
    DWRUtil.addOptions('beginUnit',courseUnitMap,'id','name');   
    DWRUtil.removeAllOptions('endUnit');
    DWRUtil.addOptions('endUnit',courseUnitMap,'id','name');
    function changeBeginTime(){DWRUtil.setValue('beginTime',courseUnitMap[DWRUtil.getValue('beginUnit')].startTime);DWRUtil.setValue('endUnit',DWRUtil.getValue('beginUnit'));changeEndTime();}
    function changeEndTime(){DWRUtil.setValue('endTime',courseUnitMap[DWRUtil.getValue('endUnit')].finishTime);}    
    
    function resetForm(){
    	changeBeginTime();
	    changeEndTime();
    }
    resetForm();
</script>
<#include "/templates/foot.ftl"/>
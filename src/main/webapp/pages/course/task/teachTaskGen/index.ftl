<#include "/templates/head.ftl"/>
<link href="${static_base}/css/tab.css" rel="stylesheet" type="text/css">
<BODY LEFTMARGIN="0" TOPMARGIN="0">
	<table id="taskGenBar"></table>
	<table class="frameTable_title" width="100%">
      	<tr>
	       	<td id="viewTD0" class="transfer" style="width:180px" onclick="javascript:loadDefaultTeachPlan(event,'true')" onmouseover="viewMouseOver(event)" onmouseout="viewMouseOut(event)">
	          	<font color="blue"><@bean.message key="attr.activeScheme"/></font>
	       	</td>
	       	<td id="viewTD1" class="padding" style="width:180px" onclick="javascript:loadDefaultTeachPlan(event,'false')" onmouseover="viewMouseOver(event)" onmouseout="viewMouseOut(event)">
	          	<font color="blue"><@bean.message key="attr.unActiveScheme"/></font>
	       	</td>
       		<td class="separator">|</td>
       	<form name="planSearchForm" method="post" action="teachTaskGen.do?method=index" onsubmit="return false;">
       		<input type="hidden" name="active" value="true"/>
       		<#include "/pages/course/calendar.ftl"/>
      	</tr>
    </table>
  	<table width="100%" class="frameTable" height="85%">
		<tr valign="top">
	     	<td width="20%" class="frameTable_view">
		   		<table width="100%">
		    		<tr>
		      			<td align="left" valign="bottom">
		       				<img src="${static_base}/images/action/info.gif" align="top"/>
		          			<B><@bean.message key="action.advancedQuery"/></B>
		      			</td>
		     		<tr>
				    <tr>
				      	<td colspan="8" style="font-size:0px">
				          	<img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
				      	</td>
				    </tr>
	  			</table>
	  			<table onKeyDown="javascript:search();" width="100%">
    				<input type="hidden" name="pageNo" value="1">
    				<tr>
     					<td width="40%"><@bean.message key="attr.enrollTurn"/></td>
     					<td><input name="teachPlan.enrollTurn" maxlength="7" type="text" value="" style="width:100px"/></td>
				    </tr>
				    <tr>
	     				<td><@bean.message key="entity.studentType"/></td>
					    <td>
							<select id="stdTypeOfSpeciality" name="teachPlan.stdType.id" value="" style="width:100px">
						     	<#if defaultStdType?exists>
						     		<#assign defaultStdTypeId=defaultStdType.id>
						     	<#else>
						     		<#assign defaultStdTypeId=stdTypeList?first.id>
						     	</#if>
						     	<option value='${defaultStdTypeId}'></option>
						     </select>
					    </td>
    				</tr>
    				<tr>
   	 					<td><@bean.message key="entity.department"/></td>
      					<td> 
					        <select id="department" name="teachPlan.department.id"  style="width:100px;">
					           <option value=""><@bean.message key="common.selectPlease"/></option>
					        </select>
      					</td>
				    </tr>
				    <tr>
      					<td><@bean.message key="entity.speciality"/></td>
      					<td align="left">
					        <select id="speciality" name="teachPlan.speciality.id" style="width:100px;">
					           <option value=""><@bean.message key="common.selectPlease"/></option>
					        </select>
      					</td>
    				</tr>
    				<tr>
      					<td><@bean.message key="entity.specialityAspect"/></td>
      					<td align="left">
        					<select id="specialityAspect" name="teachPlan.aspect.id" style="width:100px;">
         						<option value=""><@bean.message key="common.selectPlease"/></option>
        					</select>
      					</td>
    				</tr>
					<tr>
     					<td><@msg.message key="std.specialityType"/>:</td>
     					<td>
      						<select name="teachPlan.speciality.majorType.id" onchange="changeClassSpecialityType(event)" style="width:100px;">
        						<option value="1"><@msg.message key="entity.firstSpeciality"/></option>
        						<option value="2"><@msg.message key="entity.secondSpeciality"/></option>
      						</select>
     					</td>
    				</tr>
				    <tr>
				     	<td colspan="2">
					     	<input type="radio" name="teachPlan.isConfirm" value="1"><@msg.message key="action.affirm"/>
					     	<input type="radio" name="teachPlan.isConfirm" value="0"><@msg.message key="action.negate"/>
					     	<input type="radio" name="teachPlan.isConfirm" value=""><@msg.message key="common.all"/>
				     	</td>
    				</tr>
    				<tr align="center">
				     	<td colspan="2">
					     	<input type="button" onClick="searchPlan()" class="buttonStyle" value="<@bean.message key="action.query"/>" style="width:60px"/>		    
				     	</td>
    				</tr>
	  			</table>
     		</td>
        </form>
	  			<#assign stdTypeList=calendarStdTypes/>
     			<#include "/templates/stdTypeDepart3Select.ftl"/>
     		<td valign="top" width="80%">
     			<iframe src="#" id="planListFrame" name="planListFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0"  height="100%" width="100%"></iframe>
     		</td>
    	</tr>
  	<table>
	<script>
		var bar = new ToolBar("taskGenBar","<@msg.message key="teachTask.generation.title"/>",null,true,true);
		bar.setMessage('<@getMessage/>');
		bar.addHelp("<@msg.message key="action.help"/>");
		
	   	searchPlan();
	   	
	    function searchPlan(){
	       	var form = document.planSearchForm;
	       	form.action="teachTaskGen.do?method=teachPlanList"
	       	form.target="planListFrame";
	       	form.submit();
	    }
	   	function loadDefaultTeachPlan(event,active){
	      	changeView(getEventTarget(event));
	      	var form = document.planSearchForm;
	      	form.active.value=active;
	      	form.action = "teachTaskGen.do?method=teachPlanList";
	      	form.target="planListFrame";
	      	form.submit();
	   	}
	  	var viewNum=2;
	  	
	    function search() {
            if (window.event.keyCode == 13) {
                searchPlan();
            }
        }
	</script>
	<script language="JavaScript" type="text/JavaScript" src="scripts/viewSelect.js"></script>
</body>
<#include "/templates/foot.ftl"/> 
  
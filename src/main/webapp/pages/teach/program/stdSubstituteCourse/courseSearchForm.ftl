	<table width="100%">
	   <tr>
            <td align="left" valign="bottom" colspan="2"><img src="${static_base}/images/action/info.gif" align="top"/>&nbsp;<B>个人替代课程查询(模糊输入)</B></td>
        </tr>
        <tr>
            <td colspan="2" style="font-size:0px"><img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"></td>
        </tr>
	    <form name="planSearchForm" method="post" action="" onsubmit="return false;">
	    <tr>
	     	<td class="infoTitle">学号：</td>
	     	<td><input name="substitutionCourse.std.code" maxlength="32" type="text" value="" style="width:100px"/></td>
	    </tr>
	    <tr>
	     	<td class="infoTitle">姓名：</td>
	     	<td><input name="substitutionCourse.std.name" maxlength="20" type="text" value="" style="width:100px"/></td>
	    </tr>
	     <tr>
	     	<td id="f_grade" width="50%">所在年级：</td>
	     	<td><input name="substitutionCourse.std.enrollTurn" type="text" value="" style="width:100px" maxlength="7"/></td>
	    </tr>
	     <tr>
	     	<td >原始课程代码：</td>
	     	<td><input name="substitutionCourse.origin.code" maxlength="20" type="text" value="" style="width:100px"/></td>
	    </tr>
	    <tr>
	     	<td>原始课程名称：</td>
	     	<td><input name="substitutionCourse.origin.name" maxlength="20" type="text" value="" style="width:100px"/></td>
	    </tr>
	     <tr>
	     	<td >替代课程代码：</td>
	     	<td><input name="substitutionCourse.substitute.code" maxlength="20" type="text" value="" style="width:100px"/></td>
	    </tr>
	    <tr>
	     	<td width="20%">替代课程名称：</td>
	     	<td><input name="substitutionCourse.substitute.name" maxlength="20" type="text" value="" style="width:100px"/></td>
	    </tr>
	    <tr>
	     	<td><@bean.message key="entity.studentType"/>：</td>
	     	<td>
		     	<select id="stdTypeOfSpeciality" name="substitutionCourse.std.type.id" value="" style="width:100px">
		     		<option value="${stdTypeList?first.id}"></option>
		     	</select>
	     	</td>
	    </tr>
	    <tr>
	   	 	<td><@bean.message key="entity.department"/>：</td>
	      	<td>
	        	<select id="department" name="substitutionCourse.std.department.id" style="width:100px;">
	           		<option value="<#if departmentList?size==1>${departmentList?first.id}</#if>"></option>
	        	</select>
	      	</td>
	    </tr>
	    <tr>
	      	<td><@bean.message key="entity.speciality"/>：</td>
	      	<td align="left">
	        	<select id="speciality" name="substitutionCourse.std.firstMajor.id" style="width:100px;">
	           		<option value=""><@bean.message key="common.selectPlease"/></option>
	        	</select>
	      	</td>
	    </tr>
	    <tr>
	      	<td ><@bean.message key="entity.specialityAspect"/>：</td>
	      	<td align="left" >
	        	<select id="specialityAspect" name="substitutionCourse.std.firstAspect.id" style="width:100px;">
	         		<option value=""><@bean.message key="common.selectPlease"/></option>
	        	</select>
	      	</td>
	    </tr>
		<tr>
	     	<td>专业类别：</td>
	     	<td>
	      		<select name="substitutionCourse.std.firstMajor.majorType.id" onchange="changeMajorType(event)" style="width:100px;">
	        		<option value="1">第一专业</option>
	        		<option value="2">第二专业</option>
	      		</select>
	     	</td>
	    </tr>
	    <tr align="center">
	     	<td colspan="2">
			    <input type="submit" onClick="doSearch()" class="buttonStyle" value="<@bean.message key="action.query"/>" style="width:60px"/>		    
		        <input type="reset" class="buttonStyle" value="<@msg.message key="action.reset"/>" style="width:60px"/>
	     	</td>
	    </tr>
	    </form>
	</table>
	<#include "/templates/stdTypeDepart3Select.ftl"/>
	<script>
	    var form = document.planSearchForm;
	    function doSearch() {
	    	var form = document.planSearchForm;
	    	form.target = "stdSubstituteCourseFrame";
	    	form.action = "stdSubstituteCourse.do?method=search";
	    	form.submit();
	    }
	</script>
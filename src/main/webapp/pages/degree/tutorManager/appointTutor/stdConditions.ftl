<table width="100%">
        <tr>
            <td align="left" valign="bottom" colspan="2"><img src="${static_base}/images/action/info.gif" align="top"/>&nbsp;<B>查询条件</B></td>
       </tr>
        <tr>
            <td colspan="2" style="font-size:0px"><img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/></td>
        </tr>
    	<tr>
	     <td width="40%"><@msg.message key="attr.stdNo"/>：</td>
	     <td><input type="text" name="student.code" maxlength="32" style="width:100px"/>
	     </td>
       </tr>
	   <tr>
	     <td><@msg.message key="filed.enrollYearAndSequence"/>：</td>
	     <td><input type="type" name="student.enrollYear" maxlength="7" value="" style="width:100px"/>
         </td>
       </tr>
       <tr>
	     <td><@msg.message key="entity.studentType"/>：</td>
	     <td><select id="stdTypeOfSpeciality" name="student.type.id" style="width:100px"></select></td>
		</tr>
    	<tr>
	     <td><@msg.message key="common.college"/>：</td>
	     <td>
           <select id="department" name="student.department.id" style="width:100px;">
         	  <option value="">...</option>
           </select>
         </td>  
       </tr>
	   <tr>
	     <td><@msg.message key="entity.speciality"/>：</td>
	     <td >
           <select id="speciality" name="student.firstMajor.id" style="width:100px;">
         	  <option value="">...</option>
           </select>
         </td>
       </tr>
	   <tr>
	     <td><@msg.message key="entity.specialityAspect"/>：</td>
	     <td >
           <select id="specialityAspect" name="student.firstAspect.id" style="width:100px;">
         	  <option value="">...</option>
           </select>
         </td>
       </tr>
    	<tr>
	     <td>班级名称：</td>
	     <td>
	      <input type="text" name="enrollClassName" maxlength="20" value="" style="width:100px"/>
	     </td>
       </tr>
	  <tr>
	  <tr>
	     <td>有无导师：</td>
	     <td><select name="isHasTutor" style="width:100px">
	     		<option value="">全部</option>
	     		<option value="true">有</option>
	     		<option value="false">无</option>
	     	</select>
	     </td>
       </tr>
	  <tr height="50px">
	   <td colspan="2" align="center">
	    <button name="button9" class="buttonStyle" onClick="search(1);"><@msg.message key="system.button.confirm"/></button>
       </td>
	  </tr>
   </table>
 <#assign stdTypeNullable=true>
<#include "/templates/stdTypeDepart3Select.ftl"/>
	  <table width="100%" onKeyDown="javascript:search(event);">
	    <tr>
	        <td align="left" valign="bottom" colspan="2"><img src="${static_base}/images/action/info.gif" align="top"/>&nbsp;<B>计划查询(模糊输入)</B></td>
	    </tr>
	    <tr>
	        <td colspan="2" style="font-size:0px"><img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"></td>
	    </tr>
	    <form name="planSearchForm" method="post" action="" onsubmit="return false;">
	    <input type="hidden" name="pageNo" id="pageNo" value="1"/>
	    <input type="hidden" name="pageSize" id="pageSize" value="20"/>
	    <input type="hidden" name="params"  value=""/>
	    <input type="hidden" name="withAuthority" value="0"/>
	    <input type="hidden" name="type"  value="speciality"/>
	    <input type="hidden" name="teachPlan.isConfirm" value="1"/>
	    <tr>
	     <td width="40%"><@bean.message key="attr.enrollTurn"/>:</td>
	     <td width="60%"><input name="teachPlan.enrollTurn" maxlength="7" type="text" value="" style="width:100%"/></td>
	    </tr>
	    <tr>
	     <td><@bean.message key="entity.studentType"/>:</td>
	     <td>
		     <select id="stdTypeOfSpeciality" name="teachPlan.stdType.id" value="" style="width:100%">
		     	<option value="${stdTypeList?first.id}"></option>
		     </select>
	     </td>
	    </tr>
	    <tr>
	   	 <td><@bean.message key="common.college"/>:</td>
	      <td>
	        <select id="department" name="teachPlan.department.id" style="width:100%;">
	           <option value="<#if departmentList?size==1>${departmentList?first.id}</#if>"></option>
	        </select>
	      </td>
	    </tr>
	    <tr>
	      <td><@bean.message key="entity.speciality"/>:</td>
	      <td align="left">
	        <select id="speciality" name="teachPlan.speciality.id" style="width:100%;">
	           <option value=""><@bean.message key="common.selectPlease"/></option>
	        </select>
	      </td>
	    </tr>
	    <tr>
	      <td><@bean.message key="entity.specialityAspect"/>:</td>
	      <td align="left" >
	        <select id="specialityAspect" name="teachPlan.aspect.id" style="width:100%;">
	         <option value=""><@bean.message key="common.selectPlease"/></option>
	        </select>
	      </td>
	    </tr>
	    <tr align="center">
	     <td colspan="2">
		     <input type="button" onClick="searchTeachPlan(1,20)" class="buttonStyle" value="<@bean.message key="action.query"/>" style="width:60px"/>		    
	     </td>
	    </tr>
        </form>
	  </table>
      <#include "/templates/stdTypeDepart3Select.ftl"/>

	   <table  width="100%">
	    <tr>
	      <td  class="infoTitle" align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B>详细查询(模糊输入)</B>      
	      </td>
	    </tr>
	    <tr>
	      <td  colspan="8" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
	   </tr>	
	  </table>
	  <table class="searchTable"  onkeypress="DWRUtil.onReturn(event, search)">	  
	    <tr>
	     <td class="infoTitle"><@bean.message key="attr.courseNo"/>:</td>
	     <td><input name="examTake.task.course.code" type="text" value="" style="width:60px" maxlength="32"/></td>
	    </tr>
	    <tr>
	     <td class="infoTitle"><@bean.message key="attr.courseName"/>:</td>
	     <td><input type="text" name="examTake.task.course.name" value="" style="width:100px" maxlength="20"/></td>
	    </tr>
	    <tr>
	     <td class="infoTitle"><@bean.message key="attr.teachDepart"/>:</td>
	     <td>
		     <select name="examTake.task.arrangeInfo.teachDepart.id" value="" style="width:100px">
		     	<option value=""><@bean.message key="common.all"/></option>
		     	<#list sort_byI18nName(teachDepartList) as teachDepart>
		     	<option value=${teachDepart.id}><@i18nName teachDepart/></option>
		     	</#list>-->
		     </select>
	     </td>
	    </tr>
	    <#--<tr>
	     <td class="infoTitle"><@bean.message key="entity.teacher"/>:</td>
	     <td><input type="text" name="activity.teacher.name" value="" style="width:100px" maxlength="20"/>
	     </td>
	    </tr>
	    <tr>
	     <td class="infoTitle"><@bean.message key="attr.enrollTurn"/>:</td>
	     <td><input type="text" name="activity.task.teachClass.enrollTurn" value="" maxlength="7" style="width:60px"></td>
	    </tr>-->
	    <tr align="center">
	     <td colspan="2">
		     <button onClick="search();" accesskey="Q"  style="width:60px">
		       <@bean.message key="action.query"/>(<U>Q</U>)
		     </button>		    
	     </td>
	    </tr>
	  </table>

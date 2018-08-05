<#include "/templates/head.ftl"/>
<table width="100%" class="frameTable">
 <tr>
   <td valign="top" width="20%" class="frameTable_view">
	  <table class="searchTable" onKeyDown="javascript:search(event);">
	    <form name="planSearchForm" action="courseStat.do?method=planCourseList" target="queryFrame" method="post" onsubmit="return false;">
	    <tr>
	     <td class="infoTitle"><@bean.message key="entity.studentType"/></td>
	     <td><@htm.i18nSelect datas=stdTypeList id="stdType" name="stdType.id" selected="" style="width:110px"/></td>
	    </tr>
	    <tr>
	   <td class="infoTitle"><@bean.message key="attr.year2year"/></td>
	   <td style="width:100px;">
	     <select id="year" name="calendar.year"  style="width:100px;">                
	        <option value="${calendar.year}"></option>
	      </select>
	    </td>
	    </tr>
	    <tr>
	    <td class="infoTitle"><@bean.message key="attr.term"/></td>
	    <td>     
	     <select id="term" name="calendar.term" style="width:60px;">
	        <option value="${calendar.term}"></option>
	      </select>
	   </td>
	   </tr>
	    <tr>
	   	 <td class="infoTitle"/><@bean.message key="attr.teachDepart"/></td>
	      <td class="brightStyle">
              <@htm.i18nSelect datas=departmentList  name="planCourse.teachDepart.id" selected="" style="width:110px">
	            <option value="">...</option>
	          </@>
	      </td>
	    </tr>
		<tr>
	     <td class="infoTitle">专业类别:</td>
	     <td>
	      <select name="majorTypeId">
	        <option value="1">第一专业</option>
	        <option value="2">第二专业</option>
	      </select>
	     </td>
	    </tr>
	    <tr align="center">
	     <td colspan="2">
		     <input type="button" onClick="this.form.submit()" class="buttonStyle" value="<@bean.message key="action.query"/>" style="width:60px"/>		    
	     </td>
	    </tr>
        </form>
	  </table>
	  <br><br><br><br><br><br><br><br>
	  </td>
  	<td width="70%" valign="top">
     	<iframe name="queryFrame" id="queryFrame" src="#" width="100%" frameborder="0" scrolling="no"></iframe>
	</td>
	</tr>
	</table>
	<script>
		document.planSearchForm.submit();
	</script>
 <#include "/templates/calendarSelect.ftl"/>
<#include "/templates/foot.ftl"/>
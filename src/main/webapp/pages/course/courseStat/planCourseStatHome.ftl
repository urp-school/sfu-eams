<#include "/templates/head.ftl"/>
<table width="100%" class="frameTable">
 <tr>
   <td valign="top" width="20%" class="frameTable_view">
	  <table class="searchTable" onKeyDown="javascript:search(event);">
	    <form name="planSearchForm" action="courseStat.do?method=planCourseStat" target="queryFrame" method="post" onsubmit="return false;">
	    <tr>
	     <td class="infoTitle"  ><@bean.message key="entity.studentType"/></td>
	     <td><@htm.i18nSelect datas=stdTypeList id="stdType" name="stdType.id" selected="" style="width:110px"/></td>
	    </tr>
        <tr>
			<td colspan="2">
			    <fieldSet align=left> 
		 		 <legend style="font-weight:bold;font-size:12px">从</legend>
		 		 <table width="100%"><tr>
		 		   <td>学年度:</td><td><select id="yearStart" name="yearStart"></select></td>
		 		   </tr><tr>
				   <td>学 期:</td><td><select id="termStart" name="termStart" ></select></td>
				</tr></table>
				</fieldSet>
			  </td>
			</tr>
			<tr>
			<td colspan="2">
			    <fieldSet align=left> 
		 		 <legend style="font-weight:bold;font-size:12px">到</legend>
		 		 <table width="100%"><tr>
		 		   <td>学年度:</td><td><select id="yearEnd" name="yearEnd"></select></td>
		 		   </tr><tr>
				   <td>学 期:</td><td><select id="termEnd" name="termEnd" ></select></td>
				</tr></table>
				</fieldSet>
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
		     <button  onClick="this.form.submit()"><@bean.message key="action.query"/></button>		    
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
 		<script src='dwr/interface/calendarDAO.js'></script>
		<script src='scripts/common/CalendarSelect.js'></script>
		<script>
			var stdTypeArray = new Array();
			<#list stdTypeList?sort_by("code") as stdType>
				stdTypeArray[${stdType_index}]={'id':'${stdType.id?if_exists}','name':'<@i18nName stdType/>'};
			</#list>
			DWREngine.setAsync(false);
			var dd1 = new CalendarSelect("stdType","yearStart","termStart",false,false,false);
			dd1.init(stdTypeArray);
			var dd2 = new CalendarSelect("stdType","yearEnd","termEnd",false,false,false);
			dd2.init(stdTypeArray);
			document.planSearchForm.submit();
    	</script>
<#include "/templates/foot.ftl"/>
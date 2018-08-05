	<table width="100%">
	    <tr>
	      <td class="infoTitle" align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B>详细查询(模糊输入)</B>
	      </td>
	    </tr>
	    <tr>
	      <td colspan="8" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
	      </td>
	   </tr>
	  </table>
    <table width="100%" class="searchTable" onkeypress="DWRUtil.onReturn(event, search)">
    <form name="searchForm" method="post" action="" onsubmit="return false;">
        <input type="hidden" name="pageNo" value="1"/>
    	<tr>
	     <td class="infoTitle" width="35%"><@bean.message key="attr.stdNo"/>:</td>
	     <td>
	      <input type="text" name="signUp.std.code" maxlength="32" size="10" value="${RequestParameters['student.code']?if_exists}" style="width:100px"/>
	     </td>
		</tr>
    	<tr>
	     <td class="infoTitle"><@msg.message key="attr.personName"/>:</td>
	     <td>
	      <input type="text" name="signUp.std.name" maxlength="20" size="10" value="${RequestParameters['student.name']?if_exists}" style="width:100px"/>
	     </td>
		</tr>
	   <tr>
	     <td class="infoTitle">就读年级:</td>
	     <td ><input type="text" name="signUp.std.enrollYear" id='student.enrollYear' style="width:100px;" maxlength="7"/></td>
	   </tr>
	   <tr>
	   	<td><@msg.message key="std.adminClass.baseInfo.name"/>:</td>
	   	<td><input type="text" name="stdAdminClass" value="" maxlength="50" style="width:100px"/></td>
	   </tr>
       <tr>
	     <td class="infoTitle"><@bean.message key="entity.studentType"/>:</td>
	     <td>
	          <select id="stdTypeOfSpeciality" name="signUp.std.type.id" style="width:100px;">
	            <option value="${RequestParameters['student.type.id']?if_exists}"><@bean.message key="filed.choose"/></option>
	          </select>	 
         </td>
		</tr>
    	<tr>
	     <td class="infoTitle"><@bean.message key="common.college"/>:</td>
	     <td>
           <select id="department" name="signUp.std.department.id" style="width:100px;">
         	  <option value=""><@bean.message key="filed.choose"/>...</option>
           </select>
         </td>
        </tr> 
	   <tr>
	     <td class="infoTitle"><@bean.message key="entity.speciality"/>:</td>
	     <td>
           <select id="speciality" name="signUp.std.firstMajor.id" style="width:100px;">
         	  <option value=""><@bean.message key="filed.choose"/>...</option>
           </select>
         </td>
        </tr>
	   <tr>
	     <td class="infoTitle"><@bean.message key="entity.specialityAspect"/>:</td>
	     <td >
           <select id="specialityAspect" name="signUp.std.firstAspect.id" style="width:100px;">
         	  <option value=""><@bean.message key="filed.choose"/>...</option>
           </select>
         </td>
        </tr>
        <tr>
		   <td class="infoTitle" style="width:60px;"><@bean.message key="attr.year2year"/>:</td>
		   <td style="width:100px;">
		     <select id="year" name="signUp.calendar.year" style="width:100px;">
		        <option value=""></option>
		      </select>
		   </td>
      </tr>
      <tr>
	    <td class="infoTitle" style="width:50px;"><@bean.message key="attr.term"/>:</td>
	    <td style="width:50px;">
	     <select id="term" name="signUp.calendar.term" style="width:100px;">
	        <option value=""></option>
	      </select>
	   </td>
	   </tr>
       <tr>
	     <td class="infoTitle">考试类别:</td>
	     <td><@htm.i18nSelect datas=otherExamCategories?sort_by("name") selected="" name="signUp.category.id" style="width:100px">
	          <option value="">全部</option>
	          </@>
         </td>
       </tr>
       <tr>
	     <td class="infoTitle">起始时间:</td>
	     <td><input name="startAt" value="" maxlength="10" onfocus="calendar()" style="width:100px"/></td>
       </tr>
       <tr>
	     <td class="infoTitle">结束时间:</td>
	     <td><input name="endAt" value="" onfocus="calendar()" maxlength="10" style="width:100px"/></td>
       </tr>
	   <tr align="center">
	     <td colspan="2">
		     <button  onclick="search(1)" style="width:60px"><@bean.message key="action.query"/></button>
	     </td>
	   </tr>
    </form>
  </table> 
  <#assign stdTypeNullable=true>
<#include "/templates/stdTypeDepart3Select.ftl"/>
<script src='dwr/interface/calendarDAO.js'></script>
<script src='scripts/common/CalendarSelect.js'></script>
<script>
    var stdTypeArray = new Array();
    <#list stdTypeList as stdType>
    stdTypeArray[${stdType_index}]={'id':'${stdType.id?if_exists}','name':'<@i18nName stdType/>'};
    </#list>
    var dd = new CalendarSelect("stdTypeOfSpeciality","year","term",true,true,false);
    dd.init(stdTypeArray);
    sds.firstSpeciality=1;
</script> 
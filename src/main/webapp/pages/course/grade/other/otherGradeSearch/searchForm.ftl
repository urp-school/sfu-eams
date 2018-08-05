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
    <table width='100%' class="searchTable" onkeypress="DWRUtil.onReturn(event, search)">
    <form name="searchForm" method="post" action="" onsubmit="return false;">
        <input type="hidden" name="pageNo" value="1"/>
    	<tr>
	     <td class="infoTitle" width="35%"><@bean.message key="attr.stdNo"/>:</td>
	     <td>
	      <input type="text" name="otherGrade.std.code" size="10" value="${RequestParameters['student.code']?if_exists}" style="width:100px;"/>
	     </td>
		</tr>
    	<tr>
	     <td class="infoTitle"><@msg.message key="attr.personName"/>:</td>
	     <td>
	      <input type="text" name="otherGrade.std.name" size="10" value="${RequestParameters['student.name']?if_exists}" style="width:100px;"/>
	     </td>
		</tr>
	   <tr>
	     <td class="infoTitle">就读年级:</td>
	     <td><input type="text" name="otherGrade.std.enrollYear" id='student.enrollYear' style="width:100px;" value="${RequestParameters["otherGrade.std.enrollYear"]?if_exists}"/></td>
	   </tr>
       <tr>
	     <td class="infoTitle"><@bean.message key="entity.studentType"/>:</td>
	     <td>
	          <select id="stdTypeOfSpeciality" name="otherGrade.std.type.id" style="width:100px;">
	            <option value="${RequestParameters['student.type.id']?if_exists}"><@bean.message key="filed.choose"/></option>
	          </select>
         </td>
		</tr>
    	<tr>
	     <td class="infoTitle"><@bean.message key="common.college"/>:</td>
	     <td>
           <select id="department" name="otherGrade.std.department.id" style="width:100px;">
         	  <option value=""><@bean.message key="filed.choose"/>...</option>
           </select>
         </td>
        </tr>
	   <tr>
	     <td class="infoTitle"><@bean.message key="entity.speciality"/>:</td>
	     <td>
           <select id="speciality" name="otherGrade.std.firstMajor.id" style="width:100px;">
         	  <option value=""><@bean.message key="filed.choose"/>...</option>
           </select>
         </td>
        </tr>
	   <tr>
	     <td class="infoTitle"><@bean.message key="entity.specialityAspect"/>:</td>
	     <td >
           <select id="specialityAspect" name="otherGrade.std.firstAspect.id" style="width:100px;">
         	  <option value=""><@bean.message key="filed.choose"/>...</option>
           </select>
         </td>
        </tr>
        <tr>
		   <td class="infoTitle" style="width:60px;"><@bean.message key="attr.year2year"/>:</td>
		   <td style="width:100px;">
		     <select id="year" name="otherGrade.calendar.year" style="width:100px;">
		        <option value=""></option>
		      </select>
		   </td>
      </tr>
      <tr>
	    <td class="infoTitle" style="width:50px;"><@bean.message key="attr.term"/>:</td>
	    <td style="width:50px;">
	     <select id="term" name="otherGrade.calendar.term" style="width:100px;">
	        <option value=""></option>
	      </select>
	   </td>
	   </tr>
	   <tr>
	   	<td><@msg.message key="std.adminClass.baseInfo.name"/>:</td>
	   	<td><input type="text" name="stdAdminClass" value="" maxlength="50" style="width:100px"/></td>
	   </tr>
       <tr>
	     <td class="infoTitle">考试类别:</td>
	     <td><@htm.i18nSelect datas=otherExamCategories?sort_by("name") selected="" name="otherGrade.category.id" style="width:100px">
	          <option value="">全部</option>
	          </@>
         </td>
       </tr>
       <tr>
	     <td class="infoTitle">是否通过:</td>
	     <td >
	     <select id="term" name="otherGrade.isPass" style="width:100px;">
	        <option value="">全部</option>
	        <option value="1">通过</option>
	        <option value="0">未通过</option>
	        <option value="-1">一直未通过</option>
	      </select>
         </td>
       </tr>
       <tr>
	     <td class="infoTitle">记录方式:</td>
	     <td><@htm.i18nSelect datas=markStyles?sort_by("name") selected="" name="otherGrade.markStyle.id" style="width:100px">
	          <option value="">全部</option>
	         </@>
         </td>
       </tr>
    <#-- <tr>
    <td id="f_studentStatus">学籍状态:</td>
	 	<td>
	 		<select name="otherGrade.std.state.id" style="width:100px;">
				<option value="">...</option>
				<#list result.studentStateList as studentState>
					<option value="${studentState.id}"<#if RequestParameters["otherGrade.std.state.id"]?default("0")==studentState.id?string> selected</#if>/><@i18nName studentState/></option>
				</#list>
			</select>
	 	</td>
	 	</tr>  -->
	   <tr align="center">
	     <td colspan="2">
		     <button  onclick="search(1)" style="width:60px"><@bean.message key="action.query"/></button>
	     </td>
	   </tr>
    </form>
  </table> 
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
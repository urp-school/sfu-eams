   <div style="display: block;" id="view1">
    <form name="adminClassSearchForm" method="post" action="" onsubmit="return false;">
	  <#include "/pages/components/adminClassSearchTable.ftl">
     </form>
    </div>
    
   <div style="display: none;" id="view2">
	  <table class="frameTable" onkeypress="DWRUtil.onReturn(event, searchResource)" width="100%">
	    <tr>
	      <td colspan="2" class="infoTitle" align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B><@bean.message key="baseinfo.searchTeacher"/></B>
	      </td>
	    <tr>
	      <td colspan="2" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
	   </tr>
	    <form name="teacherSearchForm" method="post" action="" onsubmit="return false;">
	    <input name="teacher.isTeaching" type="hidden" value="1"/>
	    <tr> 
	     <td><@bean.message key="teacher.code"/>:</td>
	     <td><input name="teacher.code" type="text" value="" style="width:60px" maxlength="32"/></td>
	    </tr>
	    <tr>
	     <td><@bean.message key="attr.personName"/>:</td>
	     <td><input name="teacher.name" type="text" value="" style="width:60px" maxlength="20"/></td>
	    </tr>
	    <tr>
	   	 <td id="f_department"><@bean.message key="entity.college"/>:</td>
	      <td >
	        <select name="teacher.department.id"  style="width:100px;">
	    	   <option value=""><@bean.message key="common.selectPlease"/></option>
		       <#list teacherDeparts?sort_by("name") as depart>
		       <option value="${depart.id}"><@i18nName depart/></option>
	           </#list>
	        </select>
	      </td>
	     </td>
	    </tr>
	    <tr align="center">
	     <td colspan="2">
		     <input type="button" onClick="searchResource()" class="buttonStyle" value="<@bean.message key="action.query"/>" style="width:60px"/>
	     </td>
	    </tr>
        </form>
	  </table>
    </div>
   <div style="display: none;" id="view3">
	  <table class="frameTable" onkeypress="DWRUtil.onReturn(event, searchResource)" width="100%">
	    <tr>
	      <td colspan="2" class="infoTitle" align="left" valign="bottom" >
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B><@bean.message key="baseinfo.searchRoom"/></B>
	      </td>
	    <tr>
	      <td colspan="2" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
	   </tr>
	    <form name="roomSearchForm" method="post">
	    <tr>
	     <td class="infoTitle"><@bean.message key="attr.name"/>：</td>
	     <td><input name="classroom.name" type="text" value="${RequestParameters["classroom.name"]?if_exists}" style="width:100px"/></td>
	    </tr>
        <tr>
          <td class="infoTitle">设备配置：</td>
	      <td id="configType">
	         <@htm.i18nSelect datas=classroomConfigTypeList selected=RequestParameters["classroom.configType.id"]?default("") name="classroom.configType.id" style="width:100px;">
	           <option value=""><@bean.message key="common.all"/></option>
	         </@>
	      </td>
	    </tr>
		<tr>
		   <td class="infoTitle"><@bean.message key="entity.schoolDistrict"/>：</td>
	 	    <td>
	  	       <select id="district" name="classroom.schoolDistrict.id" style="width:100px;" value="${RequestParameters["classroom.schoolDistrict.id"]?if_exists}">
		           <option value=""><@bean.message key="common.all"/></option>
		       </select>
  	       </td>
	    </tr>
	    <tr>
	      <td class="infoTitle"><@bean.message key="entity.building"/>：</td>
	      <td >
	         <select id="building" name="classroom.building.id" style="width:100px;" value="${RequestParameters["classroom.building.id"]?if_exists}">
		         <option value=""><@bean.message key="common.all"/></option>
	         </select>
	 	    </td>
	    </tr>
	    <tr>
	       <td>考试人数：</td>
	       <td><input type="text" name="examCountFrom" value="${RequestParameters["examCountFrom"]?if_exists}" style="width:43px;" maxlength="4"/>－<input type="text" name="examCountTo" value="${RequestParameters["examCountTo"]?if_exists}" style="width:43px;" maxlength="4"/></td>
	    </tr>
	    <tr>
	       <td>听课人数：</td>
	       <td><input type="text" name="courseCountFrom" value="${RequestParameters["courseCountFrom"]?if_exists}" style="width:43px;" maxlength="4"/>－<input type="text" name="courseCountTo" value="${RequestParameters["courseCountTo"]?if_exists}" style="width:43px;" maxlength="4"/></td>
	    </tr>
	    <tr>
	       <td>真正容量：</td>
	       <td><input type="text" name="capacityFrom" value="${RequestParameters["capacityFrom"]?if_exists}" style="width:43px;" maxlength="4"/>－<input type="text" name="capacityTo" value="${RequestParameters["capacityTo"]?if_exists}" style="width:43px;" maxlength="4"/></td>
	    </tr>
	    <tr align="center">
	     <td colspan="2">
		     <input type="button" onClick="searchResource()" class="buttonStyle" value="<@bean.message key="action.query"/>" style="width:60px"/>
	     </td>
	    </tr>
        </form>
	  </table>
    </div>
   <div style="display: block;" id="view4">
    <form name="stdSearchForm" method="post">
	  <#include "/pages/components/stdSearchTable.ftl"/>
    </form>
   </div>
    <script>
        function searchClass() {
           searchForm=document.adminClassSearchForm;
           search();
        }
    </script>
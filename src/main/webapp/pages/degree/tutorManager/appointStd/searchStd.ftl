<#include "/templates/head.ftl"/>
<link href="${static_base}/css/tab.css" rel="stylesheet" type="text/css">
 <script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<BODY>
	<table id="bar"></table>
     <table class="frameTable_title"> 
      <tr>
       <td class="separator">|</td>
       <td>导师工号:${result.tutor.code?if_exists}</td>
       <td><@msg.message key="attr.personName"/>:<@i18nName result.tutor/></td>
       <td><@msg.message key="entity.department"/>:<@i18nName (result.tutor.department)?if_exists/></td>
       <td><@msg.message key="entity.speciality"/>:<@i18nName (result.tutor.speciality)?if_exists/></td> 
       <td><@msg.message key="entity.specialityAspect"/>:<@i18nName (result.tutor.aspect)?if_exists/></td>       
      </tr>
     </table>
  <table width="100%"  class="frameTable" height="89%">
    <tr>     
     <td valign="top"  style="width:160px" class="frameTable_view">
		     	<table width="100%" border="0" class="searchTable">
		  		<form name="commonForm" method="post" target='teachTaskListFrame' action="" onsubmit="return false;">
		  		<input type='hidden' name='tutorId' value="${result.tutor.id}">
		  		<tr>
			     <td class="infoTitle"><@msg.message key="attr.stdNo" />:
			     </td>
			     <td><input type="text" name="student.code" maxlength="32" style="width:100px"/>
			     </td>
		       </tr> 
			   <tr>
			     <td class="infoTitle"><@msg.message key="filed.enrollYearAndSequence" />
			     </td>
			     <td ><input type="type" name="student.enrollYear" maxlength="7" value="" style="width:100px"/>
		         </td>
		       </tr>
		       <tr>              
			     <td class="infoTitle">
			      <@msg.message key="entity.studentType" />
			     </td>
			     <td >
			          <select id="stdTypeOfSpeciality" name="student.type.id" style="width:100px">
			          </select>	 
		         </td>
				</tr>       
		    	<tr>
			     <td class="infoTitle">
			      <@msg.message key="common.college" />：
			     </td>
			     <td >
		           <select id="department" name="student.department.id"  style="width:100px;" >
		         	  <option value="">...</option>
		           </select>     
			             
		         </td>  
		       </tr>
		
			   <tr>
			     <td class="infoTitle">
			      <@msg.message key="entity.speciality" />
			     </td>
			     <td >
		           <select id="speciality" name="student.firstMajor.id"  style="width:100px;" >
		         	  <option value="">...</option>
		           </select>     
		         </td>            
		       </tr>
		
			   <tr>
			     <td class="infoTitle">
			      <@msg.message key="entity.specialityAspect" />
			     </td>
			     <td >
		           <select id="specialityAspect" name="student.firstAspect.id"  style="width:100px;" >
		         	  <option value="">...</option>
		           </select>
		           <input type="hidden" name="student.teacher.id" value="${result.tutor.id}">
		         </td>
		       </tr>
			  <tr>
			   <td colspan="2" align="center"> 
			    <button name="button9" class="buttonStyle" onClick="search(1)"><@msg.message key="system.button.confirm" /></button>  
		       </td>
			  </tr>
			   <#assign stdTypeNullable=true>
		<#include "/templates/stdTypeDepart3Select.ftl"/>
			  </form>  
		   </table>	
     </td>
     <td valign="top">
     <iframe  src="#"
     id="teachTaskListFrame" name="teachTaskListFrame" scrolling="no"
     marginwidth="0" marginheight="0"      frameborder="0"  height="100%" width="100%">
     </iframe>
     </td>
    </tr>
<script>
  	var bar = new ToolBar("bar", "<@i18nName result.tutor/>老师所带学生", null, true, true);
  	bar.setMessage('<@getMessage/>');
  	bar.addBack("<@msg.message key="action.back"/>");
  	
	var form = document.commonForm;
	function search(pageNo,pageSize,orderBy){
	    form.action="appointStd.do?method=tutorStdList";
		goToPage(form,pageNo,pageSize,orderBy);
	}
	search(1);
</script>
  </table>
</body>
<#include "/templates/foot.ftl"/> 
  
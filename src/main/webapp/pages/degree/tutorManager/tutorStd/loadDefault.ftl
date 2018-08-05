<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" onLoad="document.pageGoForm.submit();" >
<form name="pageGoForm" action="tutorStd.do?method=doSearch"  target="tuturFrame" method="post" onsubmit="return false;">
  <table cellpadding="0" cellspacing="1" width="80%" border="0"  class="listTable" align="center">   
	   <tr>
	    <td align="center" class="darkColumn" colspan=2>
	     <B><@bean.message key="filed.tutorFind" /></B>
	    </td>
	   </tr>   
	   <tr>
	    <td width="50%">    
		  <table width="100%"  align="center" class="listTable">
		   <tr>
		     <td class="grayStyle" width="30%" id="f_department">
		      &nbsp;<@bean.message key="filed.firstSubject" />：
		     </td>
		     <td class="brightStyle">
		           <select id="firstSubject" name="tutor.secondSubject.firstSubject.firstCode"  style="width:100px;" >
		         	  <option value=""><@bean.message key="common.selectPlease" />...</option>
		           </select>     
		     </td>
		   </tr>
		   <tr>
		     <td class="grayStyle" width="30%" id="f_department">
		      &nbsp;<@bean.message key="filed.secondSubject" />：
		     </td>
		     <td class="brightStyle">
		           <select id="secondSubject" name="tutor.secondSubject.secondCode"  style="width:100px;" >
		         	  <option value=""><@bean.message key="common.selectPlease" />...</option>
		           </select>     
		     </td>
		   </tr>
		   <tr>
		     <td class="grayStyle" width="30%" id="f_department">
		      &nbsp;<@bean.message key="filed.tutorAndMaster" />：
		     </td>
		     <td class="brightStyle">
		           <select id="thirdSubject" name="tutor.thirdSubject.thirdCode"  style="width:100px;" >
		         	  <option value=""><@bean.message key="common.selectPlease" />...</option>
		           </select>     
		     </td>
		   </tr>	
		  </table>  	 
		</td>  
		<td width="50%">   
		  <table width="100%"  align="center" class="listTable">		   		   
		   <tr>
		     <td class="grayStyle" width="25%" id="f_department">
		      &nbsp;<@bean.message key="entity.college" />：
		     </td>
		     <td class="brightStyle">
		           <select id="department" name="tutor.department.id"  style="width:100px;" >
		         	  <option value=""><@bean.message key="common.selectPlease" />...</option>
		         	  <#list result.departmentList?if_exists as department>
		         	  	 <option value="${department.id}">${department.name?if_exists}</option>
		         	  </#list>
		           </select>     
		     </td>
		   </tr> 
		   <tr> 
		     <td class="grayStyle" width="25%">
		      &nbsp;<@bean.message key="filed.tutortype" />：
		     </td>
		     <td class="brightStyle">
		           <select  name="tutor.tutorType.id"  style="width:100px;" >
		         	  <option value=""><@bean.message key="common.selectPlease" />...</option>	 
		         	  <option value="${result.doctorType}"><@bean.message key="filed.masterType" /></option>	 
		         	  <option value="${result.masterType}"><@bean.message key="filed.tutorType" /></option>	         	  
		           </select>  		           
		     </td>		     		     
		   </tr>  
		   <tr>	   
		     <td class="grayStyle" width="25%">
		      &nbsp;<@bean.message key="filed.tutorName" />：
		     </td>
		     <td class="brightStyle">
		           <input type="text" name="tutor.name" maxlength="20" style="width:150px;" />
		     </td>
		   </tr>  	  			           
		  </table>
		</td>	    
	  </tr>
	   <tr>
	     <td colspan="4" align="center" class="darkColumn">
	 	   <input type="hidden" name="pageNo" value="1" />	    
	       <input type="submit" value="<@bean.message key="system.button.confirm" />" name="button9"  class="buttonStyle"/>&nbsp;
	       <input type="reset"  value="<@bean.message key="system.button.reset" />" name="reset1"  class="buttonStyle" />   
         </td>
	   </tr>	  
	</table>	  
 </form>  	  
  <iframe id="tuturFrame" name="tuturFrame" 
     marginwidth="0" marginheight="0" scrolling="no"
     frameborder="1"  height="100%" width="100%">
  </iframe>  
</body>     

<#include "/templates/foot.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/CascadeSelect.js"></script> 
<#include "../cascadeMenu.ftl"/>
<script>
    function pageGo(pageNo){
       var form = document.pageGoForm;
       form.pageNo.value = pageNo;
       form.submit();
    }
</script>    
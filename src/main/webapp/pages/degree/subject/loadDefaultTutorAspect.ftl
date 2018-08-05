<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" onLoad="document.pageGoForm.submit()" >
<form name="pageGoForm" action="tutorAspect.do?method=doSearch" target="tutorAspectFrame" method="post" onsubmit="return false;">
  <table cellpadding="0" cellspacing="1" width="80%" border="0"  class="listTable" align="center">   
	   <tr>
	    <td align="center" class="darkColumn" colspan=4>
	     <B><@bean.message key="filed.tutorAndMasterSearch" /></B>
	    </td>
	   </tr>   
	   <tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_subjectClass">
	      &nbsp;<@bean.message key="filed.subjectKind" />：
	     </td>
	     <td class="brightStyle">
	           <select id="subjectClass" name="thirdSubject.secondSubject.firstSubject.subjectClass.subjectCode"  style="width:100px;" >
	         	  <option value=""><@bean.message key="common.selectPlease" />...</option>
	           </select>     
	     </td>
	     <td class="grayStyle" width="25%" id="f_firstSubject">
	      &nbsp;<@bean.message key="filed.firstSubject" />：
	     </td>
	     <td class="brightStyle">
	           <select id="firstSubject" name="thirdSubject.secondSubject.firstSubject.firstCode"  style="width:100px;" >
	         	  <option value=""><@bean.message key="common.selectPlease" />...</option>
	           </select>     
	     </td>		     
	   </tr>  
	   <tr>
	     <td class="grayStyle" width="25%" id="f_secondSubject">
	      &nbsp;<@bean.message key="filed.secondSubject" />：
	     </td>
	     <td class="brightStyle">
	           <select id="secondSubject" name="thirdSubject.secondSubject.secondCode"  style="width:100px;" >
	         	  <option value=""><@bean.message key="common.selectPlease" />...</option>
	           </select>     
	     </td>
	     <td class="grayStyle" width="25%" id="f_name">
	      &nbsp;<@bean.message key="filed.tutorAndMaster" />：
	     </td>
	     <td class="brightStyle">
			<input type="text" name='thirdSubject.name' maxlength="30"/>	
	     </td>		     
	  </tr>	    	
	   <tr>
	     <td colspan="4" align="center" class="darkColumn">
	 	   <input type="hidden" name="pageNo" value="1" />	    
	       <input type="submit" value="<@bean.message key="system.button.query" />" name="button9" class="buttonStyle"/>&nbsp;
	       <input type="reset"  value="<@bean.message key="system.button.reset" />" name="reset1"  class="buttonStyle" />   
         </td>
	   </tr>	  
	</table>	  
 </form>  	  
  <iframe id="tutorAspectFrame" name="tutorAspectFrame" 
     marginwidth="0" marginheight="0" scrolling="no"
     frameborder="1"  height="100%" width="100%">
  </iframe>  
</body>     
<#include "/templates/foot.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/CascadeSelect.js"></script> 
<#include "cascade3Menu.ftl"/>
<script>
    function pageGo(pageNo){
       var form = document.pageGoForm;
       form.pageNo.value = pageNo;
       form.submit();
    }    
</script>    
<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
  <table cellpadding="0" cellspacing="0" width="100%" border="0">
   <form action="studentSearch.do?method=search" onsubmit="return false;" method="post">
   <table width="60%" align="center">
   <tr>
   <td colspan="2"><@bean.message key="attr.stdNo"/>:<input value="" name="code" maxlength="32" type ="text"/></td>
   </tr>
   <tr>
   <td colspan="2"><@bean.message key="attr.personName"/>:<input value="" name="name" maxlength="20" type="text"/></td>   
   <tr>
   <tr>
   <td align="right" colspan="2"><input type="submit" name="submit" value="<@bean.message key="action.query"/>"/></td>
   </tr>
   </table>
   <tr>
    <td align="center" colspan="4" class="contentTableTitleTextStyle"><B><@bean.message key="std.stdList"/></B></td>
   </tr>
   <tr>
    <td>
     <table width="60%" align="center" class="listTable">       
       <tr class="darkColumn">
	    <td width="5%" align="center"><@bean.message key="attr.personName"/></td>
	    <td width="5%" align="center"><@bean.message key="attr.stdNo"/></td>
	   </tr>
	   <#list studentList as std>
	   <tr class="">
	    <td width="5%" align="center" bgcolor="#CBEAFF">${std.code?if_exists}</td>
	    <td width="5%" align="center" bgcolor="#CBEAFF"><@i18nName std?if_exists/></td>
	   </tr>
	   </#list>
	  </form>
       <form name="pageGoForm" method="post" action="studentSearch.do?method=search" onsubmit="return false;">
	   <tr class="darkColumn">
	    <td colspan="5" align="center">
	      <input type="hidden" name="pageNo" value="${result['studentList'].pageNo}">
	       ${result['studentList'].pageNo}/${result['studentList'].maxPageNo}
	      <a href="#" onClick='pageGo("1")'><@bean.message key="page.first"/></a>
	      <a href="#" onClick='pageGo("${result['studentList'].previousPageNo}")'><@bean.message key="page.previous"/></a>
	      <a href="#" onClick='pageGo("${result['studentList'].nextPageNo}")'><@bean.message key="page.next"/></a>
	      <a href="#" onClick='pageGo("${result['studentList'].maxPageNo}")'><@bean.message key="page.last"/></a>
	   </td>
	  </tr>	   
	   </form> 
     </table>
    </td>    
   </tr>
  </table>
    <script>
    function pageGo(pageNo){
       document.pageGoForm.pageNo.value = pageNo;
       document.pageGoForm.submit();
    }
    
    function pageGoWithSize(pageNo,pageSize){
       var form = document.pageGoForm;
       form.pageNo.value = pageNo;
       form.action+="?pageSize="+pageSize;
       form.submit();
    }
  </script>
 </body>
<#include "/templates/foot.ftl"/>
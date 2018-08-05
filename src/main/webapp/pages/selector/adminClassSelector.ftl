<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/Selector.js"></script>
 <script>
    var detailArray = {};
    
    function getIds(){
       return(getRadioValue(document.getElementsByName("adminClassId")));
    }
    
    function getName(id){
       if (id != ""){
          return detailArray[id]['name'];
       }else{
          return "";
       }
    }
    

     function selfAdapte(){
   	    window.self.moveTo((screen.width-400)/3, (screen.height-(8*30+150))/5);
   	    window.self.resizeTo(400, screen.height*(3/4));
     }
 </script>
 <BODY onload="selfAdapte();" LEFTMARGIN="0" TOPMARGIN="0">
  <table cellpadding="0" cellspacing="0" width="100%" border="0">
   <tr>
    <td align="center" colspan="3" class="contentTableTitleTextStyle" bgcolor="#ffffff">
     <B><@bean.message key="filed.adminClassList"/></B>
    </td>
   </tr>
   <tr>
    <td>
     <table width="90%" align="center" class="listTable">
	   <tr bgcolor="#ffffff" onKeyDown="javascript:enterQuery(event)">
	   	<td align="center">
        	<img src="${static_base}/images/action/search.gif"  align="top" onClick="query()" alt="<@bean.message key="info.filterInResult"/>"/>
      	</td>
      	<td ><input style="width:100%" type="text" id="code" name="code" value="${RequestParameters['adminClass.code']?if_exists}"/></td>
      	<td ><input style="width:100%" type="text" id="name" name="name" value="${RequestParameters['adminClass.name']?if_exists}"/></td>
      	<td ><input style="width:100%" type="text" id="engName" name="engName" value="${RequestParameters['adminClass.engName']?if_exists}"/></td>
       </tr>
	   <tr align="center" class="darkColumn">
	     <td align="center">&nbsp;</td>
	     <td><@bean.message key="filed.adminClassCode"/></td>
	     <td><@bean.message key="common.adminClass"/></td>
   	     <td><@bean.message key="filed.adminClassEnglishName"/></td>
	   </tr>
	   <#list (result.adminClassList.items)?if_exists as adminClass>

	   <#if adminClass_index%2==1 ><#assign class="grayStyle" ></#if>
	   <#if adminClass_index%2==0 ><#assign class="brightStyle" ></#if>
	   <tr class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)">
	    <script>
	       detailArray['${adminClass.id}'] = {'name':'${adminClass.name}'};
	    </script>
	    <td width="5%" align="center" bgcolor="#CBEAFF">
	     <input type="radio" name="adminClassId" value="${adminClass.id}" <#if adminClass.id == (result.adminClassId)?default(-1)>checked</#if>>
	    </td>
	    <td width="20%">&nbsp;${adminClass.code?if_exists}</td>
	    <td width="30%">&nbsp;${adminClass.name?if_exists}</td>
	    <td width="30%">&nbsp;${adminClass.engName?if_exists}</td>
	   </tr>
	   </#list>
    </td>
   </tr>
   <#assign paginationName="adminClassList"/>
   <#include "/templates/pageBar.ftl"/>

  </table><br>
  <form name="adminClassForm" method="post" action="adminClassSelector.do" onsubmit="return false;">
  <input type="hidden" name="pageNo" value="1"/>
  <input type="hidden" name="method" value="withSpeciality"/>
  <#if result.specialityId?exists><input type="hidden" name="adminClass.speciality.id" value="${result.specialityId}"/></#if>
  <#if result.aspectId?exists><input type="hidden" name="adminClass.aspect.id" value="${result.aspectId}"/></#if>
  <#if result.departmentId?exists><input type="hidden" name="adminClass.department.id" value="${result.departmentId}"/></#if>
  <#if result.stdTypeId?exists><input type="hidden" name="adminClass.stdType.id" value="${result.stdTypeId}"/></#if>
  <#if result.enrollYearId?exists><input type="hidden" name="adminClass.enrollYear" value="${result.enrollYearId}"/></#if>
  <#if result.selectorId?exists><input type="hidden" name="selectorId" value="${result.selectorId}"/></#if>
  <#if result.is2ndSpeciality?exists><input type="hidden" name="adminClass.speciality.is2ndSpeciality" value="${result.is2ndSpeciality}"/></#if>
  <input type="hidden" name="adminClass.code"/>
  <input type="hidden" name="adminClass.name"/>
  <input type="hidden" name="adminClass.engName"/>
  </form>  
  <div align="center">
     <input type="button" name="button1" value="<@bean.message key="system.button.confirm"/>" class="buttonStyle"
            onClick="window.top.opener.setAdminClassIdAndDescriptions${(result.selectorId)?default(1)}(getId('adminClassId'),getName(getId('adminClassId')));
                     window.close();"/>&nbsp;&nbsp;&nbsp;&nbsp;
     <input type="button" name="reset" value="<@bean.message key="system.button.reChoose"/>" onClick="window.top.opener.resetAdminClasssSelector${result.selectorId?default(1)}();window.close();" class="buttonStyle"/>  
  </div>   
 </body>
  <script>
  	var form = document.adminClassForm;
    function pageGo(pageNo){
       form["pageNo"].value=pageNo;
       query();
    }
    function pageGoWithSize(pageNo,pageSize){
       var form = document.adminClassForm;
       form.pageNo.value = pageNo;
       form.action+="?pageSize="+pageSize;
       query();
    }
    function enterQuery(event) {
       if (event.keyCode == 13) {
        query();
      }
    }
    function query(){
    	var code = $("code").value;
    	var name = $("name").value;
    	var engName = $("engName").value;
    	if(code!=""){form["adminClass.code"].value=code;}
    	if(name!=""){form["adminClass.name"].value=name;}
    	if(engName!=""){form["adminClass.engName"].value=engName;}
    	form.submit();
    }
  </script>
</script>
<#include "/templates/foot.ftl"/>
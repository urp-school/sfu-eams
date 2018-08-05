<#include "/templates/head.ftl"/>
 <body> 
   <table width="90%" align="center" class="listTable">
       <form name="creditForm" action="" method="post" onsubmit="return false;">
   	   <tr class="darkColumn">
	     <td align="center" colspan="2"><@bean.message key="info.creditInit.management"/></td>
	   </tr>
	   <tr>
	     <td class="title" width="25%" id="f_name">&nbsp;<@bean.message key="attr.yearAndTerm"/>：</td>
 	     <td><@i18nName calendar.studentType/>&nbsp;${calendar.year}&nbsp;${calendar.term}</td>
	   </tr>
	   <tr>
	     <td class="title" width="25%" id="f_name">&nbsp;<@bean.message key="entity.studentType"/>：</td>
 	     <td>
 	            <#list stdTypes?sort_by("code") as stdType>
 	              <input type="checkBox" name="stdTypeId" checked value="${stdType.id}"><@i18nName stdType/>&nbsp;
 	            </#list>
 	     </td>
	   </tr>
	   <tr>
	     <td class="title" width="25%" id="f_name">&nbsp;<@bean.message key="entity.college"/>：</td>
 	     <td>
 	            <#list departList as depart>
 	             <@i18nName depart/><#if (depart_index%3==2)><br></#if>
 	            </#list>
 	     </td>
	   </tr>
	   <tr>
	     <td class="title" width="25%" id="f_name">&nbsp;<@bean.message key="info.operation.tip"/>：</td>
 	     <td>
 	      	<@bean.message key="info.creditInitOperationTip"/>
 	     </td>
	   </tr>
	   <tr  class="darkColumn">
	     <td class="title" width="25%" id="f_name">&nbsp;<@bean.message key="info.operation"/>：</td>
   	      <td align="center"><input type="button" value="<@bean.message key="action.init"/>" onclick="javascript:creditInit()" class="buttonStyle"/>
 	     </td>
	   </tr>
	   </form>
   </table>
   <script>
   function creditInit(){
       var form = document.creditForm;
       var stdTypeIds=getCheckBoxValue(document.getElementsByName("stdTypeId"));
       if(""==stdTypeIds){alert("请选择学生类别");return;}
       form.action="creditConstraint.do?method=initCreditConstraint&calendar.id=${calendar.id}&stdTypeIds="+stdTypeIds;
       form.submit();
   }
   </script>
 </body>
<#include "/templates/foot.ftl"/>
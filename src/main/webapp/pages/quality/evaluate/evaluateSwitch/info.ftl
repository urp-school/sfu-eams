<#include "/templates/head.ftl"/>
<BODY>
<#assign labInfo>评教开关信息</#assign>
  <#include "/templates/back.ftl"/>
  <table class="infoTable" align="center">
        <tr>
          <td class="title"  width="30%">学年学期:
		      <input type="hidden" name="switch.calendar.id" value="${(switch.calendar.id)?if_exists}" />
		  </td>
		  <td> 
		   <@i18nName switch.calendar.studentType/> ${switch.calendar.year} ${switch.calendar.term}
		   </td>
        </tr>
	 	<tr>
	 		<td class="title"  width="30%">是否开放:</td>
	 		<td>${(switch.isOpen)?string("开放","关闭")}</td>
	 	</tr>
	 	<tr>
	 		<td class="title" id="f_openDate" width="30%">开始时间:</td>
	 		<td> ${(switch.openAt?string("yyyy-MM-dd HH:mm"))?default("")}</td>
	 	</tr>
	 	<tr>
	 		<td class="title" id="f_closeDate">结束时间:</td>
	 		<td>${(switch.closeAt?string("yyyy-MM-dd HH:mm"))?default("")}</td>
	 	</tr>
	   <tr>
	    <td class="title" id="f_studentType"><@bean.message key="entity.studentType"/>：</td>
	    <td >
	         <#list switch.stdTypes?sort_by('code') as stdType>
	           <@i18nName stdType/> ,
	         </#list>
	    </td>
	   </tr>
	   <tr>
	    <td class="title" id="f_department"><@bean.message key="entity.department"/>：</td>
	    <td >
	         <#list switch.departs?sort_by('name') as depart>
	           <@i18nName depart/>,
	         </#list>
	    </td>
	   </tr>
  </table>
</body>
<#include "/templates/foot.ftl"/>
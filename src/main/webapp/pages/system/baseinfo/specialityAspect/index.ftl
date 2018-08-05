<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" scrolling="no">
 <table id="myBar"></table>
 <table class="frameTable">
   <tr>
    <td width="20%" class="frameTable_view">
     <form name="searchForm" target="contentListFrame" method="post" action="" onsubmit="return false;">
      <#assign extraSearchOptions><#include "../../base.ftl"/><@stateSelect "specialityAspect"/></#assign>
	  <#include "/pages/components/specialityAspectSearchTable.ftl"/>
	  <table><tr height="300px"><td></td></tr></table>
     </form>
    </td>
    <td valign="top">
	  <iframe src="#" 
	     id="contentListFrame" name="contentListFrame" 
	     marginwidth="0" marginheight="0" scrolling="no"
	     frameborder="0" height="100%" width="100%">
	  </iframe>
    </td>
   </tr>
  </table>
  <script language="JavaScript" type="text/JavaScript" src="<@msg.message key="js.baseinfo"/>"></script>
  <script language="javascript">
    type="specialityAspect";
    keys="code,name,engName,abbreviation,description,createAt,modifyAt,remark,state,dateEstablished,speciality.name,maxPeople";
    titles="代码,名称,英文名,简称,简介,创建日期,修改日期,备注,是否使用,设立年月,<@msg.message key="entity.speciality"/>,最大人数";
    searchSpecialityAspect=search;
    labelInfo="<@bean.message key="page.specialityAspectListFrame.label" />";
    search();
    initBaseInfoBar();
</script>
<#include "/templates/stdTypeDepart2Select.ftl"/>
</body>
<#include "/templates/foot.ftl"/>
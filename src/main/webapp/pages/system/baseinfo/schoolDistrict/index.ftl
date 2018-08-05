<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
   <table id="myBar"></table> 
   <table class="frameTable">
   <tr>
    <td width="20%" class="frameTable_view">
        <#include "searchForm.ftl"/>
        <table><tr height="400px"><td></td></tr></table>
    </td>
    <td valign="top">
	  <iframe src="#" id="contentListFrame" name="contentListFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" height="100%" width="100%"></iframe> 
    </td>
   </tr>
  </table>
  <script language="JavaScript" type="text/JavaScript" src="<@msg.message key="js.baseinfo"/>"></script>
  <script language="javascript">
    type="schoolDistrict";
    keys="code,name,engName,abbreviation,description,createAt,modifyAt,remark,state";
    titles="代码,名称,英文名,简称,简介,创建日期,修改日期,备注,是否使用";
    labelInfo="<@bean.message key="page.schoolDistrictListFrame.label"/>";
    search();
    searchSchoolDistrict=search;
    initBaseInfoBar();
  </script>  
</body>
<#include "/templates/foot.ftl"/>
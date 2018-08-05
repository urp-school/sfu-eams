<#include "/templates/head.ftl"/> 
<BODY LEFTMARGIN="0" TOPMARGIN="0"> 
  <table id="myBar"></table>
  <table class="frameTable">
   <tr valign="top">
    <td width="20%" class="frameTable_view">
        <#include "searchForm.ftl"/>
        <table><tr height="400px"><td></td></tr></table>
    </td>
    <td>
	  <iframe src="#" id="contentListFrame" name="contentListFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" height="100%" width="100%"></iframe>
    </td>
   </tr>
  </table>
  <script language="JavaScript" type="text/JavaScript" src="<@msg.message key="js.baseinfo"/>"></script>
  <script language="javascript">
    type="studentType";
    keys="code,name,engName,superType.name,abbreviation,description,createAt,modifyAt,remark,state,dateEstablished,isBase";
    titles="代码,名称,英文名,上级类别名称,简称,简介,创建日期,修改日期,备注,是否使用,设立年月,是否基础学生类别";
    labelInfo="<@bean.message key="page.studentTypeListFrame.label" />";
    search();
    searchStudentType=search;
    initBaseInfoBar();
  </script>
</body>
<#include "/templates/foot.ftl"/>
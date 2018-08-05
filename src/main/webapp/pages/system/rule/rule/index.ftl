<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
 <table id="ruleBar"></table>
  <table class="frameTable">
   <tr>
    <td width="20%" class="frameTable_view">
        <#include "searchForm.ftl"/>
        <table><tr height="400px"><td></td></tr></table>
    </td>
    <td valign="top">
	 <iframe src="#" id="contentListFrame" name="contentListFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0"  height="100%" width="100%"></iframe>
    </td>
   </tr>
  </table>
  <script language="JavaScript" type="text/JavaScript" src="<@msg.message key="js.baseinfo"/>"></script>
  <script language="javascript">
    var bar=new ToolBar("ruleBar","规则管理",null,true,true);
  	bar.setMessage('<@getMessage />');
    bar.addHelp();
    function searchRule() {
    	var form = document.ruleForm;
    	form.target = "contentListFrame";
    	form.action = "rule.do?method=search";
    	form.submit();
    }
    searchRule();
  </script>  
  </body>
<#include "/templates/foot.ftl"/>
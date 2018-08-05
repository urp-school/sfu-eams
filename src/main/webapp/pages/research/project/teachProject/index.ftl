<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
 <#assign labInfo>项目上报列表</#assign>
 <#include "/templates/help.ftl"/>
    <table  class="frameTable_title">
        <tr>
	        <td style="width:50px"><font color="blue"><@bean.message key="action.advancedQuery"/></font></td>
            <td>|</td>
           <form name="teachProjectForm" method="post" action="teachProject.do?method=index" onsubmit="return false;">
        </tr>
    </table>
    <table class="frameTable_title">
        <tr>
            <td valign="top"  style="width:160px" class="frameTable_view">
                <#include "searchTable.ftl"/>
            </td>
          </form>
            <td valign="top" bgcolor="white">
                <iframe  src="#" id="teachProjectQueryFrame" name="teachProjectQueryFrame" scrolling="no" marginwidth="0" marginheight="0" frameborder="0"  height="100%" width="100%"></iframe>
            </td>
        </tr>
    </table>
 <script language="javascript">
    var form=document.teachProjectForm;
    action="teachProject.do";
 	function search(){
       form.action=action="?method=search";
       form.target="teachProjectQueryFrame";
       form.submit();
 	}
 	search();
 </script>
<#include "/templates/foot.ftl"/>
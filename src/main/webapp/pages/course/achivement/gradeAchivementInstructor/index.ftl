<#include "/templates/head.ftl"/>
<BODY> 
    <table id="awardAssessmentBar"></table>
    <table class="frameTable_title">
     <tr>
       <td style="width:50px"/>
          <font color="blue"><@bean.message key="action.advancedQuery"/></font>
       </td>
       <td>|</td>
       <form name="achivementSettingForm" target="achivementListFrame" method="post" action="gradeAchivementInstructor.do?method=index" onsubmit="return false;">
       <#include "../setting.ftl" />
       </form>
     </tr>
    </table>
    <#include "/pages/components/initAspectSelectData.ftl"/>
    <table class="frameTable">
        <tr>
            <td valign="top" width="19%" class="frameTable_view">
                <form name="achivementSearchForm" target="achivementListFrame" method="post" action="gradeAchivementInstructor.do?method=index" onsubmit="return false;">
                <input type="hidden" name="setting.id" value="${setting.id}"/>
                <#include "searchForm.ftl"/>
                </form>
            </td>
      
            <td valign="top" bgcolor="white">
                <iframe  src="#" id="achivementListFrame" name="achivementListFrame" scrolling="auto" marginwidth="0" marginheight="0" frameborder="0"  height="100%" width="100%"></iframe>
            </td>
        </tr>
    </table>
	<script>
		var bar=new ToolBar("awardAssessmentBar","综合测评数据——院系",null,true,true);
        bar.addHelp("<@msg.message key="action.help"/>");
        function search(pageNo,pageSize,orderBy){
            var form = document.achivementSearchForm;
            form.action = "?method=search";
            form.target="achivementListFrame";
            goToPage(form,pageNo,pageSize,orderBy);
        }
        search();
	</script>
</body>
<#include "/templates/foot.ftl"/> 
  
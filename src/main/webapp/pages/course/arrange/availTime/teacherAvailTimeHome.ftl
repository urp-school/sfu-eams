<#include "/templates/head.ftl"/>
 
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<#assign labInfo><@bean.message key="info.availTime.teacher"/></#assign>  
<#include "/templates/help.ftl"/>
   <table class="frameTable_title">
    <form name="teacherSearchForm" action="teacherAvailTime.do?method=teacherList" target="teacherListFrame" method="post" onsubmit="return false;">
    <input type="hidden" name="pageNo" value="1"/>
    <input type="hidden" name="pageSize" value="15"/>
    <input type="hidden" name="teacher.isTeaching" value="1"/>
     <tr  style="font-size: 10pt;" onKeyDown="javascript:enterQuery(event)">
       <td  align="left" id="id">
         <@bean.message key="teacher.code"/>&nbsp;&nbsp;:
    	   <input type="text" name="teacher.code" style="width:80px;" maxlength="32"/>
    	   <@bean.message key="attr.personName"/>&nbsp;&nbsp;:
         <input type="text" name="teacher.name"  style="width:80px;" maxlength="20"/>
          <@bean.message key="common.college"/>&nbsp;&nbsp;:
          <select name="teacher.department.id" style="width:100px;">
          <option value=""><@bean.message key="common.all"/></option>
          <#list departList?sort_by("name") as depart>
          <option value="${depart.id}"><@i18nName depart/>
          </#list>
    	   <input type="button" onclick="pageGo(1);" value="<@bean.message key="action.query"/>" class="buttonStyle"/>
       </td>
     </tr>
     </form>
    </table>
    <table  width="100%" height="85%" class="frameTable">
   	<tr>
		<td width="17%" class="frameTable_view">
		  <iframe src="#" id="teacherListFrame" name="teacherListFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" height="100%" width="100%"></iframe>
		</td>
		<td colspan="3" valign="top">
		  <iframe src="teacherAvailTime.do?method=teacherAvailTimeInfo" id="availTimeFrame" name="availTimeFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0"  height="100%" width="100%"></iframe>
		</td>
	</tr>
</table>
<script>
    function pageGo(pageNo){
       var form = document.teacherSearchForm;
       form.pageNo.value = pageNo;
       form.target="teacherListFrame";
       form.action="teacherAvailTime.do?method=teacherList";
       form.submit();
    }
     function availTimeInfo(teacherId){
       var form = document.teacherSearchForm;
       form.target="availTimeFrame"
       form.action="teacherAvailTime.do?method=teacherAvailTimeInfo&teacher.id=" + teacherId;
       form.submit();
     }
     function edit(){
       var form = document.teacherSearchForm;
       form.target="availTimeFrame";
       form.action="teacherAvailTime.do?method=teacherAvailTimeInfo&teacher.id=" + teacherId
       form.submit();
     }
     function enterQuery(event) {
        if (portableEvent(event).keyCode == 13) {
            pageGo(1);
        }
     }
     pageGo(1);
</script>
</body>
<#include "/templates/foot.ftl"/>
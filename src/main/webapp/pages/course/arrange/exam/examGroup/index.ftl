<#include "/templates/head.ftl"/>
<BODY>
	<table id="examGroupBar"></table>
	<script>
	  var bar=new ToolBar("examGroupBar","排考课程组管理",null,true,true);
	  bar.addHelp("<@msg.message key="action.help"/>");
	</script> 
     <table class="frameTable">
      <tr class="frameTable_title">
       <form name="examGroupForm" target="contentFrame" action="examGroup.do?method=index" method="post" onsubmit="return false;">
       <td>考试类型:
          <select name="examGroup.examType.id" onchange="search(document.examGroupForm)">
             <#list examTypes?sort_by("code") as examType>
             	<option value="${examType.id}"><@i18nName examType/></option>	
             </#list>
          </select>
       </td>
        <input type="hidden" name="task.calendar.studentType.id" value="${studentType.id}"/>
       <#include "/pages/course/calendar.ftl"/>
       </form>
    </tr>
    <tr>
     <td valign="top" colspan="8">
     <iframe src="#" id="contentFrame" name="contentFrame" scrolling="no" marginwidth="0" marginheight="0" frameborder="0" height="100%" width="100%"></iframe>
     </td>
    </tr>
  <table>
 <script>
   search(document.examGroupForm);
   function search(form,pageNo,pageSize,orderBy){
	    form.action="examGroup.do?method=groupList";
        goToPage(form,pageNo,pageSize,orderBy);
   }
 </script>
</body>
<#include "/templates/foot.ftl"/>

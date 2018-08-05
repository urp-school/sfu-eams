<#include "/templates/head.ftl"/>
<BODY>
	<table id="taskBar"></table>
     <table class="frameTable_title">
      <tr>
       <td>详细查询</td>
	    <form name="takeSearchForm" method="post" action="examStudent.do?method=index" action="return false;">
	    <input name="keys" type="hidden" value=""/>
	    <input name="titles" type="hidden" value=""/>
       <td class="separator">
          <select name="take.examType.id" onchange="search()">
             <#list examTypes?sort_by("code") as examType>
             <option value="${examType.id}"><@i18nName examType/></option>
             </#list>
          </select>
       </td>
      <#include "/pages/course/calendar.ftl"/>
      </tr>
  </table>
  <table width="100%" class="frameTable" height="89%">
    <tr>
     <td valign="top" style="width:160px" class="frameTable_view">
     <#include "searchForm.ftl"/>
     </td>
   </form>
     <td valign="top">
	     <iframe src="#" id="contentFrame" name="contentFrame" scrolling="no" marginwidth="0" marginheight="0" frameborder="0" height="100%" width="100%"></iframe>
     </td>
    </tr>
  <table>
 <script>
      var form=document.takeSearchForm;
	  var bar=new ToolBar("taskBar","应考学生管理",null,true,true);
	  bar.addHelp("<@msg.message key="action.help"/>");
	  function stdList(method){
        form.target="contentFrame"
	    form.action="examStudent.do?method="+method;
	    form.submit();
	  }
	  function search(pageNo,pageSize,orderBy){
	    form.target="contentFrame";
		form.action="examStudent.do?method=examTakeList";
	    goToPage(form,pageNo,pageSize,orderBy);
	  }
	  function statTake(){
	    form.target="_blank";
		form.action="examStudent.do?method=statTake";
		form.submit();
	  }
	  search();
 </script>
</body>
<#include "/templates/foot.ftl"/> 
  
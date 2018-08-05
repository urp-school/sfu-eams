<#include "/templates/head.ftl"/>
 <body >  
 <table id="gradeBar"></table>
  <table width="100%" border="0" height="100%" class="frameTable">
  <tr >
   <form name="stdSearchForm" method="post" action="" onsubmit="return false;">
     <td style="width:20%" valign="top" class="frameTable_view">
        <#include "/pages/components/initAspectSelectData.ftl"/>
		<#include "/pages/components/stdSearchTable.ftl"/>
     </td>
   </form>
     <td valign="top">
	     <iframe src="#" id="reportFrame" name="reportFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" height="100%" width="100%"></iframe>
     </td>
    </tr>
  <table>
<script>
    var bar=new ToolBar("gradeBar","<@msg.message key="common.stdPersonGradeTable"/>",null,true,true);
    bar.addHelp("<@msg.message key="action.help"/>");
    
    var action="stdGradeReport.do";
    function search(pageNo,pageSize,orderBy){
       var form = document.stdSearchForm;
	   form.action = action+"?method=stdList";
	   form.target="reportFrame";
       goToPage(form,pageNo,pageSize,orderBy);
    }
    search(1,null);
    function personGrade(id){
       if(id==null||''==id){
          alert("<@msg.message key="action.noSelectedStd"/>");
          return;
       }
       var form =document.stdSearchForm;
       form.action=action+"?method=report&stdIds="+id;
       form.target="_blank";
       form.submit();
    } 
</script> 
 </body>   
<#include "/templates/foot.ftl"/> 
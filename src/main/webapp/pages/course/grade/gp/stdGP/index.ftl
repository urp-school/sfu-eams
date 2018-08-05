<#include "/templates/head.ftl"/>
<body>
 <table id="gradeBar"></table>
  <table width="100%" border="0" height="100%" class="frameTable">
  <tr>
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
	    var bar=new ToolBar("gradeBar","学生个人绩点管理",null,true,true);
	    bar.setMessage('<@getMessage/>');
	    bar.addItem("每学期绩点重算","reStatGPSetting()");
	    bar.addHelp("<@msg.message key="action.help"/>");
	    
	    var action="stdGP.do";
	    var form = document.stdSearchForm;
	    function search(pageNo,pageSize,orderBy){
	    form.action = action+"?method=stdList";
		   form.target="reportFrame";
	       goToPage(form,pageNo,pageSize,orderBy);
	    }
	    function reStatGPSetting(){
		    form.action=action+"?method=reStatGPSetting";
		    form.submit();
	    }
	    search();
	</script>
</body>
<#include "/templates/foot.ftl"/>
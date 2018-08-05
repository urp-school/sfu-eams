<#include "/templates/head.ftl"/> 
<script language="JavaScript" type="text/JavaScript" src="scripts/tabpane.js"></script>
<link href="${static_base}/css/tab.css" rel="stylesheet" type="text/css">
<body>
	<table id="bar"></table>
<table cellpadding="0" cellspacing="0" width="100%" border="0">
   	<form name="commonForm" action="dutyRecordManager.do" method="post" onsubmit="return false;">
   	<tr>
    	<td align="center" colspan="4" class="contentTableTitleTextStyle" bgcolor="#ffffff">
     		<B>考勤记录统计</B>
    	</td>
   	</tr>  
   	<tr>
    	<td align="center">
     		<div class="dynamic-tab-pane-control tab-pane" id="tabPane1" >
     			<script type="text/javascript">tp1 = new WebFXTabPane( document.getElementById( "tabPane1") ,false );</script>
	    		<div style="display: block;" class="tab-page" id="tabPage1">
      				<h2 class="tab"><a href="#">学生统计</a></h2>
      				<script type="text/javascript">tp1.addTabPage( document.getElementById( "tabPage1" ) );</script>
       				<table width="100%" align="center" height="100%">
	   				<tr>
	     				<td align="center" colspan="2">
				      		<iframe  src="dutyRecordManager.do?method=studentStatisticsForm"
					     		id="studentStatisticsFrame" name="studentStatisticsFrame" 
					     		marginwidth="0" marginheight="0" scrolling="auto"
					     		frameborder="0" height="100%" width="100%">
				     		</iframe>
     					</td>
	  				</tr>
     				</table>
     			</div>
     			<div style="display: block;" class="tab-page" id="tabPage2">
    				<h2 class="tab"><a href="#" onclick="showClassForm()" >班级统计</a></h2>
      				<script type="text/javascript">tp1.addTabPage( document.getElementById( "tabPage2" ) );</script>
      				<table width="100%" align="center" height="100%">
				   	<tr>
				     	<td align="center" colspan="2">
					     	<iframe  src="#"
					     		id="adminClassStatisticsFrame" name="adminClassStatisticsFrame" 
					    		marginwidth="0" marginheight="0" scrolling="auto"
					     		frameborder="0"  height="100%" width="100%">
					     	</iframe>
				     	</td>
				  	</tr>
	  				</table>
   				</div>
   			</div>
  		</td>
  	</tr>
</table>
<script type="text/javascript">
	var bar = new ToolBar("bar", "考勤记录统计", null, true, true);
	bar.setMessage('<@getMessage/>');
	bar.addHelp("<@msg.message key="action.help"/>");
	
 	setupAllTabs();
</script>
<#assign tableWidth='98.5%' />
<#assign tableAlign='left' />
<#include "/pages/duty/dutyRuleInf.ftl"/>
</body>
<script language="javascript" >
 var isShowClassForm=false;
 function showClassForm(){
   if(!isShowClassForm){
     document.getElementById("adminClassStatisticsFrame").src='dutyRecordManager.do?method=adminClassStatisticsForm';
     isShowClassForm=true;
   }
 }
</script>
<#include "/templates/foot.ftl"/>
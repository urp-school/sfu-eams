<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/itemSelect.js"></script>
<#include "/pages/degree/thesis/processManageBar.ftl"/>
<body>
<table id="backBar" width="100%"></table>
<#assign stdName><@i18nName student/></#assign>
<table   width="100%"  class="frameTable"> 
		<tr>
			<td  style="width:160px" class="frameTable_view" valign="top">
				<table width="100%" class="searchTable" id="menuTable">
					<form name="searchForm" method="post" target="displayFrame" action="" onsubmit="return false;">
						<input type="hidden" name="stdType" value="${stdType}"/>
						<input type="hidden" name="topicOpenId" value="${(topicOpen.id)?if_exists}"/>
				    <tr>
	      				<td  colspan="8" style="font-size:0px">
	          				<img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      				</td>
	   				</tr>
       				<tr>
         				<td class="padding" id="defaultItem1" onclick="detail(this, 'openInfo')" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">&nbsp;&nbsp;<image src="${static_base}/images/action/list.gif" align="bottom">申请论文开题</td>
       				</tr>
       				<tr>
         				<td class="padding" id="defaultItem2" onclick="detail(this, 'reportInfo')" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">&nbsp;&nbsp;<image src="${static_base}/images/action/list.gif" align="bottom">记录开题结果</td>
       				</tr>
				</table>
				</form>
			</td>
			<td valign="top">
				<iframe name="displayFrame" src="#" width="100%" frameborder="0" scrolling="no"></iframe>
			</td>
		</tr>
	</table>
<script>
 var bar = new ToolBar('backBar','<@bean.message key="thesis.topicOpen.title" arg0=stdName/>',null,true,true);
  <#if "master"==stdType?if_exists>
   bar.addItem("<@msg.message key="thesis.topicOpen.downloadReport"/>","downloadTopicOpen('topicOpen','M')")
  <#elseif "doctor"==stdType?if_exists>
   bar.addItem("<@msg.message key="thesis.topicOpen.downloadReport"/>","downloadTopicOpen('topicOpen','D')")
 </#if>
  bar.addItem("<@msg.message key="thesis.topicOpen.uploadReportAsAttachment"/>","uploadThesis()");
  	<#if (thesisManage.schedule)?exists>
  		<#assign schedule = thesisManage.schedule/>
		<@processBar id="01" personType="std"/>
  	</#if>
function uploadThesis(){
   	if(!form["topicOpenId"].value){
   		alert("你先提交你的开题报告,然后上传附件");
   		return;
   	}
   	form.action="loadThesisTopic_std.do?method=doUpThesis";   	
   	form.target=""
   	form.submit();
}
function downloadTopicOpen(type,value){
   		if(!form["topicOpenId"].value){
   		    alert("你先提交你的开题报告,然后上传附件");
   		    return;
   	    }
   		form.action="thesisManageStd.do?method=export";
   		addInput(form,"template",type+"_"+value+".xls");
   		addInput(form,"fileName","${student.name}的开题报告");
   		form.submit();
}
var form = document.searchForm;
	var form = document.searchForm;
	function detail(td, value){
		clearSelected(menuTable, td);
		setSelectedRow(menuTable, td);
	
		form.action="loadThesisTopic_std.do?method=topicOpenInfo";
		addInput(form,"topicOpenFlag",value);
		form.submit();
	}
	$("defaultItem1").onclick();
</script>
</body>
<#include "/templates/foot.ftl"/>
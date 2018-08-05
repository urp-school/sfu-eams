<#include "/templates/head.ftl"/>
<#macro getRemark remark><#list remark?split(",") as message><#if (message?length>2)><@bean.message key="${message}"/></#if></#list></#macro>
<body>
<#assign showStudentAttach=(RequestParameters['showStudentAttach']?default('')=='true') />
<#assign show=RequestParameters['show']?default('') />
<#assign termsShow=RequestParameters['termsShow']?default('') />
<#assign showAllGroup=false />
<#assign showNegative=false />
<#if RequestParameters['showBackward']?default('false')=="true">
	<table class="frameTableStyle">
 		<tr>
 			<td  width="80%"/>
 			<td  width="10%" class="infoTitle" style="height:22px;width:300px" class="padding" onclick="javascript:history.back();" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
          		<img src="${static_base}/images/action/backward.gif" class="iconStyle" /><@msg.message key="action.back"/>
      		</td>
      		<td  width="10%"/>
      	</tr>
	</table>
</#if>
<table cellpadding="0" cellspacing="0" border="0" width="100%" align="center" >
	<tr>
    	<td align="center" >
     		<table id="topBar" width="95%" align="center"></table>
    	</td>
   	</tr>
<script>
  	var bar=new ToolBar('topBar','学生培养计划未完成科目列表',null,true,true);
	bar.setMessage('<@getMessage/>');
	bar.addItem("<@msg.message key="action.print"/>","javascript:print();",'print.gif');
	bar.addItem("导出数据",exportRecordExcel,'excel.png');
	<#if RequestParameters['showBackward']?default('true')=="true">
	bar.addItem("<@msg.message key="action.back"/>","javascript:history.back();","backward.gif");
	</#if>
  	function exportRecordExcel(){
  		try {
        	var oXL = new ActiveXObject("Excel.Application"); 
    	}catch (e) {
        	alert("无法启动Excel!\n\n" + e.message + 
           	"\n\n如果您确信您的电脑中已经安装了Excel，"+
           	"那么请调整IE的安全级别(安全级 - 低)。\n\n具体操作：\n\n"+
           	"工具 → Internet选项 → 安全 → 拉动安全级至(低)");
        	return false;
    	}
  		var oWB = oXL.Workbooks.Add(); 
  		var oSheet = oWB.ActiveSheet; 
  		var Lenr = printDiv.rows.length;
  		for (i=0;i<Lenr;i++) 
  		{ 
			var Lenc = printDiv.rows(i).cells.length;
			var k=0; 
		    for (j=0;j<Lenc;j++) 
   			{
   				oSheet.Cells(i+1,k+1).value='\''+printDiv.rows(i).cells(j).innerText;
   				//oSheet.Cells(i+1,k+1).format='string';
   				k+=printDiv.rows(i).cells(j).colSpan;	
   			} 
  		} 
  		oXL.Visible = true; 
  	}
</script>
	<tr>    
    	<td>
     		<#include "auditResult/batchAuditResultDetail/printDiv.ftl"/>
     		<br>
    	</td>
   	</tr>
</table>
<#include "auditResult/batchAuditResultDetail/resultList.ftl"/>
<div id="styleDiv" style="visibility: hidden; display:none;">
<style>
/*数据列表的样式单*/
.contentTableTitleTextStyle{
	 color: #1f3d83; 
     font-style: normal; 
     font-size: 15pt; 
     line-height: 16pt; 
     text-decoration: none; 
     font-weight: bold; 
     letter-spacing:0
}
.listTable {
	border-collapse: collapse;
    border:solid;
	border-width:1px;
    border-color:#006CB2;
  	vertical-align: middle;
  	font-style: normal; 
	font-size: 10pt; 
}
table.listTable td{
	border:solid;
	border-width:0px;
	border-right-width:1;
	border-bottom-width:1;
	border-color:#006CB2;
}
</style>
</div>
</body>
<script>  
	function showGroup(divId){
	  	var oNewWindow  = window.open('about:blank');
	  	oNewWindow.document.write(document.getElementById('styleDiv').innerHTML+document.getElementById(divId).innerHTML);
	  	oNewWindow.document.title="详情";
	}
</script>
<#include "/templates/foot.ftl"/>
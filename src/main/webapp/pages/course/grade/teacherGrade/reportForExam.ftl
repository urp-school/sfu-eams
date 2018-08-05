<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="statBar"></table>
<script>
   var bar = new ToolBar("statBar","试卷分析表",null,true,true);
   bar.addPrint("<@msg.message key="action.print"/>");
   bar.addItem("导出到Word","AllAreaWord()");
   bar.addClose("<@msg.message key="action.close"/>");
</script>
<style>
body{
 font-family:楷体_GB2312;
 font-size:14px;
}
.reportTable {
	border-collapse: collapse;
    border:solid;
	border-width:1px;
    border-color:black;
  	vertical-align: middle;
  	font-style: normal; 
	font-family:楷体_GB2312;
	font-size:15px;
}
table.reportTable td{
	border:solid;
	border-width:1px;
	border-right-width:1;
	border-bottom-width:1;
	border-color:black;

}
</style>
<div id="DATA" width="100%">
<#list courseStats as courseStat>
     <#assign teachTask=courseStat.task>
    <table border="0" width="100%">
     <tr>
      <td>
 	 <div align='center'><h2><@i18nName systemConfig.school/>课程考核试卷分析表</h2></div>
 	 <div align='center' style="font-weight:bold;">(${teachTask.calendar.year}学年 <#if teachTask.calendar.term='1'>第一学期<#elseif teachTask.calendar.term='2'>第二学期<#else>${teachTask.calendar.term}</#if>)</div><br> 	 
 	 <table align="center" width="100%" border='0' style="font-weight:bold;">
 	 	<tr>
            <td colspan="3" width='30%' align='left'>开课院(系、部):<@i18nName teachTask.arrangeInfo.teachDepart/></td>
	 	 	<td colspan="3" width='40%'>授课班级:<#if teachTask.requirement.isGuaPai>挂牌<#else><@getBeanListNames teachTask.teachClass.adminClasses/></#if></td>
	 	 	<td colspan="3">主讲教师:<@getTeacherNames teachTask.arrangeInfo?if_exists.teachers/></td>
 	 	</tr>
 	 	<tr>
 	 		<td colspan="3"><@msg.message key="attr.taskNo"/>:${teachTask.seqNo?if_exists}</td>
 	 	  <td colspan="3" width='40%'><@msg.message key="attr.courseName"/>:<@i18nName teachTask.course/></td>
  	 	<td colspan="3" align='left'><@msg.message key="entity.courseType"/>:<@i18nName teachTask.courseType/></td>
 	 	</tr>
 	 </table>
 	 <br>
   </td></tr>
   <tr><td>
	   <#list courseStat.gradeSegStats as gradeStat>
	   <table width="100%" align="center" class="reportTable">
	     <tr><td rowspan="5">期末考试成绩统计</td>
	      <td align="left">分数段</td>
	      <#list gradeStat.scoreSegments as seg>
	      <td align="center">${seg.min?string("##.#")}-${seg.max?string("##.#")}</td>
	      </#list>
	     </tr>
	     <tr align="center">
	      <td align="left">人数</td>
	      <#list gradeStat.scoreSegments as seg>
	      <td>${seg.count}</td>
	      </#list>
	     </tr>
	     <tr align="center">
	      <td align="left">比例数</td>
	      <#list gradeStat.scoreSegments as seg>
	      <td>${((seg.count/gradeStat.stdCount)*100)?string("##.#")}%</td>
	      </#list>
	     </tr>
	     <tr align="center">
	      <td align="left">最高分</td>
	      <td colspan="1"><#if gradeStat.heighest?exists>${gradeStat.heighest?string("##.#")}</#if></td>
	      <td align="left">最低分</td>
	      <td colspan="1"><#if gradeStat.lowest?exists>${gradeStat.lowest?if_exists?string("##.#")}</#if></td>
	      <td align="left">平均分</td>
	      <td colspan="1">${(gradeStat.average?string("##.#"))?default('')}</td>
	     </tr>
	     <tr align="center">
	     	<td align="left">实考人数</td>
	      <td>${gradeStat.stdCount}</td>
	      <td></td>
	      <td></td>
	      <td></td>
	      <td></td>
	     </tr>
	     <tr>
	     <td colspan="9">
	      试题分析：考试内容的覆盖面、难易程度及课程教学大纲要求相符程度<#list 1..10 as  i><br></#list>&nbsp;<br>
	     </td>
	     </tr>
	     <tr>
	     <td colspan="9">
	      考试结果分析：学生对本课程教学大纲规定应掌握的基本理论、基本知识和基本技能实际掌握情况<#list 1..10 as  i><br></#list>&nbsp;<br>
	     </td>
	     </tr>
	     <tr>
	     <td colspan="9">
	      其他：根据考试结果分析，本课程教学存在的主要问题和改进意见<#list 1..10 as  i><br></#list>&nbsp;<br>
	     </td>
	     </tr>
	   </table>
	   </#list>
   <br>
   </td></tr>
   <tr><td>
	   <table align="center" width="100%" border='0' style="font-size:15px;">
	   <tr>
	     <td  colspan="${2+segStat.scoreSegments?size}">
	      <div align="right">授课老师签名：<U><#list 1..15 as  i>&nbsp;&nbsp;&nbsp;</#list></U></div>
	      <div align="right">院系部主任签名：<U><#list 1..15 as  i>&nbsp;&nbsp;&nbsp;</#list></U></div>
	      <div align="right">日期：<U><#list 1..15 as  i>&nbsp;&nbsp;&nbsp;</#list></U></div>
	     </td>
	   </tr>
	   <tr>
	     <td colspan="${2+segStat.scoreSegments?size}">
	     备注：此表一式二份，在考试结束后和成绩单一并交院、系（部），由教务秘书汇总后一份交教务科，一份院、系（部）存档。<br>
	    </td>
	   </tr>
	  </table>
  </td>
 </tr>
</table>
    <#if courseStat_has_next>
    <div style='PAGE-BREAK-AFTER: always'></div>
    </#if>
</#list>
</div>
</body>
<#include "/templates/foot.ftl"/>
<SCRIPT LANGUAGE="javascript">
 //指定页面区域内容导入Excel
 function AllAreaExcel()  {
  var oXL= newActiveX("Excel.Application");
  if(null==oXL) return;
  var oWB = oXL.Workbooks.Add(); 
  var oSheet = oWB.ActiveSheet;  
  var sel=document.body.createTextRange();
  sel.moveToElementText(PrintA);
  sel.select();
  sel.execCommand("Copy");
  oSheet.Paste();
  oXL.Visible = true;
 }

 //指定页面区域内容导入Word
 function AllAreaWord() {
  var oWD= newActiveX("Word.Application");
  if(null==oWD) return;
  var oDC = oWD.Documents.Add("",0,1);
  var oRange =oDC.Range(0,1);
  var sel = document.body.createTextRange();
  sel.moveToElementText(PrintA);
  sel.select();
  sel.execCommand("Copy");
  oRange.Paste();
  oWD.Application.Visible = true;
  //window.close();
 }
</SCRIPT>
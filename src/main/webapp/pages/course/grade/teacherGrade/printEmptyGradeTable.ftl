<#include "/templates/head.ftl"/>
<#include "/templates/print.ftl"/>
<object id="factory2" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="css/smsx.cab#Version=6,2,433,14"></object> 
<style>
.printTableStyle {
	border-collapse: collapse;
    border:solid;
	border-width:2px;
    border-color:#006CB2;
  	vertical-align: middle;
  	font-style: normal; 
	font-size: 10pt; 
}
table.printTableStyle td{
	border:solid;
	border-width:0px;
	border-right-width:2;
	border-bottom-width:2;
	border-color:#006CB2;
        height:26px;
}
</style>
<#macro emptyTd count>
     <#list 1..count as i>
     <td></td>
     </#list>
</#macro>
<#assign pagePrintRow = 25 />
<body  onload="SetPrintSettings()">
   <#list tasks?if_exists as task >
   <#assign boycount=0/>
   <#assign girlcount=0/>
    <#assign takes=courseTakes[task.id?string]?sort_by(['student','code'])>
    <br>
     <table width="100%" align="center" border="0"  >
	   <tr>
	    <td align="center" colspan="5" style="font-size:17pt" >
	     <B><@i18nName systemConfig.school/>学生登分册</B>
	    </td>
	   </tr>
	   <tr><td colspan="5">&nbsp;</td></tr>
	 </table>	 
	 <#assign pageNos=(takes?size/(pagePrintRow*2))?int />
	 <#if ((takes?size)>(pageNos*(pagePrintRow*2)))>
	 <#assign pageNos=pageNos+1 />
	 </#if>
	 <#list 0..pageNos-1 as pageNo>
	 <#assign passNo=pageNo*pagePrintRow*2 />
<table width="100%" align="center" border="0"  >
	   <tr class="infoTitle">
	       <td >学年学期：${task.calendar.year}学年 <#if task.calendar.term='1'>第一学期<#elseif task.calendar.term='2'>第二学期<#else>${task.calendar.term}</#if></td>
		   <td ><@msg.message key="attr.taskNo"/>：${task.seqNo}</td>
		   <td ><@msg.message key="attr.courseNo"/>：${task.course.code}</td>
		   <td ><@msg.message key="attr.courseName"/>：<@i18nName task.course?if_exists/></td>
		   <td ><@msg.message key="entity.teacher"/>：<@getTeacherNames task.arrangeInfo.teachers?if_exists/></td>
	   </tr>
	 </table>	 
	 <table class="printTableStyle"  width="100%"  >
	   
	   <tr class="darkColumn" align="center">
	     <td width="4%" >序号</td>
	     <td width="10%"><@msg.message key="attr.stdNo"/></td>
	     <td width="12%"><@msg.message key="attr.personName"/></td>
	     <#list gradeTypes as gradeType>
	     <td width="8%"><@i18nName gradeType/></td>
	     </#list>
	     <td width="8%" >总评成绩</td>
	     <td width="4%">序号</td>
	     <td width="10%"><@msg.message key="attr.stdNo"/></td>
	     <td width="12%"><@msg.message key="attr.personName"/></td>
	     <#list gradeTypes as gradeType>
	     <td width="8%"><@i18nName gradeType/></td>
	     </#list>
	     <td width="8%">总评成绩</td>
	   </tr>
	 
	   <#list 0..pagePrintRow-1 as i>
	   <tr class="brightStyle" >
         <#if takes[i+passNo]?exists>
	     <td>${i+1+passNo}</td>
	     <td>${takes[i+passNo].student.code}</td>
	     <td><@i18nName takes[i+passNo].student/></td>
	     <@emptyTd count=(gradeTypes?size+1)/>
	     </#if>
       
         <#if takes[i+pagePrintRow+passNo]?exists>
	     <td>${i+pagePrintRow+1+passNo}</td>
	     <td>${takes[i+pagePrintRow+passNo].student.code}</td>
	     <td><@i18nName takes[i+pagePrintRow+passNo].student/></td>
        <@emptyTd count=(gradeTypes?size+1)/>
        <#elseif takes[i+passNo]?exists>
          <@emptyTd count=(gradeTypes?size+4)/>
        </#if>
	   </tr>
	   </#list>	   
     </table>
 	 <#if pageNo_has_next>
 	 <div style='PAGE-BREAK-AFTER: always'></div>  
 	 </#if> 
 	 
 	 <#if !pageNo_has_next>	   
	   	 <table class="printTableStyle"  width="100%">
		    <tr  width="100%">
			   <td rowspan='7' width="40%">
			   实际参加考试考查人数____________人<br>
			   缺&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;考&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;人&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数____________人<br>
			   教师签名:_______________________<br>
			   系主任签名:_____________________<br>
			   <br>
			   日期:_____________年_____月_____日
			   </td>
			   <td colspan='3' width="60%" align="center">成绩统计</td>
			  <tr>
			   <td width="20%"  align="center">成绩</td>
			   <td width="20%"  align="center">人数</td>
			   <td width="20%"  align="center">百分比</td>
			  <tr>
			   <td align="center">90-100(优)</td>
			   <td></td>
			   <td align="right">%</td>
			  <tr>
			   <td align="center">80-89.9(良)</td>
			   <td></td>
			   <td align="right">%</td>
			  <tr>
			   <td align="center">70-79.9(中)</td>
			   <td></td>
			   <td align="right">%</td>
			  <tr>
			   <td align="center">60-69.9(及格)</td>
			   <td></td>
			   <td align="right">%</td>
			  <tr>
			   <td align="center">0-59.9(不及格)</td>
			   <td></td>
			   <td align="right">%</td>
		    </tr>
	  		</table>
	  		<#if task_has_next><div style='PAGE-BREAK-AFTER: always'></div> </#if>
		   </#if> 
     </#list>
   </#list>
   <table width="100%" align="center">
	   <tr>
		   <td align="center">
		   <button onclick="print()"  class="notprint" ><@msg.message key="action.print"/></button>
		  </td>
	  </tr>
  </table>
 </body>
<#include "/templates/foot.ftl"/>
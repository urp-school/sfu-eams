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
<#assign pagePrintRow = 30 />
<body  onload="SetPrintSettings()">
   <#list tasks?if_exists as task >
    <#assign takes=courseTakes[task.id?string]?sort_by(['student','code'])>
    <br>
     <table width="100%" align="center">
	   <tr>
	    <td align="center" colspan="4" style="font-size:17pt">
	     <B><@i18nName systemConfig.school/>学生学生名单</B>
	    </td>
	   </tr>
	   <tr><td>&nbsp;</td></tr>
	   <tr class="infoTitle">
	       <td  >学年学期：${task.calendar.year}学年 <#if task.calendar.term='1'>第一学期<#elseif task.calendar.term='2'>第二学期<#else>${task.calendar.term}</#if></td>
		   <td  ><@msg.message key="attr.taskNo"/>：${task.seqNo}&nbsp;&nbsp;</td>
		   <td  ><@msg.message key="attr.courseNo"/>：${task.course.code}&nbsp;&nbsp;</td>
		   <td  ><@msg.message key="attr.courseName"/>：<@i18nName task.course?if_exists/>&nbsp;&nbsp;</td>
		   <td  ><@msg.message key="entity.teacher"/>：<@getTeacherNames task.arrangeInfo.teachers?if_exists/>&nbsp;&nbsp;</td>
	   </tr>
	 </table>	 
	 <#assign pageNos=(takes?size/(pagePrintRow*2))?int/>
	 <#if ((takes?size)>(pageNos*(pagePrintRow*2)))>
	 <#assign pageNos=pageNos+1 />
	 </#if>
	 <#list 0..pageNos-1 as pageNo>
	 <#assign passNo=pageNo*pagePrintRow*2 />

	 <table class="printTableStyle"  width="100%">
	   <tr class="darkColumn" align="center">
	     <td width="6%">序号</td>
	     <td width="10%"><@msg.message key="attr.stdNo"/></td>
	     <td width="15%"><@msg.message key="attr.personName"/></td>
	     <td width="10%">备注</td>
	     <td width="6%">序号</td>
	     <td width="10%"><@msg.message key="attr.stdNo"/></td>
	     <td width="15%"><@msg.message key="attr.personName"/></td>
	     <td width="10%">备注</td>
	   </tr>
	   <#list 0..pagePrintRow-1 as i>
	   <tr class="brightStyle" >
         <#if takes[i+passNo]?exists>
	     <td>${i+1+passNo}</td>
	     <td>${takes[i+passNo].student.code}</td>
	     <td><@i18nName takes[i+passNo].student/></td>
         <#else><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
         </#if>
	     <td></td>
         <#if takes[i+pagePrintRow+passNo]?exists>
	     <td>${i+pagePrintRow+1+passNo}</td>
	     <td>${takes[i+pagePrintRow+passNo].student.code}</td>
	     <td><@i18nName takes[i+pagePrintRow+passNo].student/></td>
         <#else><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></#if>
	     <td></td>
	   </tr>
	   </#list>	   
     </table>
     <div style='PAGE-BREAK-AFTER: always'></div>
     </#list>
   </#list>
   <table  width="90%" align="center" class="ToolBar">
	   <tr>
		   <td align="center">
		   <input class="buttonStyle"  type="button" value="<@msg.message key="action.print"/>" onclick="print()"/>
		  </td>
	  </tr>
  </table>
 </body>
<#include "/templates/foot.ftl"/>
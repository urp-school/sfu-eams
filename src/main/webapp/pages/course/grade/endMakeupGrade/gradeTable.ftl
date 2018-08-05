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
<#assign pagePrintRow = 26 />
<body  onload="SetPrintSettings()">
   <#list activities?if_exists as examTasks >
   <#assign boycount=0/>
   <#assign girlcount=0/>
    <#assign examTask=examTasks?sort_by(['std','code'])>
    <br>
     <table width="100%" align="center" border="0"  >
	   <tr>
	    <td align="center" colspan="5" style="font-size:17pt" >
	     <B><@i18nName systemConfig.school/>补考成绩登记表</B>
	    </td>
	   </tr>
	   <tr><td colspan="5">&nbsp;</td></tr>
	 </table>	 
	 <#assign pageNos=(examTask?size/(pagePrintRow*2))?int />
	 <#if ((examTask?size)>(pageNos*(pagePrintRow*2)))>
	 <#assign pageNos=pageNos+1 />
	 </#if>
	 <#list 0..pageNos-1 as pageNo>
	 <#assign passNo=pageNo*pagePrintRow*2 />
	 <table width="100%" align="center" border="0"  >
	 <tr class="infoTitle">
	       <td >学年学期：${calendar.year}学年 <#if calendar.term='1'>第一学期<#elseif calendar.term='2'>第二学期<#else>${calendar.term}</#if></td>
		   <td ><@msg.message key="attr.courseNo"/>：${examTask[0]?if_exists.task.course.code}</td>
		   <td ><@msg.message key="attr.courseName"/>：<@i18nName examTask[0]?if_exists.task.course?if_exists/></td>
		   <td ><@msg.message key="attr.teachDepart"/>：<@i18nName examTask[0]?if_exists.task.arrangeInfo.teachDepart/></td>
	 </tr>
	 </table>
	 <table class="printTableStyle"  width="100%"  >
	   <tr class="darkColumn" align="center">
	     <td width="4%" >序号</td>
	     <td width="10%"><@msg.message key="attr.stdNo"/></td>
	     <td width="12%"><@msg.message key="attr.personName"/></td>	     
	     <td width="8%">备注</td>
	     <td width="8%" >缓考成绩</td>
	     <td width="8%" >补考成绩</td>

	     <td width="4%">序号</td>
	     <td width="10%"><@msg.message key="attr.stdNo"/></td>
	     <td width="12%"><@msg.message key="attr.personName"/></td>
	     <td width="8%">备注</td>
	     <td width="8%" >缓考成绩</td>
	     <td width="8%" >补考成绩</td>
	   </tr>
	   <#list 0..pagePrintRow-1 as i>
	   <tr class="brightStyle" >
         <#if examTask[i+passNo]?exists>
	     <td>${i+1+passNo}</td>
	     <td>${examTask[i+passNo]?if_exists.std?if_exists.code}</td>
	     <td><@i18nName examTask[i+passNo]?if_exists.std?if_exists/></td>
	     <td><@i18nName examTask[i+passNo]?if_exists.examType/></td>
	     <@emptyTd count=2/>
         </#if>
         
         <#if examTask[i+pagePrintRow+passNo]?exists>
	     <td>${i+pagePrintRow+1+passNo}</td>
	     <td>${examTask[i+pagePrintRow+passNo]?if_exists.std?if_exists.code}</td>
	     <td><@i18nName examTask[i+pagePrintRow+passNo]?if_exists.std?if_exists/></td>
	     <td><@i18nName examTask[i+pagePrintRow+passNo]?if_exists.examType/></td>
	     <@emptyTd count=2/>
         <#elseif examTask[i+passNo]?exists>
          <@emptyTd count=6/>
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
			   <td width="40%">
			   实际参加考试考查人数____________人<br>
			   缺&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;考&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;人&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数____________人<br>
			   教师签名:_______________________<br>
			   系主任签名:_____________________<br>
			   <br>
			   日期:_____________年_____月_____日
			   </td>
		     <td>
		       说明 :<br>
		      (1)学生缓考成绩以实际分数填写,学生补考成绩以及格或不及格填写,不及格成绩以红笔标记。<br>
		      (2)本表由阅卷教师填写,一式两份,一份由院系(部)汇总交教务处,一份留系部保存。 
		     </td>
		    </tr>
	  		</table>
	  		<#if examTasks_has_next>
			 	  <div style='PAGE-BREAK-AFTER: always'></div>  
			</#if>
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
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
<body  onload="SetPrintSettings()">
   <#assign tableNum=0>
    <#list activities as activity>
     <#if tableNum!=0>
     <div style='PAGE-BREAK-BEFORE: always'></div>
     </#if>
     <table width="100%" align="center"  >
	   <tr>
	    <td align="center" colspan="4" style="font-size:17pt" >
	     <B><@i18nName systemConfig.school/>学生考试座位表</B>
	     <br>
	    </td>
	   </tr>
	   <tr><td colspan="3">&nbsp;</td></tr>
	   <tr class="infoTitle">
	       <td width="30%">课程代码(序号)：${activity.task.course.code}(${activity.task.seqNo})</td><td><@msg.message key="attr.courseName"/>: <@i18nName activity.task.course/></td><td>授课教师:<@getBeanListNames  activity.task.arrangeInfo.teachers/></td>
	   </tr>
	   <tr class="infoTitle">
		   <td>考试安排：${activity.task.arrangeInfo.digestExam(activity.task.calendar,Request["org.apache.struts.action.MESSAGE"],Session["org.apache.struts.action.LOCALE"],RequestParameters['examType.id'],"第:weeks周 :day :time")}</td>
		   <td>开课院系:<@i18nName activity.task.arrangeInfo.teachDepart/> 是否挂牌:${activity.task.requirement.isGuaPai?string("是",'否')}</td>
		   <td>考试地点:<@i18nName activity.room?if_exists/>
	   </tr>
	 </table>
	 
	 <#assign examTakes = activity.examTakes?sort_by(["std","code"])/>	  
	 <#assign takeSize=(examTakes?size+1)>
	 <#if (((activity.room.capacityOfExam)?default(30)?int+1)>takeSize)><#assign takeSize=((activity.room.capacityOfExam)?default(30)?int+1)></#if>
	 <#assign pageSize=(takeSize/2)?int />
	 <table class="printTableStyle"  width="100%"  >
	   <tr class="darkColumn" align="center">
	     <td width="6%" >序号</td>
	     <td width="10%"><@msg.message key="attr.stdNo"/></td>
	     <td width="15%"><@msg.message key="attr.personName"/></td>
	     <td width="10%">备注</td>
	     <td width="6%" >序号</td>
	     <td width="10%"><@msg.message key="attr.stdNo"/></td>
	     <td width="15%"><@msg.message key="attr.personName"/></td>
	     <td width="10%">备注</td>
	   </tr>
	   <#list 0..pageSize-1 as i>
	   <tr class="brightStyle" >
         <#if examTakes[i]?exists>
	     <td>${i+1}</td>
	     <td>${examTakes[i].std.code}</td>
	     <td><@i18nName examTakes[i].std/></td>
         <#else><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
         </#if>
	     <td></td>
         <#if examTakes[i+pageSize]?exists>
	     <td>${i+pageSize+1}</td>
	     <td>${examTakes[i+pageSize].std.code}</td>
	     <td><@i18nName examTakes[i+pageSize].std/></td>
         <#else><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></#if>
	     <td></td>
	   </tr>
	   </#list>
     </table>
     <#assign tableNum=tableNum+1/>
     <!--add by lzs
     <#if activity_has_next>
	 	 <div style='PAGE-BREAK-AFTER: always'></div>
 	 </#if>
 	 -->
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
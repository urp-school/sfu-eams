<#include "/templates/head.ftl"/>
<BODY>
	<table id="taskBar"></table>
	<script>
	  var bar=new ToolBar("taskBar","<@msg.message key="action.print"/>缓考申请",null,true,true);
	  bar.addItem("<@msg.message key="action.print"/>","print()");
	  bar.addBack("<@msg.message key="action.back"/>");
	</script> 
	<#assign whitespace>&nbsp;&nbsp;&nbsp;&nbsp;</#assign>
  <table width="100%" align="center" style="font-size:14px">    
    <tr  align="center">
      <td ><B>${calendar.year}学年 <#if calendar.term='1'>第一学期<#elseif calendar.term='2'>第二学期<#else>${calendar.term}</#if><@i18nName std.type/> 缓考申请表</B></td>
    </tr>
    <tr><td>&nbsp;</td></tr>
    <tr  align="center">  
     <td><@msg.message key="attr.personName"/>:${std.name}${whitespace}<@msg.message key="attr.stdNo"/>:${std.code}${whitespace}系别:<@i18nName std.department/>${whitespace}<@msg.message key="entity.speciality"/>:<@i18nName std.firstMajor?if_exists/></td>
    </tr>
    <tr  align="center">
      <td>申请日期:${curDate?string("yyyy-MM-dd")}${whitespace}${whitespace}申请人签名:${whitespace}${whitespace}${whitespace}联系电话:${whitespace}</td>
    </tr>
  </table>
  <br>
  <table width="100%" align="center" class="listTable">
    <thead align="center">     
     <td width="10%"><B><@msg.message key="attr.taskNo"/></B></td>
     <td width="10%"><B><@msg.message key="attr.courseNo"/></B></td>
     <td width="25%"><B>考试时间</B></td>
     <td width="25%"><B>课程全称</B></td>
     <td width="30%"><B>缓考原因</B></td>
     
    </thead>
    <tbody>
    <#list examTakes as examTake>
    <tr align="center">
     <td>${examTake.courseTake.task.seqNo}</td>
     <td>${examTake.courseTake.task.course.code}</td>
     <td>${examTake.activity.task.arrangeInfo.digestExam(examTake.activity.task.calendar,Request["org.apache.struts.action.MESSAGE"],Session["org.apache.struts.action.LOCALE"],examTake.activity.examType.id,"第:weeks周 :day :time")}</td>
     <td><@i18nName examTake.courseTake.task.course/></td>
     <td>${examTake.remark?if_exists}</td>
    </tr>
    </#list>
    <tr >
     <td align="center">院系意见并<br>盖章</td>
     <td colspan="4"></td>
    </tr>
    <tr >
     <td align="center">教学主管部门<br>意见</td>
     <td colspan="4"></td>
    </tr>
    </tbody>
  <table>
  <pre>
   1.因病申请缓考需持保健科证明
   2.若两门课程考试冲突，则重修课程或奖励学分课程缓考
  </pre>
 <script>   
   function save(){
     var form =document.activityForm;
     if(confirm("确定提交该申请吗?"))
     form.submit();
   }
 </script>
</body>
<#include "/templates/foot.ftl"/> 
  
<#include "/templates/head.ftl"/>
<BODY>
	<table id="taskBar"></table>
	<script>
	  var bar=new ToolBar("taskBar","<@msg.message key="exam.applicationDelay"/>",null,true,true);
	  <#if canApplyDelay>
	  bar.addItem("<@msg.message key="action.save"/>","save(0)","save.gif");
	  //bar.addItem("<@msg.message key="action.savePrint"/>","save(1)","print.gif");
	  </#if>
	  bar.addBack("<@msg.message key="action.back"/>");
	</script> 
  <#if !canApplyDelay>
     <pre>
      <@msg.message key="exam.cannotApplyDelayTip"/>
     </pre>
  <#else>
  <table width="90%" class="listTable" align="center">
   <form name="activityForm" method="post" action="stdExamTable.do?method=submitApply&calendar.id=${RequestParameters['calendar.id']}&examType.id=${RequestParameters['examType.id']}" onsubmit="return false;">
    <input type="hidden" name="print" value="1">
    <input type="hidden" name="examTake.id" value="${RequestParameters['examTake.id']}">
    <tr>
     <td><@msg.message key="attr.stdNo"/>:${std.code}</td><td><@msg.message key="attr.personName"/>:${std.name}</td><td><@msg.message key="exam.applicationDelay"/></td>
    </tr>
    <tr>
     <td><@msg.message key="attr.taskNo"/>:${examTake.courseTake.task.seqNo}</td>
     <td><@msg.message key="attr.courseName"/>:<@i18nName examTake.courseTake.task.course/></td>
     <td><@msg.message key="exam.applicationReason"/><font color="red">*</font>
        <select name="examTake.delayReason.id">
           <#list delayReasons as delayReason>
           <option value="${delayReason.id}" <#if delayReason.id?string=examTake.delayReason?if_exists.id?if_exists?string>selected</#if>><@i18nName delayReason/></option>
           </#list>
        </select>
     </td>
    </tr>
   </form>
  <table>
 <script>   
   function save(print){
     var form =document.activityForm;
     form['print'].value=print;
     if(confirm("<@msg.message key="exam.sureToApply"/>")){
        form.submit();
     }
   }
 </script>
 </#if>
</body>
<#include "/templates/foot.ftl"/> 
  
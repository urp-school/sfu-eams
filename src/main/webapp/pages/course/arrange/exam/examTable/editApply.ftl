<#include "/templates/head.ftl"/>
<BODY>
	<table id="taskBar"></table>
	<script>
	  var bar=new ToolBar("taskBar","申请缓考",null,true,true);
	  bar.addItem("<@msg.message key="action.save"/>","save(0)","save.gif");
	  bar.addItem("保存并打印","save(1)","print.gif");
	  bar.addBack("<@msg.message key="action.back"/>");
	</script> 
  <table width="90%" class="listTable" align="center">
   <form name="activityForm" method="post" action="examTable.do?method=submitApply&calendar.id=${RequestParameters['calendar.id']}&examType.id=${RequestParameters['examType.id']}" onsubmit="return false;">
    <input type="hidden" name="print" value="1">
    <input type="hidden" name="examTakeIds" value="${RequestParameters['examTakeIds']}">
    <tr>
     <td><@msg.message key="attr.stdNo"/>:${std.code}</td><td><@msg.message key="attr.personName"/>:${std.name}</td><td>申请缓考${examTakes?size}门</td>
    </tr>
    <#list examTakes as examTake>
    <tr>
     <td><@msg.message key="attr.taskNo"/>:${examTake.courseTake.task.seqNo}</td>
     <td><@msg.message key="attr.courseName"/>:<@i18nName examTake.courseTake.task.course/></td>
     <td>申请原因<font color="red">*</font>
        <select name="examTake${examTake.id}.delayReason.id">
           <#list delayReasons as delayReason>
           <option value="${delayReason.id}" <#if delayReason.id?string=examTake.delayReason?if_exists.id?if_exists?string>selected</#if>><@i18nName delayReason/></option>
           </#list>
        </select>
     </td>
    </tr>
    </#list>
   </form>
  <table>
 <script>   
   function save(print){
     var form =document.activityForm;
     form['print'].value=print;
     if(confirm("确定提交该申请吗?")){
        form.submit();
     }
   }
 </script>
</body>
<#include "/templates/foot.ftl"/> 
  
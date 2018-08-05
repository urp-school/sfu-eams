<#include "/templates/head.ftl"/>
<body >
  <table id="examStatusBar" width="100%"></table>
  <script>
     var bar=new ToolBar('examStatusBar','设置考试情况',null,true,true);
     bar.setMessage('<@getMessage/>');
     bar.addItem("<@msg.message key="action.save"/>","updateExamStatus()");
     bar.addBack("<@msg.message key="action.back"/>");
	function updateExamStatus(){
       var form =document.statusForm;
       if(confirm("确定按照选定的考试情况进行设置？"))
         form.submit();
     }
</script>
 <table class="formTable">
    <form name="statusForm" method="post" action="examTake.do?method=updateExamStatus" onsubmit="return false;">
    <input type="hidden" name="examTakeIds" value="${RequestParameters['examTakeIds']}">
    <input type="hidden" name="examTake.examStatus.id" value="${examStatus.id}">
    <input type="hidden" name="params" value="${RequestParameters['params']}">
    <tr><td>将${examTakes?size}个学生的考试情况设置为：<@i18nName examStatus/></td>
    <#if examDelayReasons?exists>
    <td>缓考原因：
    <select name="examTake.delayReason.id">
      <#list examDelayReasons as reason>
       <option value="${reason.id}"><@i18nName reason/></option>
      </#list>
    </select></td>
    </#if>
    </tr>
    <tr><td colspan="2">写明备注(50个字以内，可以不填)：<textarea cols="80" rows="3" name="examTake.remark"></textarea></td></tr>
  </form>
 </table>
 <@table.table width="100%" id="listTable">
    <@table.thead>
      <td width="10%"><@msg.message key="attr.stdNo"/></td>
      <td width="8%"><@msg.message key="attr.personName"/></td>
      <td width="8%"><@bean.message key="attr.taskNo"/></td>
      <td width="20%"><@bean.message key="attr.courseName"/></td>
      <td width="22%">考试日期</td>
      <td width="8%">考试地点</td>
      <td width="8%">考试情况</td>
      <td width="8%">缓考原因</td>
    </@>
    <@table.tbody datas=examTakes;take>
      <td>${take.std.code}</td>
      <td><@i18nName take.std/></td>
      <td>${take.task.seqNo?if_exists}</td>
      <td><@i18nName take.task.course/></td>
      <td>${take.activity.time.firstDay?string("yyyy-MM-dd")} ${take.activity.digest(Session["org.apache.struts.action.LOCALE"],Request["org.apache.struts.action.MESSAGE"],":time")}</td>
      <td><@i18nName take.activity.room?if_exists/></td>
      <td><@i18nName take.examStatus/></td>
      <td><@i18nName take.delayReason?if_exists/></td>
    </@>
   </@>
</body> 
<#include "/templates/foot.ftl"/> 
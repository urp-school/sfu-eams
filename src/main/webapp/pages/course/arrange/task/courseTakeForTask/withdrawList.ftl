<#include "/templates/head.ftl"/>
<body  LEFTMARGIN="0" TOPMARGIN="0">
<table id="withdrawStdBar"></table>
 <table width="100%" border="0" class="listTable" >
    <tr align="center" class="darkColumn">
      <td align="center" width="2%">
        <input type="checkbox"  onClick="toggleCheckBox(document.getElementsByName('stdId'),event);"/>
      </td>
      <td width="8%"><@msg.message key="attr.stdNo"/></td>
      <td width="8%"><@msg.message key="attr.personName"/></td>
      <td width="8%"><@msg.message key="entity.studentType"/></td>
      <td width="8%">修读类别</td>
      <td width="10%">退课时间</td>
    </tr>
    <#list task.teachClass.withdraws?sort_by("student","stdNo") as withdraw>
   	  <#if withdraw_index%2==1><#assign class="grayStyle"/></#if>
	  <#if withdraw_index%2==0><#assign class="brightStyle"/></#if>
     <tr class="${class}" align="center"
       onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)"
       onclick="onRowChange(event)">
      <td width="2%" class="select">
        <input type="checkbox" name="stdId" value="${withdraw.student.id}"/>
      </td>
      <td>${withdraw.student.code}</td>
      <td><@i18nName withdraw.student/></td>
      <td><@i18nName withdraw.student.type/></td>
      <td><@i18nName withdraw.courseTakeType/></td>
      <td>${withdraw.time?string("yyyy-MM-dd HH:mm")}</td>
    </tr>
	</#list>
	</table>
<script>
   var bar = new ToolBar('withdrawStdBar','退课学生名单(${task.teachClass.withdraws?size})人次',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("打印","javascript:print();",'print.gif');
   bar.addBack("<@bean.message key="action.back"/>");
</script>
</body>
<#include "/templates/foot.ftl"/> 
<#include "/templates/head.ftl"/>
<BODY >
<table  width="100%" height="20" border="0" cellpadding="0" cellspacing="0">
  <tr> 
    <td align="center">     
      <span class="contentTableTitleTextStyle">
       <font color="red"><#if nofound?exists>
       	<#if nofound=='noschedule'>
       	未找到[学生类别为:${student.type.name}/入学年份:${student.enrollYear}/学制::<#if student.schoolingLength?exists>${student.schoolingLength}<#else>无设置</#if>]进度设置 请联系管理员
       	<#else>
       		未找到进度设置里面的<b><#if nofound=="topicOpen">论文开题</#if></b>环节,请联系管理员
       	</#if>
       	</#if>
       	</font>
      </span> 
    </td>
  </tr>
</table>
</body>
<#include "/templates/foot.ftl"/>
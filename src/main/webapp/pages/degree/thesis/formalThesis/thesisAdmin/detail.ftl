<#include "/templates/head.ftl"/>
<body>
<table id="backBar" width="100%"></table>
<script>
var bar = new ToolBar('backBar','${thesis.student.name}的提交论文详细信息',null,true,true);
bar.setMessage('<@getMessage/>');
bar.addBack();
</script>
<table width="100%" class="listTable" align="center">
 <form name="listForm" method="post" action="" onSubmit="return false;">
  <tr>
  	<td colspan="6" align="center" class="darkColumn">${thesis.student.name}提交论文内容</td>
  </tr>
  <tr>
    <td colspan="2" class="grayStyle" id="f_name">论文题目<font color="red">*</font></td>
    <td colspan="4">${(thesis.name)?if_exists}</td>
  </tr>
  <tr>
    <td colspan="2" class="grayStyle" id="f_keyWords">论文主题词<font color="red">*</font></td>
    <td colspan="4">${(thesis.keyWords)?if_exists}</td>
  </tr>
  <tr>
    <td colspan="2" class="grayStyle" id="f_abstract_cn">中文摘要<font color="red">*</font></td>
    <td colspan="4">${(thesis.abstract_cn)?if_exists}</td>
  </tr>
  <tr>
    <td colspan="2" class="grayStyle" id="f_abstract_en">英文摘要<font color="red">*</font></td>
    <td colspan="4">${(thesis.abstract_en)?if_exists}</td>
  </tr>
  <tr>
  	<td colspan="2" class="grayStyle">导师</td>
  	<td colspan="4"><#if (thesis.thesisManage.majorType.id)?exists><#if thesis.thesisManage.majorType.id == 1><@i18nName (thesis.student.teacher)?if_exists/><#elseif thesis.thesisManage.majorType.id == 2><@i18nName (thesis.student.tutor)?if_exists/></#if></#if></td>
  </tr>
  <tr>
  	<td colspan="2" class="grayStyle">备注</td>
  	<td colspan="4">${(thesis.remark)?if_exists}</td>
  </tr>
     <tr>
    <td width="8%" class="grayStyle" id="f_thesisSource">论文来源</td>
    <td width="16%">${(thesis.thesisSource.name)?if_exists}</td>
    <td width="15%" class="grayStyle" id="f_startOn">论文开始时间<font color="red">*</font></td>
    <td width="25%">${(thesis.startOn?string("yyyy-MM-dd"))?if_exists}</td>
    <td width="15%" class="grayStyle" id="f_endOn">论文结束时间<font color="red">*</font></td>
    <td width="21%"><#if (thesis.endOn)?exists>${thesis.endOn?string("yyyy-MM-dd")}</#if></td>
  </tr>
    </form>
</table>
</body>
<#include "/templates/foot.ftl"/>
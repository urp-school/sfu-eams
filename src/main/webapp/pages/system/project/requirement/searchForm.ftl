  <#include "code.ftl"/>
  <table class="searchTable" onkeypress="DWRUtil.onReturn(event, goFirstPage)">
    <form name="requireSearchForm" action="requirement.do?method=search" target="requireListFrame" method="post" onsubmit="return false;">
    <input type="hidden" name="pageNo" value="1" />
    <input name="params" value="" type="hidden" value="">
    <tr><td>模块名称:</td></tr>
    <tr><td><input type="text" name="require.module" style="width:100px;" maxlength="20"/></td></tr>
   	<tr><td>建议人:</td></tr>
    <tr><td><input type="text" name="require.fromUser"  style="width:100px;" maxlength="20"/></td></tr>
   	<tr><td>负责人:</td></tr>
    <tr><td><input type="text" name="require.developers"  style="width:100px;" maxlength="20"/></td></tr>
   	<tr><td>建议内容:</td></tr>
    <tr><td><input type="text" name="require.content"  style="width:100px;" maxlength="100"/></td></tr>
   	<tr><td>优先级:</td></tr>
    <tr><td>
         <select name="require.priority" style="width:100px">
            <option value="" >全部</option>
            <#list priorityMap?keys as priority>
            <option value="${priority}" >${priorityMap[priority]}</option>
            </#list>
         </select>
    </td></tr>
   	<tr><td>类型:</td></tr>
    <tr><td>
         <select  name="require.type" style="width:100px">
            <option value="" >全部</option>
            <#list typeMap?keys as type>
            <option value="${type}" >${typeMap[type]}</option>
            </#list>
         </select>
    </td></tr>
   	<tr><td>状态:</td></tr>
    <tr><td>
         <select  name="require.status" style="width:100px">
            <option value="" >全部</option>
            <#list statusMap?keys as status>
            <option value="${status}" >${statusMap[status]}</option>
            </#list>
         </select>
    </td></tr>
    <tr><td>计划时间(起始-截至):<br>
       <input type="text" name="planCompleteOn.from" maxlength="10" style="width:70px;" onfocus="calendar()"/><input type="text" name="planCompleteOn.to"  style="width:70px;"onfocus="calendar()" />
    </td></tr>

    <tr><td>完成时间(起始-截至):<br>
        <input type="text" name="actualCompleteOn.from" maxlength="10" style="width:70px;" onfocus="calendar()"/><input type="text" name="actualCompleteOn.to"  style="width:70px;"onfocus="calendar()" />
    </td></tr>
    
    <tr><td>提交时间(起始-截至):<br>
        <input type="text" name="createdOn.from" maxlength="10" style="width:70px;" onfocus="calendar()"/><input type="text" name="createdOn.to"  style="width:70px;"onfocus="calendar()" />
    </td></tr>
    <tr><td >
    <button class="buttonStyle" accessKey="r" name="resetButton" onclick="this.form.reset();return false;">重置(<U>R</U>)</button>
    <button class="buttonStyle" accessKey="q" onclick="pageGoWithSize(1);" ><@bean.message key="action.query"/>(<U>Q</U>)</button></td></tr>
    </form>
  </table>

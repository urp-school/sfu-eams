<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
  	<@table.table width="100%" sortable="true" id="listTable">
  	  <@table.thead>
  	    <@table.sortTd text="登录名" id="onlineRecord.name"/>
  		<@table.sortTd text="姓名" id="onlineRecord.userName"/>
  		<@table.sortTd width="15%" text="登录时间"  id="onlineRecord.loginAt"/>
  		<@table.sortTd width="15%" text="退出时间" id="onlineRecord.logoutAt"/>
  		<@table.sortTd width="10%" text="在线时间" id="onlineRecord.onlineTime"/>
 		<@table.sortTd text="主机" id="onlineRecord.host"/>
 		<@table.td text="备注"/>
      </@>
   	  <@table.tbody datas=onlineRecords;onlineRecord>
   		 <td>${onlineRecord.name}</td>
   		 <td>${onlineRecord.userName}</td>
   		 <td>${onlineRecord.loginAt?string("yy-MM-dd HH:mm")}</td>
   		 <td><#if onlineRecord.logoutAt?exists>${onlineRecord.logoutAt?string("yy-MM-dd HH:mm")}</#if></td>
   		 <td><#if onlineRecord.onlineTime?exists>${(onlineRecord.onlineTime/60000)?int}分${(onlineRecord.onlineTime/1000)%60}秒</#if></td>
   		 <td>${onlineRecord.host}</td>
   		 <td>${onlineRecord.remark?default('')}</td>
  	  </@>
  	</@>
 </body>
 <#include "/templates/foot.ftl"/>
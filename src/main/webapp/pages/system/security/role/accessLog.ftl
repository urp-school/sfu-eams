<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="myBar"></table>
	<@table.table width="95%" sortable="true" id="listTable" align="center" style="word-break: break-all">
       	<@table.thead>
			<td width="5%" >序号</td>
         	<@table.sortTd  width="10%" text="URI" id="uri" />
         	<@table.sortTd  width="20%" text="参数" id="params" />
         	<@table.sortTd  width="10%" text="帐号" id="user.name" />
         	<@table.sortTd  width="10%" text="姓名" id="user.userName" />
         	<@table.sortTd  width="20%" text="开始于" id="beignAt" />
	     	<@table.sortTd  width="20%" text="结束于" id="endTimeAt"/>
	     	<@table.sortTd  width="10%" text="持续时间(毫秒)" id="duration"/>
	   	</@>
	   	<@table.tbody datas=accessLogs;accessLog,accessLog_index>
         	<td>${accessLog_index+1}</td>
	    	<td>${accessLog.uri?if_exists}</td>
	    	<td title="${accessLog.params?if_exists?html}" nowrap><span style="display:block;width:150px;overflow:hidden;text-overflow:ellipsis;">${accessLog.params?if_exists}</span></td>
	    	<td>${(accessLog.user.name)?if_exists}</td>
	    	<td>${(accessLog.user.userName)?if_exists}</td>
	    	<td>${accessLog.beignTime?string("yy-MM-dd HH:mm:ss SSS")}</td>
	    	<td>${(accessLog.endTime?string("yy-MM-dd HH:mm:ss SSS"))?if_exists}</td>
	    	<td>${accessLog.duration?if_exists}</td>
	   	</@>
	</@>
	<#if (accessLogs?size==0)>没有记录可能是由于没有启用资源访问过滤器.</#if>
	<script>
   var bar = new ToolBar('myBar','系统资源访问记录<#if systemConfig?exists>&nbsp;－&nbsp;<@i18nName systemConfig.school/></#if>',null,true,true);
   bar.addPrint("<@bean.message key="action.print"/>");    
	</script>
</body>
<#include "/templates/foot.ftl"/>
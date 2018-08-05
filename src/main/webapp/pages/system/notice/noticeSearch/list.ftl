<#include "/templates/head.ftl"/>
<#assign forPortal=RequestParameters['forPortal']?default("false")>
<body>
	<#if forPortal="false">
		<table id="bar" width="100%"></table>
		<@table.table cellspacing="0" cellpadding="0" width="100%">
			<@table.thead>
				<td width="2.5%"></td>
				<@table.td name="notice.title"/>
				<@table.td name="notice.dateDeployed"/>
			</@>
			<@table.tbody datas=notices;notice,notice_index>
				<td>${(notice_index + 1) + pageSize * (pageNo - 1)}</td>
				<td style="text-align:justify;text-justify:inter-ideograph;"><A style="color:blue" href="#" alt="查看详情" onclick="getNoticeInfo('${notice.id}');">${notice.title}</A></td>
	        	<td>${notice.modifyAt}</td>
			</@>
		</@>
	<#else>
	<table>
		<#list notices as notice>
		<tr>
			<td width="4%">*</td>
			<td width="81%"><A style="color:blue" href="#" alt="查看详情" onclick="getNoticeInfo('${notice.id}');">${notice.title}</A></td>
	    	<td  width="15%">${notice.modifyAt}</td>
		</tr>
		<#if notice_index + 1 == 10><#break/></#if>
		</#list>
		<#if (notices?size > 10)>
		<tr>
			<td></td>
			<td></td>
			<td><a href="noticeSearch.do?method=search&orderBy=notice.modifyAt desc" target="_blank">更多...</a></td>
		</tr>
		</#if>
	</table>
	</#if>
	<script>
		var bar = new ToolBar("bar", "系统公告", null, true, true);
		bar.setMessage('<@getMessage/>');
		
		function getNoticeInfo(id){
			window.open("notice.do?method=info&notice.id="+id, '', 'scrollbars=yes,left=0,top=0,width=800,height=350,status=yes');
		}
	</script>
</body>
<#include "/templates/foot.ftl"/>
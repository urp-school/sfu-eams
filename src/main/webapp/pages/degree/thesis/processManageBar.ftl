<#macro processBar id extra...>
	<#if schedule.containTache("${id}")>
	<#assign tacheSetting=schedule.getSetting("${id}")>
	<#if extra['personType']?exists>
		<#assign documentList = tacheSetting.thesisModels/>
		<#if (documentList?size > 0)>
			<#if (documentList?size == 1)>
				bar.addItem("下载${documentList[0].name?replace(".xls", "(参考模板)")?replace(".doc", "(参考文档)")}", "downloadFile('<#if extra['personType']=="std">thesisManageStd.do?method=doDownLoad<#else>thesisDownload.do?method=doDownLoadSystemFile</#if>&document.id=${documentList[0].id}')");
			<#else>
				<#list documentList?sort_by("id") as document>
					<#if document_index == 0>
						var menuBar = bar.addMenu("${document.name?replace(".xls", "(参考模板)")?replace(".doc", "(参考文档)")}", "downloadFile('<#if extra['personType']=="std">thesisManageStd.do?method=doDownLoad<#else>thesisDownload.do?method=doDownLoadSystemFile</#if>&document.id=${document.id}')");
					<#else>
						menuBar.addItem("${document.name?replace(".xls", "(参考模板)")?replace(".doc", "(参考文档)")}", "downloadFile('<#if extra['personType']=="std">thesisManageStd.do?method=doDownLoad<#else>thesisDownload.do?method=doDownLoadSystemFile</#if>&document.id=${document.id}')");
					</#if>
	   			</#list>
			</#if>
			function downloadFile(path) {
				form.action = path;
				form.submit();
			}
		</#if>
	</#if>
</#if>
</#macro>

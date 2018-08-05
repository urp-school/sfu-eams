<#macro processTable id href extra...>
	<#if schedule.containTache("${id}")>
	<#assign tacheSetting=schedule.getSetting("${id}")>
	<table class="infoTable" style="width:90%;" align="center">
	<tr>
		<td colspan="4" ><a href="${href}">${index}.&nbsp;<@i18nName tacheSetting.tache/></a></td>
	</tr>
	<tr>
		<td class="title" style="width:20%"><@msg.message key="thesis.plannedOn"/></td>
		<td style="width:30%">${tacheSetting.planedTimeOn?default("")?string("yyyy-MM-dd")}</td>
		<td class="title" style="width:20%"><@msg.message key="thesis.finishedOn"/></td>
		<td>
			<#if id=="01">
				<#if (thesisManage.topicOpen.finishOn)?exists>${thesisManage.topicOpen.finishOn?string("yyyy-MM-dd")}</#if>
			<#elseif id=="04">
				<#if (thesisManage.getPreAnswer().finishOn)?exists>${(thesisManage.getPreAnswer().finishOn)?string("yyyy-MM-dd")}</#if>
			<#elseif id=="03">
				<#if (thesisManage.annotate.finishOn)?exists>${(thesisManage.annotate.finishOn)?string("yyyy-MM-dd")}</#if>
			<#elseif id=="05">
				<#if (thesisManage.formalAnswer.finishOn)?exists>${(thesisManage.formalAnswer.finishOn)?string("yyyy-MM-dd")}</#if>
			</#if>
		</td>
	</tr>
	<tr>
		<td class="title"><@msg.message key="thesis.contentDescription"/></td>
		<td colspan="3">
			<#if id=="01">
				<b><@msg.message key="thesis.topic"/></b>: ${(thesisManage.topicOpen.getTopicOpenName())?if_exists}<br>
				<b><@msg.message key="thesis.time"/></b>:<#if (thesisManage.topicOpen.openReport.openOn)?exists>${(thesisManage.topicOpen.openReport.openOn)?string("yyyy-MM-dd")}</#if>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><@msg.message key="thesis.place"/></b>:${(thesisManage.topicOpen.openReport.address)?if_exists}<br>
				<b><@msg.message key="thesis.topicOpenResult"/></b>:<#if (thesisManage.topicOpen.isPassed)?exists><#if (thesisManage.topicOpen.isPassed)?string=="1"><@msg.message key="thesis.passed"/><#elseif (thesisManage.topicOpen.isPassed)?string=="2"><@msg.message key="thesis.afterModify"/><#else><@msg.message key="thesis.unpassed"/></#if></#if>
			<#elseif id=="04">
				<b><@msg.message key="thesis.time"/></b>:<#if (thesisManage.getPreAnswer().answerTime)?exists>${(thesisManage.getPreAnswer().answerTime)?string("yyyy-MM-dd")}</#if>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><@msg.message key="thesis.place"/></b>:${(thesisManage.getPreAnswer().answerAddress)?if_exists}<br>
				<b><@msg.message key="thesis.PWC_reply"/></b>:<#if (thesisManage.getPreAnswer().isPassed)?exists><#if thesisManage.getPreAnswer().isPassed><@msg.message key="thesis.qualified"/><#else><@msg.message key="thesis.disqualification"/></#if><#else><@msg.message key="thesis.noResults"/></#if>
			<#elseif id=="03">
				<b><@msg.message key="thesis.MeSH"/></b>:${(thesisManage.topicOpen.content.keyWords)?if_exists}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><@msg.message key="thesis.topicsSources"/></b>:${(thesisManage.topicOpen.taskSource.thesisSourse.name)?if_exists}<br>
				<b><@msg.message key="thesis.wordage"/></b>:<@msg.message key="thesis.factWorage"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><@msg.message key="thesis.type"/></b>:${(thesisManage.topicOpen.thesisType.name)?if_exists}<br>
			 	<b><@msg.message key="thesis.blindTrialResults"/></b>
			 <table class="listTable" width="100%">
  				<tr class="darkColumn">
  					<td align="center" colspan="5"><@msg.message key="thesis.remarkingBookResults"/></td>
  				</tr>
				<tr class="grayStyle">
					<td><@msg.message key="thesis.seqNo"/></td>
					<td><@msg.message key="thesis.scores"/></td>
					<td><@msg.message key="thesis.avgScores"/></td>
					<td><@msg.message key="thesis.degreeLevel"/></td>
					<td><@msg.message key="thesis.agreeToAnswer"/></td>
				</tr>
   				<#list (thesisManage.annotate.annotateBooks)?if_exists?sort_by("serial") as book>
   					<#if book_index%2==0><#assign class="brightStyle"><#else><#assign class="grayStyle"></#if>
					<tr class="${class}">
						<td>${book.serial?if_exists}</td>
						<td><#if book.evaluateIdea?if_exists.mark?exists>${book.evaluateIdea.mark?string("##0.0")}</#if></td>
						<td><#if thesisManage.annotate.avgMark?exists>${thesisManage.annotate.avgMark?string("##0.0")}</#if></td>
						<td><#if book.evaluateIdea?if_exists.learningLevel?exists><#if book.evaluateIdea.learningLevel=="A"><@msg.message key="thesis.best"/><#elseif book.evaluateIdea.learningLevel=="B"><@msg.message key="thesis.good"/><#elseif book.evaluateIdea.learningLevel=="C"><@msg.message key="thesis.normal"/><#elseif book.evaluateIdea.learningLevel=="D"><@msg.message key="thesis.bad"/></#if></#if></td>
						<td><#if book.isReply?exists><#if book.isReply=="A"><@msg.message key="thesis.toAnswer.agree"/><#elseif book.isReply=="M"><@msg.message key="thesis.toAnswer.afterModify"/><#elseif book.isReply=="D"><@msg.message key="thesis.toAnswer.disagree"/></#if></#if></td>
					</tr>
 				</#list>
 				<tr class="darkColumn">
 					<td  colspan="5"><@msg.message key="thesis.fillTableTip"/></td>
 				</tr>
 			</table>
			<#elseif id=="05">
				<b><@msg.message key="thesis.time"/></b>:<#if (thesisManage.formalAnswer.time)?exists>${(thesisManage.formalAnswer.time)?string("yyyy-MM-dd")}</#if>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><@msg.message key="thesis.place"/></b>:${(thesisManage.formalAnswer.address)?if_exists}<br>
				<b><@msg.message key="thesis.toAnswer.results"/></b>:${(thesisManage.formalAnswer.formelMark)?default("")?string}
			<#elseif id=="06">
			</#if>
		</td>
	</tr>
	<#if extra['personType']?exists&&extra['personType']=="std">
		<tr>
			<td class="title"><@msg.message key="thesis.workingFile_Regulations"/></td>
			<td colspan="3" height="10%">
			<#list tacheSetting.thesisDocuments?sort_by("id") as docuemnt>
   			<a href="thesisManageStd.do?method=doDownLoad&document.id=${docuemnt.id}">${docuemnt.name}</a><br>
   			</#list>
			</td>
		</tr>
	</#if>
	<#if extra['personType']?exists>
		<#assign thesisStore=thesisManage.getProcessById("${id}")?if_exists/>
		<tr>
			<td class="title"><@msg.message key="thesis.upload_downloadTable"/></td>
			<td colspan="3">
				<#list tacheSetting.thesisModels?sort_by("id") as docuemnt>
   					${docuemnt.name}<br>
   				</#list>
   				<br>
   				<#if (thesisStore.id)?exists&&extra['personType']=="admin">
   				 <@msg.message key="thesis.std.moduleDoc"/>:<#if (thesisManage.thesis.displayName)?exists><a href="thesisDownload.do?method=doDownLoad&thesisManageId=${thesisManage.id}&storeId=${id}">${thesisManage.thesis.displayName}<img class="icon" src="${static_base}/images/action/paperclip.gif"></a></#if>
				</#if>
			</td>
		</tr>
	</#if>
	<tr>
		<td colspan="4">&nbsp;</td>
	</tr>
	<tr>
		<td colspan="4" class="dark"></td>
	</tr>
</table>
<#assign index=index+1>
</#if>
</#macro>
<script>
	function upload(id,thesisManageId,isHasUp){
		var message="";
		if("01"==id){
			message="论文开题";
		}else if("04"==id){
			message="博士论文预答辩";
		}else if("03"==id){
			message="论文评阅";
		}else if("05"==id){
			message="论文答辩";
		}else{
		}
		if(isHasUp&&!confirm("你已经上传了一份副本,确定还要上传吗?")){
				return;
		}
		if(confirm("你确定要上传"+message+"文档吗\n请注意把该模块里面的文件保存成一个压缩包上传\n你的该模块只保存一个附件")){
			document.conditionForm.action="thesisDownload.do?method=loadUploadPage&thesisId="+id+"&thesisManageId="+thesisManageId;
			document.conditionForm.submit();
		}
	}
</script>
<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<script>
function doAction(form){
<#if "openInfo"==topicOpenFlag>
     var a_fields = {
         'topicOpen.thesisTopic':{'l':'<@msg.message key="filed.thesisTitle" />', 'r':true, 't':'f_thesisTopic', 'mx':100},
         'topicOpen.thesisType.id':{'l':'论文类型', 'r':true, 't':'f_thesisType'},
         'topicOpen.taskSource.thesisSourse.id':{'l':'选题来源', 'r':true, 't':'f_taskLevel'},
         'topicOpen.taskSource.projectTaskName':{'l':'<@msg.message key="filed.whichCourse" />', 'r':true, 't':'f_projectTaskName', 'mx':50},
         'topicOpen.taskSource.passCompany':{'l':'批准单位', 'r':true, 't':'f_passCompany', 'mx':50},
         'topicOpen.taskSource.outlaySource':{'l':'<@msg.message key="filed.moeyFrom" />', 'r':true, 't':'f_outlaySource', 'mx':50},
         'topicOpen.content.keyWords':{'l':'论文主题字', 'r':true, 't':'f_selectTopicKeyWord', 'mx':100},
         'topicOpen.content.meaning':{'l':'选题的理论意义与实用价值', 'r':true, 't':'f_meaning'},
         'topicOpen.content.researchActuality':{'l':'国内外研究现状', 'r':true, 't':'f_researchActuality'},
         'topicOpen.content.thinkingAndMethod':{'l':'研究的基本思路与方法', 'r':true, 't':'f_thinkingAndMethod'},
         'topicOpen.content.innovate':{'l':'论文的创新点', 'r':true, 't':'f_innovate'},
         'topicOpen.content.expectHarvest':{'l':'预期成果', 'r':true, 't':'f_expectHarvest'},
         'topicOpen.content.researchArea':{'l':'研究内容范围', 'r':true, 't':'f_researchArea'},
         'topicOpen.content.aboutThesisNumber':{'l':'论文字数', 'r':true,'f':'unsignedReal','t':'f_wordNumber','mx':6},
         'topicOpen.referrenceLiterature':{'l':'查阅主要文献', 'r':true, 't':'f_literature'},
         <#if "doctor"==stdType>
         'topicOpen.thesisPlan.researchTime':{'l':'研究阶段开始时间', 'r':true, 't':'f_thesisPlan'},
         'topicOpen.thesisPlan.thesisWriteTime':{'l':'论文开始写作时间', 'r':true, 't':'f_writeTime'},
         'topicOpen.thesisPlan.thesisFinishTime':{'l':'论文定稿时间', 'r':true, 't':'f_finishTime'},
         'topicOpen.thesisPlan.rehearseAnswerTime':{'l':'论文预答辩时间', 'r':true, 't':'f_beforeAnswerTime'},
         'topicOpen.thesisPlan.biBlindTime':{'l':'论文双盲评审时间', 'r':true, 't':'f_biBlindTime'},
         <#elseif "master"==stdType>
         'topicOpen.fare':{'l':'进度安排情况', 'r':true, 't':'f_fare'},
         </#if>
         'topicOpen.thesisPlan.thesisAnswerTime':{'l':'论文答辩时间', 'r':true, 't':'f_answerTime'}
     };
<#elseif "reportInfo"==topicOpenFlag>
	var a_fields={
		'topicOpen.thesisPlan.thesisTopicArranged':{'l':'调整后题目', 't':'f_thesisTopicArranged', 'mx':200},
		'topicOpen.openReport.openOn':{'l':'报告日期', 'r':true, 't':'f_openOn'},
		'topicOpen.openReport.participatorCount':{'l':'与会人数', 'r':true,'f':'unsigned','t':'f_participatorCount','mx':10},
		'topicOpen.openReport.experts':{'l':'与会者', 'r':true, 't':'f_experts'},
		'topicOpen.openReport.opinions':{'l':'与会者主要意见', 'r':true, 't':'f_opinions','mx':1000},
		'topicOpen.openReport.address':{'l':'报告地点', 'r':true, 't':'f_address','mx':100}
		};
</#if>
     var v = new validator(form, a_fields, null); 
      if (v.exec()&&confirm("你确定提交你的开题信息吗??")) {   
        form.action="loadThesisTopic_std.do?method=save";
        form.submit();
       }    
   }
</script>
<body>
<table id="backBar" width="100%"></table>
<script>
 var bar = new ToolBar('backBar','开题基本信息',null,true,true);
 bar.addItem("提交","doAction(form)")
 bar.addBack();
</script>
<#if "openInfo"==topicOpenFlag>
<#include "stdThesisOpen.ftl"/>
<#elseif "reportInfo"==topicOpenFlag>
<#include "stdReportInfo.ftl"/>
</#if>
<input type="hidden" name="topicOpenFlag" value="${topicOpenFlag}">
<input type="hidden" name="stdType" value="${stdType}">
</body>
<script>
	var form =document.<#if formName?exists>${formName}<#else>topicOpenInfoForm</#if>;
</script>
<#include "/templates/foot.ftl"/>
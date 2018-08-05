<#include "/templates/head.ftl"/>
<body>
	<table id="bar" width="100%"></table>
	<h2 align="center">授予教师在职攻读硕士学位基本数据表</h2>
	<table width="100%" class="listTable">
		<tr>
			<td rowspan="4" align="center">学<br>科<br>信<br>息</td>
			<td width="12%">授学位单位</td>
			<td colspan="3">${(systemConfig.school.name)?if_exists}</td>
			<td width="12%">学科门类</td>
			<td colspan="3">${(student.firstMajor.subjectCategory.name)?if_exists}</td>
		</tr>
		<tr>
			<td>获硕士学位类别</td>
			<td colspan="3">${(student.firstMajor.subjectCategory.name)?if_exists}</td>
			<td>一级学科</td>
			<td colspan="3">${(level2Subject.level1Subject.name)?if_exists}</td>
		</tr>
		<tr>
			<td rowspan="2">高等学校教师、中等职业学校教师在职攻读硕士学位类别</td>
			<td rowspan="2" colspan="3"></td>
			<td>二级学科</td>
			<td colspan="3">${(student.firstMajor.name)?if_exists}</td>
		</tr>
		<tr>
			<td>自主设置专业名称</td>
			<td colspan="3"></td>
		</tr>
		<tr>
			<td rowspan="4" align="center">个<br>人<br>基<br>本<br>信<br>息</td>
			<td>姓名</td>
			<td width="12%">${(student.name)?if_exists}</td>
			<td width="12%">性别</td>
			<td width="12%">${(student.basicInfo.gender.name)?if_exists}</td>
			<td>民族</td>
			<td width="12%">${(student.basicInfo.nation.name)?if_exists}</td>
			<td rowspan="3" colspan="2" align="center" valign="middle">数码照片</td>
		</tr>
		<tr>
			<td>国家或地区</td>
			<td>${(student.basicInfo.country.name)?if_exists}</td>
			<td>籍贯</td>
			<td>${(student.basicInfo.ancestralAddress)?if_exists}</td>
			<td>政治面貌</td>
			<td>${(student.basicInfo.politicVisage.name)?if_exists}</td>
		</tr>
		<tr>
			<td>出生日期</td>
			<td>${(student.basicInfo.birthday?string("yyyy-MM-dd"))?if_exists}</td>
			<td colspan="2">入学前（前置）最高学历</td>
			<td colspan="2">${(student.studentStatusInfo.educationBeforEnroll.name)?if_exists}</td>
		</tr>
		<tr>
			<td>身份证件号码</td>
			<td colspan="3">${(student.basicInfo.idCard)?if_exists}</td>
			<td>联系电话</td>
			<td colspan="3">${(student.basicInfo.phone)?if_exists}</td>
		</tr>
		<tr>
			<td rowspan="4" align="center">学<br>业<br>信<br>息</td>
			<td>入学日期</td>
			<td>${(student.studentStatusInfo.enrollDate?string("yyyy-MM-dd"))?if_exists}</td>
			<td>毕业日期</td>
			<td>${(student.degreeInfo.graduateOn)?if_exists}</td>
			<td colspan="2">获硕士学位日期</td>
			<td colspan="2">${(student.degreeInfo.graduateOn)?if_exists}</td>
		</tr>
		<tr>
			<td>学号</td>
			<td colspan="3">${(student.code)?if_exists}</td>
			<td>录取类别</td>
			<td>${(student.studentStatusInfo.educationMode.name)?if_exists}</td>
			<td width="12%">学习方式</td>
			<td width="12%">${(student.degreeInfo.studyType.name)?if_exists}</td>
		</tr>
		<tr>
			<td>导师姓名</td>
			<td>${(student.teacher.name)?if_exists}</td>
			<td>导师职称</td>
			<td colspan="2">${(student.teacher.title.name)?if_exists}</td>
			<td>学生类别</td>
			<td colspan="2">${(student.type.name)?if_exists}</td>
		</tr>
		<tr>
			<td>学位证书号</td>
			<td colspan="4">${(student.degreeInfo.certificateNo)?if_exists}</td>
			<td colspan="2">是否按一级学科授予</td>
			<td>是</td>
		</tr>
		<tr>
			<td rowspan="2" align="center">学<br>历<br>信<br>息</td>
			<td>本科毕业院校</td>
			<td>${(student.degreeInfo.undergraduate.school.name)?if_exists}</td>
			<td colspan="2">获学士学位专业</td>
			<td colspan="4">${(student.degreeInfo.undergraduate.specialityName)?if_exists}</td>
		</tr>
		<tr>
			<td>授学士学位单位</td>
			<td>${(student.degreeInfo.undergraduate.school.name)?if_exists}</td>
			<td colspan="2">获学士学位门类</td>
			<td>${(student.degreeInfo.undergraduate.subjectCategory.name)?if_exists}</td>
			<td colspan="2">获学士学位日期</td>
			<td>${(student.degreeInfo.undergraduate.graduateOn)?if_exists}</td>
		</tr>
		<tr>
			<td rowspan="4" align="center">学<br>位<br>论<br>文<br>信<br>息</td>
			<td>硕士学位论文题目</td>
			<td colspan="7">${(thesisManage.topicOpen.topicOpenName)?if_exists}</td>
		</tr>
		<tr>
			<td>硕士学位论文主题词</td>
			<td colspan="7">${(thesisManage.topicOpen.content.keyWords)?if_exists}</td>
		</tr>
		<tr>
			<td>论文类型</td>
			<td>${(thesisManage.topicOpen.thesisType.name)?if_exists}</td>
			<td colspan="2">论文选题来源</td>
			<td>${(thesisManage.topicOpen.taskSource.thesisSourse.name)?if_exists}</td>
			<td colspan="2">论文字数（单位/万）</td>
			<td>${(thesisManage.thesis.thesisNum?string("0.0"))?if_exists}</td>
		</tr>
		<tr>
			<td>论文开始日期</td>
			<td colspan="2">${(thesisManage.thesis.startOn?string("yyyy-MM-dd"))?if_exists}</td>
			<td colspan="2">论文答辩日期</td>
			<td colspan="3">${(thesisManage.formalAnswer.time?string("yyyy-MM-dd"))?if_exists}</td>
		</tr>
		<tr>
			<td align="center">其<br>他<br>信<br>息</td>
			<td>备注</td>
			<td colspan="7"></td>
		</tr>
	</table>
	<#if "std"==flag?if_exists>
		<table>
		  	<form name="dataInfoForm" method="post" action="" onsubmit="return false;">
		     	<#if !colspans?exists>
		  	 		<input type="hidden" name="degreeApply.totalMark" value="${totleMark}"/>
		  	 	</#if>
			  	<input type="hidden" name="degreeApply.specialityMark" value="${speciaMark}"/>
			  	<input type="hidden" name="degreeApply.level1Subject.id" value="${(level1Subject.id)?if_exists}"/>
			  	<input type="hidden" name="degreeApply.student.id" value="<#if (degreeApply.id)?exists>${(degreeApply.student.id)?if_exists}<#else>${student.id}</#if>"/>
			  	<input type="hidden" name="degreeApply.thesisManage.id" value="<#if (degreeApply.id)?exists>${(degreeApply.thesisManage.id)?if_exists}<#else>${(thesisManage.id)?if_exists}</#if>"/>
			  	<input type="hidden" name="degreeApply.id" value="${(degreeApply.id)?if_exists}"/>
		  	</form>
			<tr valign="top">
				<td width="8px">注：</td>
				<td><#include "tableFootContent.ftl"/></td>
			</tr>
		</table>
	</#if>
	<br>
	<script>
		var bar = new ToolBar("bar", "申请学位－<@i18nName systemConfig.school/>申请硕士学位数据表", null, true, true);
		bar.setMessage('<@getMessage/>');
	   <#if "std" == flag?if_exists>
		   bar.addItem("下载个人学位数据表模板", "downloadTemplate('degreeInfo', 'T')");
		   bar.addItem("<#if (degreeApply.id)?exists>重新申请<#else>申请学位</#if>", "doSubmit()");
	   </#if>
	   bar.addPrint("<@msg.message key="action.print"/>");
	   ${otherJS?if_exists}
	   
		var form = document.dataInfoForm;
		
		function doSubmit() {
			if (<#if (thesisManage.id)?exists>false<#else>true</#if>) {
			 	alert("你的论文各个环节的信息还没有填写，不能提交学位申请。");
			 	return;
			}
			if (confirm("你确认对应的信息没有错误，可以提交学位申请了吗？")) {
		    	form.action = "degreeApplyStd.do?method=doSaveDegreeApply";
		    	form.submit();
		    }
		}
		
		function downloadTemplate(type, value) {
		   	form.action = "thesisManageStd.do?method=export";
		   	if(<#if (degreeApply.id)?exists>false<#else>true</#if>) {
				alert("你还没有提交学位申请。");
		       	return;
		    }
		    addInput(form, "template", type+"_"+value+".xls");
		   	addInput(form, "fileName", "${student.name}的学位数据表");
		   	addInput(form, "exportFlag", "degreeInfo");
		   	form.submit();
		}
	</script>
</body>
<#include "/templates/foot.ftl"/>
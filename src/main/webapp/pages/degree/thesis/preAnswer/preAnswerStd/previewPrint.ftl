<#include "/templates/head.ftl"/>
<body>
<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','博士学生预答辩情况表',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@msg.message key="action.print"/>","doPrint()");
   bar.addBack();
   function doPrint(){
   		print();
   		window.close();
   }
   
</script>
<style>
.printStyle {
	font-size: ${fontSize}pt;
}
table.printStyle td{
	font-size: ${fontSize}pt;
}
</style>
<table cellpadding="0" cellspacing="0" width="100%" align="center" class="printStyle">
  <tr>
  		<td colspan="4" align="center"><h3><b><@i18nName systemConfig.school/><br>博士研究生学位论文预答辩情况表</b></h3></td>
  </tr>
  <tr>
    <td width="15%"><b>院&nbsp;系&nbsp;所</b>:</td><td width="35%">${student?if_exists.department?if_exists.name?if_exists}</td>
    <td width="15%"><b><@msg.message key="entity.speciality"/></b></td><td width="35%">:${student?if_exists.firstMajor?if_exists.name?if_exists}</td>
  </tr>
  <tr>
 	 <td><b>姓&nbsp;&nbsp;&nbsp;名</b>:</td><td>${student?if_exists.name?if_exists}</td>
   <td><b>联系电话</b>:</td><td>${student?if_exists.basicInfo?if_exists.phone?if_exists}&nbsp;</td>
  </tr>
  <tr>
 	   <td><b>学&nbsp;&nbsp;&nbsp;号</b>:</td><td>${student?if_exists.code?if_exists}</td>
     <td><b>导师姓名</b>:</td><td>${student?if_exists.teacher?if_exists.name?if_exists}</td>
  </tr>
  <tr>
  		<td colspan="4" class="printStyle">
  			<table width="100%" class="listTable" align="center">
  				<tr>
    				<td width="20%" height="25px;" align="center">论文题目</td>
    				<td colspan="3"><#if topicOpen?if_exists.thesisPlan?if_exists.thesisTopicArranged?exists>${topicOpen.thesisPlan.thesisTopicArranged}<#else>${topicOpen?if_exists.thesisTopic?if_exists}</#if></td>
  				</tr>
  				<tr>
  					<td align="center">首次<input type="checkBox" name="preAnswer.answerNum" value="1" <#if num?exists&&num?string=="1">checked</#if> disabled>第二次<input type="checkBox" name="preAnswer.answerNum" value="2" <#if num?exists&&num?string=="2">checked</#if> disabled>末次<input type="checkBox" name="preAnswer.answerNum" value="3" <#if num?exists&&num?string=="3">checked</#if> disabled></td>
  					<td align="center">是否同意预答辩</td>
  					<td align="center">是<input type="checkBox" name="preAnswer.isTutorAffirm" value="true" <#if preAnswer?if_exists.isTutorAffirm?exists&&"true"==preAnswer.isTutorAffirm?string>checked</#if> disabled>否<input type="checkBox" name="preAnswer.isTutorAffirm" value="false" <#if preAnswer?if_exists.isTutorAffirm?exists&&"false"==preAnswer?if_exists.isTutorAffirm?string>checked</#if> disabled></td>
  					<td height="25px;" valign="top">导师签名</td>
  				</tr>
  				<tr align="center">
    					<td>预答辩小组成员<br>(含导师共5人):</td>
    					<td id="f_name"><@msg.message key="attr.personName"/></td>
    					<td id="f_specialityAspect"><@msg.message key="entity.specialityAspect"/></td>
    					<td id="f_depart">院系所(单位)</td>
    			</tr>
    			<tr>
					<td align="center">负责人<#if answerChairMan?exists><input type="hidden" name="answerTeam0.id" value="${answerChairMan.id}"></#if></td>
					<td height="25px;">${answerChairMan?if_exists.name?if_exists}</td>
					<td>${answerChairMan?if_exists.specialityAspect?if_exists}</td>
					<td>${answerChairMan?if_exists.depart?if_exists}</td>
    			</tr>
    			<tr>
    				<td rowspan="5" align="center">成员</td>
    			</tr>
    			<#list answerTeamSet?if_exists?sort_by("name") as at>
				  <tr>
				    <td>${at.name?if_exists}</td>
				    <td>${at.specialityAspect?if_exists}</td>
				    <td>${at.depart?if_exists}</td>  
				  </tr>
				  </#list>
				  <#if answerTeamSet?if_exists?size<3>
					  	<#list  answerTeamSet?if_exists?size..3 as i>
					  	<tr>
					    	<td height="25px;"></td>
					    	<td></td>
					    	<td></td>
					  	</tr>
					  </#list>
				 </#if>
    			<tr>
    				<td height="25px;">预答辩时间</td>
    				<td><#if preAnswer?if_exists.answerTime?exists>${preAnswer?if_exists.answerTime?string("yyyy-MM-dd")}</#if></td>
    				<td>预答辩地点</td>
    				<td>${preAnswer?if_exists.answerAddress?if_exists}</td>
    			</tr>
    			<tr>
    				<td colspan="4" height="150px;" valign="top">
    				   专家意见和建议:<br>
    				   ${preAnswer?if_exists.advice?if_exists}
    				</td>
    			</tr>
    			<tr>
    				<td colspan="4" id="f_isPassed">
    				   预答辩结果:<br>
    				   1、<input type="checkBox" name="preAnswer.isPassed" value="1" <#if (preAnswer.isPassed)?exists&& (preAnswer.isPassed)>checked</#if>>预答辩合格，论文修订后送双盲评审.<br>
    				   2、<input type="checkBox" name="preAnswer.isPassed" value="0" <#if (preAnswer.isPassed)?exists&& !(preAnswer.isPassed)>checked</#if>>预答辩不合格，修改论文，参加下一级答辩.
    				</td>
    			</tr>
    			
    			<tr>
    				<td colspan="2" height="25px;"><p>预答辩小组负责人签字：</p>
    				</td>
    				<td colspan="2" align="right">年&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;日</td>
    			</tr>
    			<tr>
    				<td colspan="4" align="right" height="25px;">院系所(盖章)</td>
    			</tr>
  			</table>
  		</td>
  </tr>
  <tr>
  	  <td colspan="2" class="printStyle" align="left">
  	  	注：<br>			
		&nbsp;&nbsp;&nbsp;&nbsp;1.申请人应在每学期开学时向院系所提交该表1份和5本论文，送预答辩专家评审，于3月底/9月底前完成预答辩。 预答辩结束院系所将该表和预答辩信息汇总表交我部学科学位办。<br>		
		&nbsp;&nbsp;&nbsp;&nbsp;2.合格者修改论文定稿后于4月/10月中旬装订完毕，向学科学位办公室提交不含个人和导师信息的学位论文和论文评阅书（含自评表）各一式3份参加双盲评审。<br>
		&nbsp;&nbsp;&nbsp;&nbsp;3.不合格者办理延期毕业手续。预答辩次数最多3次，后2次预答辩费用自理。<br>
  	  	
  	  </td>
  </tr>
  </table>
</body>
<#include "/templates/foot.ftl"/>
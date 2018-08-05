<table cellpadding="0" cellspacing="0" width="100%" border="0" align="center">
  <tr>
  		<td colspan="2" align="center"><h3><b><@i18nName systemConfig.school/><br>博士研究生学位论文预答辩申请及备案情况表</b></h3></td>
  </tr>
  <tr>
    <td align="left"><b>院&nbsp;系&nbsp;所</b>:${student?if_exists.department?if_exists.name?if_exists}</td>
    <td align="right"><b><@msg.message key="entity.speciality"/></b>:${student?if_exists.firstMajor?if_exists.name?if_exists}</td>
  </tr>
  <tr>
 	 <td align="left"><b>姓&nbsp;&nbsp;&nbsp;名</b>:${student?if_exists.name?if_exists}</td>
     <td align="right"><b>联系电话</b>:${student?if_exists.basicInfo?if_exists.phone?if_exists}</td>
  </tr>
  <tr>
 	 <td align="left"><b>学&nbsp;&nbsp;&nbsp;号</b>:<input type="hidden" name="studentId" value="${student?if_exists.id?if_exists}">${student?if_exists.code?if_exists}</td>
     <td align="right"><b>导师姓名</b>:${student?if_exists.teacher?if_exists.name?if_exists}</td>
  </tr>
  <tr>
  		<td colspan="2">
  			<table width="100%" class="listTable" align="center">
  				<tr>
    				<td width="20%"><b>论文题目</b></td>
    				<td colspan="3">${thesisTopicOpen?if_exists.thesisTopic?if_exists}</td>
  				</tr>
  				<tr>
  					<td align="center">首次<input type="checkBox" name="preAnswer.answerNum" value="1" <#if preAnswer.answerNum?exists><#if preAnswer.answerNum?string=="1">checked</#if><#else>checked</#if>>第二次<input type="checkBox" name="preAnswer.answerNum" value="2" <#if preAnswer.answerNum?exists&&preAnswer.answerNum?string=="2">checked</#if>>末次<input type="checkBox" name="preAnswer.answerNum" value="3" <#if preAnswer.answerNum?exists&&preAnswer.answerNum?string=="3">checked</#if>></td>
  					<td id="f_isAffirm">是否同意预答辩</td>
  					<td>是<input type="checkBox" name="preAnswer.isTutorAffirm" value="true" <#if preAnswer.isTutorAffirm?exists&&"true"==preAnswer.isTutorAffirm?string>checked</#if>>否<input type="checkBox" name="preAnswer.isTutorAffirm" value="false" <#if preAnswer.isTutorAffirm?exists&&"false"==preAnswer.isTutorAffirm?string>checked</#if>></td>
  					<td>导师签名:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
  				</tr>
  				<tr align="center">
    					<td>预答辩小组成员<br>(含导师共5人)<#if flag=="admin"><font color="red">*</font></#if>:</td>
    					<td id="f_name"><@msg.message key="attr.personName"/></td>
    					<td id="f_specialityAspect"><@msg.message key="entity.specialityAspect"/></td>
    					<td id="f_depart">院系所(单位)</td>
    			</tr>
    			<tr>
					<td>负责人<#if answerChairMan?exists><input type="hidden" name="answerTeam0.id" value="${answerChairMan.id}"></#if></td>
					<td><input type="text" maxlength="20"  name="answerTeam0.name" value="${answerChairMan?if_exists.name?if_exists}" style="width:100%"></td>
					<td><input type="text" maxlength="30"  name="answerTeam0.specialityAspect" value="${answerChairMan?if_exists.specialityAspect?if_exists}" style="width:100%"></td>
					<td><input type="text" maxlength="20"  name="answerTeam0.depart" value="${answerChairMan?if_exists.depart?if_exists}" style="width:100%"></td>
    			</tr>
    			<tr>
    				<td rowspan="5">成员</td>
    			</tr>
    			<#list answerTeamSet?if_exists?sort_by("name") as at>
				  <tr>
				    <input type="hidden" name="answerTeam${(at_index+1)}.id" value="${at.id}">
				    <td><input type="text" maxlength="20" name="answerTeam${at_index+1}.name" value="${at.name?if_exists}" style="width:100%"></td>
				    <td><input type="text" maxlength="30" name="answerTeam${at_index+1}.specialityAspect" value="${at.specialityAspect?if_exists}" style="width:100%"></td>
				    <td><input type="text" maxlength="20" name="answerTeam${at_index+1}.depart" value="${at.depart?if_exists}" style="width:100%"></td>  
				  </tr>
				  </#list>
				  <#if answerTeamSet?if_exists?size<3>
					  	<#list  answerTeamSet?if_exists?size..3 as i>
					  	<tr>
					    	<td><input type="text" maxlength="20"  name="answerTeam${i+1}.name" value="" style="width:100%">
					    	<td><input type="text" maxlength="30"  name="answerTeam${i+1}.specialityAspect" value="" style="width:100%">
					    	<td><input type="text" maxlength="20"  name="answerTeam${i+1}.depart" value="" style="width:100%">
					  	</tr>
					  </#list>
				 </#if>
    			<tr>
    				<td>预答辩时间</td>
    				<td><#if preAnswer?if_exists.answerTime?exists>${preAnswer.answerTime?string("yyyy-MM-dd")}</#if></td>
    				<td>预答辩地点</td>
    				<td>${preAnswer?if_exists.answerAddress?if_exists}</td>
    			</tr>
    			<tr>
    				<td colspan="4" id="f_advice">
    				   专家意见和建议:<br>
    				   <textarea name="preAnswer.advice" cols="50" rows="5" style="width:100%">${preAnswer.advice?if_exists}</textarea>
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
    				<td colspan="2"><p>预答辩小组负责人签字：</p>
    				</td>
    				<td colspan="2" align="right">年&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;日</td>
    			</tr>
    			<tr>
    				<td colspan="4" align="right">院系所(盖章)</td>
    			</tr>
  			</table>
  		</td>
  </tr>
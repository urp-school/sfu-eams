<table id="printDiv" align="center" cellpadding="0" cellspacing="0" border="1" width="95%" class="listTable">      
	<tr align="center" class="darkColumn">
	<#assign r=1/>
	<#if showStudentAttach>
   		<td rowspan="${r}">序号</td>
   		<td rowspan="${r}"><@msg.message key="entity.department"/></td>
   		<td rowspan="${r}"><@msg.message key="entity.speciality"/></td>
   		<td rowspan="${r}">方向</td>
   		<td rowspan="${r}">班级</td>
   	</#if>
       	<td rowspan="${r}"><@msg.message key="attr.stdNo"/></td>
       	<td rowspan="${r}"><@msg.message key="attr.personName"/></td>
       	<td rowspan="${r}"><@bean.message key="attr.cultivateScheme.sort"/></td>
       	<td rowspan="${r}"><@bean.message key="attr.courseNo"/></td>
       	<td rowspan="${r}"><@bean.message key="attr.courseName"/></td>
       	<td rowspan="${r}">学院</td>
       	<td rowspan="${r}"><@bean.message key="entity.speciality"/></td>
       	<td rowspan="${r}"><@bean.message key="entity.adminClass"/></td>
       	<td rowspan="${r}">未完成学分</td>
	<#if termsShow=="true"><td rowspan="${r}">学期</td></#if>
       	<td rowspan="${r}">备注</td>
	</tr>      
    <#assign index=1/>
    <#list result.resultList?if_exists as auditResult>
      	<#if auditResult.teachPlan?exists>
      		<#--开始绘制课程组-->
      		<#list auditResult.orderCourseGroupAuditResults as group>
      			<#if group.courseType.isCompulsory>
			        <#assign passCourseCount=group.passCourseCount/>
			      	<#assign isAllCoursePass = (passCourseCount==group.planCourseAuditResults?size) />
			      	<#if (show!="unpass")||!isAllCoursePass>
			      		<#if group.planCourseAuditResults?size!=0><#assign typeRowspan=group.planCourseAuditResults?size+1><#else><#assign typeRowspan=2></#if>
	      				<#assign parentCourseType></#assign>
			      		<#if group.parentCourseType?exists>
				      		<#if group.parentCourseType.id!=group.courseType.id>
				      			<#assign parentCourseType><@i18nName group.parentCourseType/>/</#assign>
				      		</#if>
			      		</#if>
	      				<#assign orderPlanCourseAuditResults = group.orderPlanCourseAuditResults />
	      				<#if (orderPlanCourseAuditResults?size>0) > 	      		
	    					<#--开始绘制课程-->
  							<#list orderPlanCourseAuditResults as planCourse>
  								<#if !planCourse.creditAuditInfo.isPass>
  									<tr>
  										<#if showStudentAttach>
  											<td rowspan="${r}">&nbsp;${index}<#assign index=index+1 /></td>
  											<td rowspan="${r}">&nbsp;<@i18nName auditResult.student.department?if_exists /></td>
									       	<td rowspan="${r}">&nbsp;<@i18nName auditResult.student.firstMajor?if_exists /></td>
									       	<td rowspan="${r}">&nbsp;<@i18nName auditResult.student.firstAspect?if_exists /></td>
									       	<td rowspan="${r}">&nbsp;<@i18nName auditResult.student.firstMajorClass?if_exists /></td>
       									</#if>
								       	<td rowspan="${r}">&nbsp;${auditResult.student.code}</td>
								       	<td rowspan="${r}">&nbsp;<@i18nName auditResult.student /></td>
								       	<td>&nbsp;<@i18nName group.courseType/></td>
							       		<td align="center">${planCourse.course.code}</td>
							       		<td align="left">&nbsp;<@i18nName planCourse.course/></td>
			       						<td align="center"><@i18nName (auditResult.student.department)?if_exists/></td>
			       						<td align="center"><@i18nName (auditResult.student.firstMajor)?if_exists/></td>
			       						<td align="center"><@i18nName (auditResult.student.firstMajorClass)?if_exists/></td>
							       		<td align="center">${planCourse.creditAuditInfo.required?if_exists}</td>
							       		<#if termsShow=="true">			       		
							       			<td align="center">${planCourse.terms?if_exists}</td>
							       		</#if>
			       						<#--开始绘制课程每学期上课情况-->
	       								<td align="center"><#list planCourse.substitionScores as substitionScore><@i18nName substitionScore.course /><#if substitionScore_has_next>/</#if></#list></td>
	       								<#--结束绘制课程每学期上课情况-->       
	      							</tr>
	      						</#if>
	      					</#list>
	      					<#if isAllCoursePass&&!group.creditAuditInfo.isPass>
						      	<script>
						      		$("${group.courseType.id}").rowSpan=${typeRowspan}+1;
						      	</script>
								<tr>
							        <td align="center">${group.creditAuditInfo.getCreditToComplete(showNegative)}<#--><#if group.creditAuditInfo.converted?exists&&group.creditAuditInfo.converted!=0>(${group.creditAuditInfo.completed?if_exists}+${group.creditAuditInfo.converted})<#else>${group.creditAuditInfo.completed?if_exists}</#if>/${group.creditAuditInfo.required?if_exists}--></td>
							        <#if termsShow=="true">			       		
						       			<td align="center"></td>
						       		</#if>
							      	<td align="center"></td>
						      	</tr>      	
			      			</#if>
	      				<#elseif !group.creditAuditInfo.isPass&&(group.creditAuditInfo.getCreditToComplete(showNegative)>0) >
		      				<tr>
						      	<#if showStudentAttach>
							      	<td rowspan="${r}">&nbsp;${index}<#assign index=index+1 /></td>
							      	<td rowspan="${r}">&nbsp;<@i18nName auditResult.student.department?if_exists /></td>
							       	<td rowspan="${r}">&nbsp;<@i18nName auditResult.student.firstMajor?if_exists /></td>
							       	<td rowspan="${r}">&nbsp;<@i18nName auditResult.student.firstAspect?if_exists /></td>
							       	<td rowspan="${r}">&nbsp;<@i18nName auditResult.student.firstMajorClass?if_exists /></td>
       							</#if>
						       	<td rowspan="${r}">&nbsp;${auditResult.student.code}</td>
						       	<td rowspan="${r}">&nbsp;<@i18nName auditResult.student /></td>
						        <td colspan="3" align="left">&nbsp;<@i18nName group.courseType/></td>
	       						<td align="center"><@i18nName (auditResult.student.department)?if_exists/></td>
	       						<td align="center"><@i18nName (auditResult.student.firstMajor)?if_exists/></td>
	       						<td align="center"><@i18nName (auditResult.student.firstMajorClass)?if_exists/></td>
	       						<td align="center">${group.creditAuditInfo.getCreditToComplete(showNegative)}</td>
						        <#if termsShow=="true">			       		
					       			<td align="center"></td>
					       		</#if>
						      	<td align="center"></td>
					      	</tr>
		      			</#if>
	      				<#if showAllGroup>
			  				<tr>     
							  	<#if showStudentAttach>
								  	<td rowspan="${r}">&nbsp;${index}<#assign index=index+1 /></td>
								  	<td rowspan="${r}">&nbsp;<@i18nName auditResult.student.department?if_exists /></td>
							       	<td rowspan="${r}">&nbsp;<@i18nName auditResult.student.firstMajor?if_exists /></td>
							       	<td rowspan="${r}">&nbsp;<@i18nName auditResult.student.firstAspect?if_exists /></td>
							       	<td rowspan="${r}">&nbsp;<@i18nName auditResult.student.firstMajorClass?if_exists /></td>
       							</#if>
						       	<td rowspan="${r}">&nbsp;${auditResult.student.code}</td>
						       	<td rowspan="${r}">&nbsp;<@i18nName auditResult.student /></td>
						        <td colspan="3" align="left">&nbsp;<@i18nName group.courseType/></td>
	       						<td align="center"><@i18nName (auditResult.student.department)?if_exists/></td>
	       						<td align="center"><@i18nName (auditResult.student.firstMajor)?if_exists/></td>
	       						<td align="center"><@i18nName (auditResult.student.firstMajorClass)?if_exists/></td>
						        <td align="center">${group.creditAuditInfo.getCreditToComplete(showNegative)}</td>
						        <#if termsShow=="true">			       		
					       			<td align="center"></td>
					       		</#if>
						      	<td align="center"></td>
					      	</tr>
		      			</#if>
		      		<#elseif (show=="unpass")&&!group.creditAuditInfo.isPass&&(group.creditAuditInfo.getCreditToComplete(showNegative)>0)>
      					<tr>     
		      				<#if showStudentAttach>
			      				<td rowspan="${r}">&nbsp;${index}<#assign index=index+1 /></td>
			      				<td rowspan="${r}">&nbsp;<@i18nName auditResult.student.department?if_exists /></td>
						       	<td rowspan="${r}">&nbsp;<@i18nName auditResult.student.firstMajor?if_exists /></td>
						       	<td rowspan="${r}">&nbsp;<@i18nName auditResult.student.firstAspect?if_exists /></td>
						       	<td rowspan="${r}">&nbsp;<@i18nName auditResult.student.firstMajorClass?if_exists /></td>
       						</#if>
					       	<td rowspan="${r}">&nbsp;${auditResult.student.code}</td>
					       	<td rowspan="${r}">&nbsp;<@i18nName auditResult.student /></td>
							<td colspan="3" align="left">&nbsp;<@i18nName group.courseType/></td>
       						<td align="center"><@i18nName (auditResult.student.department)?if_exists/></td>
       						<td align="center"><@i18nName (auditResult.student.firstMajor)?if_exists/></td>
       						<td align="center"><@i18nName (auditResult.student.firstMajorClass)?if_exists/></td>
						    <td align="center">${group.creditAuditInfo.getCreditToComplete(showNegative)}</td>
						    <#if termsShow=="true">			       		
				       			<td align="center"></td>
				       		</#if>
						    <td align="center"></td>
			    		</tr>
      				</#if>
      			<#else>
		      		<#assign typeRowspan=2>
		      		<#if (show!="unpass"||!group.creditAuditInfo.isPass)&&(group.creditAuditInfo.getCreditToComplete(showNegative)>0)>
			      		<tr>
				      		<#assign parentCourseType></#assign>
					      	<#if group.parentCourseType?exists>
								<#if group.parentCourseType.id!=group.courseType.id>
								<#assign parentCourseType><@i18nName group.parentCourseType/>/</#assign>
								</#if>
							</#if>
							<#if typeRowspan=2>
								<#if showStudentAttach>
									<td rowspan="${r}">&nbsp;${index}<#assign index=index+1 /></td>
							       	<td rowspan="${r}">&nbsp;<@i18nName auditResult.student.department?if_exists /></td>
							       	<td rowspan="${r}">&nbsp;<@i18nName auditResult.student.firstMajor?if_exists /></td>
							       	<td rowspan="${r}">&nbsp;<@i18nName auditResult.student.firstAspect?if_exists /></td>
							       	<td rowspan="${r}">&nbsp;<@i18nName auditResult.student.firstMajorClass?if_exists /></td>
       							</#if>
						      	<td rowspan="${r}">&nbsp;${auditResult.student.code}</td>
						       	<td rowspan="${r}">&nbsp;<@i18nName auditResult.student /></td>
								<td colspan="3" align="left">&nbsp;<a HREF="javascript:showGroup('${auditResult_index}_${group.courseType.id}');"><@i18nName group.courseType/></a></td>
	       						<td align="center"><@i18nName (auditResult.student.department)?if_exists/></td>
	       						<td align="center"><@i18nName (auditResult.student.firstMajor)?if_exists/></td>
	       						<td align="center"><@i18nName (auditResult.student.firstMajorClass)?if_exists/></td>
						        <td align="center">${group.creditAuditInfo.getCreditToComplete(showNegative)}</td>
     	 					<#else>
        						<td class="darkColumn" rowspan="${typeRowspan}" align="left">${parentCourseType}<@i18nName group.courseType/></td>
      						</#if>
				      		<#if termsShow=="true">
				       			<td align="center"></td>
				       		</#if>
      						<td align="center"></td>
      					</tr>
      					<#--结束绘制课程组小记-->      
      				</#if> 
      			</#if>     
      			<#--开始绘制全程总计--> 
      		</#list>
      		<#if auditResult.remark?exists&&auditResult.remark!="">
		      	<tr>
					<#assign major>
			       		<#if auditResult.majorType.id==1><#if auditResult.student.firstMajor?exists><@i18nName auditResult.student.firstMajor /><#else><@bean.message key="attr.null"/></#if></#if>
			       		<#if auditResult.majorType.id==2><#if auditResult.student.secondMajor?exists><@i18nName auditResult.student.secondMajor /><#else><@bean.message key="attr.null"/></#if></#if>
		       		</#assign>
					<#if !typeRowspan?exists||typeRowspan=2>
						<#if showStudentAttach>
							<td rowspan="${r}">&nbsp;${index}<#assign index=index+1 /></td>
					      	<td rowspan="${r}">&nbsp;<@i18nName auditResult.student.department?if_exists /></td>
					       	<td rowspan="${r}">&nbsp;<@i18nName auditResult.student.firstMajor?if_exists /></td>
					       	<td rowspan="${r}">&nbsp;<@i18nName auditResult.student.firstAspect?if_exists /></td>
					       	<td rowspan="${r}">&nbsp;<@i18nName auditResult.student.firstMajorClass?if_exists /></td>
       					</#if>
				       	<td rowspan="${r}">&nbsp;${auditResult.student.code}</td>
				       	<td rowspan="${r}">&nbsp;<@i18nName auditResult.student /></td>
						<td colspan="3" align="center">${major}<@msg.message key="entity.speciality"/></td>
   						<td align="center"><@i18nName (auditResult.student.department)?if_exists/></td>
   						<td align="center"><@i18nName (auditResult.student.firstMajor)?if_exists/></td>
   						<td align="center"><@i18nName (auditResult.student.firstMajorClass)?if_exists/></td>
				        <td align="center"></td>
		        		<#if termsShow=="true"><td align="center"></td></#if>
     	 			<#else>
        				<td class="darkColumn" rowspan="${typeRowspan}" align="center">${major}<@msg.message key="entity.speciality"/></td>
      				</#if>
      				<td align="center"><font color='red'><@getRemark auditResult.remark/></font></td>
      			</tr>
      		</#if>
      	<#else>
      	<#--开始绘制无培养计划学生-->      		
      		<tr>
				<#assign major>
		       		<#if auditResult.majorType.id==1><#if auditResult.student.firstMajor?exists><@i18nName auditResult.student.firstMajor /><#else><@bean.message key="attr.null"/></#if></#if>
		       		<#if auditResult.majorType.id==2><#if auditResult.student.secondMajor?exists><@i18nName auditResult.student.secondMajor /><#else><@bean.message key="attr.null"/></#if></#if>
	       		</#assign>
				<#if !typeRowspan?exists||typeRowspan=2>
					<#if showStudentAttach>
						<td rowspan="${r}">&nbsp;${index}<#assign index=index+1 /></td>
				      	<td rowspan="${r}">&nbsp;<@i18nName auditResult.student.department?if_exists /></td>
				       	<td rowspan="${r}">&nbsp;<@i18nName auditResult.student.firstMajor?if_exists /></td>
				       	<td rowspan="${r}">&nbsp;<@i18nName auditResult.student.firstAspect?if_exists /></td>
				       	<td rowspan="${r}">&nbsp;<@i18nName auditResult.student.firstMajorClass?if_exists /></td>
       				</#if>
			       	<td rowspan="${r}">&nbsp;${auditResult.student.code}</td>
			       	<td rowspan="${r}">&nbsp;<@i18nName auditResult.student /></td>
					<td colspan="3" align="center">${major}<@msg.message key="entity.speciality"/></td>
   					<td align="center"><@i18nName (auditResult.student.department)?if_exists/></td>
   					<td align="center"><@i18nName (auditResult.student.firstMajor)?if_exists/></td>
   					<td align="center"><@i18nName (auditResult.student.firstMajorClass)?if_exists/></td>
		        	<td align="center"></td>
		        	<#if termsShow=="true"><td align="center"></td></#if>
	     	 	<#else>
	        		<td class="darkColumn" rowspan="${typeRowspan}" align="center">${major}<@msg.message key="entity.speciality"/></td>
	      		</#if>
      			<td align="center"><font color='red'>培养计划缺失</font></td>
      		</tr>      
      		<#--结束绘制课程组小记-->      
      	</#if> 
     	<#--结束绘制全程总计-->      
     </#list>
</table>
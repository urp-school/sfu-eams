<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/course/TaskActivity.js"></script> 
<style type="text/css">
	.info{
	    border-collapse: collapse;
	    border:solid;
	    border-width:1px;
	    border-color:#006CB2;
	    text-align: left;
	    font-style: normal; 
	    font-size: 10pt; 
	}
	table.info td{
	    border:solid;
	    border-width:0px;
	    border-right-width:1;
	    border-bottom-width:1;
	    border-color:#006CB2;
	    color: #000000; 
	    text-decoration: none; 
	    letter-spacing:0;
	}
	.info .title{
	   text-align: right;
	   background-color: #EBEBEB;
	}
	.info .content{
	   text-align: left;
	   background-color: white
	}
    .info tr {
       
    }
</style>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
	<table id="bar"></table>
	<table width="100%">
	   <tr>
	       <td align="right"  valign="top">
	           <#list students as student>
	               <table width="90%">
	                    <tr>
							<td style="text-align:center; font-size:18px; font-weight:bold"><@i18nName systemConfig.school/><@i18nName (student.type)?if_exists/>学籍表</td>
						</tr>
						<tr>
							<td>
								<table class="info" style="width:100%; font-size:14; TABLE-LAYOUT:fixed;  WORD-BREAK:break-all; text-align:center;">
									<tr>
										<td width="10%">姓　　名</td>
										<td width="10%">${student.name?default("")}</td>
										<td width="10%">性　　别</td>
										<td width="10%">${student.basicInfo.gender.name?default("")}</td>
										<td width="10%">身份证号</td>
										<td colspan="3">${student.basicInfo.idCard?default("")}</td>
										<td rowspan="6">照<br><br>片</td>
									</tr>
				                    <tr>
				                        <td>学　　号</td>
				                        <td>${student.code?default("")}</td>
				                        <td>专　　业</td>
				                        <td colspan="2"><@i18nName (student.firstMajor)?if_exists/></td>
				                        <td width="10%">研究方向</td>
				                        <td colspan="2"><@i18nName (student.firstAspect)?if_exists/></td>
				                    </tr>
				                    <tr>
				                        <td>籍　　贯</td>
				                        <td>${(student.basicInfo.ancestralAddress)?default('')}</td>
				                        <td>民　　族</td>
				                        <td>${(student.basicInfo.nation.name)?default('')}</td>
				                        <td>政治面貌</td>
				                        <td>${(student.basicInfo.politicVisage.name)?default('')}</td>
				                        <td width="10%">出生日期</td>
				                        <td>${(student.basicInfo.birthday?string("yyyy-MM-dd"))?default('')}</td>
				                    </tr>
				                    <tr>
				                        <td>最后学历</td>
				                        <td>${(student.studentStatusInfo.educationBeforEnroll.name)?default("")}</td>
				                        <td colspan="6" align="left">${(student.studentStatusInfo.educationBeforEnrollDate)?default("　　　　")}年毕业于${"　"+(student.studentStatusInfo.graduateSchool)?default("　　　　　　　　大学（学院）")}${(student.studentStatusInfo.educationBeforEnrollSpeciality)?default("　　　　　　　　　 ")}专业</td>
				                    </tr>
				                    <tr>
				                        <td>最后学位</td>
				                        <td></td>
				                        <td colspan="6" align="left"><#if student.studentStatusInfo.degreeBeforEnrollDate?exists>${"　　" + (student.studentStatusInfo.degreeBeforEnrollDate)?string("yyyy年MM月")}<#else>${(student.studentStatusInfo.degreeBeforEnrollDate)?default("　　　　年　月")}</#if>获${(student.studentStatusInfo.degreeBeforEnrollOrganization)?default("　　　　　　　　　　　　")}学位</td>
				                    </tr>
				                    <tr>
				                        <td colspan="2">入学前学校或工作单位</td>
				                        <td colspan="6" align="left">${(student.basicInfo.workPlace)?default("")}</td>
				                    </tr>
				                    <tr>
				                        <td>所在年级</td>
				                        <td>${(student.studentStatusInfo.enrollDate?string("yyyy-MM"))?if_exists}</td>
				                        <td>学　　制</td>
				                        <td>${(student.schoolingLength)?if_exists}</td>
				                        <td>培养方式</td>
				                        <td colspan="4" align="left"><@i18nName (student.studentStatusInfo.educationMode)?if_exists/></td>
				                    </tr>
				                    <tr>
				                        <td rowspan="3" colspan="2">家庭通讯地址</td>
				                        <td rowspan="3" colspan="4" align="left" valign="top">${"　" + (student.basicInfo.homeAddress)?if_exists}</td>
				                        <td>邮政编码</td>
				                        <td colspan="2">${(student.basicInfo.postCode)?if_exists}</td>
				                    </tr>
				                    <tr>
				                        <td>固定电话</td>
				                        <td colspan="2">${(student.basicInfo.phone)?if_exists}</td>
				                    </tr>
				                    <tr>
				                        <td>移动电话</td>
				                        <td colspan="2">${(student.basicInfo.mobile)?if_exists}</td>
				                    </tr>
				                    <tr>
				                        <td rowspan="2" colspan="2">工作单位地址</td>
				                        <td rowspan="2" colspan="4" align="left" valign="top">${(student.basicInfo.workAddress)?if_exists}</td>
				                        <td>邮政编码</td>
				                        <td colspan="2">${(student.basicInfo.workPlacePostCode)?if_exists}</td>
				                    </tr>
				                    <tr>
				                        <td>联系电话</td>
				                        <td colspan="2">${(student.basicInfo.workPhone)?if_exists}</td>
				                    </tr>
				                    <#if stdAlterationMap[student.id?string]?exists>
				                        <#if stdAlterationMap[student.id?string]?size <= 3>
				                            <#assign colspan=3/>
				                        <#else>
				                            <#assign colspan=stdAlterationMap[student.id?string]?size/>
				                        </#if>
				                    </#if>
				                    <tr>
				                        <td rowspan="${colspan + 1}" colspan="2">学　　籍<br>变　动　记　录</td>
				                        <td colspan="3">时　　间</td>
				                        <td colspan="4">原　　　　因</td>
				                    </tr>
                                    <#list stdAlterationMap[student.id?string] as stdAlter>
	                                    <tr>
	                                        <td colspan="3">${(stdAlter.alterDate?string("yyyy-MM-dd"))?default('　')}</td>
	                                        <td colspan="4">${(stdAlter.alterReason.name)?default('　')}</td>
	                                    </tr>
                                    </#list>
                                    <#if colspan = 3>
                                        <#if colspan != stdAlterationMap[student.id?string]?size>
	                                        <#list 1..(colspan-stdAlterationMap[student.id?string]?size) as i>
			                                    <tr>
			                                        <td colspan="3">　</td>
			                                        <td colspan="4">　</td>
			                                    </tr>
	                                        </#list>
                                        </#if>
                                    </#if>
				                    <tr>
				                        <td colspan="2">毕、结业日期</td>
				                        <td colspan="7" style="text-align:left">　${(student.graduateOn?string("yyyy-MM"))?if_exists}</td>
				                    </tr>
								</table>
							</td>
						</tr>
					</table>
					<br><br><br><br>
					<#if student_has_next><div style='PAGE-BREAK-AFTER: always'></div></#if>					
				</#list>
		   </td>
		</tr>
	</table>
	<script>
	   var bar = new ToolBar("bar", "学生学籍表打印", null, true, true);
	   bar.addPrint("<@msg.message key="action.print"/>");
	   //bar.addBack("<@msg.message key="action.back"/>");
	   bar.addClose("<@msg.message key="action.close"/>");
	</script>
</BODY>
<#include "/templates/foot.ftl"/>
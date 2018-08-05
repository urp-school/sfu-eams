<#include "/templates/head.ftl"/>
<body>
<#assign show=RequestParameters['show']?default('') />
<#assign termsShow=RequestParameters['termsShow']?default('') />
<#assign showAllGroup=(RequestParameters['showAllGroup']?default('false')=="true") />
<#assign showGroupAll=(RequestParameters['showGroupAll']?default('true')=="true") />
  <table id="bar"></table>
  <#list result.resultList?if_exists as auditResult>
<#assign firstMajorName><@i18nName auditResult.student.firstMajor?if_exists/></#assign>
<#assign secondMajorName><@i18nName auditResult.student.secondMajor?if_exists/></#assign>
  <table cellpadding="0" cellspacing="0" border="0" width="100%" align="center">
  <tr><td><br></td></tr>
   <tr>
    <td align="center" class="contentTableTitleTextStyle" bgcolor="#ffffff">
     <B><#if (result.resultList?size >1) ><#if auditResult.majorType.id==1><@bean.message key="plan.finishSituationParam1" arg0=firstMajorName?default("")/><#elseif auditResult.majorType.id==2><@bean.message key="plan.finishSituationParam2" arg0=secondMajorName?default("")/></#if><#else><@msg.message key="plan.finishSituation"/></#if></B>
    </td>
   </tr>
   <#if RequestParameters['showStudent']?default('true')=="true">
   <tr>
    <td align="center" class="contentTableTitleTextStyle">
      <table align="center" cellpadding="0" cellspacing="0" border="1" width="95%" class="listTable">
        <tr>
          <td align="center" class="grayStyle" width="20%"><@msg.message key="attr.stdNo"/></td>
          <td class="brightStyle" width="30%">&nbsp;${auditResult.student.code}</td>
          <td align="center" class="grayStyle" width="20%"><@msg.message key="attr.personName"/></td>
          <td class="brightStyle" width="30%">&nbsp;<@i18nName auditResult.student /></td>
        </tr>
        <tr>
          <td align="center" class="grayStyle"><#if auditResult.majorType.id==2><@msg.message key="std.doubleSpecialty"/><#else><@msg.message key="entity.speciality"/></#if></td>
          <td class="brightStyle">&nbsp;<#if auditResult.majorType.id==1><@i18nName auditResult.student.firstMajor?if_exists /><#elseif auditResult.majorType.id==2><@i18nName auditResult.student.secondMajor?if_exists /></#if></td>
          <td align="center" class="grayStyle"><#if auditResult.majorType.id==2><@msg.message key="common.detailSituation"/><#else><@msg.message key="entity.specialityAspect"/></#if></td>
          <td class="brightStyle">&nbsp;<#if auditResult.majorType.id==1><@i18nName auditResult.student.firstAspect?if_exists /><#elseif auditResult.majorType.id==2><@i18nName auditResult.student.secondAspect?if_exists /></#if></td>
        </tr>
        <tr>
          <td align="center" class="grayStyle"><@msg.message key="entity.studentType"/></td>
          <td class="brightStyle">&nbsp;<@i18nName auditResult.student.type?if_exists /></td>
          <td align="center" class="grayStyle"><@msg.message key="common.adminClass"/></td>
          <td class="brightStyle">&nbsp;<#list auditResult.student.adminClasses?if_exists as adminClass><@i18nName adminClass/>(${adminClass.code})<#if adminClass_has_next><br>&nbsp;</#if></#list></td>
        </tr>
      </table>
    </td>
   </tr>
   </#if>
   <tr><td><br></td></tr>
   <tr><td>
    <table align="center" cellpadding="0" cellspacing="0" border="1" width="95%" class="listTable">
      <tr align="center" class="darkColumn">
       <#if termsShow=="true"><#assign r=2/><#else><#assign r=1/></#if>
       <td rowspan="${r}" width="15%"><@bean.message key="attr.cultivateScheme.sort"/></td>
       <td rowspan="${r}"><@msg.message key="grade.curriculumsRequirement"/></td>
       <td rowspan="${r}"><@msg.message key="grade.finalCredit"/>：</td>
       <td rowspan="${r}"><@msg.message key="grade.isFinish"/></td>
      </tr> 
      <#list auditResult.orderCourseGroupAuditResults as group>
      <#assign parentCourseType = ""/>
            <#if group.parentCourseType?exists>
              <#if group.parentCourseType.id!=group.courseType.id>
              <#assign parentCourseType><@i18nName group.parentCourseType/>/</#assign>
              </#if>
            </#if>
      <#assign orderPlanCourseAuditResults = group.orderPlanCourseAuditResults />
      <tr>
        <td>${parentCourseType}<@i18nName group.courseType/></td>
      <td align="center" >${group.creditAuditInfo.required?if_exists}</td>
          <td align="center" ><I><@msg.message key="grade.finalCredit"/>：</I>${group.creditAuditInfo.completed?if_exists}<#if group.creditAuditInfo.converted?exists&&group.creditAuditInfo.converted!=0>&nbsp;<I><@msg.message key="grade.conversionCredit"/>：</I>${group.creditAuditInfo.converted}</#if></td>        
          <td align="center"><#if (group.creditAuditInfo.isPass)?exists><#if group.creditAuditInfo.isPass><@msg.message key="yes"/><#else><font color='red'><@msg.message key="no"/></font></#if></#if></td>
      </tr>   
        
    </#list>
    </table>
    </td>
   </tr>
   <tr><td><br></td></tr>
   <tr>    
    <td>
     <table align="center" cellpadding="0" cellspacing="0" border="1" width="95%" class="listTable">
      <tr align="center" class="darkColumn">
       <#if termsShow=="true"><#assign r=2/><#else><#assign r=1/></#if>
       <td rowspan="${r}" width="3%"><@bean.message key="attr.cultivateScheme.sort"/></td>
       <td rowspan="${r}"><@bean.message key="attr.courseNo"/></td>
       <td rowspan="${r}"><@bean.message key="attr.courseName"/></td>
       <td rowspan="${r}"><@msg.message key="grade.score"/></td>
       <td rowspan="${r}"><@msg.message key="attr.gradePoint"/></td>
       <td rowspan="${r}"><@msg.message key="grade.restudyCount"/></td>
       <td rowspan="${r}"><@msg.message key="attr.credit"/></td>
       <td rowspan="${r}"><@msg.message key="grade.isFinish"/></td>
       <#if termsShow=="true"><td colspan="${auditResult.termsCount?default(1)}" width="30%"><@msg.message key="grade.asTermAndCreditToDeployed"/></td>
       <#else><td rowspan="${r}"><@msg.message key="attr.term"/></td></#if>
       <td rowspan="${r}"><@msg.message key="attr.remark"/></td>
       <td rowspan="${r}" width="7%">调整分类</td>
      </tr>
      <#if termsShow=="true">
      <tr class="darkColumn">
       <#list 1..auditResult.termsCount?default(1) as i>
        <td width="2%"><p align="center">${i}</p></td>
       </#list>
      </tr>
      </#if>
      
      <#--开始绘制课程组-->
      <#list auditResult.orderCourseGroupAuditResults as group>
      <#if group.courseType.isCompulsory||showGroupAll>
        <#assign passCourseCount=group.passCourseCount/>
        <#assign isAllCoursePass = (passCourseCount==group.planCourseAuditResults?size) />
        <#if (show!="unpass")||!isAllCoursePass>
          <#if group.planCourseAuditResults?size!=0><#assign typeRowspan=group.planCourseAuditResults?size+1><#else><#assign typeRowspan=2></#if>
      
            <#assign parentCourseType> </#assign>
            <#if group.parentCourseType?exists>
              <#if group.parentCourseType.id!=group.courseType.id>
              <#assign parentCourseType><@i18nName group.parentCourseType/>/</#assign>
              </#if>
            </#if>
            <#assign orderPlanCourseAuditResults = group.orderPlanCourseAuditResults />
            <#if (orderPlanCourseAuditResults?size>0)>
              <tr>
              <td align="center" rowspan="<#if show=="unpass">${group.planCourseAuditResults?size-passCourseCount+1}<#else>${typeRowspan}</#if>" align="left" id="${group.courseType.id}">${parentCourseType}<@i18nName group.courseType/></td>
              </tr>
        <#--开始绘制课程-->
              <#list orderPlanCourseAuditResults as planCourse>
              <#if show!="unpass"||!planCourse.creditAuditInfo.isPass>
              <tr>
                <td align="center">${planCourse.course.code}</td>
                <td align="left">&nbsp;<@i18nName planCourse.course/></td>
                <td align="center"><#list planCourse.getScoresOrderByScore(null) as score><#if (score.scoreDisplay)?exists>${score.scoreDisplay}<#else><@msg.message key="grade.noScore"/></#if><#if score_has_next>/</#if></#list></td>
                <td align="center"><#list planCourse.scores as score><#if score.GP?exists><#if (score.GP)?exists>${((score.GP*100)?int/100)?string('#0.00')}<#else><@msg.message key="grade.noScore"/></#if></#if><#if score_has_next>/</#if></#list></td>
                <td align="center">${planCourse.reStudyCount?if_exists}</td>
                <td align="center">${planCourse.creditAuditInfo.required?if_exists}</td>
                <td align="center"><#if (planCourse.creditAuditInfo.isPass)?exists><#if planCourse.creditAuditInfo.isPass><@msg.message key="yes"/><#else><font color='red'><@msg.message key="no"/></font></#if></#if></td>
             <#--开始绘制课程每学期上课情况-->
                <#if termsShow=="true">
                <#list 1..auditResult.termsCount?default(1) as i>
                  <td>
                  <p align="center">
                   <#if planCourse.terms?exists && (","+planCourse.terms+",")?contains(","+i+",")>*<#else>&nbsp;</#if>
                  </p>
                  </td>
                </#list>
                <#else>
                  <td align="center">${planCourse.terms?if_exists}</td>
                </#if>
                <td align="center"><#list planCourse.substitionScores as substitionScore>${substitionScore.course.name}(${substitionScore.course.code})<#if substitionScore_has_next>/</#if></#list></td>
                <td rowspan="${r}" align="center"><a href="#" onclick="batchEdit('${planCourse.course.id}','${auditResult.student.id}')">调整</a></td>
         <#--结束绘制课程每学期上课情况-->
              </tr>
              </#if>
              </#list>
              <script>
                $("${group.courseType.id}").rowSpan=${typeRowspan}+1;
              </script>
          <tr bgcolor="#C0C0C0">
            <td align="center" colspan="2"><@msg.message key="grade.curriculumsRequirement"/></td>
                <td align="center" colspan="3"><I><@msg.message key="grade.finalCredit"/>：</I>${group.creditAuditInfo.completed?if_exists}<#if group.creditAuditInfo.converted?exists&&group.creditAuditInfo.converted!=0>&nbsp;<I><@msg.message key="grade.conversionCredit"/>：</I>${group.creditAuditInfo.converted}</#if></td>        
                <td align="center">${group.creditAuditInfo.required?if_exists}</td>
                <td align="center"><#if (group.creditAuditInfo.isPass)?exists><#if group.creditAuditInfo.isPass><@msg.message key="yes"/><#else><font color='red'><@msg.message key="no"/></font></#if></#if></td>
                <td align="center" <#if termsShow=="true">colspan="${auditResult.termsCount?default(1)}"</#if>></td>
                <td align="center"></td>
                <td align="center"></td>
              </tr>
            <#elseif !group.creditAuditInfo.isPass>
            <tr>     
              <td colspan="3" align="left">&nbsp;<@i18nName group.courseType/></td>
              <td align="center" colspan="3"><I><@msg.message key="grade.finalCredit"/>：</I>${group.creditAuditInfo.completed?if_exists}<#if group.creditAuditInfo.converted?exists&&group.creditAuditInfo.converted!=0>&nbsp;<I><@msg.message key="grade.conversionCredit"/>：</I>${group.creditAuditInfo.converted}</#if></td>        
              <td align="center">${group.creditAuditInfo.required?if_exists}</td>
              <td align="center"><#if (group.creditAuditInfo.isPass)?exists><#if group.creditAuditInfo.isPass><@msg.message key="yes"/><#else><font color='red'><@msg.message key="no"/></font></#if></#if></td>
              <td align="center" <#if termsShow=="true">colspan="${auditResult.termsCount?default(1)}"</#if>></td>
              <td align="center"></td>
            </tr>
            </#if>
        
            <#if showAllGroup>
          <tr>
              <td colspan="3" align="left">&nbsp;<@i18nName group.courseType/></td>
              <td colspan="3" align="center" colspan="3"><I><@msg.message key="grade.finalCredit"/>：</I>${group.creditAuditInfo.completed?if_exists}<#if group.creditAuditInfo.converted?exists&&group.creditAuditInfo.converted!=0>&nbsp;<I><@msg.message key="grade.conversionCredit"/>：</I>${group.creditAuditInfo.converted}</#if></td>        
              <td align="center">${group.creditAuditInfo.required?if_exists}</td>
              <td align="center"><#if (group.creditAuditInfo.isPass)?exists><#if group.creditAuditInfo.isPass><@msg.message key="yes"/><#else><font color='red'><@msg.message key="no"/></font></#if></#if></td>
              <td align="center" <#if termsShow=="true">colspan="${auditResult.termsCount?default(1)}"</#if>></td>
              <td align="center"></td>
            </tr>
            </#if>
            
            <#elseif (show=="unpass")&&!group.creditAuditInfo.isPass>
              <tr>
            <td colspan="3" align="left">&nbsp;<@i18nName group.courseType/></td>
              <td colspan="3" align="center" colspan="3"><I><@msg.message key="grade.finalCredit"/>：</I>${group.creditAuditInfo.completed?if_exists}<#if group.creditAuditInfo.converted?exists&&group.creditAuditInfo.converted!=0>&nbsp;<I><@msg.message key="grade.conversionCredit"/>：</I>${group.creditAuditInfo.converted}</#if></td>        
              <td align="center">${group.creditAuditInfo.required?if_exists}</td>
                <td align="center"><#if (group.creditAuditInfo.isPass)?exists><#if group.creditAuditInfo.isPass><@msg.message key="yes"/><#else><font color='red'><@msg.message key="no"/></font></#if></#if></td>
              <td align="center" <#if termsShow=="true">colspan="${auditResult.termsCount?default(1)}"</#if>></td>
              <td align="center"></td>
            </tr>
            </#if>
      
          <#else>
          <#assign typeRowspan=2>
          <#if show!="unpass"||!group.creditAuditInfo.isPass>
          <tr>
          <#assign parentCourseType> </#assign>
          <#if group.parentCourseType?exists>
        <#if group.parentCourseType.id!=group.courseType.id>
        <#assign parentCourseType><@i18nName group.parentCourseType/>/</#assign>
        </#if>
      </#if>
      <#if typeRowspan=2>
      
        <td colspan="3" align="left">&nbsp;<a HREF="javascript:showGroup('${auditResult_index}_${group.courseType.id}','<@i18nName group.courseType/>');">${parentCourseType}<@i18nName group.courseType/></a></td>
            <td align="center" colspan="3"><I><@msg.message key="grade.finalCredit"/>：</I>${group.creditAuditInfo.completed?if_exists}<#if group.creditAuditInfo.converted?exists&&group.creditAuditInfo.converted!=0>&nbsp;<I><@msg.message key="grade.conversionCredit"/>：</I>${group.creditAuditInfo.converted}</#if></td>
            <td align="center">${group.creditAuditInfo.required?if_exists}</td>
            <td align="center"><#if (group.creditAuditInfo.isPass)?exists><#if group.creditAuditInfo.isPass><@msg.message key="yes"/><#else><font color='red'><@msg.message key="no"/></font></#if></#if></td>
            <#if termsShow=="true">
            <#if group.creditPerTerms?length==0>
              <td colspan="${auditResult.termsCount?default(1)}"></td>
            <#else>
              <#assign i=1>
              <#list group.creditPerTerms[1..group.creditPerTerms?length-2]?split(",") as credit>
              <td>
                <p align="center"><#if credit!="0">${credit}<#else>&nbsp;</#if></p>
              </td>
              </#list>
            </#if>
            <#else>
              <td></td>
            </#if>
        <#else>
          <td class="darkColumn" rowspan="${typeRowspan}" align="left">${parentCourseType}<@i18nName group.courseType/></td>
          </#if>
          <td align="center"></td>
          <td align="center"></td>
          </tr>
      <#--结束绘制课程组小记-->
        </#if>
        </#if>
      <#--开始绘制全程总计--> 
      </#list>
      <tr>
       <td align="center" colspan="3"><@bean.message key="attr.cultivateScheme.allTotle"/></td>
       <td align="center" colspan="3">${auditResult.creditAuditInfo.completed?if_exists}</td>
       <td align="center">${auditResult.creditAuditInfo.required?if_exists}</td>
      <td align="center"><#if (auditResult.isPass)?exists><#if auditResult.isPass><@msg.message key="yes"/><#else><font color='red'><@msg.message key="no"/></font></#if></#if></td>
       <td align="center" <#if termsShow=="true">colspan="${auditResult.termsCount?default(1)}"</#if>></td>
       <td align="center"></td>
       <td align="center"></td>
       
      </tr>
      <#--结束绘制全程总计-->
      
      <#--开始绘制备注-->
            
      <tr class="darkColumn">
       <td align="center" colspan="2"><@msg.message key="attr.remark"/></td>
       <td align="center" <#if termsShow=="true">colspan="${9+auditResult.termsCount?default(1)}"<#else>colspan="${9}"</#if>><#if !auditResult.teachPlan?exists><font color='red'>培养计划缺失</font></#if></td>
      </tr>
      
      <#--结束绘制备注-->
     </table>
    </td>
   </tr>
  </table>
  </#list>
<#list result.resultList?if_exists as auditResult>
  <#list auditResult.orderCourseGroupAuditResults as group>
  <#if !group.courseType.isCompulsory>
    <div id="${auditResult_index}_${group.courseType.id}" style="visibility: hidden; display:none;">
    <table align="center" cellpadding="0" cellspacing="0" border="1" width="95%" class="listTable">
    <tr align="center" class="darkColumn">
          <td><@bean.message key="attr.courseNo"/></td>
          <td><@bean.message key="attr.courseName"/></td>
          <td><@msg.message key="grade.score"/></td>
          <td><@msg.message key="grade.points"/></td>
          <td><@msg.message key="attr.credit"/></td>
          <td><@msg.message key="grade.isFinish"/></td>
          <td><@msg.message key="attr.remark"/></td>
          <td rowspan="${r}" width="7%">调整分类</td>
        </tr>
    <#list group.orderPlanCourseAuditResults as planCourse>
    <tr>
      <td align="center">${planCourse.course.code}</td>
      <td align="left">&nbsp;<@i18nName planCourse.course/></td>
          <td align="center"><#list planCourse.getScoresOrderByScore(null) as score><#if (score.score)?exists>${score.score}<#else><@msg.message key="grade.noScore"/></#if><#if score_has_next>/</#if></#list>&nbsp;</td>
          <td align="center"><#list planCourse.scores as score><#if (score.GP)?exists>${((score.GP*100)?int/100)?string('#0.00')}<#else><@msg.message key="grade.noScore"/></#if><#if score_has_next>/</#if></#list>&nbsp;</td>
          <td align="center">${planCourse.creditAuditInfo.required?if_exists}</td>
        <td align="center"><#if (planCourse.creditAuditInfo.isPass)?exists><#if planCourse.creditAuditInfo.isPass><@msg.message key="yes"/><#else><font color='red'><@msg.message key="no"/></font></#if></#if></td>
          <td align="center"><#list planCourse.substitionScores as substitionScore><@i18nName substitionScore.course />(${substitionScore.course.code})<#if substitionScore_has_next>/</#if></#list>&nbsp;</td>     
        <td rowspan="${r}" width="7%"><a href="#" onclick="batchEdit('${planCourse.course.id}','${auditResult.student.id}')">调整</a></td>
        </tr>
        </#list>
        </table>
  </div>
  </#if>
  </#list>
</#list>
<form name="gradeListForm" method="post" action="studentAuditOperation.do?method=changeCourseType" onsubmit="return false;">
  </form>
<style>
/*数据列表的样式单*/
.contentTableTitleTextStyle{
   color: #1f3d83; 
     font-style: normal; 
     font-size: 15pt; 
     line-height: 16pt; 
     text-decoration: none; 
     font-weight: bold; 
     letter-spacing:0
}
.listTable {
  border-collapse: collapse;
    border:solid;
  border-width:1px;
    border-color:#006CB2;
    vertical-align: middle;
    font-style: normal; 
  font-size: 10pt; 
}
table.listTable td{
  border:solid;
  border-width:0px;
  border-right-width:1;
  border-bottom-width:1;
  border-color:#006CB2;
}
</style>
  <script>
  var bar = new ToolBar("bar", "培养计划完成情况", null, true, true);
  bar.setMessage('<@getMessage/>');
  bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
  
  var form=document.gradeListForm;
  function batchEdit(courseId,studentId){
      form.action="studentAuditOperation.do?method=changeCourseType"
      form.target="_blank";
      form.action=form.action + "&courseId=" + courseId + "&studentId=" + studentId;
      form.submit();
    }
  function showGroup(divId,typeName){
    var oNewWindow  = window.open('about:blank');
    var titleString ="<table align='center' cellpadding='0' cellspacing='0' border='0' width='95%'><tr align='center'><td colspan='3' class='contentTableTitleTextStyle' bgcolor='#ffffff'><B><@msg.message key="plan.finishSituationOfpersonalPlan"/></B><td></tr><tr><td colspan='3'>&nbsp;</td></tr><tr><td>&nbsp;<@msg.message key="attr.stdNo"/>：<#list result.resultList?if_exists as auditResult><#if auditResult_index==0>${auditResult.student.code}</#if></#list></td><td>&nbsp;<@msg.message key="attr.personName"/>：<#list result.resultList?if_exists as auditResult><#if auditResult_index==0><@i18nName auditResult.student/></#if></#list></td><td>&nbsp;<@msg.message key="entity.courseType"/>："+typeName+"</td></tr></table>";
    oNewWindow.document.write(titleString+document.getElementById('styleDiv').innerHTML+document.getElementById(divId).innerHTML);
    oNewWindow.document.title="<@msg.message key="common.detailSituation"/>";
  }
  </script>
</body>
<#include "/templates/foot.ftl"/>
<#include "/templates/head.ftl"/>
<table id="myBar" width="100%" ></table>
<#assign info=std.degreeInfo?if_exists/>
<table class="infoTable" width="100%">
  	<tr>
     	<td class="title" width="28%"><@msg.message key="std.degreeInfo.studyMode"/>：</td>
     	<td><@i18nName (info.studyType)?if_exists/></td>
     	<td width="28%" class="title"><@msg.message key="std.degreeInfo.corporationKind"/>：</td>
     	<td width="25%"><@i18nName (info.corporationKind)?if_exists/></td>
   </tr>
   <tr>
     	<td class="title"><@msg.message key="std.degreeInfo.trainingUnits"/>：</td>
     	<td><@i18nName (info.school)?if_exists/></td>
     	<td class="title"><@msg.message key="std.degreeInfo.patentNum"/>：</td>
     	<td>${(info.patentNum)?if_exists}</td>
   </tr>
   <tr>
     	<td class="title"><@msg.message key="std.degreeInfo.graduateOn"/>：</td>
     	<td>${(info.graduateOn)?if_exists}</td>
     	<td class="title"><@msg.message key="std.degreeInfo.certificateNo"/>：</td>
     	<td>${(info.certificateNo)?if_exists}</td>
   </tr>
   <#if info.undergraduate?exists>
   <#assign undergraduate=info.undergraduate?if_exists>
   <tr>
     <td class="title"><@msg.message key="std.degreeInfo.undergraduate.graduationSchool"/>：</td>
     <td><@i18nName (undergraduate.school)?if_exists/></td>
     <td class="title"><@msg.message key="std.degreeInfo.undergraduate.graduationOn"/>：</td>
     <td>${undergraduate.graduateOn?default("")}</td>
   </tr>
   <tr>
     <td class="title"><@msg.message key="std.degreeInfo.undergraduate.graduationSpeciality"/>：</td>
     <td>${(undergraduate.specialityName)?default("")}</td>
     <td class="title"><@msg.message key="std.degreeInfo.undergraduate.specialityCategory"/>：</td>
     <td><@i18nName (undergraduate.subjectCategory)?if_exists/></td>
   </tr>
   </#if>
   
   <#if info.master?exists>
   <#assign master=info.master?if_exists>
   <tr>
     <td class="title"><@msg.message key="std.degreeInfo.master.graduationSchool"/>：</td>
     <td><@i18nName (master.school)?if_exists/></td>
     <td class="title"><@msg.message key="std.degreeInfo.master.graduationOn"/>：</td>
     <td>${(master.graduateOn)?if_exists}</td>
   </tr>
   <tr>
     <td class="title"><@msg.message key="std.degreeInfo.master.graduationSpeciality"/>：</td>
     <td>${(master.specialityName)?default("")}</td>
     <td class="title"><@msg.message key="std.degreeInfo.master.specialityCategory"/>：</td>
     <td><@i18nName (master.subjectCategory)?if_exists/></td>
   </tr>
   <tr>
     <td class="title"><@msg.message key="std.degreeInfo.master.specialityCode"/>：</td>
     <td>${(master.specialityCode)?default("")}</td>
     <td class="title"><@msg.message key="std.degreeInfo.master.certificateNo"/>：</td>
     <td>${(master.certificateNo)?if_exists}</td>
   </tr>
   </#if>
   
   <#if info.equivalent?exists>
   <#assign equivalent=info.equivalent?if_exists>
   <tr>
     <td class="title"><@msg.message key="std.degreeInfo.equivalent.applyNo"/>：</td>
     <td>${(equivalent.applyNo)?if_exists}</td>
     <td class="title"><@msg.message key="std.degreeInfo.equivalent.applyOn"/>：</td>
     <td>${(equivalent.applyOn)?if_exists}</td>
   </tr>
   <tr>
     <td class="title"><@msg.message key="std.degreeInfo.equivalent.adminDuty"/>：</td>
     <td>${(equivalent.adminDuty)?if_exists}</td>
     <td class="title"><@msg.message key="std.degreeInfo.equivalent.specialDuty"/>：</td>
     <td>${(equivalent.specialDuty)?if_exists}</td>
   </tr>
   <tr>
     <td class="title"><@msg.message key="std.degreeInfo.equivalent.workYears"/>：</td>
     <td colspan="3">${(equivalent.workTime)?if_exists}</td>
   </tr>
   <#if (std.type.degree.id)?if_exists != 102 && (std.type.degree.id)?if_exists != 103>
   <tr>
     <td class="title"><@msg.message key="std.degreeInfo.equivalent.firstReferee"/>：</td>
     <td>${(equivalent.recommender1)?if_exists}</td>
     <td class="title"><@msg.message key="std.degreeInfo.equivalent.firstReferee.unit"/>：</td>
     <td>${(equivalent.recommender1Company)?if_exists}</td>
   </tr>
   <tr>
     <td class="title"><@msg.message key="std.degreeInfo.equivalent.firstReferee.speciallytechnicalDuty"/>：</td>
     <td colspan="3">${(equivalent.recommender1SpecialDuty)?if_exists}</td>
   </tr>
   <tr>
     <td class="title"><@msg.message key="std.degreeInfo.equivalent.secondReferee"/>：</td>
     <td>${(equivalent.recommender2)?if_exists}</td>
     <td class="title"><@msg.message key="std.degreeInfo.equivalent.secondReferee.unit"/>：</td>
     <td>${(equivalent.recommender2Company)?if_exists}</td>
   </tr>
   <tr>
     <td class="title"><@msg.message key="std.degreeInfo.equivalent.secondReferee.speciallytechnicalDuty"/>：</td>
     <td colspan="3">${(equivalent.recommender2SpecialDuty)?if_exists}</td>
   </tr>
   </#if>
   <tr>
     <td class="title"><@msg.message key="std.degreeInfo.equivalent.comprehensiveExaminations"/>：</td>
     <td>${(equivalent.exam)?if_exists}</td>
     <td class="title"><@msg.message key="std.degreeInfo.equivalent.comprehensiveExamScores"/>：</td>
     <td>${(equivalent.examScore)?if_exists}</td>
   </tr>
   <tr>
     <td class="title"><@msg.message key="std.degreeInfo.equivalent.comprehensiveExamOn"/>：</td>
     <td>${(equivalent.examOn)?if_exists}</td>
     <td class="title"><@msg.message key="std.degreeInfo.equivalent.comprehensiveExamCertificate"/>：</td>
     <td>${(equivalent.examCertificateNo)?if_exists}</td>
   </tr>
   <tr>
     <td class="title"><@msg.message key="std.degreeInfo.equivalent.foreignLanguageExaminationTypes"/>：</td>
     <td>${(equivalent.language)?if_exists}</td>
     <td class="title"><@msg.message key="std.degreeInfo.equivalent.foreignLanguageExaminationScores"/>：</td>
     <td>${(equivalent.languageScore)?if_exists}</td>
   </tr>
   <tr>
     <td class="title"><@msg.message key="std.degreeInfo.equivalent.foreignLanguageExamOn"/>：</td>
     <td>${(equivalent.languageOn)?if_exists}</td>
     <td class="title"><@msg.message key="std.degreeInfo.equivalent.foreignLanguageExamCertificate"/>：</td>
     <td>${(equivalent.languageCertificateNo)?if_exists}</td>
   </tr>
   </#if>
 </table>
<script>
    var bar =new ToolBar("myBar","<@msg.message key="std.degreeInfo.title"/>",null,true,true);
    bar.setMessage('<@getMessage/>');    
    bar.addItem("<@msg.message key="action.edit"/>","edit()");    
    bar.addPrint("<@msg.message key="action.print"/>");
    bar.addBack("<@msg.message key="action.back"/>");
    function edit(){
       self.location="stdDetail.do?method=editDegreeInfo";
    }
 </script>
<#include "/templates/foot.ftl"/>
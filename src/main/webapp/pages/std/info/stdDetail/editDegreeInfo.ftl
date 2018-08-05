<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
 <table id="myBar" width="100%" ></table>
 <#assign schools=schools?sort_by("name")/>
 <#assign categories=categories?sort_by("name")/>
 <#assign info=std.degreeInfo?if_exists/>
 <table class="formTable" width="95%" align="center">
  <form name="degreeForm" action="stdDetail.do?method=saveDegreeInfo" method="post" onSubmit="return false;">
  <input type="hidden" name="degreeInfo.id" value="${info.id}">
  <tr>
     <td class="title" width="20%">学习方式：</td>
     <td>
       <@htm.i18nSelect datas=studyTypes selected=(info.studyType.id)?default('')?string name="degreeInfo.studyType.id" style="width:200px">
         <option value="">...</option>
        </@>
     </td>
     <td width="20%"class="title">分配单位类别：</td>
     <td>
        <@htm.i18nSelect datas=corporationKinds selected=(info.corporationKind.id)?default('')?string name="degreeInfo.corporationKind.id" style="width:200px">
         <option value="">...</option>
        </@>
        </td>
   </tr>
   <tr>
     <td class="title">培养单位：</td>
     <td><@i18nName (info.school)?if_exists/></td>
     <td class="title">毕业时间：</td>
     <td><input name="degreeInfo.graduateOn" value="${info.graduateOn?default('')}" maxlength="20" style="width:100px"/></td>
   </tr>
   <tr>
     <td class="title">毕业证书号：</td>
     <td><input name="degreeInfo.certificateNo" value="${(info.certificateNo)?default("")}" style="width:100px" maxlength="20"/></td>
     <td class="title" id="f_patentNum">专利数：</td>
     <td colspan="3"><input name="degreeInfo.patentNum" maxlength="7" value="${(info.patentNum)?default("")}" style="width:100px"/></td>
  </tr>
   <#if info.undergraduate?exists>
   <#assign undergraduate=info.undergraduate?if_exists>
   <tr><td colspan="4" class="darkColumn">学士部分</td></tr>
   <tr>
     <td class="title">本科毕业学校：</td>
     <td>
        <@htm.i18nSelect datas=schools selected=(undergraduate.school.id)?default('')?string name="degreeInfo.undergraduate.school.id" style="width:200px">
         <option value="">...</option>
        </@>
     </td>
     <td class="title">本科毕业时间：</td>
     <td><input name="degreeInfo.undergraduate.graduateOn" value="${undergraduate.graduateOn?default('')}" maxlength="20" style="width:100px"/></td>
   </tr>
   <tr>
     <td class="title">本科毕业专业：</td>
     <td><input name="degreeInfo.undergraduate.specialityName" value="${(undergraduate.specialityName)?default("")}"/></td>
     <td class="title">专业门类：</td>
     <td><@htm.i18nSelect datas=categories selected=(undergraduate.subjectCategory.id)?default('')?string name="degreeInfo.undergraduate.subjectCategory.id"/></td>
   </tr>
   </#if>
   
   <#if info.master?exists>
   <#assign master=info.master?if_exists>
   <tr><td colspan="4" class="darkColumn">硕士部分</td></tr>
   <tr>
     <td class="title">硕士毕业学校：</td>
     <td><@htm.i18nSelect datas=schools selected=(master.school.id)?default('')?string name="degreeInfo.master.school.id" style="width:200px">
         <option value="">...</option>
        </@>
     </td>
     <td class="title">硕士毕业时间：</td>
     <td><input name="degreeInfo.master.graduateOn" maxlength="20" value="${master.graduateOn?default('')}" maxlength="20" style="width:100px"/></td>
   </tr>
   <tr>
     <td class="title">硕士毕业专业：</td>
     <td><input name="degreeInfo.master.specialityName" maxlength="30" value="${(master.specialityName)?default("")}"/></td>
     <td class="title">专业门类：</td>
     <td><@htm.i18nSelect datas=categories selected=(master.subjectCategory.id)?default('')?string name="degreeInfo.master.subjectCategory.id"/></td>
   </tr>
   <tr>
     <td class="title">硕士毕业专业代码：</td>
     <td><input name="degreeInfo.master.specialityCode" maxlength="40" value="${(master.specialityCode)?default("")}"/></td>
     <td class="title">硕士毕业证书号：</td>
     <td><input name="degreeInfo.master.certificateNo" maxlength="40" value="${(master.certificateNo)?default("")}"/></td>
   </tr>
   </#if>
   <#if info.equivalent?exists>
   <#assign equivalent=info.equivalent?if_exists>
   <tr><td colspan="4" class="darkColumn">同等学力部分</td></tr>
   <tr>
     <td class="title">申请编号：</td>
     <td><input name="degreeInfo.equivalent.applyNo" maxlength="40" value="${(equivalent.applyNo)?if_exists}" style="width:100px"/></td>
     <td class="title">申请时间：</td>
     <td><input name="degreeInfo.equivalent.applyOn" maxlength="10" value="<#if equivalent.applyOn?exists>${(equivalent.applyOn)?string('yyyy-MM-dd')}</#if>" onfocus="calendar()"/></td>
   </tr>
   <tr>
     <td class="title">行政职务：</td>
     <td><input name="degreeInfo.equivalent.adminDuty" maxlength="40" value="${(equivalent.adminDuty)?if_exists}"/></td>
     <td class="title">技能职务：</td>
     <td><input name="degreeInfo.equivalent.specialDuty" maxlength="40" value="${(equivalent.specialDuty)?if_exists}"/></td>
   </tr>
   <tr>
     <td class="title">工作年限：</td>
     <td colspan="3"><input name="degreeInfo.equivalent.workTime" maxlength="2" value="${(equivalent.workTime)?if_exists}" style="width:100px"/></td>
   </tr>
   <#if (std.type.degree.id)?if_exists != 102 && (std.type.degree.id)?if_exists != 103>
   <tr>
     <td class="title">推荐人1：</td>
     <td><input name="degreeInfo.equivalent.recommender1" maxlength="20" value="${(equivalent.recommender1)?if_exists}" style="width:100px"/></td>
     <td class="title">推荐人1单位：</td>
     <td><input name="degreeInfo.equivalent.recommender1Company" maxlength="40" value="${(equivalent.recommender1Company)?if_exists}" style="width:150px"/></td>
   </tr>
   <tr>
     <td class="title">推荐人1专业技术职务：</td>
     <td colspan="3"><input name="degreeInfo.equivalent.recommender1SpecialDuty" maxlength="40" value="${(equivalent.recommender1SpecialDuty)?if_exists}" style="width:150px"/></td>
   </tr>
   <tr>
     <td class="title">推荐人2：</td>
     <td><input name="degreeInfo.equivalent.recommender2" maxlength="20" value="${(equivalent.recommender2)?if_exists}" style="width:100px"/></td>
     <td class="title">推荐人2单位：</td>
     <td><input name="degreeInfo.equivalent.recommender2Company" maxlength="40" value="${(equivalent.recommender2Company)?if_exists}" style="width:150px"/></td>
   </tr>
   <tr>
     <td class="title">推荐人2专业技术职务：</td>
     <td colspan="3"><input name="degreeInfo.equivalent.recommender2SpecialDuty" maxlength="40" value="${(equivalent.recommender2SpecialDuty)?if_exists}" style="width:150px"/></td>
   </tr>
   </#if>
   <tr>
     <td class="title">综合考试种类：</td>
     <td><input name="degreeInfo.equivalent.exam" maxlength="40" value="${(equivalent.exam)?if_exists}" style="width:100px"/></td>
     <td class="title">综合考试分数：</td>
     <td><input name="degreeInfo.equivalent.examScore" maxlength="3" value="${(equivalent.examScore)?if_exists}" style="width:100px"/></td>
   </tr>
   <tr>
     <td class="title">综合考试时间：</td>
     <td><input name="degreeInfo.equivalent.examOn" maxlength="20" value="${(equivalent.examOn)?if_exists}" style="width:100px"/></td>
     <td class="title">合格证书号：</td>
     <td><input name="degreeInfo.equivalent.examCertificateNo" maxlength="40" value="${(equivalent.examCertificateNo)?if_exists}" style="width:100px"/></td>
   </tr>
   <tr>
     <td class="title">外语统考种类：</td>
     <td><input name="degreeInfo.equivalent.language" maxlength="20" value="${(equivalent.language)?if_exists}" style="width:100px"/></td>
     <td class="title">外语统考分数：</td>
     <td><input name="degreeInfo.equivalent.languageScore" maxlength="3" value="${(equivalent.languageScore)?if_exists}" style="width:100px"/></td>
   </tr>
   <tr>
     <td class="title">外语统考时间：</td>
     <td><input name="degreeInfo.equivalent.languageOn" maxlength="20" value="${(equivalent.languageOn)?if_exists}" style="width:100px"/></td>
     <td class="title">合格证书号：</td>
     <td><input name="degreeInfo.equivalent.languageCertificateNo" maxlength="40" value="${(equivalent.languageCertificateNo)?if_exists}" style="width:100px"/></td>
   </tr>
   </#if>
   <tr><td colspan="4" class="darkColumn" align="center"><button onclick="save()"><@msg.message key="action.save"/></td></tr>
   </form>
 </table>
<script>
    var bar =new ToolBar("myBar","学历学位信息",null,true,true);
    bar.addItem("<@msg.message key="action.save"/>","save()");    
    bar.addBack("<@msg.message key="action.back"/>");
    function save(){
	     var form = document.degreeForm;
	     var valid=true;
	     if(form['degreeInfo.patentNum']){
		     var a_fields = {
		          'degreeInfo.patentNum':{'l':'专利数', 'r':false, 't':'f_patentNum','f':'unsigned'}
		     };
		     var v = new validator(form, a_fields, null);
		     if (!v.exec()) {
		        valid=false;
		     }
	     }
	     if(valid)  form.submit();
    }
 </script>
<#include "/templates/foot.ftl"/>
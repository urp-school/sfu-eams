<#include "/templates/head.ftl"/>
<script>
if(this.parent==this) self.location="http://webapp.urp.sfu.edu.cn/edu/course/site/${course.id}"
else {
  window.open("http://webapp.urp.sfu.edu.cn/edu/course/site/${course.id}");
}
</script>

<#--<#assign labInfo><@bean.message key="page.courseInfo.label"/></#assign>
<body> 
<#include "/templates/back.ftl"/>

     <table class="infoTable">
	   <tr>
	     <td class="title"><@bean.message key="attr.code"/>:</td>
	     <td class="content">${course.code}</td>
	     <td class="title"><@bean.message key="attr.infoname"/>:</td>
	     <td class="content">${course.name?if_exists}</td>
	   </tr>
	   <tr>
	     <td class="title"><@bean.message key="attr.engName"/>:</td>
	     <td class="content">${course.engName?if_exists}</td>
	     <td class="title"><@bean.message key="common.abbreviation"/>:</td>
         <td class="content">${course.abbreviation?if_exists}</td>
	   </tr>
	   <tr>
	     <td class="title"><@bean.message key="course.stdType"/>:</td>
         <td class="content"><@i18nName course.stdType/></td>
         <td class="title">建议课程类别:</td>
         <td class="content"><@i18nName course.extInfo.courseType?if_exists/></td>
	   </tr>
	   <tr>
	     <td class="title"><@bean.message key="attr.credit"/>:</td>
         <td class="content">${course.credits}</td>
	     <td class="title">周课时:</td>
         <td class="content">${course.weekHour}</td>
	   </tr>
	   <tr>
        <td class="title"><@bean.message key="common.courseLength"/>:</td>
        <td  class="content">${(course.extInfo.period)?if_exists}</td>
        <td class="title">课程种类:</td>
        <td  class="content"><@i18nName course.category?if_exists/></td>
       </tr>
       <tr>
        <td class="title"><@bean.message key="course.requirement"/>:</td>
        <td  class="content" colspan="3">${(course.extInfo.requirement)?if_exists}</td>
       </tr>
	   <tr>
        <td class="title"><@bean.message key="attr.description"/>:</td>
        <td  class="content" colspan="3">${(course.extInfo.description?replace('\n', '<br>'))?if_exists}</td>
       </tr>
	   <tr>
	    <td class="title"><@bean.message key="common.remark"/>:</td>
        <td class="content">${course.remark?if_exists}</td>
	    <td class="title"><@bean.message key="attr.state"/>:</td>
        <td class="content">
        <#if course.state?if_exists == true><@bean.message key="common.yes"/><#else><@bean.message key="common.no"/></#if> 
        </td>
	   <tr>
        <td class="title"><@bean.message key="attr.createAt"/>:</td>
        <td  class="content">${(course.createAt?string("yyyy-MM-dd"))?if_exists}</td>
        <td class="title"><@bean.message key="attr.modifyAt"/>:</td>
        <td  class="content">${(course.modifyAt?string("yyyy-MM-dd"))?if_exists}</td>
       </tr>
      </table>
  </body>
<#include "/templates/foot.ftl"/>
-->
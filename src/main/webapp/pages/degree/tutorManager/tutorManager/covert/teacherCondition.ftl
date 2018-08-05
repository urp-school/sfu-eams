<#include "/templates/head.ftl"/><BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="teacherBar" width="100%"></table>
<script>
   var bar = new ToolBar('teacherBar','教师基本信息',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("升级为导师","conver()");
   bar.addBack("<@bean.message key="action.back"/>");
 </script>
  <table  class="frameTable">
   <tr>
    <td width="15%" class="frameTable_view">
    <table class="searchTable">
    <form name="teacherSearchForm" target="teacherListFrame" method="post" action="" onsubmit="return false;">
    <input type="hidden" name="doList"  value="doList">
    <input type="hidden" id="params" name="parameters" value="">
     <tr><td><@bean.message key="teacher.code"/></td></tr>
     <tr><td><input type="text" name="teacher.code" maxlength="32" style="width:80px;" value="${RequestParameters['teacher.code']?if_exists}" /></td></tr>
     <tr><td><@bean.message key="attr.personName"/>:</td></tr>
     <tr><td><input type="text" name="teacher.name" maxlength="20" value ="${RequestParameters['teacher.name']?if_exists}"style="width:110px;" /></td></tr>
     <tr><td><@bean.message key="common.college"/>:</td></tr>
     <tr><td>
         <select name="teacher.department.id" style="width:110px;">
          <option value=""><@bean.message key="common.all"/></option>
          <#list result.departmentList as depart>
          <option value="${depart.id}" <#if RequestParameters['teacher.department.id']?if_exists==depart.id?string>selected</#if>><@i18nName depart/></option>
          </#list>
         </select>
     </td></tr>
     <tr><td><@bean.message key="teacher.isTeaching"/>:</td></tr>
     <tr><td>
        <select name="teacher.isTeaching" style="width:110px;">
	   		<option value="1" <#if RequestParameters['teacher.isTeaching']?if_exists=="1">selected</#if>><@bean.message key="common.yes" /></option>
	   		<option value="0" <#if RequestParameters['teacher.isTeaching']?if_exists=="0">selected</#if>><@bean.message key="common.no" /></option>
	   		<option value="" <#if RequestParameters['teacher.isTeaching']?if_exists=="">selected</#if>><@bean.message key="common.all" /></option>
        </select>
     </td>
     </tr>
     <tr><td>教师类别:</td></tr>
     <tr><td>
        <select name="teacher.teacherType.id" style="width:110px;">
	   		<option ><@bean.message key="common.all"/></option>
          <#list teacherTypes as teacherType>
          <option value="${teacherType.id}" <#if RequestParameters['teacher.teacherType.id']?if_exists==teacherType.id?string>selected</#if>><@i18nName teacherType/></option>
          </#list>
        </select>
     </td>
     </tr>
     <tr><td align="center"><input type="button" onclick="pageGoWithSize(1,20);" value="<@bean.message key="action.query"/>"  class="buttonStyle" /></td></tr>
     </form>
	</table>
    </td>
    <td valign="top">
	  <iframe  src="#" 
	     id="teacherListFrame" name="teacherListFrame" 
	     marginwidth="0" marginheight="0" scrolling="no"
	     frameborder="0"  height="450" width="100%">
	  </iframe>
    </td>
   </tr>
  </table>
  <script language="javascript">
    function getIds(){
       return(getRadioValue(teacherListFrame.document.getElementsByName("teacherId")));
    }
    function pageGoWithSize(pageNo,pageSize){
       var form = document.teacherSearchForm;
       form.action="tutorManager.do?method=doTeacherList";
       if(null!=pageNo){
       		 form.action+="&pageNo="+pageNo;
       }
       if(null!=pageSize)
          form.action+="&pageSize="+pageSize;
       form.submit();
    }
   function conver(){
       var teacherId = getIds();
       if (isMultiId(teacherId)) {
       		alert("请选择一个老师");
       		return;
       }
       var form = document.teacherSearchForm;
       var parameters = document.getElementById("params");
       var parames = getInputParams(form);
       parameters.value=parames;
       form.action="tutorManager.do?method=doCovertTeacherToTutor&teacherId="+teacherId;
       form.submit();
   }
   pageGoWithSize(1,20);
  </script>    
  </body>
<#include "/templates/foot.ftl"/>
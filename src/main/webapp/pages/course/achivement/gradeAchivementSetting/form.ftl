<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<BODY> 
    <table id="settingBar"></table>
    <form action="gradeAchivementSetting.do?method=save" name="settingForm" method="post"
     onsubmit="return false;">
      <input type="hidden" name="setting.id" value="${(setting.id)?if_exists}">
    <table width="90%" align="center" class="formTable">
     <tr class="darkColumn">
	    <td align="center" colspan="4">综合测评设置</td>
	 </tr>
	 <tr>
      <td class="title" id="f_calendar"><font color="red">*</font>学年度:</td>
      <td class="content" colspan="3">
         <select name="setting.toSemester.id" style="width:200px">
           <#list calendars as c>
           <option value="${c.id}" <#if c.id=(setting.toSemester.id)!0>selected="selected"</#if>>${c.year}</option>
           </#list>
         </select>
      </td>
     </tr>
     <tr>
      <td class="title" id="f_name"><font color="red">*</font>名称:</td>
      <td class="content"><input type="text" name="setting.name" value="${(setting.name)?if_exists}" maxLength="30" style="width:200px"/></td>
      <td id="f_grades" class="title"><font color="red">*</font>测评年级:</td>
      <td class="content">
          <input type="text" name="setting.grades" value="${(setting.grades)?if_exists}" maxLength="40" style="width:200px"/>
      (多个用逗号,隔开)
      </td>
     </tr>
     <tr align="center" class="darkColumn">
      <td colspan="5">
          <button onclick="save(this.form)" class="buttonStyle">提交</button>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <button onclick="this.form.reset()" class="buttonStyle">重置</button>
      </td>
     </tr>
  </table>
  </form> 
  <div style="height:200px"></div>
  <script language="javascript">
    function save(form){
	         var a_fields = {
	         'setting.name':{'l':'名称', 'r':true, 't':'f_name','mx':'32'},
	         'setting.grades':{'l':'年级', 'r':true, 't':'f_grades','mx':'40'}
	     };
	     var v = new validator(form , a_fields, null);
	     if (v.exec()) {
	        form.action="gradeAchivementSetting.do?method=save"
	        form.target = "_self";
	        form.submit();
	     }
     }
 </script>
</body>
<#include "/templates/foot.ftl"/>
<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
<table id="myBar" width="100%"></table> 

<#if signUpStdList?size==0><@msg.message key="std.2ndSpeciality.noRecord"/></#if>
  <#list signUpStdList as signUpStd>
  <table class="listTable" width="85%" align="center">
    <tr>
      <td><@msg.message key="std.2ndSpeciality.yearTerm"/></td>
      <td>${signUpStd.calendar.year} ${signUpStd.calendar.term}</td>
      <td><@msg.message key="std.2ndSpeciality.signUpOn"/></td>
      <td>${signUpStd.signUpAt?if_exists}</td>
    </tr>
    <tr>
      <td><@msg.message key="std.2ndSpeciality.GPA"/></td>
      <td>${signUpStd.GPA}</td>
      <td><@msg.message key="std.2ndSpeciality.willingToAdjustable"/></td>
      <td><#if signUpStd.isAdjustable?default(false)><@msg.message key="common.yes"/><#else><@msg.message key="common.no"/></#if></td>
    </tr> 
    <#if signUpStd.setting.isOpenMatriculateResult>
    <tr>
      <td><@msg.message key="std.2ndSpeciality.isMatriculated"/></td>
      <td><#if signUpStd.isMatriculated?default(false)><@msg.message key="common.yes"/><#else><@msg.message key="common.no"/></#if></td>
      <td><@msg.message key="std.2ndSpeciality.matriculatedSpeciality"/></td>
      <td><#if signUpStd.isMatriculated?default(false)><@i18nName signUpStd.matriculated.speciality/> <@i18nName signUpStd.matriculated.aspect?if_exists/></#if></td>
    </tr>
    </#if>
  </table>
  <@table.table width="85%" align="center">
   <@table.thead>
    <@table.td name="std.2ndSpeciality.rank"/>
    <@table.td name="entity.speciality"/>
    <@table.td name="entity.specialityAspect"/>
    <@table.td name="std.2ndSpeciality.isMatriculated"/>
   </@>
   <@table.tbody datas=signUpStd.records;record>
    <td>${record.rank}</td>
    <td><@i18nName record.specialitySetting.speciality/></td>
    <td><@i18nName record.specialitySetting.aspect?if_exists/></td>
    <td><#if signUpStd.setting.isOpenMatriculateResult><#if record.status><@msg.message key="common.yes"/><#else><@msg.message key="common.no"/></#if><#else><@msg.message key="std.2ndSpeciality.unpublished"/></#if></td>
   </@>
  </@>
  <br>
  </#list>
<script>
  var bar = new ToolBar("myBar","<@msg.message key="std.2ndSpeciality.title"/>",null,true,true);
  bar.setMessage('<@getMessage/>');
  bar.addItem("<@msg.message key="std.2ndSpeciality.func.signUpInfo"/>","signUp()");
  bar.addHelp("<@msg.message key="action.help"/>");
  function signUp(){
     self.location="speciality2ndSignUp.do?method=signUpSettingList";
  }
</script>
</body>
<#include "/templates/foot.ftl"/> 
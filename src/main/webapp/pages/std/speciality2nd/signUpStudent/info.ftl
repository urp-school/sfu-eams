<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="myBar" width="100%"></table> 
  <table class="listTable" width="85%" align="center">
    <tr>
      <td>学年学期</td>
      <td>${signUpStd.calendar.year} ${signUpStd.calendar.term}</td>
      <td>报名时间</td>
      <td>${signUpStd.signUpAt?if_exists}</td>
    </tr>
    <tr>
      <td>绩点</td>
      <td>${signUpStd.GPA}</td>
      <td>是否愿意调剂</td>
      <td>${signUpStd.isAdjustable?string("是","否")}</td>
    </tr> 
    <tr>
      <td>是否录取</td>
      <td>${signUpStd.isMatriculated?default(false)?string("是","否")}</td>
      <td>录取专业</td>
      <td><#if signUpStd.isMatriculated?default(false)><@i18nName signUpStd.matriculated.speciality/> <@i18nName signUpStd.matriculated.aspect?if_exists/></#if></td>
    </tr>
  </table>
  <@table.table width="85%" align="center">
   <@table.thead>
    <@table.selectAllTd id="signUpStudentRecordId"/>
    <@table.td text="志愿"/>
    <@table.td name="entity.speciality"/>
    <@table.td name="entity.specialityAspect"/>
    <@table.td text="是否录取"/>
   </@>
   <@table.tbody datas=signUpStd.records;record>
    <@table.selectTd id="signUpStudentRecordId" value=record.id/>
    <td>${record.rank}</td>
    <td><@i18nName record.specialitySetting.speciality/></td>
    <td><@i18nName record.specialitySetting.aspect?if_exists/></td>
    <td <#if record.status?default(false)>style="background-Color:yellow"</#if>>${record.status?default(false)?string("是","否")}</td>
   </@>
  </@>
  <br>
  <form name="actionForm" method="post" action="" onsubmit="return false;">
  <input type="hidden" value="${signUpStd.id}" name="signUpStdId"/>
  <#if (specialitySettings?size>0)>  
  <table class="listTable" align="center">
     <tr>
       <td>可以录取/调剂的专业方向</td>
       <td>
          <select name="specialitySettingId" style="width:300px">
             <#list specialitySettings as setting>
             <option value="${setting.id}"><@i18nName setting.aspect/> ${setting.matriculated}/${setting.limit} (${setting.total})</option>
             </#list>
          </select>
          <br>
          x/y (z) 表示 已录取/上限 (总报名数)
       </td>
       <td><button onclick="matriculate()">录取或者调剂</button>
     </tr>
  </table>
  </#if>
  </form>
<script>
  var bar = new ToolBar("myBar","辅修专业报名记录",null,true,true);
  bar.setMessage('<@getMessage/>');
  <#if signUpStd.isMatriculated>
  bar.addItem("取消录取","cancelMatriculate()");
  </#if>
  bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
  
  var action="speciality2ndSignUpStudent.do";
  var form=document.actionForm;
  setSearchParams(parent.document.searchForm,form);
  function cancelMatriculate(){
     if(confirm("<@msg.message key="common.confirmAction"/>")){
     	form.action=action+"?method=cancelMatriculate";
     	form.submit();
     }
  }
  function matriculate(){
     if(confirm("是否录取或者调剂到选定的专业上去?")){
       form.action=action+"?method=matriculate";
       form.submit();
     }
     return false;
  }
</script>
</body>
<#include "/templates/foot.ftl"/> 
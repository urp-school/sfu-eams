<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
<@getMessage/>
<#include "settingInfoList.ftl"/> 
<#if signUpSettings?size==0>
<table id="bar" width="100%"></table>
 <script>
    var bar = new ToolBar("bar",'报名设置信息',null,true,true);
    bar.addItem("<@msg.message key="action.new"/>","add()");
 </script>
<#else>
<#list signUpSettings as setting>
<@displaySetting setting/>
     <script>
        var bar = new ToolBar("bar${setting.id}",'报名设置信息',null,true,true);
        bar.addItem("报名专业","specialityList(${setting.id})");
        bar.addItem("报名对象","scopeList(${setting.id})");
        bar.addItem("<@msg.message key="action.edit"/>","doAction('edit',${setting.id})");
        bar.addItem("<@msg.message key="action.delete"/>","doAction('remove',${setting.id})");
        bar.addItem("<@msg.message key="action.new"/>","add()");
     </script>
     <br>
</#list>
</#if>
<form name="actionForm" method="post">
  <input type="hidden" name="params" value="&signUpSetting.calendar.id=${RequestParameters['signUpSetting.calendar.id']}"/>
</form>
<script>
  var action="speciality2ndSignUpSetting.do";
  function add(){
     self.location=action+"?method=edit&signUpSetting.calendar.id="+parent.document.settingForm['signUpSetting.calendar.id'].value;
  }

  function doAction(method,id){
     if(method.indexOf("remove")!=-1){
        if(!confirm("确定删除？"))return;
     }
     form =document.actionForm;
     form.action=action+"?method="+method + "&signUpSettingId="+id;
     form.submit();
  }
  function specialityList(id){
     self.location="speciality2ndSignUpSpecialitySetting.do?method=search&specialitySetting.setting.id="+id;
  }
  function scopeList(id){
     self.location="speciality2ndSignUpStdScope.do?method=search&stdScope.setting.id="+id;
  }
</script>
</body>
<#include "/templates/foot.ftl"/> 

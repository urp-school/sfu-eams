<#include "/templates/head.ftl"/>
<link href="${static_base}/css/tab.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript" src="scripts/tabpane.js"></script> 
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
<BODY>
<table id="taskGroupBar"></table>
  <table width="100%" border="0">
    <tr><td>
   <div class="dynamic-tab-pane-control tab-pane" id="tabPane1">
   <script type="text/javascript">tp1 = new WebFXTabPane( document.getElementById( "tabPane1") ,false );</script>
   <#include "baseForm.ftl"/>
   <div style="display: block;" class="tab-page" id="tabPage2">
   <h2 class="tab"><a href="#"><@bean.message key="entity.teachTask" />(${taskCount})</a></h2>
   <script type="text/javascript">tp1.addTabPage( document.getElementById( "tabPage2" ) );</script>
     <#include "groupTaskList.ftl"/>
   </div>
   <#include "adminClassList.ftl"/>
   </div>
   <script language="JavaScript" type="text/JavaScript" src="scripts/course/TaskGroup.js"></script> 
 <script>
 function save(){
     var form = document.taskGroupForm;
     if(form['taskGroup.priority'].value =='0') {alert("<@bean.message key="info.default.priority"/>");form['taskGroup.priority'].value='1';return;}
     var a_fields = {
         'taskGroup.name':{'l':'<@bean.message key="attr.infoname"/>','r':true,'t':'f_name'},
         'taskGroup.priority':{'l':'<@bean.message key="attr.priority"/>','r':true,'t':'f_priority','f':'unsigned'}
     };
     
     var v = new validator(form, a_fields, null);
     if (v.exec()){
       form['taskGroup.suggest.time.available'].value=getAvailTime();
       form['classroomIds'].value=getAllOptionValue(form.SelectedRoom);
       form.action="taskGroup.do?method=save"
       form.submit();
     }
 }
   var bar = new ToolBar('taskGroupBar','<@bean.message key="action.modify"/><@bean.message key="entity.taskGroup"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@msg.message key="action.save"/>",save,'save.gif');
   bar.addBack("<@bean.message key="action.back"/>");
 </script>
 </body>
<#include "/templates/foot.ftl"/>
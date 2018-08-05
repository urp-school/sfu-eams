   <script language="JavaScript" type="text/JavaScript" src="<@bean.message key="js.availableTime"/>"> </script>
   <script>
   var suggestContents = new Object();
    <#list taskList as task>
       suggestContents['${task.id}']={
         'time':abbreviate('${(task.arrangeInfo.suggest.time.available)?default("")}'),
         'rooms':'<#list task.arrangeInfo.suggest.rooms as room>(<@i18nName room/>/<@i18nName room.configType/>/${room.capacityOfCourse})</#list>',
         'remark':'${(task.arrangeInfo.suggest.time.remark?js_string)?default("")}'
         };
    </#list>
    function displaySuggestOfArrange(taskId){
      var msg="建议时间:";
      if(""==suggestContents[taskId].time) msg+="全天";
      else msg+=suggestContents[taskId].time;
      msg+="<br>建议教室:";
      if(""==suggestContents[taskId].rooms) msg+="无";
      else msg+=suggestContents[taskId].rooms;
      msg+="<br>其他建议:";
      if(""==suggestContents[taskId].remark) msg+="无";
      else msg+=suggestContents[taskId].remark;
      toolTip(msg,'#000000', '#FFFF00',250);
    }
    function editTaskSuggestTime(taskId){
      window.open("arrangeSuggest.do?method=edit&task.id="+taskId,'','scrollbars=auto,width=720,height=480,left=200,top=200,status=no');
    }
    </script>
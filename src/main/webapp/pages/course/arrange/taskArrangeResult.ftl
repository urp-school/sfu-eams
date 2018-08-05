   <script>
   var contents = new Object();
    <#list arrangeInfo?keys as taskId>
       contents['${taskId}']='${arrangeInfo[taskId]}';
    </#list>
    function displayArrangeResult(taskId){
      var msg="没有安排。"
      if(null!=contents[taskId])
         msg = contents[taskId];
      toolTip(msg,'#000000', '#FFFF00',250);
    }
    </script>
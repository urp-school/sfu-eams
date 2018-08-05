<div id="reportSetting" style="display:none;width:400px;height:170px;position:absolute;top:28px;right:0px;border:solid;border-width:1px;background-color:white ">
 <table class="settingTable" >
   <form name="alterForm" method="post" action="" onsubmit="return false;">
   <input type="hidden" name="params" value="&calendar.id=${RequestParameters['calendar.id']}"/>
   <tr>
     <td colspan="1"  style="height:5px;font-size:0px;" >
       <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
     </td>
   </tr>
   <tr>
	   <td>&nbsp;请填写变更内容(限制100字以内)：</td>
   </tr>
   <tr>
	   <td><textarea name="taskAlterRequest.requisition" cols="50" rows="5"></textarea></td>
   </tr>
   </form>
   <tr align="center">
      <td colspan="1"><button onclick="submitRequest();closeSetting()" class="buttonStyle" accesskey="M">提交(<U>M</U>)</button>
         &nbsp;<button accesskey="c" class="buttonStyle" onclick="closeSetting();"><@msg.message key="action.close"/>(<U>C</U>)</button></td>
   </tr>
 </table>
  <script>
    function displayRequest(){
       if(reportSetting.style.display=="block"){
           reportSetting.style.display='none';
       }else{
	       reportSetting.style.display="block";
	       f_frameStyleResize(self);
       }
    }
    function closeSetting(){
       reportSetting.style.display='none';
    }
    function submitRequest(){
       document.alterForm.action="teacherTaskAlterRequest.do?method=save";
       document.alterForm.submit();
    }
  </script>
 </div>
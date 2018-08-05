<div id="reportSetting" style="display:none;width:500px;height:235px;position:absolute;top:28px;right:0px;border:solid;border-width:1px;background-color:white ">
 <table class="settingTable" style="background-color: #E1ECFF">
   <tr >
	   <td  style="width:40%">&nbsp;&nbsp;是否打印绩点</td>
	   <td>
	     <input type="hidden" name="reportSetting.majorType.id" value="${RequestParameters['majorTypeId']}"/>
  	     <input type="hidden" name="orderBy" value="${RequestParameters['orderBy']?default('null')}"/>
	     <input type="radio" value="1" name="reportSetting.printGP" checked/>是 
	     <input type="radio" name="reportSetting.printGP" value="0"/>否
	   </td>
   </tr>
   <tr>
     <td colspan="6"  style="height:5px;font-size:0px;" >
       <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
     </td>
   </tr>
   <tr>
	   <td>&nbsp;&nbsp;打印成绩：</td>
	   <td><input type="radio" value="0" name="reportSetting.gradePrintType" checked/>及格成绩
	       <input type="radio" name="reportSetting.gradePrintType" value="1"/>所有成绩
	   </td>
   </tr>
   <tr>
     <td colspan="6" style="height:5px;font-size:0px;">
       <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
     </td>
   </tr>
   <tr>
	   <td>&nbsp;&nbsp;是否打印奖励学分：</td>
	   <td>
	   <input type="radio" value="1" name="reportSetting.printAwardCredit" checked/>是
	   <input type="radio" value="0" name="reportSetting.printAwardCredit"/>否
	   </td>
   </tr>
   <tr>
     <td colspan="6"  style="height:5px;font-size:0px;">
       <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
     </td>
   </tr>
   <tr>
	   <td>&nbsp;&nbsp;成绩发布：</td>
	   <td><input type="radio" value="1" name="reportSetting.published" checked/>已发布成绩
           <input type="radio" value="0" name="reportSetting.published"/>全部成绩
       </td>
   </tr>
   <tr>
     <td colspan="6" style="height:5px;font-size:0px;">
       <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
     </td>
   </tr>
   <tr>
	   <td>&nbsp;&nbsp;字体大小：</td>
	   <td><input type="text" name="reportSetting.fontSize" value="13" maxlength="3"/>px</td>
   </tr>
  <tr>
     <td colspan="6" style="height:5px;font-size:0px;">
       <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
     </td>
   </tr>
   <tr>
	   <td>&nbsp;&nbsp;打印成绩类型：</td>
	   <td>
	      <select name="reportSetting.gradeType.id">
	        <#list gradeTypes?sort_by("code") as gradeType>
	        <option value="${gradeType.id}"><@i18nName gradeType/></option>
	        </#list>
	       </select>
       </td>
   </tr>
   <tr>
    <td colspan="2">&nbsp;&nbsp;提示:可在查询点击列标题中进行排序,打印出的报表也将按照查询的顺序输出.</td>
   </tr>
   <tr align="center">
      <td colspan="2"><button onclick="printGrade();closeSetting()" class="buttonStyle" accesskey="P">打印预览(<U>P</U>)</button>
         &nbsp;<button accesskey="c" class="buttonStyle" onclick="closeSetting()"><@msg.message key="action.close"/>(<U>C</U>)</button></td>
   </tr>
  </table>
  <script>
    function displaySetting(){
       closeMultiReportSetting();
       reportSetting.style.display="block";
       f_frameStyleResize(self);
    }
    function closeSetting(){
       reportSetting.style.display='none';
    }
  </script>
 </div>

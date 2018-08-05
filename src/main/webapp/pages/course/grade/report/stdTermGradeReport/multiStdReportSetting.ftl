<div id="multiStdReportSetting" style="display:none;width:500px;height:258px;position:absolute;top:28px;right:0px;border:solid;border-width:1px;background-color:white ">
 <table class="settingTable">
   <tr >
	   <td  style="width:40%">&nbsp;&nbsp;是否打印绩点</td>
	   <td>
	     <input type="hidden" name="reportSetting.majorType.id" value="${RequestParameters['majorTypeId']}"/>
   	     <input type="hidden" name="orderBy" value="${RequestParameters['orderBy']?default('null')}">
	     <input type="radio" value="1" name="reportSetting.printGP" checked>是 
	     <input type="radio" name="reportSetting.printGP" value="0" >否
	   </td>
   </tr>
   <tr>
     <td colspan="6"  style="height:5px;font-size:0px;">
       <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
     </td>
   </tr>
   <tr>
	   <td>&nbsp;&nbsp;每页最大记录数：</td>
	   <td style="width:50px"><input value="20" type="text" maxlength="7" name="reportSetting.pageSize"></td>
   </tr>
   <tr>
     <td colspan="6"  style="height:5px;font-size:0px;">
       <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
     </td>
   </tr>
   <tr>
	   <td>&nbsp;&nbsp;成绩排序：</td>
	   <td><select name="reportSetting.order.property">
	        <option value="GPA">平均绩点</option>
	        <option value="std.code"><@msg.message key="attr.stdNo"/></option>
	       </select>
	       顺序:
	       <select name="reportSetting.order.direction">
	        <option value="1">升序</option>
	        <option value="2" selected>降序</option>
	       </select>
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
	   <td>&nbsp;&nbsp;打印共同成绩时,最低比例：</td>
	   <td><input value="0.05" maxlength="10" type="text" name="ratio" style="width:40px"/></td>
   </tr>
   <tr>
	   <td colspan="2">&nbsp;&nbsp;0.05表示:在100个学生中,如果一门课有5个或更少的学生,则单独显示该课程成绩.</td>
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
      <td colspan="2"><button onclick="printMultiStdGrade();closeMultiReportSetting()" class="buttonStyle" accesskey="P">打印预览(<U>P</U>)</button>
         &nbsp;<button accesskey="c" class="buttonStyle" onclick="closeMultiReportSetting()"><@msg.message key="action.close"/>(<U>C</U>)</button></td>
   </tr>
  </table>
  <script>
    function displayMultiStdSetting(){
       if(typeof closeSetting!="undefined")
          closeSetting();
       multiStdReportSetting.style.display="block";
       f_frameStyleResize(self);
    }
    function closeMultiReportSetting(){
       multiStdReportSetting.style.display='none';
    }
  </script>
 </div>

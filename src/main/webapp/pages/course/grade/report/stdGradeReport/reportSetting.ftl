<div id="reportSetting" style="display:none;width:650px;height:230px;position:absolute;top:28px;right:0px;border:solid;border-width:1px;background-color:white">
 <table class="settingTable">
   <tr>
     <input type="hidden" name="reportSetting.majorType.id" value="${RequestParameters['majorTypeId']}"/>
     <input type="hidden" name="orderBy" value="${RequestParameters['orderBy']?default('null')}"/>
     <td width="14%">&nbsp;<@msg.message key="grade.printTemplate"/>：</td>
     <td width="30%" colspan="2">
        <select name="reportSetting.template" style="width:140px">
            <option value="default" selected>默认模板（居中）</option>
            <option value="new" selected>新版成绩单</option>
            <option value="print">打印模板（归档）</option>
            <option value="single">缺省模板（无院系、专业）</option>
        </select>
       </td>
       <td width="18%">&nbsp;<@msg.message key="grade.printScope"/>：</td>
       <td><input type="radio" value="0" name="reportSetting.gradePrintType" checked/><@msg.message key="grade.pass"/>
           <input type="radio" name="reportSetting.gradePrintType" value="1"/><@msg.message key="grade.all"/>
           <input type="radio" value="2" name="reportSetting.gradePrintType"/>最好成绩
       </td>
   </tr>
   <tr>
     <td colspan="5" style="height:5px;font-size:0px;">
       <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
     </td>
   </tr>
   <tr>
       <td id="f_pageSize" colspan="2">&nbsp;<@msg.message key="common.maxRecodesEachPage"/><font color="red">*</font>：</td>
       <td><input value="100" type="text" maxlength="5" style="width:75px" name="reportSetting.pageSize"/></td>
       <td id="f_fontSize">&nbsp;<@msg.message key="common.foneSize"/><font color="red">*</font>：</td>
       <td><input type="text" name="reportSetting.fontSize" value="10" style="width:75px" maxlength="2"/>px</td>
   </tr>
   <tr>
     <td colspan="5" style="height:5px;font-size:0px;">
       <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
     </td>
   </tr>
   <tr>
       <td>&nbsp;打印绩点：</td>
       <td colspan="2">
         <input type="radio" value="1" name="reportSetting.printGP" checked/><@msg.message key="yes"/> 
         <input type="radio" name="reportSetting.printGP" value="0"/><@msg.message key="no"/>
       </td>
       <td>&nbsp;<@msg.message key="grade.deploy"/>：</td>
       <td>
           <input type="radio" value="1" name="reportSetting.published" checked/><@msg.message key="grade.beenDeployed"/>
           <input type="radio" value="0" name="reportSetting.published"/><@msg.message key="grade.all"/>
       </td>
   </tr>
   <tr>
     <td colspan="5" style="height:5px;font-size:0px;">
       <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
     </td>
   </tr>
   <tr>
       <td>&nbsp;<@msg.message key="grade.sort"/>：</td>
       <td>
           <select name="reportSetting.order.property">
            <option value="calendar.yearTerm"><@msg.message key="attr.yearTerm"/></option>
            <option value="course.name"><@msg.message key="attr.courseName"/></option>
            <option value="credit"><@msg.message key="attr.credit"/></option>
           </select>
       </td>
       <td>
           <@msg.message key="common.order"/>:
           <select name="reportSetting.order.direction">
            <option value="1"><@msg.message key="action.asc"/></option>
            <option value="2"><@msg.message key="action.desc"/></option>
           </select>
       </td>
       <td>&nbsp;<@msg.message key="grade.printType"/>：</td>
       <td>
          <select name="reportSetting.gradeType.id">
            <#list gradeTypes?sort_by("code") as gradeType>
            <option value="${gradeType.id}"><@i18nName gradeType/></option>
            </#list>
          </select>
	<input name="request_locale" value="zh_CN" type="radio" checked>中文版          
	<input name="request_locale" value="en_US" type="radio"/>英文版
       </td>
   </tr>
   <tr>
     <td colspan="5" style="height:5px;font-size:0px;">
       <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
     </td>
   </tr>
   <tr>
     <td>&nbsp;<@msg.message key="common.printPerson"/>：</td>
     <td colspan="2"><input name="reportSetting.printBy" value="" style="width:100px" maxlength="20"/></td>
     <td>&nbsp;打印日期：</td>
     <td><input name="reportSetting.printAt" value="${RequestParameters["reportSetting.printAt"]?default(printAt?string("yyyy-MM-dd"))}" onfocus="calendar()" style="width:100px"/>（默认当天）</td>
   </tr>
   <tr>
    <td colspan="5">&nbsp;<@msg.message key="grade.stdGradeReport.tip"/></td>
   </tr>
   <tr align="center">
      <td colspan="5"><button onclick="printGrade();closeSetting()" class="buttonStyle" accesskey="P"><@msg.message key="action.preview"/>(<U>P</U>)</button>&nbsp;<button accesskey="c" class="buttonStyle" onclick="closeSetting();"><@msg.message key="action.close"/>(<U>C</U>)</button></td>
   </tr>
 </table>
  <script>
    function displaySetting(){
       if (reportSetting.style.display == "block") {
           reportSetting.style.display = 'none';
       } else {
           reportSetting.style.display = "block";
           f_frameStyleResize(self);
       }
    }
    function closeSetting(){
       reportSetting.style.display = 'none';
    }
  </script>
 </div>

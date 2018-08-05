<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
<script src="scripts/common/multiSelectChoice.js"></script>

<body>
<#assign labInfo><@bean.message key="action.split"/> <@bean.message key="entity.teachTask"/></#assign>
<#include "/templates/back.ftl"/>
   <table  width="100%">
   <form name="teachTaskForm" action="" method="post" onsubmit="return false;">
   <input type="hidden" name="task.calendar.id" value="${RequestParameters['task.calendar.id']}"/>
   <input type="hidden" value="${task.id}" name="task.id"/>
   <input type="hidden" name="params" value="${RequestParameters['params']?if_exists}"/>
   <tr>
    <td>
     <table width="90%" align="center" class="listTable">
	   <tr class="darkColumn">
	     <td align="center" colspan="4"><@bean.message key="info.task.split.mode"/></td>
	   </tr>
	   <tr>
		   <td class="grayStyle" width="25%">
		      &nbsp;&nbsp;<@bean.message key="info.task.split.studentNum"/>：
		   </td>
		   <td class="brightStyle" width="25%">
		   	  ${task.teachClass.planStdCount}
		   </td>
		   <td class="grayStyle" width="25%">
		      &nbsp;&nbsp;<@bean.message key="info.task.split.splitNum"/>：
		   </td>
		   <td class="brightStyle" width="25%">
		   <input type="hidden" name="splitNum" value="${RequestParameters['splitNum']}"/>
		   	  ${RequestParameters['splitNum']}
		   </td>
	   </tr>
	   <#if splitTag=="10"||splitTag=="20"||splitTag=="13"||splitTag=="11"||splitTag=="12">
	   <tr>
		   <td class="grayStyle">
		      &nbsp;&nbsp;<@bean.message key="entity.adminClass"/>：
		   </td>  
		   <td  class="brightStyle" colspan="3">
		   	   <#list task.teachClass.adminClasses as adminClass>
		            <@i18nName adminClass/>&nbsp;&nbsp;
		        </#list>
		   </td>
	   </tr>
	   </#if>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_count">
	     	<input type="radio" name="splitMode" checked value="avgShare" /><@bean.message key="info.task.split.avgAll"/>：
	     </td>
	     <td class="brightStyle"  colspan="3">
	        <@bean.message key="info.task.split.avgAllTip" arg0="${RequestParameters['splitNum']}"/>
	     </td>
	   </tr>
	   <#if splitTag="10"||splitTag="11">
	   <tr>
	     <td class="grayStyle" width="25%" id="f_count">
	     	<input type="radio" name="splitMode" value="keepShare"/><@bean.message key="info.task.split.keepShare"/>：
	     </td> 
	     <td class="brightStyle" colspan="3"><@bean.message key="info.task.split.keepShareTip"/></td>
	   </tr>
	   </#if>
	   <#if splitTag=="12">
	   <tr>
	     <td class="grayStyle" width="25%" id="f_count">
	     	<input type="radio" name="splitMode" value="keep" /><@bean.message key="info.task.split.keepClass"/>：
	     </td>
	     <td class="brightStyle" colspan="3"><@bean.message key="info.task.split.keepClassTip"/></td>
	   </tr>
	   </#if>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_count">
	     	<input type="radio" name="splitMode" value="genderAware" id="mode_genderAware"/>
	     	按性别分班：
	     </td>
	     <td class="brightStyle" colspan="3">
	       男生班级数量：<input name="maleClassCount" value="1" maxlength="1" size="1" onfocus="setModeFocus('mode_genderAware')"><br>
	       女生班级数量：<input name="femaleClassCount" value="1" maxlength="1" size="1" onfocus="setModeFocus('mode_genderAware')">
	     </td>
	   </tr>
	   <tr>
	   <td class="grayStyle" width="25%" id="f_count">
	     	<input type="radio" name="splitMode" value="stdnoAware" id="mode_stdnoAware"/>
	     	按单双号分班：
	     </td>
	     <td class="brightStyle" colspan="3">
	       单号班级数量：<input name="oddClassCount" value="1" maxlength="1" size="1" onfocus="setModeFocus('mode_stdnoAware')"><br>
	       双号班级数量：<input name="evenClassCount" value="1" maxlength="1" size="1" onfocus="setModeFocus('mode_stdnoAware')">
	     </td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_count">
	     	<input type="radio" name="splitMode" value="customShare" id="mode_customShare"/>
	     	<@bean.message key="info.task.split.userDefined"/>：
	     </td>
	     <td class="brightStyle" colspan="3">
	       <#assign splitNum =RequestParameters['splitNum']?number>
	       <#list 1..splitNum as i>
            <@bean.message key="info.task.split.eachClassCount" arg0=i?string/>：<input type="text" style="width:60px" value="" name="splitUnitNum" onfocus="setModeFocus('mode_customShare')" /><br>
	       </#list>
	     </td>
	   </tr> 
	   <tr class="darkColumn">
	     <td colspan="6" align="center" >
	       <input type="button" value="<@bean.message key="action.submit"/>" name="saveButton" onClick="save()" class="buttonStyle" />&nbsp;
	       <input type="reset"  name="resetButton" value="<@bean.message key="action.reset"/>" class="buttonStyle" />
	     </td>
	   </tr>
     </table>
    </td>
   </tr>
   </form>
  </table>
  <script language="javascript">
      var form = document.teachTaskForm;
      function save(){
         var mode = getRadioValue(form.splitMode);
         var splitUnitNums="";
         var splitNum=${RequestParameters['splitNum']};
         if(mode=="customShare"){
            var splitUnit= document.getElementsByName('splitUnitNum');
	        var count=new Number(0);
	        for(i=0;i<${RequestParameters['splitNum']};i++){
	            if(!/[0-9]+/.test(splitUnit[i].value)) {alert("<@bean.message key="prompt.input.Num"/>");return;}
	            var splitCount = new Number(splitUnit[i].value);
	            if(splitCount<0){alert("<@bean.message key="prompt.input.unsignedInteger"/>");return;}
	            if(splitUnitNums!="") splitUnitNums+=","
	            splitUnitNums +=splitUnit[i].value;
	            count += splitCount;
	         }
	         if(count!= ${task.teachClass.planStdCount}){alert("<@bean.message key="error.split.sumError"/>");return;}
         } else if(mode=="genderAware"){
             if(!/[1-9]/.test(form['maleClassCount'].value)||(!/[1-9]/.test(form['femaleClassCount'].value))){
                alert("拆分的班级数量格式不对");
                return;
             }
             if(splitNum!=parseInt(form['maleClassCount'].value)+parseInt(form['femaleClassCount'].value)){
                alert("拆分数量不等于总和");return;
             }
             splitUnitNums =form['maleClassCount'].value+","+form['femaleClassCount'].value;
         } else if(mode=="stdnoAware"){
             if(!/[1-9]/.test(form['oddClassCount'].value)||(!/[1-9]/.test(form['evenClassCount'].value))){
                alert("拆分的班级数量格式不对");
                return;
             }
             if(splitNum!=parseInt(form['oddClassCount'].value)+parseInt(form['evenClassCount'].value)){
                alert("拆分数量不等于总和");return;
             }
             splitUnitNums =form['oddClassCount'].value+","+form['evenClassCount'].value;
         }
	     form.action="?method=split&splitNum=${RequestParameters['splitNum']}&mode="+
	                      mode + "&splitUnitNums=" + splitUnitNums;
         form.submit();
      }
      function setModeFocus(radioboxId){
          document.getElementById(radioboxId).checked=true;
      }
  </script>
</body>
<#include "/templates/foot.ftl"/>
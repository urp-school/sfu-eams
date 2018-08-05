<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<script language="javascript" type="text/javascript" src="${static_base}/scripts/fckeditor/fckeditor.js"></script>
<script language="javascript" type="text/javascript" src="${static_base}/scripts/fckeditor/fckTextArea.js"></script>
<#assign textAreaId = "noticeContent"/>
<body>
<table width="100%" id="noticeBar"></table>
<table width="100%"  class="listTable" >
   <form name="noticeForm" method="post" action="" onsubmit="return false;">
   <input type="hidden" name="kind" value="${RequestParameters['kind']}"/>
   <input type="hidden" name="notice.id" value="${notice.id?if_exists}"/>
   <tr class="darkColumn">
     <td class="darkColumn" id="f_title" style="text-align:left" colspan="4"><font color="red">*</font>&nbsp;公告标题
     [是否置顶显示<input type="hidden" name="notice.isUp" value="<#if notice.isUp?if_exists==true>true<#else>false</#if>"/>
	        <input name="isUp" type="checkbox" onchange="changeCheckBoxValue(this.form,'notice.isUp');"
	         	<#if notice.isUp?if_exists==true>
	          	 	checked
	         	</#if>/>]</td>
   </tr>
   <tr>
     <td  align="left" colspan="4">
     <input type="text" name="notice.title" style="width:100%" value="${notice.title?if_exists}" maxLength="60"></td>
   </tr>
   <tr class="darkColumn" colspan="4">
     <td class="darkColumn" id="f_content" style="text-align:left" colspan="4"><font color="red">*</font>&nbsp公告内容</td>
   </tr>
   <tr>
     <td  align="left" colspan="4">
	 <textarea id="${textAreaId}" name="notice.content.content"  style="font-size:10pt;width:100%;height:200px">${(notice.content.content)?if_exists}</textarea>
     </td>
   </tr>
   <tr>
	   <td class="darkColumn" colspan="4">&nbsp;公告有效期时间<font color="red">*</font>：</td>
   </tr>
   <tr>
	  <td class="title" width="25%" id="f_startDate">&nbsp;<@bean.message key="attr.startDate"/><font color="red">*</font>：</td>
	  <td>
	     <input type="text" maxlength="10" name="notice.startDate" value="${(notice.startDate?string("yyyy-MM-dd"))?if_exists}" onfocus="calendar()"/>
	  </td>
	  <td class="title" width="25%" id="f_finishDate">&nbsp;<@bean.message key="attr.finishDate"/><font color="red">*</font>：</td>  
      <td>
         <input type="text" maxlength="10" name="notice.finishDate" value="${(notice.finishDate?string("yyyy-MM-dd"))?if_exists}" onfocus="calendar()"/>
      </td>
   </tr>
   </table>
   <#if RequestParameters['kind']=="std">
    <table>
    <tr>
      <td>
	     <font color="red">*</font>公告适用学生院系
	 <table>
	  <tr>
	   <td valign="top">
	    <select name="departs" MULTIPLE size="10" style="width:150px" onDblClick="JavaScript:moveSelectedOption(this.form['departs'], this.form['selectedDeparts'])" >
	     <#list departments as depart>
	      	<option value="${depart.id}"><@i18nName  depart/></option>
	     </#list>
	    </select>
	   </td>
	   <td  valign="middle">
	    <br><br>
	    <input OnClick="JavaScript:moveSelectedOption(this.form['departs'], this.form['selectedDeparts'])" type="button" value="&gt;"> 
	    <br><br>
	    <input OnClick="JavaScript:moveSelectedOption(this.form['selectedDeparts'], this.form['departs'])" type="button" value="&lt;"> 
	    <br>
	   </td> 
	   <td  class="normalTextStyle" valign="top">
	    <select name="selectedDeparts" MULTIPLE size="10" style="width:150px;" onDblClick="JavaScript:moveSelectedOption(this.form['selectedDeparts'], this.form['departs'])">
	     <#list notice.departs  as depart>
	     <option value="${depart.id}"><@i18nName  depart/></option>
	     </#list>
	    </select>
	   </td>
	  </tr>
	 </table>
	 </td>
	 <td>
	 <font color="red">*</font>公告适用学生类别
	 <table>
	  <tr>
	   <td valign="top">
	    <select name="stdTypes" MULTIPLE size="10" style="width:150px" onDblClick="JavaScript:moveSelectedOption(this.form['stdTypes'], this.form['selectedStdTypes'])" >
	     <#list studentTypes as stdType>
	      <option value="${stdType.id}"><@i18nName stdType/></option>
	     </#list>
	    </select>
	   </td>
	   <td  valign="middle">
	    <br><br>
	    <input OnClick="JavaScript:moveSelectedOption(this.form['stdTypes'], this.form['selectedStdTypes'])" type="button" value="&gt;"> 
	    <br><br>
	    <input OnClick="JavaScript:moveSelectedOption(this.form['selectedStdTypes'], this.form['stdTypes'])" type="button" value="&lt;"> 
	    <br>
	   </td> 
	   <td  class="normalTextStyle" valign="top">
	    <select name="selectedStdTypes" MULTIPLE size="10" style="width:150px;" onDblClick="JavaScript:moveSelectedOption(this.form['selectedStdTypes'], this.form['stdTypes'])">
	     <#list notice.studentTypes  as stdType>
	     <option value="${stdType.id}"><@i18nName stdType/></option>
	     </#list>
	    </select>
	   </td>
	  </tr>
	 </table>
 </td>
</tr>
<input type="hidden" name="stdTypeIds" />
<input type="hidden" name="departIds" />
</table>
   </#if>
 </form>
<script>
   var bar = new ToolBar('noticeBar','公告详情',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@msg.message key="action.save"/>",'save()','save.gif');
   bar.addBack("<@msg.message key="action.back"/>");
	
	initFCK("${textAreaId}", "100%", "");

   function save(){
     var form = document.noticeForm;
     var a_fields = {
         'notice.title':{'l':'公告标题', 'r':true, 't':'f_title'}
     };
     var errorInfo="";
     if(form['notice.startDate'].value==form['notice.finishDate'].value)
         errorInfo +="<@bean.message key="error.time.finishBeforeStart"/>";
     if(!isDateBefore(form['notice.startDate'].value,form['notice.finishDate'].value)) 
         errorInfo +="<@bean.message key="error.date.finishBeforeStart"/>";
     if (errorInfo!="") {
        alert(errorInfo);
        return false;
     }
     var v = new validator(form , a_fields, null);
     if (v.exec()) {     
      <#if RequestParameters['kind']=="std">
      form.departIds.value = getAllOptionValue(form.selectedDeparts);
      if(form['departIds'].value==""){
        alert("公告适用学生院系不能为空");return;
      }
      form.stdTypeIds.value = getAllOptionValue(form.selectedStdTypes);
      if(form['stdTypeIds'].value==""){
        alert("公告适用学生类别不能为空");return;
      }
      </#if>
      	if (FCKeditorAPI.GetInstance("${textAreaId}").GetXHTML(true) == "" ) {
     		alert("公告内容没有填写。");
     		return;
		}
      form.action="notice.do?method=save";
      form.submit();
    }
   }
   function isDateBefore(first,second){
       var firstYear = first.substring(0,4);
       var secondYear = second.substring(0,4);
       if (firstYear > secondYear) {
       	return false;
       } else if (firstYear < secondYear) {
       	return true;
       }

       var firstMonth = new Number(first.substring(first.indexOf('-')+1,first.lastIndexOf('-')));
       var secondMonth = new Number(second.substring(second.indexOf('-')+1,second.lastIndexOf('-')));

       if (firstMonth > secondMonth) {
       	return false;
       } else if (firstMonth < secondMonth) {
       	return true;
       }
       
       var firstDay = new Number(first.substring(first.lastIndexOf('-')+1));
       var secondDay = new Number(second.substring(second.lastIndexOf('-')+1));
       return firstDay <= secondDay;
   }
    function changeCheckBoxValue(form, checkboxName) {
	  	if(event.srcElement.checked) {
	  		form[checkboxName].value = "true";
	  	} else {
	  		form[checkboxName].value = "false";
	  	}
    }
</script>
</body>
<#include "/templates/foot.ftl"/>
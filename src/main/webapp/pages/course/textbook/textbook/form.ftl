<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/My97DatePicker/WdatePicker.js"></script>
<body  LEFTMARGIN="0" TOPMARGIN="0">

<#assign labInfo>教材基本信息</#assign>  
<#include "/templates/back.ftl"/>
  <table width="90%" align="center" class="formTable">
    <form action="textbook.do?method=edit" name="textbookForm" method="post" onsubmit="return false;"> 
    <tr>
      <td id="f_code" class="title"><font color="red">*</font>教材代码:</td>
      <td class="content">
      <input id="codeValue" type="text" maxlength="32" name="textbook.code" value="${textbook.code?if_exists}" maxLength="32" style="width:80px;">
      <#assign className="Textbook">
      <#include "/pages/system/checkCode.ftl"/>
      <input type="hidden" name="textbook.id" value="${textbook.id?if_exists}">
      </td>
      <td id="f_name" class="title"><font color="red">*</font>教材名称:</td>
      <td class="content"><input type="text" name="textbook.name" value="${textbook.name?if_exists}"  maxLength="100"></td>
    </tr>
    <tr>
      <td id="f_auth" class="title"><font color="red">*</font>作者:</td>
      <td class="content"><input type="text" name="textbook.auth"  value="${textbook.auth?if_exists}" maxLength="25"/></td>
      <td id="f_version" class="title">版次:</td>
	  <td class="content"><input type="text" name="textbook.version"  value="${textbook.version?if_exists}" maxLength="25"/></td>        
     </tr>
     <tr>
      <td id="f_price" class="title"><font color="red">*</font>价格:</td>
      <td class="content"><input type="text" name="textbook.price" value="${textbook.price?if_exists}" maxLength="6"/></td>
      <td class="title" id="f_publishedOn"><font color="red">*</font>出版年月:</td>
      <td class="content">
      <#--
      <input type="text" name="textbook.publishedOn" value="${(textbook.publishedOn?string("yyyy-MM"))?if_exists}" style="width:80px" maxlength="20"/>
      <button  style="width:60px" onclick="calendar(this.form['textbook.publishedOn'])">日期</button>
      -->
      <input type="text" name="textbook.publishedOn"  
      class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM'})" maxlength="10"
       value="${(textbook.publishedOn?string("yyyy-MM"))?if_exists}" style="width:50%;"/>
      </td>
     </tr>
     <tr>
      <td id="f_press" class="title"><font color="red">*</font>出版社:</td>
	  <td class="content">
          <select name="textbook.press.id">
	          <#list presses?if_exists?sort_by("name") as press>
				 <option value=${press?if_exists.id?if_exists} <#if press?if_exists.id?if_exists?string==textbook.press?if_exists.id?if_exists?string>selected</#if>>${press?if_exists.name?if_exists}</option>
		      </#list>
	      </select>
	  </td>
      <td id="f_ISBN" class="title">ISBN:</td>
      <td class="content"><input type="text" name="textbook.ISBN" value="${textbook.ISBN?if_exists}" maxLength="25"/></td> 
     </tr>
     <tr>
      <td class="title"><font color="red">*</font>教材种类:</td>
	  <td class="content">
          <select name="textbook.bookType.id">
	          <#list bookTypes?if_exists?sort_by("name") as bookType>
				 <option value="${bookType.id}" <#if bookType?if_exists.id?if_exists?string==textbook.bookType?if_exists.id?if_exists?string>selected</#if>><@i18nName bookType/></option>
		      </#list>
	      </select>
	  </td>
      <td id="f_remark" class="title"><@msg.message key="entity.textbookAwardLevel"/>:</td>
	  <td class="content">
	    <@htm.i18nSelect datas=awardLevels selected="${(textbook.awardLevel.id)?default('0')?string}" name="textbook.awardLevel.id"  style="width:100px">
   	       <option value=""><@msg.message key="common.all"/></option>
	    </@>
    </tr>
    <tr>
      <td id="f_description" class="title"><@msg.message key="attr.description"/>:</td>
	  <td colspan="3" class="content"><input type="text" name="textbook.description" size="50" value="${textbook.description?if_exists}" maxLength="50"/></td>        
    </tr> 
    <#-- 金融备注改为 条形码，必填字段
    <tr>
      <td id="f_remark" class="title"><@msg.message key="attr.remark"/>:</td>
	  <td class="content" colspan="3"><input type="text" name="textbook.remark" size="50"  value="${textbook.remark?if_exists}" maxLength="50"/></td>
    </tr> 
    -->
    <tr>
      <td id="f_remark" class="title"><font color="red">*</font>条形码:</td>
	  <td class="content" colspan="3"><input type="text" name="textbook.remark" size="50"  value="${textbook.remark?if_exists}" maxLength="50"/></td>
    </tr> 
    <tr align="center"  class="darkColumn">
      <td colspan="5">
          <button onclick="save(this.form)" class="buttonStyle">提交</button>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <button onclick="this.form.reset()" class="buttonStyle">重置</button>
      </td>
    </tr>
    </form> 
  </table>
  <br><br><br>
  <script language="javascript"> 
    function save(form){
	         var a_fields = {
	         'textbook.code':{'l':'教材代码', 'r':true, 't':'f_code','mx':'32'},
	         'textbook.name':{'l':'教材名称', 'r':true, 't':'f_name'},
	         'textbook.price':{'l':'价格','r':true,'t':'f_price','f':'real'},
             'textbook.publishedOn':{'l':'出版年月','t':'f_publishedOn','r':true},
             <#-- 条形码 -->
	         'textbook.remark':{'l':'条形码', 'r':true, 't':'f_remark'},
	         'textbook.auth':{'l':'作者','r':true,'t':'f_auth'}
	         
	     };	  
	     var v = new validator(form , a_fields, null);
	     if (v.exec()) {
	     	form["textbook.publishedOn"].value = form["textbook.publishedOn"].value + "-01";	     
	        form.action="textbook.do?method=save"
	        form.submit();
	     }
     }
   
 </script>
 </body> 
</html>
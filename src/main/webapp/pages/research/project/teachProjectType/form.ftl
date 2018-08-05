<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
<script src="dwr/interface/baseinfoUtil.js"></script>
<script src="dwr/engine.js"></script>
<script src="dwr/util.js"></script>

<body>
<#assign labInfo>项目类别信息</#assign>  
<#include "/templates/back.ftl"/>   
	<table width="90%" align="center"  class="formTable">
		<form action="teachProjectType.do?method=save" name="teachProjectTypeForm" method="post" onsubmit="return false;">
		<@searchParams/>
		<input type="hidden" name="teachProjectType.id" value="${(teachProjectType.id)?default('')}"/>

	<tr class="darkColumn">
      <td align="left" colspan="4"><B>${labInfo}</B></td>
    </tr>
    <tr>
      <td width="15%" id="f_code" class="title"><font color="red">*</font><@msg.message key="attr.code"/>:</td>
      <td width="35%"  >
      <input id="codeValue" type="text"  name="teachProjectType.code" value="${teachProjectType.code?if_exists}" style="width:80px;" maxLength="32"/>
<DIV id=codeGenDiv><BUTTON id=checkButton onclick="checkId();return false;">检查</BUTTON><BUTTON onclick="emptyCode();return false;">自动生成</BUTTON> </DIV>
<DIV id=cancelGenDiv style="DISPLAY: none"><BUTTON onclick="cancelGen();return false;">取消生成</BUTTON> </DIV>
<DIV id=checkMessage></DIV>
<SCRIPT>
  function checkId(){
  	  var valueCode = document.getElementById("codeValue").value;
  	  if(""==valueCode) {
  	  	checkMessage.innerHTML='<font color="red" size="2">代码不能为空</font>';
  	  	return;
  	  }
  	  baseinfoUtil.checkCodeIfExists(afterCheck,"com.ekingstar.eams.system.basecode.industry.TeachProjectType","code",valueCode);
  }
  function afterCheck(data){
  	  if(data==true){
  	  	checkMessage.innerHTML='<font color="red" size="2">不可用</font>';
  	  }
  	  else{
  	  	checkMessage.innerHTML='<font color="red" size="2">可用</font>';
  	  }
  }
  function emptyCode(){
     var codeInput=document.getElementById("codeValue");
     codeInput.value="******";
     codeInput.readOnly=true;
     $("codeGenDiv").style.display="none";
     $("cancelGenDiv").style.display="block";
  }
  function cancelGen(){
     var codeInput=document.getElementById("codeValue");
     codeInput.value="";
     codeInput.readOnly=false;
     $("cancelGenDiv").style.display="none";
     $("codeGenDiv").style.display="block";
  }
  </SCRIPT>
      <input type="hidden" name="teachProjectType.id" value="${teachProjectType.id?if_exists}" >
      </td>
      <td width="15%"  class="title"><@msg.message key="attr.createAt"/>: </td>
      <td width="35%" >${(teachProjectType.createAt?string("yyyy-MM-dd"))?if_exists}</td>
    </tr>
    <tr>
      <td id="f_name" class="title"><font color="red">*</font><@msg.message key="attr.infoname"/>:</td>
      <td ><input type="text" name="teachProjectType.name" value="${teachProjectType.name?if_exists}" maxLength="50"></td>	   
      <td  class="title"><@msg.message key="attr.modifyAt"/>:</td>
      <td >${(teachProjectType.modifyAt?string("yyyy-MM-dd"))?if_exists}</td>
    </tr>
    <tr>
      <td id="f_engName" class="title"><@msg.message key="attr.engName"/>:</td>
      <td ><input type="text" name="teachProjectType.engName"  value="${teachProjectType.engName?if_exists}" maxLength="100"/></td>	   
      <td class="title">是否使用:</td>
      <td><@htm.radio2  name="teachProjectType.state" value=teachProjectType.state?default(true)/></td>
    </tr>  
    <tr>
        <td id="f_teachProjectType" class="title">上级项目类别:</td>
		<td colspan="3">
	     	<select name="teachProjectType.superType.id" style="width:100%">
	     		<option value="">...</option>
		        <#list teachProjectTypes as superType>
			       <option value="${(superType.id)?if_exists}"<#if (teachProjectType.superType.id)?default("")?string == (superType.id)?string>selected</#if>><@i18nName superType/></option>
			    </#list>
	     	</select>
		</td>
	</tr>	
    <tr  class="darkColumn" align="center">
      <td colspan="6">
          <input type="button" onClick='save(this.form)' value="<@msg.message key="system.button.submit"/>" class="buttonStyle"/>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <input type="button" onClick='reset()' value="<@msg.message key="system.button.reset"/>" class="buttonStyle"/>&nbsp;&nbsp;&nbsp;           
      </td>
    </tr>
		

</form>
</table>
<SCRIPT language=javascript> 
    function reset(){
  	   document.teachProjectTypeForm.reset();
    } 
    function save(form){        
         var a_fields = {
         'teachProjectType.code':{'l':'代码', 'r':true, 't':'f_code','mx':'32'},
         'teachProjectType.name':{'l':'名称', 'r':true, 't':'f_name'}
     };
     var v = new validator(form , a_fields, null);
     if (v.exec()) {
        form.submit();
     }
   }  
  </SCRIPT>
</body> 
</html>
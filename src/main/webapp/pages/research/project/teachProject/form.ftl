<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
<script src="dwr/interface/baseinfoUtil.js"></script>
<script src="dwr/engine.js"></script>
<script src="dwr/util.js"></script>

<body>
<#assign labInfo>项目信息</#assign>  
<#include "/templates/back.ftl"/>   
	<table width="90%" align="center"  class="formTable">
		<form action="teachProject.do?method=save" name="teachProjectForm" method="post" onsubmit="return false;">
		<@searchParams/>
		<input type="hidden" name="teachProject.id" value="${(teachProject.id)?default('')}"/>
		<tr>
			<td id="f_code" align="right" class="title" width="20%"><font color="red">*</font>项目代码</td>
			<td align="bottom" width="30%">
			<input id="codeValue" type="text" name="teachProject.code" value="${(teachProject.code)?if_exists}" maxlength="50" style="width:100%"/>
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
  	  baseinfoUtil.checkCodeIfExists(afterCheck,"com.ekingstar.eams.research.project.model.TeachProject","code",valueCode);
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
			
			</td>
			<td id="f_name" align="right" class="title" width="20%"><font color="red">*</font>项目名称</td>
			<td align="bottom" width="30%">
			<input type="text" name="teachProject.name" value="${(teachProject.name)?if_exists}" maxlength="50" style="width:100%"/>
			</td>
		</tr>

		<tr>
			<td id="f_teachProjectType" class="title"><font color="red">*</font>项目类别:</td>
			<td>
	     	<select name="teachProject.teachProjectType.id" style="width:100%">
	     		<option value="">...</option>
		        <#list teachProjectTypes as teachProjectType>
			       <option value="${teachProjectType.id}"<#if (teachProject.teachProjectType.id)?default("")?string == (teachProjectType.id)?string>selected</#if>><@i18nName teachProjectType/></option>
			    </#list>
	     	</select>
			</td>
			<td id="f_teachProjectState" class="title">项目状态:</td>
			<td>
	     	<select name="teachProject.teachProjectState.id" style="width:100%">
		     	<option value="">...</option>
		        <#list teachProjectStates as teachProjectState>
			       <option value="${teachProjectState.id}"<#if (teachProject.teachProjectState.id)?default("")?string == (teachProjectState.id)?string>selected</#if>><@i18nName teachProjectState/></option>
			    </#list>
	     	</select>
			</td>
		</tr>
		<tr>
			<td  id="f_principal" class="title"><font color="red">*</font>项目负责人:</td>
			<td colspan="3">
				<input type="text" name="teachProject.principal" value="${(teachProject.principal)?if_exists}" maxlength="50" style="width:100%"/>
			</td>
		</tr>
		<tr>
			<td  id="f_describe" class="title">项目描述:</td>
			<td colspan="3">
	     		<textarea name="teachProject.describe" rows="5" style="width:100%;">${(teachProject.describe)?if_exists}</textarea>
			</td>
		</tr>
		<tr>
			<td colspan="4" align="center" class="darkColumn">
				<button onClick='save(this.form)'><@bean.message key="system.button.submit"/></button>
           			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" onClick='reset()' value="<@bean.message key="system.button.reset"/>"  class="buttonStyle"/> 
	     	</td>
		</tr>
		</form> 
    </table>
	<script language="javascript" > 
	    function save(form){
	        var a_fields = {
	         'teachProject.code':{'l':'项目代码', 'r':true, 't':'f_code'},
	         'teachProject.name':{'l':'项目名称', 'r':true, 't':'f_name','mx':200},
	         'teachProject.teachProjectType.id':{'l':'项目类别', 'r':true, 't':'f_teachProjectType'},
	         'teachProject.principal':{'l':'项目负责人', 'r':true, 't':'f_principal'}
	        };
		    var v = new validator(form , a_fields, null);
		    if (v.exec()) {
				form.submit();
		    }
	    }
	</script>
</body> 
</html>
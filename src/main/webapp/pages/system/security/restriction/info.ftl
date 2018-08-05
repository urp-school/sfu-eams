<#include "/templates/head.ftl"/>
 <body>
 <table id="restrictionBar"></table>
 <#if (restrictions?size==0)>没有设置</#if>
 <#list restrictions as restriction>
    <fieldSet  align=center> 
    <legend>${restriction.pattern.name}(${restriction.enabled?string("启用","禁用")}) <a href="#" onclick="edit('${restriction.id}')">修改</a>    <a href="#" onclick="remove('${restriction.id}')">删除</a></legend>
    
    <#list restriction.items?sort_by(["param","description"]) as p>
      <li>${p.param.description}</li>
          <#if p.param.editor?exists>
          <br><#list paramMaps[restriction.id?string][p.param.name]?if_exists as value>${value[p.param.editor.properties]}<#if value_has_next>,</#if></#list></td>
          <#else>
          <br>${paramMaps[restriction.id?string][p.param.name]?if_exists}
          </#if>
    </#list>
    </fieldSet>
</#list>
<br>
<#list patterns?if_exists as pattern>
 <li>${pattern.name} <a onclick="add('${pattern.id}')" href='#'>添加</a></li>
</#list>
<form name="actionForm" method="post">
	<input type="hidden" name="restrictionType" value="${RequestParameters['restrictionType']}"/>
	<input type="hidden" name="restriction.holder.id" value="${RequestParameters['restriction.holder.id']}"/>
	<input type="hidden" name="params" value="&restriction.holder.id=${RequestParameters['restriction.holder.id']}&restrictionType=${RequestParameters['restrictionType']}"/>
</form>
<script> 
   var bar = new ToolBar('restrictionBar','数据权限',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp();
   function edit(){
      self.location="restriction.do?method=edit&restriction.id=";
   }
   function add(patternId){
   		var form =document.actionForm;
   		addInput(form,"restriction.pattern.id",patternId);
   		form.action="restriction.do?method=edit";
   		form.submit();
   }
   function edit(restrictionId){
   		var form =document.actionForm;
   		addInput(form,"restriction.id",restrictionId);
   		form.action="restriction.do?method=edit";
   		form.submit();
   }
   function remove(restrictionId){
   		if(!confirm("确定删除?")) return;
   		var form =document.actionForm;
   		addInput(form,"restriction.id",restrictionId);
   		form.action="restriction.do?method=remove";
   		form.submit();
   }  
 </script>
 </body>
<#include "/templates/foot.ftl"/>
<#import "/templates/message.ftl" as msg>
<#assign bean=JspTaglibs["/WEB-INF/struts-bean.tld"]>
<#macro i18nName(entity)><#if .globals.localName?index_of("en")!=-1><#if entity.engName?if_exists?trim=="">${entity.name?if_exists}<#else>${entity.engName?if_exists}</#if><#else><#if entity.name?if_exists?trim!="">${entity.name?if_exists}<#else>${entity.engName?if_exists}</#if></#if></#macro>

<#macro i18nSelect datas selected  extra...><select <#list extra?keys as attr>${attr}="${extra[attr]?html}" </#list>>
    <#nested>
    <#list datas as data>
       <option value="${data.id}" <#if data.id?string=selected>selected</#if>><@i18nName data/></option>
    </#list>
</select></#macro>

<#macro radio2 name value>
  <input type="radio" value="1" name="${name}" <#if value>checked</#if> ><@bean.message key="common.yes" />
  <input type="radio" value="0" name="${name}" <#if (!value)>checked</#if> ><@bean.message key="common.no" />
</#macro>

<#macro select2 name selected hasAll extra...>
  <select name="${name}" <#list extra?keys as attr>${attr}="${extra[attr]?html}" </#list>>
    <#if hasAll><option value="" <#if selected=''>selected</#if>><@bean.message key="common.all" /></option></#if>
    <option value="1" <#if selected='1'>selected</#if>><@bean.message key="common.yes" /></option>
    <option value="0" <#if selected='0'>selected</#if>><@bean.message key="common.no" /></option>
  </select>
</#macro>


<!--name action-->
<#macro actionForm entity name action extra...>
	<form name="${name}" method="post">
	 <#nested>
	</form>
	<script>
	 var form = document.${name};
	 var action = "${action}";
	 resultQueryStr = "";
	 if(typeof queryStr != "undefined"){
	   resultQueryStr = queryStr;
	 }
	 
	 function beforSubmmit(method) {
	 	var ids = getSelectIds("${entity}Id");
	 	if (ids == null || ids == "") {
	 		alert("你没有选择要操作的记录！");
	 		return false;
	 	}
	 	
	    form.action = "${action}?method=" + method;
	    addHiddens(form,resultQueryStr);
		addParamsInput(form,resultQueryStr);
	    return true;
	 }
	 function edit() {
	 	if (beforSubmmit("edit")) {
			submitId(form, '${entity}Id', false);
	    }
	 }
	 
	 function info(giveId) {
   	    if(giveId==null){
   	       if (beforSubmmit("info")) {
   	        submitId(form, '${entity}Id', false);
   	       }
	    }else{
	      form.action = "${action}?method=info";
	       addInput(form,"${entity}Id",giveId, "hidden");
	        form.submit();
	    }
	 }
	 function remove(){
	    if (beforSubmmit("remove")) {
	    	submitId(form,"${entity}Id",true,null,"<@bean.message key="common.confirmAction"/>");
	    }
	 }
	 function add(){
	    form.action = "${action}?method=edit";
	    addHiddens(form,resultQueryStr);
	    addInput(form,'${entity}Id',"");
	    addParamsInput(form,resultQueryStr);
	    form.submit();
	 }
	 function multiAction(method,confirmMsg){
	    submitAction(method,true,confirmMsg);
	 }
	 function singleAction(method,confirmMsg){
	    submitAction(method,false,confirmMsg);
	 }
	 function singleAction(method,tar,confirmMsg){
	    submitAction(method,false,confirmMsg);
	    form.target = tar;
	 }	 
	 function submitAction(method,multiId,confirmMsg){
	    if (beforSubmmit(method)) {
		    if(null!=confirmMsg){
		       if(!confirm(confirmMsg))return;
		    }
		    submitId(form,"${entity}Id",multiId);
	    }
	 }
	 function exportList(){
	    form.action="${action}?method=export";
	    addHiddens(form,resultQueryStr);
	    form.submit();
	 }
	 function submitActionForm(){
  	   addHiddens(form,resultQueryStr);
  	   form.submit();
	 }
	 var multiIdAction=multiAction;
	 </script>
</#macro>

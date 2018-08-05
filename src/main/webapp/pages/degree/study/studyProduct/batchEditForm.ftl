<#include "/templates/head.ftl"/>
 <BODY>	
 <table id="backBar" width="100%"></table>
 	<@table.table   width="100%">
 	 <form name="editForm" method="post" action="" onsubmit="return false;">
 		<tr>
 			<td width="20%"><#if "studyThesis"==productType>出版物级别<#elseif "literature"==productType>著作类别<#elseif "project"==productType>项目类别<#elseif "studyMeeting"==productType>会议类别</#if></td>
 			<#assign nameType><#if "studyThesis"==productType>publicationLevel<#elseif "literature"==productType>literatureType<#elseif "project"==productType>projectType<#elseif "studyMeeting"==productType>meetingType</#if></#assign>
 			<td width="30%"><@htm.i18nSelect datas=studyProductTypes selected="" id="studyProductTypeId" name="product.${nameType}.id">
 					<option value="">请选择</option>
 			   </@></td>
 			<td width="20%">是否通过审核</td>
 			<td>
 				<select name="product.isPassCheck" align="center" style="width:100%">
 					<option value="">请选择</option>
 					<option value="true">通过</option>
 					<option value="false">不通过</option>
 				</select>
 			</td>
 		</tr>
 		<input type="hidden" name="productType" value="${RequestParameters['productType']}"/>
    </form>
 	</@>
    <#include "${RequestParameters['productType']}List.ftl"/>
<script>
    var bar = new ToolBar('backBar','批量修改列表',null,true,true);
    bar.setMessage('<@getMessage/>');
	bar.addItem("保存","batchSave()");
	bar.addBack();
	var form =document.editForm;
	var action="studyProduct.do";
	function batchSave(){
		var info="";
		var studyProductIds = getSelectIds("studyProductId");
		if ("" == studyProductIds) {
			alert("请选择要操作的记录。");
			return;
		}
		if(""!=form["product.${nameType}.id"].value){
			info+="\n成果类型为:"+DWRUtil.getText(form["product.${nameType}.id"]);
		}
		if(""!=form["product.isPassCheck"].value){
			info+="\n审核通过为:"+DWRUtil.getText(form["product.isPassCheck"]);
		}
		if(""!=info){
			info="你要批量修改对应的条件是:"+info;
		}else{
			alert("你没有选择任何条件!!")
			return;
		}
		if(confirm(info)){
			form.action=action+"?method=batchSave";
			addInput(form, "studyProductIds", studyProductIds, "hidden");
			setSearchParams(parent.form,form); 
			form.submit();
		}
	}
</script>
</body>
<#include "/templates/foot.ftl"/>
	
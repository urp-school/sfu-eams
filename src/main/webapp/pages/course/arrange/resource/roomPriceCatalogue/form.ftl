<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
    <table id="bar"></table>
	<form method="post" action="roomPriceCatalogue.do?method=saveCatalogue" name="roomPriceForm" target="_parent" onsubmit="return false;">
	    <input type="hidden" name="catalogue.id" value="${(roomPriceCatalogue.id)?default('')}"/>
	    <input type="hidden" name="isDefaultSetting" value="${(isDefaultSetting?string("true", "false"))?default("false")}"/>
	    <table class="formTable" align="center" width="70%">
	        <tr>
	            <td class="darkColumn" align="center" colspan="2"><b><#if isDefaultSetting?exists && isDefaultSetting>配置默认的<#else><#if roomPriceCatalogue?exists>修改<@i18nName roomPriceCatalogue.schoolDistrict/>校区的<#else>新增校区的</#if></#if>归口审核部门</b></td>
	        </tr>
	        <tr>
	           <td class="title">所在校区：</td>
	           <td><#if isDefaultSetting?exists && isDefaultSetting>配置默认校区价目表，无需选择校区<#else><@htm.i18nSelect datas=schoolDistricts?sort_by("name") selected=(roomPriceCatalogue.schoolDistrict.id?string)?default("") name="catalogue.schoolDistrict.id"/></#if></td>
	        </tr>
	        <tr>
	            <td class="title">发布部门：</td>
	            <td>
	                <@htm.i18nSelect datas=departments?sort_by("name") selected=(roomPriceCatalogue.department.id?string)?default("") name="catalogue.department.id"/>
	            </td>
	        </tr>
	        <tr>
	            <td class="title" id="f_selectedDepartments"><font color=red>*</font>审核部门：</td>
	            <td>
	                <table>
	                    <tr>
	                        <td>
			                    <select name="departments" MULTIPLE size="10" style="width:200px" onDblClick="JavaScript:moveSelectedOption(this.form['departments'], this.form['selectedDepartments'])">
	                                <#list toSelectDeparts as department>
	                                    <option value="${department.id}"><@i18nName department/></option>
	                                </#list>
			                    </select>
	                        </td>
	                        <td>
	                            <button onclick="JavaScript:moveSelectedOption(this.form['departments'], this.form['selectedDepartments'])">→</button>
	                            <br>
	                            <button onclick="JavaScript:moveSelectedOption(this.form['selectedDepartments'], this.form['departments'])">←</button>
	                        </td>
	                        <td>
			                    <select  name="selectedDepartments" MULTIPLE size="10" style="width:200px" onDblClick="JavaScript:moveSelectedOption(this.form['selectedDepartments'], this.form['departments'])">
                                    <#list (roomPriceCatalogue.auditDeparts)?if_exists as auditDepart>
                                        <option value="${auditDepart.id}"><@i18nName auditDepart/></option>
                                    </#list>
	                        </td>
	                    </tr>
	                </table>
	            </td>
	        </tr>
	        <tr>
	            <td class="title" id="f_publishedOn"><font color=red>*</font>发布日期：</td>
	            <td>
	                <input type="text" name="catalogue.publishedOn" onfocus="calendar()" value="${(roomPriceCatalogue.publishedOn)?default(nowAt)?string("yyyy-MM-dd")}" maxlength="10"/>
	            </td>
	        </tr>
	        <tr>
	            <td class="title" id="f_remark"><font color=red>*</font>备注：</td>
	            <td>
	                <textarea name="catalogue.remark">${(roomPriceCatalogue.remark)?default('')}</textarea>
	            </td>
	        </tr>
            <tr>
                <td class="darkColumn" align="center" colspan="2"><button onclick="save()">保存</button></td>
            </tr>
	    </table>
	</form>
	<script>
	    var bar = new ToolBar("bar","<#if isDefaultSetting?exists && isDefaultSetting>配置默认的<#else><#if roomPriceCatalogue?exists>修改<@i18nName roomPriceCatalogue.schoolDistrict/>校区的<#else>新增校区的</#if></#if>教室级差价目表",null,true,true);
	    bar.setMessage('<@getMessage/>');
	    bar.addItem("<@msg.message key="action.save"/>", "save()");
	    bar.addBack("<@msg.message key="action.back"/>");
	    
	    function save(){
	       <#if !isDefaultSetting?exists && schoolDistricts?size == 0>
	           alert("当前没有任何校区可选，请先在基础代码中添加校区后，再继续。");
	           return;
	       </#if>
	     var form = document.roomPriceForm;
	     if (getAllOptionValue(document.roomPriceForm['selectedDepartments']) == "") {
	     	alert("审核部门必须填写！");
	     	return;
	     }
	     
	     var a_fields = {
              'catalogue.publishedOn':{'l':'发布日期', 'r':true, 't':'f_publishedOn', 'f':'date'},
	          'catalogue.remark':{'l':'备注', 'r':true, 't':'f_remark','mx':200}
	     };
	     var v = new validator(form, a_fields, null);
	     if (v.exec()) {
	        addInput(document.roomPriceForm, "selectedDepartmentIds", getAllOptionValue(document.roomPriceForm['selectedDepartments']));
	        document.roomPriceForm.submit();
	     }
	    }
	</script>
</body>
<#include "/templates/foot.ftl"/>
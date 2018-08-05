<#include "/templates/head.ftl"/>
 <BODY>	
 <table id="backBar" width="100%"></table>
    <#include "../studyProduct/${RequestParameters['productType']}List.ftl"/>
    <form name="actionForm" method="post" action="" onsubmit="return false;">
      	<input type="hidden" name="productType" value="${RequestParameters['productType']}"/>
    </form>
<script>
 var passChecked = new Object();
 <#list studyProducts as product>
 passChecked['${product.id}']=${product.isPassCheck?string};
 </#list>
	   var bar = new ToolBar('backBar','查询结果列表',null,true,true);
	   bar.setMessage('<@getMessage/>');
	   bar.addItem("<@msg.message key="action.info"/>","info()");
	   bar.addItem("<@msg.message key="action.add"/>","add()");
	   bar.addItem("<@msg.message key="action.edit"/>","edit()");
	   bar.addItem("添加获奖","addAwardInfo()");
	var form =document.actionForm;
	action="studyProductStd.do";
	function info(){
	   form.action=action+"?method=info";
	   submitId(form,"studyProductId",false);
	}
	function addAwardInfo(){
	   form.action=action+"?method=editAward";
	   submitId(form,"studyProductId",false);
	}
	function add(){
	   form.action=action+"?method=edit";
	   form.submit();
	}
	function edit(){
	    form.action=action+"?method=edit";
	    var ids = getSelectIds("studyProductId");
	    if(""==ids||isMultiId(ids)){
	       alert("请选择一个");return;
	    }
	    if(passChecked['ids']){
	       alert("你选择的科研成果已经通过审核,不能修改!");return;
	    }
	    submitId(form,"studyProductId",false);
	}
	orderBy =function(what){
		parent.search(1,${pageSize},what);
	}
	function pageGoWithSize(pageNo,pageSize){
       parent.search(pageNo,pageSize,'${RequestParameters['orderBy']?default('null')}');
    }
</script>
</body>
<#include "/templates/foot.ftl"/>
	
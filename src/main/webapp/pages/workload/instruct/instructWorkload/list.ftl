<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
    <table id="backBar" width="100%"></table>
	<script>
		var bar = new ToolBar('backBar','工作量具体操作',null,true,true);
		bar.setMessage('<@getMessage/>');
		var manageMenu = bar.addMenu('管理工具',null);
		manageMenu.addItem("查看详细信息","detailObject()");
		manageMenu.addItem("修改","updateObject()");
		manageMenu.addItem("删除","deleteObject()");
		manageMenu.addItem("导出","exportObject()");
		var teacherAffirmMenu = bar.addMenu('教师确认',null,'detail.gif');
		teacherAffirmMenu.addItem("确认","affirmBySelect('teacherAffirm','true')");
		teacherAffirmMenu.addItem("取消确认","affirmBySelect('teacherAffirm','false')");
		var caculateMenu = bar.addMenu('计工作量确认',null,'detail.gif');
		caculateMenu.addItem('确认','affirmBySelect("calcWorkload","true")','detail.gif')
		caculateMenu.addItem('取消确认','affirmBySelect("calcWorkload","false")','detail.gif');
		var payMenu = bar.addMenu('支付报酬确认',null,'detail.gif');
		payMenu.addItem('确认','affirmBySelect("payReward","true")','detail.gif')
		payMenu.addItem('取消确认','affirmBySelect("payReward","false")','detail.gif');
	</script>
	<#include "instructWorkloadList.ftl"/>	
	<#list 1..5 as i><br></#list>
	<script language="javascript">
		var form = document.selectForm;
		function detailObject(){
			form.action="instructWorkload.do?method=loadDetail";
			submitId(form,"instructWorkloadId",false);
		}
		function updateObject(){
			form.action="instructWorkload.do?method=loadUpdate";
			submitId(form,"instructWorkloadId",false);
		}
		function deleteObject(){
			form.action="instructWorkload.do?method=doDelete";
			setSearchParams(parent.form,form);
			submitId(form,"instructWorkloadId",true,"","你确定要删除吗?\n删除了数据就无法找回!!");
		}
		function exportObject(){
			form.action="instructWorkload.do?method=export";
			transferParams(parent.form,form,"instructWorkload");
			form.submit();
		}
	    function affirmBySelect(affirmType,affirmEstate){
	    		var ids = getSelectIds("instructWorkloadId");
	    		if(""==ids)
	    		{alert("请选择一些你要确认的选项");
	    			return;
	    		}
	    		form.action="instructWorkload.do?method=doAffirmSelect&instructWorkloadIds="+ids
	    					+"&affirmType="+affirmType
	    					+"&affirmEstate="+affirmEstate;
	    		setSearchParams(parent.form,form);
	    		form.submit();
	    }
	    function affirmAll(affirmType,affirmEstate){
	      	if(confirm("你确定要确认所有的选项吗?\n修改全部以后数据就不能恢复!!!")){
	      		form.action="instructWorkload.do?method=affirmAll&affirmType="+affirmType
	      					+"&affirmEstate="+affirmEstate;
	      		setSearchParams(parent.form,form);
	      		form.submit();
	      	}
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
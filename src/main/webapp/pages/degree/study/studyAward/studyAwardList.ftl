<#include "/templates/head.ftl"/>
 <BODY>	
 <table id="backBar" width="100%"></table>
    <#include "${RequestParameters['productType']}AwardList.ftl"/>
    <form name="actionForm" method="post" action="" onsubmit="return false;">
      <input type="hidden" name="productType" value="${RequestParameters['productType']}"/>
      <input type="hidden" id="keys" name="keys" value="student.type.name,student.department.name,student.code,student.name,">
      <input type="hidden" id="titles" name="titles" value="<@msg.message key="entity.studentType"/>,<@msg.message key="entity.department"/>,<@msg.message key="attr.stdNo"/>,<@msg.message key="attr.personName"/>,">
    </form>
<script>
	   var bar = new ToolBar('backBar','查询结果列表',null,true,true);
	   bar.setMessage('<@getMessage/>');
	   bar.addItem("<@msg.message key="action.edit"/>","edit()");
	   bar.addItem("<@msg.message key="action.delete"/>","multiIdAction('remove')");
	   bar.addItem("审核通过","multiIdAction('check&isPassCheck=1')")
	   bar.addItem("取消通过","multiIdAction('check&isPassCheck=0')");
	   bar.addItem("<@msg.message key="action.export"/>","exportObject()");
	   bar.addPrint("<@msg.message key="action.print"/>");
	var form =document.actionForm;
	action="studyAward.do";
	function add(){
	   form.action=action+"?method=edit";
	   form.submit();
	}
	function edit(){
	   form.action=action+"?method=edit";
	   submitId(form,"studyAwardId",false);
	}
	function exportObject(){
	   form.action =action+"?method=export";
	   form.submit();
	}
    function multiIdAction(actionType){
       form.action=action+"?method="+actionType;
       setSearchParams(parent.document.searchForm,form);
       submitId(form,"studyAwardId",true,null,"确认该操作？");
    }
	orderBy =function(what){
		parent.search(1,${pageSize},what);
	}
	function pageGoWithSize(pageNo,pageSize){
       parent.search(pageNo,pageSize,'${RequestParameters['orderBy']?default('null')}');
    }
    var keyTitleObject ={
        "studyThesisTitle":"论文题目,刊物名称,刊物等级,期刊号,发表时间,本人独立完成字数(万),科研排序,论文备注",
    	"studyThesisKey":"studyThesis.name,studyThesis.publicationName,studyThesis.publicationLevel.name,studyThesis.publicationNo,studyThesis.publishOn,studyThesis.wordCount,studyThesis.rate,studyThesis.remark",
    	"literatureTitle":"著作名称,著作分类,出版时间,本人独立完成字数(万),科研排序,著作备注",
    	"literatureKey":"literature.name,literature.literatureType.name,literature.publishOn,literature.wordCount,literature.rate,literature.remark",
    	"projectTitle":"项目名称,立项编号,立项单位,立项日期,结项日期,承担任务,责任人,项目类别,本人独立完成字数(万),科研排序,项目备注",
    	"projectKey":"project.name,project.projectNo,project.company,project.startOn,project.endOn,project.bearTask,project.principal,project.projectType.name,project.wordCount,project.rate,project.remark"
    };
    var key= document.getElementById("keys");
    key.value+=keyTitleObject["${RequestParameters['productType']}Key"]+",awardName,awardedOn,departmentName,type.name,remark";
    var title= document.getElementById("titles");
    title.value+=keyTitleObject["${RequestParameters['productType']}Title"]+",获奖名称,获奖时间,颁奖单位,科研获奖等级,获奖备注";
    //alert(key.value);
    //alert(title.value);
</script>
</body>
<#include "/templates/foot.ftl"/>
	
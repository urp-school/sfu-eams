<#include "/templates/head.ftl"/>
<body >
  
  <table id="examActivityBar"></table>
  <#include "../examiner/listResult.ftl"/>
  <script>
     var bar=new ToolBar('examActivityBar','排考结果列表',null,true,false);
     bar.setMessage('<@getMessage/>');
     bar.addItem("<@msg.message key="action.export"/>","exportData()","excel.png");
     var menu1 =bar.addMenu("批量设置主考老师","batchSet('batchSetExaminers1')");
     menu1.addItem("批量设置监考老师","batchSet('batchSetInvigilators')");

    var form = document.actionForm;
    form["examActivityIds"].value = "";
    form["extraCount"].value = "";
    
    function batchSet(type){
       var examActivityIds = getSelectIds("examActivityId");
	   if(""==examActivityIds){alert("请选择考试安排进行设置");return;}
       form.target="_blank";
       form.action = "examiner.do?method=" + type;
       form["examActivityIds"].value = examActivityIds;
       form.submit();
    }
    function exportData(){
       var examActivityIds = getSelectIds("examActivityId");
	   if(""==examActivityIds){alert("请选择考试安排进行设置");return;}
	   form["examActivityIds"].value = examActivityIds;
       form.action="examinerDepart.do?method=export";
       form.target="";
       form.submit();
    }
  </script>
</body> 
<#include "/templates/foot.ftl"/> 
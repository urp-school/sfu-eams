<#include "/templates/head.ftl"/>
<body>
<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','修改${student.name?if_exists}的论文评阅书',null,true,true);
   bar.setMessage('<@getMessage/>');
   var message = document.getElementById("message");
   bar.addItem("提交","stdSubmit(document.listForm)");
</script>
<#assign disPlay=false>
<#include "selfEvaluateTable.ftl">
</body>
<#include "/templates/foot.ftl"/>
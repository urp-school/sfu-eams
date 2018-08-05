<#include "/templates/head.ftl"/>
<style type="text/css">
<!--
.rectb {
	border: 2px solid #808080;
}
.rectd {
	border-bottom: 2px solid #808080;
	border-right: 1px solid #000000;
	background-color:#c7dbff;
	position:relative;
}
.rectdr {
	BORDER-RIGHT: #000 1px solid;
	BORDER-TOP: #000 0px solid;
	BORDER-LEFT: #000 0px solid;
	BORDER-BOTTOM: #000 1px solid;
	position:relative;
	text-align: center;	
	width:200px;
}
.rectdh {
	BORDER-RIGHT: #000 1px solid;
	BORDER-TOP: #000 0px solid;
	BORDER-LEFT: #000 0px solid;
	BORDER-BOTTOM: #000 1px solid;
	background-color:#c7dbff;
	position:relative;
	text-align: center;
	z-index:4;
	left:expression(this.parentElement.offsetParent.parentElement.scrollLeft);	
}
-->
</style>
<style type="text/css">
tr {
	background-color: #ffffff;
}
</style>
<body>
<table id="backBar"></table>
<script>
   var bar = new ToolBar('backBar','${building.name}教室占用情况',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addPrint("<@msg.message key="action.print"/>");
   bar.addHelp("<@msg.message key="action.help"/>");
</script>
   <#assign unitList=calendar.timeSetting.courseUnits>
   <#assign unitSize=unitList?size>
   <#assign weekSize=weekList?size>
	<table>
		<tr>
			<td>${building.name}教室利用率：</td>
			<td><#if unitMap?size * occupyTables?size!=0>${(occupyCount*100/(unitMap?size*occupyTables?size))?string("##.##")}%<#else>0.0%</#if></td>
		</tr>
	</table>
 <div id="div1" style="height:90%;width:100%;overflow:auto;cursor:default;" class="rectb">  
   <table width="100%" height="100%" bgcolor="#000000" style="DISPLAY: inline; font-size:12px; border-collapse:collapse" cellpadding="2" cellspacing="0">
  	<tr style="z-index:5; position: relative; top:expression(this.offsetParent.scrollTop);">
		<th class="rectd" style="z-index:6;left:expression(document.getElementById('div1').scrollLeft)">&nbsp;</th>
		<#list weekList as week>
	    <th colspan="${unitSize}" align="center" class="rectd"><@i18nName week/></th>
	    </#list>
	</tr>
	<tr style="z-index:5; position: relative; top:expression(this.offsetParent.scrollTop);">
		<th class="rectd" style="z-index:6;left:expression(document.getElementById('div1').scrollLeft)">教室</th>
		<#list weekList as week>
  	    <#list 1..unitList?size as unit>
   		<th class="rectd" style="width:200px">${unit_index+1}</th>
		</#list>
		</#list>
	</tr>
	<#list occupyTables?if_exists as occupyTable>
		<tr>
			<td class="rectdh">${occupyTable.classroom.name}</td>
			<#list weekList as week>
  	    	<#list 1..unitList?size as unit>
   				<td id="TD${week_index*unitList?size+unit_index}_${occupyTable.classroom.id}" class="rectdr">&nbsp;</td>
			</#list>
			</#list>
		</tr>
	</#list>
</table>
</div>
<script>
    $("message").innerHTML = "正在统计中...";
    function initTable() {
        <#list occupyTables?if_exists as occupyTable>
                <#list weekList as week>
                <#list 1..unitList?size as unit>
                if(${unit_index}==13 && ${week_index}!=6){
                    document.getElementById("TD${week_index*unitList?size+unit_index}_${occupyTable.classroom.id}").style.borderRightWidth="2";
                    document.getElementById("TD${week_index*unitList?size+unit_index}_${occupyTable.classroom.id}").style.borderRightColor="#006CB2";
                }
                </#list>
                </#list>
        </#list>
                <#assign unitSize=unitList?size>
        <#list occupyTables?if_exists as occupyTable>
            <#list occupyTable.activities?if_exists as activity>
                document.getElementById("TD${activity.unit}_${occupyTable.classroom.id}").innerText="${activity.detail}";
            </#list>
        </#list>
        <#list occupyTables?if_exists as occupyTable>
            for(var i=0;i<7;i++){
                for(var j=0;j<${unitSize}-1;j++){
                    var index =${unitSize}*i+j;
                    var preTd=document.getElementById("TD"+index+"_"+${occupyTable.classroom.id});
                    var nextTd=document.getElementById("TD"+(index+1)+"_"+${occupyTable.classroom.id});
                    while(preTd.innerText!=" " && nextTd.innerText!=" " && preTd.innerText==nextTd.innerText){
                        preTd.parentNode.removeChild(nextTd);
                        var spanNumber = new Number(preTd.colSpan);
                        spanNumber++;
                        preTd.colSpan=spanNumber;
                        j++;
                        if(j>=${unitSize}-1){
                            break;
                        }
                        index=index+1;
                        nextTd=document.getElementById("TD"+(index+1)+"_"+${occupyTable.classroom.id});
                    }
                }
            }
        </#list>
        $("message").innerHTML = "";
        $("message").style.display = "none";
    }
    setTimeout("initTable()", 5000);
</script>
</body> 
<#include "/templates/foot.ftl"/> 


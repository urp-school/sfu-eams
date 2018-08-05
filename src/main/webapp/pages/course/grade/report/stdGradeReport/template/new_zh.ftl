[#ftl]
[#assign fontsize=10/]
    <style>
        .semester{
            text-align:center;
            font-size:16px;
            font-family:楷体;
            border-top:2px #000 solid;
            border-right:2px #000 solid;
            border-left:2px #000 solid;
            border-bottom:2px #000 solid;
        }
        .blank{
            text-align:center;
            font-size:16px;
            font-family:楷体;
            border-right:2px #000 solid;
            border-left:2px #000 solid;
        }
        .tds{
            text-align:center;
        }
        .tableclass{
            border-collapse:collapse;
            font-size:14px;
            border-top:2px #000 solid;
            border-right:2px #000 solid;
            border-left:2px #000 solid;
            border-bottom:2px #000 solid;
        }
        .titlecss{
            text-align:center;
            font-size:14px;
            width:250px;
            font-family:楷体;
        }
        .title{
            text-align:center;
            font-size:14px;
            font-family:楷体;
            width:50px;
        }
    </style>
    [#--最大成绩行数--]
    [#assign maxRows = 35/]
    [#assign maxCols = 16/]
    [#--每列最大学期数--]
[#list stdGradeReports as report]
   [#assign schoolName]${systemConfig.school.name}[/#assign]
[#assign std=report.std/]
    [#assign stdTypeName = report.std.type.name /]
    <div  style="padding-left:0px;[#if report_index>0]PAGE-BREAK-BEFORE: always[/#if]">
    <table  width='1460px'  valign='top' >
        <tr><td colspan="5" align="center"><h1>${schoolName}${(report.std.enrollYear + "级")?replace("-3级","(春季)级")?replace("-9级","(秋季)级")}${stdTypeName}学生成绩单表</h1></td></tr>
        <tr style="font-size:14px">
         <td >层&nbsp;&nbsp;&nbsp;&nbsp;次：[#if stdTypeName?contains("本科")]本科[#elseif stdTypeName?contains("专升本")]专升本[#else]专科[/#if]</td>
         <td >专&nbsp;&nbsp;&nbsp;&nbsp;业：${(std.major.name)?default("")}</td>
         <td >姓&nbsp;&nbsp;&nbsp;&nbsp;名：${std.name}</td>
         <td >学&nbsp;&nbsp;&nbsp;&nbsp;号：${((std.code)?default(""))?trim}</td>
         <td >性别：${((std.gender.name)?default(""))?trim}</td>
        </tr>
        </table>
        <table width='100%' border="1" id="transcript${std.id}" class="tableclass">
            [#list 1..maxRows as row]
                <tr height='20px'  >[#list 0..15 as col]<td  id="transcript${std.id}_${(col/4)?int*4*maxRows+(col%4)+(row-1)*4}" [#if col==3 || col==7 || col=11] style="border-right:2px #000 solid;" [/#if] [#if col==4 || col==8 || col==12] width="250px"  [/#if]  [#if col==5 || col==6 || col==7||  col==9 || col==10 || col==11 || col==13 || col==14 || col==15] width="50px"  [/#if]>&nbsp;</td>[/#list]</tr>
            [/#list]
        </table>
    <script>
    var index=0;
    var semesterCourses={};
    var nowsemsenumber=0;
    var blankRow=0;
    var array =[];
    //统计学期总个数，并且去掉重复元素
    function semsernumber(semsename){
         array.push(semsename);
         array = array || [];
         var a = {};
        for (var i=0; i<array.length; i++) {
            var v = array[i];
            if (typeof(a[v]) == 'undefined'){
                a[v] = 1;
            }
        };
        array.length=0;
        for (var i in a){
           array[array.length] = i;
        }
    }
    
    //移除重复元素
    function unique(){
        data = data || [];
        var a = {};
        for (var i=0; i<data.length; i++) {
            var v = data[i];
            if (typeof(a[v]) == 'undefined'){
                a[v] = 1;
            }
        };
        data.length=0;
        for (var i in a){
            data[data.length] = i;
        }
        return data.length;
   }
   
    //添加学期和以下空白等说明
    function setTitle(table,tbindex,value){
        var td = document.getElementById(table+"_"+tbindex);
        td.innerHTML=value;
        td.parentNode.removeChild(td.nextSibling);
        td.parentNode.removeChild(td.nextSibling);
        td.parentNode.removeChild(td.nextSibling);
        td.colSpan=4;
        if(value!="以下空白"){
            td.className="semester";
        }else{
            td.className="blank";
        }
    }
    function calcRow(tdindex){
        return parseInt(tdindex/${maxRows*4})*4 + tdindex%4;
    }
    
    function calcCol(tdindex){
        return parseInt((tdindex-parseInt(tdindex/${maxRows*4})*4*${maxRows})/4) +1;
    }
    
    //添加表头
    function addtitle(table){
        var col = calcRow(index);
        var row = calcCol(index);
        if(row>${maxRows} || col >= ${maxCols}) {
            return;
        }
        document.getElementById(table+"_"+(index)).className="titlecss";
        document.getElementById(table+"_"+(index)).innerHTML="课程名称";
        document.getElementById(table+"_"+(index+1)).className="title";
        document.getElementById(table+"_"+(index+1)).innerHTML="课程类型";
        document.getElementById(table+"_"+(index+2)).className="title";
        document.getElementById(table+"_"+(index+2)).innerHTML="学分";
        document.getElementById(table+"_"+(index+3)).className="title";
        document.getElementById(table+"_"+(index+3)).innerHTML="成绩";
        index+=4;
    }
    //添加学年学期
    function addSemester(table,semesterId,value){
        nowsemsenumber++;//当前是第几个学期
        var col = calcRow(index);
        var row = calcCol(index);
        var myCourseCnt= semesterCourses['c'+semesterId];
        if(row>${maxRows} || col >= ${maxCols}) {
            return;
        }
        if(array.length<=8 &&( nowsemsenumber==3 || nowsemsenumber ==5 || nowsemsenumber ==7)){
            //转到下一列的第一行
            addBlank(table);
            index=${maxRows}*(col+4);
            if(calcRow(index)>${maxRows}|| calcCol(index) >= ${maxCols}) {return;}
        }
        if( array.length>8 && (${maxRows} - row-1) < myCourseCnt){
            //转到下一列的第一行
            addBlank(table);
            index=${maxRows}*(col+4);
            if(calcRow(index)>${maxRows}|| calcCol(index) >= ${maxCols}) {return;}
        }
        setTitle(table,index,value);
        index+=4;
    }
    
    //添加以下空白
    function addBlank(table){
        var col = calcRow(index);
        var row = calcCol(index);
        if(row>${maxRows}|| col >= ${maxCols}) {return;}
        //空白行不放在第一行
        if(row==1)return;
        setTitle(table,index,"以下空白");
    }
    
    function addScore(table,name,courseTypeName,credit,score){
        var col = calcRow(index);
        var row = calcCol(index);
        if(row>${maxRows} || col >= ${maxCols}) {return;}
        document.getElementById(table+"_"+(index)).innerHTML=name;
        document.getElementById(table+"_"+(index+1)).className="tds";
        document.getElementById(table+"_"+(index+2)).className="tds";
        document.getElementById(table+"_"+(index+3)).className="tds";
        document.getElementById(table+"_"+(index+1)).innerHTML=courseTypeName;
        document.getElementById(table+"_"+(index+2)).innerHTML=credit;
        document.getElementById(table+"_"+(index+3)).innerHTML=score;
        index+=4;
        if (blankRow<row){
            blankRow=row;
        }
    }
    /**累计每学期课程*/
    function addSemesterCourse(semesterId){
        //学年学期占一行,所以初始为1
        if(typeof semesterCourses['c'+semesterId] == "undefined")
            semesterCourses['c'+semesterId]=1;
        else semesterCourses['c'+semesterId]= semesterCourses['c'+semesterId]+1;
    }
     /**添加备注*/
    function addRemark(table,content){
		var col = calcRow(index);
		var row = calcCol(index);
		if(row>${maxRows} || col >= ${maxCols}) {return;}
		document.getElementById(table+"_"+(index)).innerHTML=content;
		document.getElementById(table+"_"+(index)).colSpan=4
		var parentNode = document.getElementById(table+"_"+(index)).parentNode
		parentNode.removeChild(document.getElementById(table+"_"+(index+1)));
		parentNode.removeChild(document.getElementById(table+"_"+(index+2)));
		parentNode.removeChild(document.getElementById(table+"_"+(index+3)));
		index+=4;
		if (blankRow<row) blankRow=row;
	}
    [#list report.grades as g]addSemesterCourse('${g.calendar.id}');semsernumber('${g.calendar.id}');[/#list]
    [#assign semester_id=0]
    [#list report.grades?sort_by(["course","code"])?sort_by(["calendar","start"])  as courseGrade]
        [#if courseGrade.calendar.id  != semester_id]
            [#assign semester_id=courseGrade.calendar.id]
            addSemester("transcript${std.id}",'${courseGrade.calendar.id}','${courseGrade.calendar.year!}'+"学年第"+'[#if courseGrade.calendar.term='1']一[#elseif courseGrade.calendar.term='2']二[#else]${courseGrade.calendar.term}[/#if]'+"学期");
            addtitle("transcript${std.id}");
        [/#if]
        addScore("transcript${std.id}" ,'${courseGrade.course.name}','${courseGrade.course.code[0..0]}','${courseGrade.course.credits!}','${courseGrade.getScoreDisplay(setting.gradeType)}');
    [/#list]
    addBlank("transcript${std.id}");
        function removeTr(){
            blankRow=blankRow+1;
            if(${maxRows}-blankRow>0){
                var t1=document.getElementById("transcript${std.id}");
                var maxr =${maxRows};
                for(var i=0;i<=maxr;i++){
                    if(i>blankRow){
                        t1.deleteRow(blankRow);
                    }
                }
            }
        }
        removeTr();
	 index= ((${maxRows}*3-1)*4)+((blankRow-7)*4)
	var title = document.getElementById("transcript${std.id}_"+(index));
	title.className="semester"
	addRemark("transcript${std.id}",'课程类型备注');
	addRemark("transcript${std.id}",'1 代表公共基础必修课');
	addRemark("transcript${std.id}",'2 代表学科基础必修课');
	addRemark("transcript${std.id}",'3 代表专业方向必修课');
	addRemark("transcript${std.id}",'4 代表学科基础选修课');
	addRemark("transcript${std.id}",'5 代表专业方向选修课');
	addRemark("transcript${std.id}",'6 代表实践教学课');
	addRemark("transcript${std.id}",'7 代表公共基础选修课');
    </script>
	 <table width='100%' border=0 valign='bottom' style="font-family:宋体;font-size:${fontsize+2}px;">
	<tr>
	<td align='left' id="TD_TC">总学分:${report.credit?if_exists}</td>
	<td align='left' id="TD_GPA">平均绩点:[@reserve2 stdGPMap[report.std.id?string].GPA/]</td>
	<td align='right' >[@i18nName systemConfig.school/]教务处</td>
	<td align='right' width="100px" >${now?string('yyyy年MM月dd日')}</td>
	</tr>
	</table>
   </div>
[/#list]

<#include "/templates/head.ftl"/>
<body>
    <table id="bar"></table>
    <table width="99%" align="center" class="infoTable">
        <tr>
            <td colspan="4" class="darkColumn">教师基本信息</td>
        </tr>
        <tr>
            <td width="15%" class="title">职工号:</td>
            <td width="35%">${teacher.code}</td>
            <td width="15%" class="title">名称:</td>
            <td width="35%">${teacher.name?html}</td>
        </tr>
        <tr>
            <td class="title">性别:</td>
            <td><@i18nName (teacher.gender)?if_exists/></td>
            <td class="title">证件号:</td>
            <td>${(teacher.credentialNumber?html)?default(" ")}</td>
        </tr>
        <tr>
            <td class="title">国籍:</td>
            <td><@i18nName (teacher.country)?if_exists/></td>
            <td class="title">院系所:</td>
            <td><@i18nName (teacher.department)?if_exists/></td>
        </tr>
        <tr>
            <td class="title">民族:</td>
            <td><@i18nName (teacher.nation)?if_exists/></td> 
            <td class="title">教职工类别:</td>
            <td><@i18nName (teacher.teacherType)?if_exists/></td>
        </tr>
        <tr>
            <td class="title">出生日期:</td>
            <td colspan="3">${(teacher.birthday?string("yyyy-MM-dd"))?default(" ")}</td>
        </tr>
        <tr>
            <td colspan="4" class="darkColumn">联系信息</td>
        </tr>
        <tr>
            <td class="title">单位电话: </td>
            <td>${(teacher.addressInfo.phoneOfCorporation?html)?default(" ")}</td>
            <td class="title">单位邮编: </td>
            <td>${(teacher.addressInfo.postCodeOfCorporation?html)?default(" ")}</td>
        </tr>
        <tr>
            <td class="title">单位地址: </td>
            <td colspan="3">${(teacher.addressInfo.corporationAddress?html)?default(" ")}</td>
        </tr>
        <tr>
            <td class="title">家庭电话: </td>
            <td>${(teacher.addressInfo.phoneOfHome?html)?default(" ")}</td>
            <td class="title">家庭邮编: </td>
            <td>${(teacher.addressInfo.postCodeOfFamily?html)?default(" ")}</td>
        </tr>
        <tr>
            <td class="title">家庭地址: </td>
            <td colspan="3">${(teacher.addressInfo.familyAddress?html)?default(" ")}</td>
        </tr>
        <tr>
            <td class="title">移动电话: </td>
            <td>${(teacher.addressInfo.mobilePhone?html)?default(" ")}</td>
            <td class="title">传真: </td>
            <td>${(teacher.addressInfo.fax?html)?default(" ")}</td>
        </tr>
        <tr>
            <td email" class="title">电子邮件: </td>
            <td colspan="3">${(teacher.addressInfo.email?html)?default(" ")}</td>
        </tr>
        <tr>
            <td class="title">主页: </td>
            <td colspan="3">${(teacher.addressInfo.homepage?html)?default(" ")}</td>
        </tr>
        <tr>
            <td colspan="4" class="darkColumn">学历学位职称信息</td>
        </tr>
        <tr>
            <td class="title">学历:</td>
            <td><@i18nName (teacher.degreeInfo.eduDegreeInside)?if_exists/></td>
            <td class="title">现学历年月:</td>
            <td>${(teacher.degreeInfo.dateOfEduDegreeInside?string("yyyy-MM-dd"))?default(" ")}</td>
        </tr>
        <tr>
            <td class="title">学位:</td>
            <td><@i18nName (teacher.degreeInfo.degree)?if_exists/></td>
            <td class="title">现学位年月:</td>
            <td>${(teacher.degreeInfo.dateOfDegree?string("yyyy-MM-dd"))?default(" ")}</td>
        </tr>
        <tr>
            <td class="title">毕业学校:</td>
            <td colspan="3"><@i18nName (teacher.degreeInfo.graduateSchool)?if_exists/></td>
        </tr>
        <tr>
            <td class="title">职称:</td>
            <td><@i18nName (teacher.title)?if_exists/></td>
            <td class="title">现职称年月:</td>
            <td>${(teacher.dateOfTitle?string("yyyy-MM-dd"))?default(" ")}</td>
        </tr>
        <tr>
            <td colspan="4" class="darkColumn">其他信息</td>
        </tr>
       <tr>
            <td class="title">来校年月: </td>
            <td>${(teacher.dateOfJoin?string("yyyy-MM-dd"))?default(" ")}</td>
            <td class="title">是否任课: </td>
            <td>${(teacher.isTeaching?string("是", "否"))?default(" ")}</td>
       </tr>     
       <tr>
            <td class="title">是否退休返聘: </td>
            <td>${(teacher.isEngageFormRetire?string("是", "否"))?default(" ")}</td>
            <td class="title">是否兼职: </td>
            <td>${(teacher.isConcurrent?string("是", "否"))?default(" ")}</td>
       </tr>
       <tr>
            <td class="title">创建时间: </td>
            <td>${(teacher.createAt?string("yyyy-MM-dd"))?if_exists}</td>
            <td class="title">修改时间:</td>
            <td>${(teacher.modifyAt?string("yyyy-MM-dd"))?if_exists}</td>
       </tr>
       <tr>
            <td id="f_remark" class="title">备注:</td>
            <td colspan="3">${(teacher.remark?html)?default(" ")}</td>
       </tr>       
    </table>
    <br><br><br><br>
    <form method="post" action="" name="actionForm" onsubmit="return false;">
        <input type="hidden" name="teacherId" value="${teacher.id}"/>
    </form>
    <script>
        var bar = new ToolBar("bar", "教师详细信息", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addItem("个人简介", "intro()", "detail.gif");
        bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
        
        var form = document.actionForm;
        
        function intro() {
            form.action = "?method=intro";
            form.submit();
        }
    </script>
</body>
<#include "/templates/foot.ftl"/>
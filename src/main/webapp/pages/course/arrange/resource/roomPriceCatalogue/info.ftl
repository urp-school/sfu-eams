<#include "/templates/head.ftl"/>
<body>
    <table id="bar"></table>
     <table class="infoTable">
       <tr>
         <td class="title">所在校区：</td>
         <td class="content"><#assign schoolDistrictObj = (roomPriceCatalogue.schoolDistrict)?if_exists><#if !schoolDistrictObj?exists><font color="blue">配置默认校区价目表</font><#else><@i18nName schoolDistrictObj/></#if></td>
         <td class="title">发布部门：</td>
         <td class="content"><@i18nName roomPriceCatalogue.department/></td>
       </tr>
       <tr>
         <td class="title">审核部门：</td>
         <td class="content" colspan="3"><#list (roomPriceCatalogue.auditDeparts)?if_exists as auditDepart><@i18nName auditDepart/><#if auditDepart_has_next>，</#if></#list></td>
       </tr>
       <tr>
        <td class="title">备注:</td>
        <td class="content" colspan="3">${(roomPriceCatalogue.remark?html)?if_exists}</td>
       </tr>
       <tr>
         <td class="title">发布日期：</td>
         <td class="content">${roomPriceCatalogue.publishedOn?string("yyyy-MM-dd")}</td>
         <td class="title"></td>
         <td class="content"></td>
       </tr>
    </table>
    <script>
        var bar = new ToolBar("bar", "配置归口审核部门的详细信息", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
    </script>
</body>
<#include "/templates/foot.ftl"/>
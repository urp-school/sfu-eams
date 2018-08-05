<table width="100%" onkeypress="DWRUtil.onReturn(event, search)">
    <tr>
      <td colspan="2" class="infoTitle" align="left" valign="bottom">
       <img src="${static_base}/images/action/info.gif" align="top"/>
          <B>查询选项（模糊查询）</B>
      </td>
    <tr>
      <td colspan="2" style="font-size:0px">
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
      </td>
    </tr>
    <tr><td class="infoTitle"><@bean.message key="attr.code"/>:</td><td><input type="text" name="planCourse.course.code" style="width:100px;" maxlength="32"/></td></tr>
    <tr><td><@bean.message key="attr.infoname"/>:</td><td><input type="text" name="planCourse.course.name" style="width:100px;" maxlength="30"/></td></tr>
    <tr><td><@bean.message key="entity.studentType"/>：</td><td>
      <@htm.i18nSelect datas=stdTypeList selected="" name="planCourse.course.stdType.id" style="width:100px;">
         <option value="">...</option>
      </@>
    </td></tr>
    <tr>
        <td>课程类别：</td>
        <td><@htm.i18nSelect datas=courseTypeList selected="" name="planCourse.courseGroup.courseType.id" style="width:100px;">
         <option value="">...</option>
      </@></td>
    </tr>
    <tr>
        <td>是否确认：</td>
        <td><select name="teachPlan.isConfirm" style="width:100px;">
            <option value="">全部</option>
            <option value="1">已确认</option>
            <option value="0">未确认</option>
        </select></td>
    </tr>
    <input type="hidden" name="course.state" value="1"/>
    <tr height="50px">
        <td colspan="2" align="center">
            <button onclick="search()" accesskey="Q">查询统计</button>
        </td>
    </tr>
</table>
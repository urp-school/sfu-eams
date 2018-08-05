<#assign extraSearchTR_other>
    <tr>
        <td>指定学生</td>
        <td>
            <select name="stdState" style="width:100px">
                <option value=""><@bean.message key="common.all"/></option>
                <option value="2">需要指定</option>
            </select>
        </td>
    </tr>
    <tr>
        <td class="infoTitle">是否必修:</td>
        <td>
            <select name="task.courseType.isCompulsory" style="width:100px" value="${RequestParameters["task.courseType.isCompulsory"]?if_exists}">
               <option value=""><@bean.message key="common.all"/></option>
               <option value="1">是</option>
               <option value="0">否</option>
            </select>
        </td>
    </tr>
    <input type="hidden" name="task.course.extInfo.courseType.isPractice" value="1"/>
</#assign>
<#include "/pages/course/taskSearchForm.ftl"/>

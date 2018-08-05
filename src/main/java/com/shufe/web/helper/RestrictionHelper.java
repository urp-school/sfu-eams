package com.shufe.web.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.bean.comparators.PropertyComparator;
import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.utils.persistence.UtilService;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.ekingstar.security.Authentication;
import com.ekingstar.security.Resource;
import com.ekingstar.security.User;
import com.ekingstar.security.restriction.Restriction;
import com.ekingstar.security.restriction.service.RestrictionService;
import com.ekingstar.security.service.AuthorityService;
import com.shufe.model.system.baseinfo.Department;
import com.shufe.model.system.security.DataRealm;
import com.shufe.service.system.baseinfo.DepartmentService;
import com.shufe.service.system.baseinfo.StudentTypeService;
import com.shufe.util.DataRealmUtils;
import com.shufe.web.exception.DepartAuthInsufficientException;
import com.shufe.web.exception.StdTypeAuthInsufficientException;

public class RestrictionHelper {
    
    public static int hasStdType = 1;
    
    public static int hasDepart = 2;
    
    public static int hasCollege = 4;
    
    public static int hasAdminDepart = 8;
    
    public static int hasTeachDepart = 10;
    
    public static int hasStdTypeDepart = 3;
    
    public static int hasStdTypeCollege = 5;
    
    public static int hasStdTypeAdminDepart = 9;
    
    public static int hasStdTypeTeachDepart = 11;
    
    /** 向request或session注册学生类别权限的键值 */
    public static final String STDTYPES_KEY = "stdTypeList";
    
    /** 向request或session注册院系所权限的键值 */
    public static final String DEPARTS_KEY = "departmentList";
    
    /**
     * 部门服务对象
     */
    protected DepartmentService departmentService;
    
    /**
     * 学生类别服务对象
     */
    protected StudentTypeService studentTypeService;
    
    protected UtilService utilService;
    
    protected AuthorityService authorityService;
    
    protected RestrictionService restrictionService;
    
    public User getUser(HttpSession session) {
        return (User) utilService.get(User.class, (Long) session
                .getAttribute(Authentication.USERID));
    }
    
    /**
     * 加在数据权限
     * 
     * @param request
     * @param realmScope
     * @param mapping
     * @param form
     */
    public void setDataRealm(HttpServletRequest request, int realmScope) {
        List stdTypeList = null;
        if (realmScope % 2 == 1) {
            stdTypeList = getStdTypes(request);
            request.setAttribute(STDTYPES_KEY, stdTypeList);
            request.setAttribute("stdTypeIdSeq", "," + getStdTypeIdSeq(request) + ",");
            realmScope--;
        }
        if (realmScope == hasTeachDepart) {
            request.setAttribute(DEPARTS_KEY, getTeachDeparts(request));
        }
        if (realmScope == hasAdminDepart) {
            request.setAttribute(DEPARTS_KEY, getAdminDeparts(request));
        }
        if (realmScope == hasCollege) {
            request.setAttribute(DEPARTS_KEY, getColleges(request));
        }
        if (realmScope == hasDepart) {
            request.setAttribute(DEPARTS_KEY, getDeparts(request));
        }
        
    }
    
    public List getStdTypes(HttpServletRequest request) {
        List stdTypes = studentTypeService.getStudentTypes(getStdTypeIdSeq(request));
        Collections.sort(stdTypes, new PropertyComparator("code"));
        return stdTypes;
    }
    
    public List getDeparts(HttpServletRequest request) {
        List departs = departmentService.getDepartments(getDepartmentIdSeq(request));
        Collections.sort(departs, new PropertyComparator("code"));
        return departs;
    }
    
    public List getColleges(HttpServletRequest request) {
        List departs = departmentService.getColleges(getDepartmentIdSeq(request));
        Collections.sort(departs, new PropertyComparator("code"));
        return departs;
    }
    
    public List getTeachDeparts(HttpServletRequest request) {
        List departs = departmentService.getTeachDeparts(getDepartmentIdSeq(request));
        Collections.sort(departs, new PropertyComparator("code"));
        return departs;
    }
    
    public List getAdminDeparts(HttpServletRequest request) {
        List departs = departmentService.getAdministatives(getDepartmentIdSeq(request));
        Collections.sort(departs, new PropertyComparator("code"));
        return departs;
    }
    
    /**
     * 查询部门类别权限<br>
     * 首先查询session中是否已经存储了于resourceName对应的DataRealm<br>
     * 如果没有查询到，则查询数据库，等放入session后，在返回使用者<br>
     * 如果查询的部门权限为空,则抛出部门权限异常<br>
     * 
     * @param request
     * @param moduleDefine
     * 
     * @return
     */
    public DataRealm getDataRealmOf(HttpServletRequest request, AuthorityService authorityService) {
        return DataRealm.mergeAll(getDataRealms(request, authorityService));
    }
    
    public List getDataRealms(HttpServletRequest request) {
        return getDataRealms(request, authorityService);
    }
    
    /**
     * 将用户的学生类别权限限制在一个选定的学生类别上。<br>
     * 例如用户拥有10个学生类别的权限，如果他选择了其中一个，则根据这个以及他的子类<br>
     * 进行重新限定，但不改变他原来的权限。
     * 
     * @param stdTypeId
     * @param request
     * @return
     */
    public List getDataRealmsWith(Long stdTypeId, HttpServletRequest request) {
        List dataRealms = getDataRealms(request);
        if (null == stdTypeId) {
            return dataRealms;
        } else {
            String stdTypeIds = studentTypeService.getStdTypeIdSeqUnder(stdTypeId);
            List newRealms = new ArrayList();
            for (Iterator iterator = dataRealms.iterator(); iterator.hasNext();) {
                DataRealm newRealm = (DataRealm) ((DataRealm) iterator.next()).clone();
                newRealm.setStudentTypeIdSeq(SeqStringUtil.intersectSeq(stdTypeIds, newRealm
                        .getStudentTypeIdSeq()));
                if (StringUtils.isNotEmpty(newRealm.getStudentTypeIdSeq())) {
                    newRealms.add(newRealm);
                }
            }
            if (newRealms.isEmpty()) {
                throw new StdTypeAuthInsufficientException(getUser(request.getSession()),
                        getResourceName(request));
            }
            return newRealms;
        }
        
    }
    
    /**
     * 向查询语句中添加学生大类查询项<br>
     * resourceName 为空时不考虑权限
     * 
     * @param query
     * @param stdTypeAttrPath
     * @param request
     * @param resourceName
     */
    public void addStdTypeTreeDataRealm(EntityQuery query, String stdTypeAttrPath,
            HttpServletRequest request, String resourceName) {
        Long stdTypeId = RequestUtils.getLong(request, stdTypeAttrPath);
        addStdTypeTreeDataRealm(query, stdTypeId, stdTypeAttrPath, request);
    }
    
    public void addStdTypeTreeDataRealm(EntityQuery query, Long stdTypeId, String stdTypeAttrPath,
            HttpServletRequest request) {
        if (null != stdTypeId) {
            String stdTypeIds = "";
            String resourceName = getResourceName(request);
            Resource resource = (Resource) authorityService.getResource(resourceName);
            if (null != resource && !resource.getPatterns().isEmpty()) {
                stdTypeIds = getStdTypeIdSeqOf(stdTypeId, request);
            } else {
                stdTypeIds = studentTypeService.getStdTypeIdSeqUnder(stdTypeId);
            }
            if (StringUtils.isNotEmpty(stdTypeIds)) {
                DataRealmUtils.addDataRealm(query, new String[] { stdTypeAttrPath, null },
                        new DataRealm(stdTypeIds, null));
            }
        }
    }
    
    /**
     * @author 鄂州蚊子
     * @param request
     * @param authorityService
     * @return
     */
    public List getDataRealms(HttpServletRequest request, AuthorityService authorityService) {
        Map restrictionMap = (Map) request.getSession().getAttribute("security.restriction");
        if (null == restrictionMap) {
            restrictionMap = new HashMap();
            request.getSession().setAttribute("security.restriction", restrictionMap);
        }
        List realms = (List) restrictionMap.get(getResourceName(request));
        if (null == realms || realms.isEmpty()) {
            realms = new ArrayList();
            String resourceName = getResourceName(request);
            Resource resource = (Resource) authorityService.getResource(resourceName);
            if (resource.getPatterns().isEmpty()) {
                DataRealm realm = new DataRealm();
                String stdTypeIds = "";
                for (Iterator it = utilService.loadAll(StudentType.class).iterator(); it.hasNext();) {
                    StudentType stdType = (StudentType) it.next();
                    stdTypeIds += stdType.getId() + ",";
                }
                String departmentIds = "";
                for (Iterator it = utilService.loadAll(Department.class).iterator(); it.hasNext();) {
                    Department department = (Department) it.next();
                    departmentIds += department.getId() + ",";
                }
                realm.setStudentTypeIdSeq(stdTypeIds);
                realm.setDepartmentIdSeq(departmentIds);
                realms.add(realm);
            } else {
                List restrictions = restrictionService.getRestrictions(
                        getUser(request.getSession()), resource);
                for (Iterator iter = restrictions.iterator(); iter.hasNext();) {
                    Restriction restriction = (Restriction) iter.next();
                    DataRealm realm = new DataRealm();
                    realm.setDepartmentIdSeq(restriction.getItem("departIds").getValue());
                    realm.setStudentTypeIdSeq(restriction.getItem("stdTypeIds").getValue());
                    realms.add(realm);
                }
            }
            restrictionMap.put(resourceName, realms);
        }
        // 没有权限就报错
        if (realms.isEmpty()) {
            throw new DepartAuthInsufficientException(getUser(request.getSession()),
                    getResourceName(request));
        }
        return realms;
    }
    
    public String getDepartmentIdSeq(HttpServletRequest request) {
        if (null == request.getSession().getAttribute(Authentication.USERID)) {
            return getIds(Department.class, utilService);
        } else {
            try {
                DataRealm realm = getDataRealmOf(request, authorityService);
                if (StringUtils.isNotEmpty(realm.getDepartmentIdSeq())) {
                    return realm.getDepartmentIdSeq();
                } else {
                    throw new DepartAuthInsufficientException(getUser(request.getSession()),
                            getResourceName(request));
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new DepartAuthInsufficientException(getUser(request.getSession()),
                        getResourceName(request));
            }
        }
    }
    
    public String getStdTypeIdSeq(HttpServletRequest request) {
        if (null == request.getSession().getAttribute(Authentication.USERID)) {
            return getIds(StudentType.class, utilService);
        } else {
            try {
                DataRealm realm = getDataRealmOf(request, authorityService);
                if (StringUtils.isNotEmpty(realm.getStudentTypeIdSeq())) {
                    return realm.getStudentTypeIdSeq();
                } else {
                    throw new StdTypeAuthInsufficientException(getUser(request.getSession()),
                            getResourceName(request));
                }
            } catch (Exception e) {
                throw new StdTypeAuthInsufficientException(getUser(request.getSession()),
                        getResourceName(request));
            }
        }
    }
    
    private static String getIds(Class clazz, UtilService utilService) {
        EntityQuery query = new EntityQuery(clazz, "entity");
        query.add(new Condition("entity.state=true"));
        query.setSelect("id");
        Collection departIds = utilService.search(query);
        StringBuffer sb = new StringBuffer();
        for (Iterator iter = departIds.iterator(); iter.hasNext();) {
            Long id = (Long) iter.next();
            sb.append(id).append(",");
        }
        if (sb.length() != 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
    
    /**
     * 返回在权限内的指定学生类别及其子类别的列表串.
     * 
     * @param parentStdTypeId
     * @param request
     * @return
     */
    public String getStdTypeIdSeqOf(Long parentStdTypeId, HttpServletRequest request) {
        StudentType stdType = (StudentType) utilService.get(StudentType.class, parentStdTypeId);
        List stdTypes = stdType.getDescendants();
        stdTypes.add(stdType);
        StringBuffer buf = new StringBuffer();
        for (Iterator iter = stdTypes.iterator(); iter.hasNext();) {
            StudentType stdtype = (StudentType) iter.next();
            buf.append(",").append(stdtype.getId());
        }
        return SeqStringUtil.intersectSeq(buf.toString(), getStdTypeIdSeq(request));
    }
    
    public Long[] getStdTypeIdsOf(Long parentStdTypeId) {
        StudentType stdType = (StudentType) utilService.get(StudentType.class, parentStdTypeId);
        List stdTypes = stdType.getDescendants();
        stdTypes.add(stdType);
        Long[] stdTypeIds = new Long[stdTypes.size()];
        for (int i = 0; i < stdTypeIds.length; i++) {
            stdTypeIds[i] = ((StudentType) (stdTypes.get(i))).getId();
        }
        return stdTypeIds;
    }
    
    public List getStdTypesOf(Long parentStdTypeId, HttpServletRequest request) {
        StudentType stdType = (StudentType) utilService.get(StudentType.class, parentStdTypeId);
        List stdTypes = stdType.getDescendants();
        stdTypes.add(stdType);
        stdTypes = (List) CollectionUtils.intersection(stdTypes, getStdTypes(request));
        Collections.sort(stdTypes);
        return stdTypes;
    }
    
    public void setAuthorityService(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }
    
    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
    
    public void setStudentTypeService(StudentTypeService studentTypeService) {
        this.studentTypeService = studentTypeService;
    }
    
    public void setUtilService(UtilService utilService) {
        this.utilService = utilService;
    }
    
    public void setRestrictionService(RestrictionService restrictionService) {
        this.restrictionService = restrictionService;
    }
    
    private static String getResourceName(HttpServletRequest request) {
        return RequestUtils.getRequestAction(request);
    }
}

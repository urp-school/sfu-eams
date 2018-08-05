
package com.shufe.web.action.quality.review;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.limit.PageLimit;
import com.shufe.model.quality.review.ReviewDetail;
import com.shufe.service.quality.review.ReviewDetailService;
import com.shufe.web.action.common.DispatchBasicAction;

public class TeachReviewAction extends DispatchBasicAction {
    
    private ReviewDetailService reviewDetailService;
    
    
    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String ids = request.getParameter("teachCheck.id");
        Long[] delids = SeqStringUtil.transformToLong(ids);
        for (int x = 0; x < delids.length; x++) {
            ReviewDetail teachCheckDetail = reviewDetailService.getTeachCheckDetail(delids[x]);
            teachCheckDetail.setId(delids[x]);
            utilService.remove(teachCheckDetail);
        }
        
        return this.redirect(request, "index", "info.delete.success");
    }
    
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        PageLimit limit = new PageLimit();
        limit.setPageNo(getPageNo(request));
        limit.setPageSize(getPageSize(request));
        Collection teachCheckDetailList = (Collection) reviewDetailService.getTeachCheckDetail(
                null, limit);
        addCollection(request, "teachCheckDetailList", teachCheckDetailList);
        return forward(request);
    }
    
    public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        
        return forward(request);
    }
    
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long id = getLong(request, "teachCheckListId.id");
        ReviewDetail teachCheckDetail = reviewDetailService.getTeachCheckDetail(id);
        addEntity(request, teachCheckDetail);
        return this.forward(request, "new");
    }
    
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ReviewDetail teachCheckDetail = new ReviewDetail();
        teachCheckDetail.setStudyStyle(request.getParameter("studyStyle"));
        teachCheckDetail.setTeachStyle(request.getParameter("teachStyle"));
        teachCheckDetail.setCourseBuild(request.getParameter("courseBuild"));
        teachCheckDetail.setTeachDoc(request.getParameter("teachDoc"));
        teachCheckDetail.setGraduteDoc(request.getParameter("graduateDoc"));
        teachCheckDetail.setTeachOutline(request.getParameter("teachOutline"));
        teachCheckDetail.setOther(request.getParameter("other"));
        Long id = getLong(request, "hid");
        if (0 != id.intValue())
            teachCheckDetail.setId(id);
        reviewDetailService.saveTeachCheckDetail(teachCheckDetail);
        return this.redirect(request, "index", "info.save.success");
    }
    
    public void setReviewDetailService(ReviewDetailService teachCheckDetailService) {
        this.reviewDetailService = teachCheckDetailService;
    }
    
}

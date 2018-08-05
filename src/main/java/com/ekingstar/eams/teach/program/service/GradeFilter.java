package com.ekingstar.eams.teach.program.service;

import java.util.List;

public interface GradeFilter {
    
    /**
     * @param grades
     *            <SubstitueCourse>
     * @return
     */
    public List filter(List grades);
    
    public void setSubstituteCourses(List substituteCourses);
}

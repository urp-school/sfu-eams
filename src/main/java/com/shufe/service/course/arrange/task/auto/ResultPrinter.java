//$Id: ResultPrinter.java,v 1.1 2006/11/09 11:22:28 duanth Exp $
/*
 *
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2006. All rights reserved.
 * 
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is intended 
 * only for the use of KINGSTAR MEDIA application development. Reengineering, reproduction
 * arose from modification of the original source, or other redistribution of this source 
 * is not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 * 
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone             2005-11-14         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.arrange.task.auto;

import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Map;

import org.apache.commons.collections.MultiHashMap;

/**
 * 安排结果输出类
 * @author chaostone
 *
 */
public class ResultPrinter {
    PrintStream writer;

    private Map Infos = new MultiHashMap();

    public ResultPrinter(PrintStream writer) {
        this.writer = writer;
    }
    public void print(ArrangeResult result, long runTime) {
        printHeader(runTime);
        printFailures(result);
        printFooter(result);
    }

    protected void printHeader(long runTime) {
        getWriter().println();
        getWriter().println(
                "Time: " + ArrangeManager.elapsedTimeAsString(runTime));
    }

    protected void printFailures(ArrangeResult result) {
        printDefects(result.getFailures(), result.failureCount(), "failure");
    }

    public void printDefect(ArrangeFailure booBoo, int count) { // only public
        printDefectHeader(booBoo, count);
        printDefectTrace(booBoo);
        getWriter().print("\n");
    }

    protected void printDefectHeader(ArrangeFailure booBoo, int count) {
        // I feel like making this a println, then adding a line giving the
        // throwable a chance to print something
        // before we get to the stack trace.
        getWriter().print(count + ") " + booBoo.failedArrange());        
    }

    protected void printDefectTrace(ArrangeFailure booBoo) {
        getWriter().print(
                "[" + booBoo.failedArrange().getName() + "]"
                        + booBoo.thrownException().getMessage());
    }

    protected void printDefects(Enumeration booBoos, int count, String type) {
        if (count == 0)
            return;
        if (count == 1)
            getWriter().println("There was " + count + " " + type + ":");
        else
            getWriter().println("There were " + count + " " + type + "s:");
        for (int i = 1; booBoos.hasMoreElements(); i++) {
            printDefect((ArrangeFailure) booBoos.nextElement(), i);
        }
    }

    protected void printFooter(ArrangeResult result) {
        if (result.wasSuccessful()) {
            getWriter().println();
            getWriter().print("OK");
            getWriter().println(
                    " (" + result.runCount() + " Arrange"
                            + (result.runCount() == 1 ? "" : "s") + ")");

        } else {
            getWriter().println();
            getWriter().println("FAILURES!!!");
            getWriter().println(
                    "Arranges run: " + result.runCount() + ",  Failures: "
                            + result.failureCount());
        }
        getWriter().println();
    }

    /**
     * @return Returns the infos.
     */
    public Map getInfos() {
        return Infos;
    }

    public PrintStream getWriter() {
        return writer;
    }

    /**
     * @param infos
     *            The infos to set.
     */
    public void setInfos(Map infos) {
        Infos = infos;
    }
}

package com.gmail.fursovych20.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import java.io.IOException;

/**
 * Defines tag without body for the pagination
 */
public class PaginationTag extends TagSupport {

    private static final long serialVersionUID = 9064087208466894729L;

    /** The current page. */
    private int currentPage;

    /** The total page count. */
    private int totalPageCount;

    /** The count of pages indexes for choose */
    private int viewPageCount;

    /** The url pattern when your click the page index. */
    private String urlPattern;

    /**
     * Defines tag without body for the pagination
     * 
     * @see {@link TagSupport#doStartTag}
     */
    public int doStartTag() throws JspException {
        if (viewPageCount > totalPageCount) {
            viewPageCount = totalPageCount;
        }

        int startIndex = 1;
        int endIndex = startIndex + viewPageCount - 1;

        if (currentPage > endIndex) {
            if (currentPage == totalPageCount) {
                startIndex = totalPageCount - viewPageCount + 1;
            } else {
                startIndex = startIndex + 1;
            }
            endIndex = currentPage;
        }

        if (currentPage < startIndex) {
            startIndex = currentPage;
            if (currentPage == 1) {
                endIndex = viewPageCount;
            } else {
                endIndex = endIndex - 1;
            }
        }

        try {
            JspWriter out = pageContext.getOut();
            if (startIndex > 1) {
                out.write(formLink(1, "<<"));
                out.write(formLink(currentPage - 1, "<"));
            }

            for (int pageIndex = startIndex; pageIndex <= endIndex; pageIndex++) {
                if (pageIndex == currentPage) {
                    out.write(formLink(pageIndex, String.valueOf(pageIndex)));
                } else {
                    out.write(formLink(pageIndex, String.valueOf(pageIndex)));
                }
            }

            if (endIndex < totalPageCount) {
                out.write(formLink(currentPage + 1, ">"));
                out.write(formLink(totalPageCount, ">>"));
            }

        } catch (IOException e) {
            throw new JspException("Error in Paginator tag", e);
        }
        return SKIP_BODY;
    }

    private String formLink(int pageIndex, String linkContent) {
        StringBuilder link = new StringBuilder();
        if (pageIndex == currentPage){
            link.append("<li class='page-item active' aria-current='page'>");
            link.append("<span class='page-link'>").append(pageIndex).append("</span>");
            link.append("</li>");
        }else {
            link.append("<li class='page-item'>");
            link.append("<a class='page-link' href='").append(urlPattern);
            link.append("currentPage=");
            link.append(pageIndex);
            link.append("'>").append(linkContent);
            link.append("</a>");
            link.append("</li>");
        }
        return link.toString();
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setTotalPageCount(int pageCount) {
        this.totalPageCount = pageCount;
    }

    public void setViewPageCount(int viewPageCount) {
        this.viewPageCount = viewPageCount;
    }

    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }

}

package com.gmail.fursovych20.web.command.impl;

import com.gmail.fursovych20.service.ReviewService;
import com.gmail.fursovych20.service.exception.ServiceException;
import com.gmail.fursovych20.web.command.Command;
import com.gmail.fursovych20.web.command.exception.CommandExeption;
import com.gmail.fursovych20.web.util.HttpUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.gmail.fursovych20.web.util.WebConstantDeclaration.*;

public class DeleteReviewCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(DeleteReviewCommand.class);

    private final ReviewService reviewService;

    public DeleteReviewCommand(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandExeption {
        try {
            LOG.debug("DeleteReviewCommand starts");
            int reviewId = Integer.parseInt(request.getParameter(REQUEST_PARAM_REVIEW_ID));
            LOG.trace("ReviewID is --> {}", reviewId);
            reviewService.delete(reviewId);
            String referPage = HttpUtil.getReferPage(request);
            LOG.trace("referPage --> {}",referPage);
            LOG.debug("DeleteReviewCommand finished successfully!");
            return SEND_TO_REDIRECT+referPage;
        } catch (ServiceException e) {
            LOG.error("Exception deleting review", e);
            return SEND_TO_FORWARD+VIEW_503_ERROR;
        }
    }
}

package com.gmail.fursovych20.web.command.impl;

import com.gmail.fursovych20.entity.Review;
import com.gmail.fursovych20.service.ReviewService;
import com.gmail.fursovych20.service.exception.ServiceException;
import com.gmail.fursovych20.web.command.Command;
import com.gmail.fursovych20.web.command.exception.CommandExeption;
import com.gmail.fursovych20.web.util.HttpUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

import static com.gmail.fursovych20.web.util.WebConstantDeclaration.*;

public class AddReviewCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(AddReviewCommand.class);
    private final ReviewService reviewService;

    public AddReviewCommand(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandExeption {
        try {
            LOG.debug("AddReviewCommand starts");
            String referPage = HttpUtil.getReferPage(request);
            LOG.trace("referPage --> {}",referPage);
            int userId = (int) request.getSession().getAttribute(SESSION_ATTR_USER_ID);
            LOG.trace("User ID is --> {}",userId);
            Review review = getReview(request);
            review.setUserId(userId);

            reviewService.addReview(review);
            LOG.debug("AddReviewCommand finish!");
            return SEND_TO_REDIRECT+referPage;
        } catch (ServiceException e) {
            LOG.error("Exception adding review",e);
            return SEND_TO_FORWARD+VIEW_503_ERROR;
        }
    }

    private Review getReview(HttpServletRequest request) {
        Review review = new Review();
        review.setDateOfPublication(LocalDate.now());
        review.setText(request.getParameter(REQUEST_PARAM_REVIEW_TEXT));
        review.setMark(Byte.parseByte(request.getParameter(REQUEST_PARAM_REVIEW_MARK)));
        review.setPublicationId(Integer.parseInt(request.getParameter(REQUEST_PARAM_REVIEW_ID_OF_PUBLICATION)));
        return review;
    }
}

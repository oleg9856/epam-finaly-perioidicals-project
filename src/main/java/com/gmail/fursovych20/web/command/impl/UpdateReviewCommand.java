package com.gmail.fursovych20.web.command.impl;

import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.entity.Review;
import com.gmail.fursovych20.service.ReviewService;
import com.gmail.fursovych20.service.exception.ServiceException;
import com.gmail.fursovych20.service.exception.ValidationException;
import com.gmail.fursovych20.web.command.Command;
import com.gmail.fursovych20.web.command.exception.CommandExeption;
import com.gmail.fursovych20.web.util.HttpUtil;
import com.gmail.fursovych20.web.util.MessageResolver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.gmail.fursovych20.web.util.WebConstantDeclaration.*;

public class UpdateReviewCommand implements Command {
	
	private static final Logger LOG = LogManager.getLogger(UpdateReviewCommand.class);
	
	private static final String FAIL_MESSAGE_KEY = "review.update_fail";
	
	private final ReviewService reviewService;

	public UpdateReviewCommand(ReviewService reviewService) {
		this.reviewService = reviewService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandExeption {
		LOG.debug("UpdateReviewCommand starts");
		LocaleType locale = HttpUtil.getLocale(request);
		LOG.trace("Locale --> {}",locale.name());
		try {
			int reviewId = Integer.parseInt(request.getParameter(REQUEST_PARAM_REVIEW_ID));
			LOG.trace("Review Id --> {}",reviewId);

			Review review = reviewService.findReviewById(reviewId);
			updateReview(review, request);
			
			reviewService.update(review);
			String referPage = HttpUtil.getReferPage(request);
			LOG.trace("referPage --> {}",referPage);
			LOG.debug("UpdateReviewCommand finish success!");
			return SEND_TO_REDIRECT+referPage;

		} catch (ValidationException e) {
			LOG.warn("Invalid data");
			String message = MessageResolver.getMessage(FAIL_MESSAGE_KEY, locale);
			request.setAttribute(FAIL_MESSAGE_ADD_PUBLICATION, message);
			return SEND_TO_FORWARD+COMMAND_SHOW_PUBLICATION;
		} catch (ServiceException e) {
			LOG.error("Exception updating review", e);
			return SEND_TO_FORWARD+VIEW_503_ERROR;
		}
	}

	private void updateReview(Review review, HttpServletRequest request) {
		review.setMark(Byte.parseByte(request.getParameter(REQUEST_PARAM_REVIEW_MARK)));
		review.setText(request.getParameter(REQUEST_PARAM_REVIEW_TEXT));
	}

}

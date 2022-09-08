package com.gmail.fursovych20.web.command.impl;

import com.gmail.fursovych20.entity.BalanceOperation;
import com.gmail.fursovych20.entity.BalanceOperationType;
import com.gmail.fursovych20.service.BalanceOperationService;
import com.gmail.fursovych20.service.exception.ServiceException;
import com.gmail.fursovych20.web.command.Command;
import com.gmail.fursovych20.web.command.exception.CommandExeption;
import com.gmail.fursovych20.web.util.HttpUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.gmail.fursovych20.web.util.WebConstantDeclaration.*;

/**
 * A class which implements interface Command <br/>
 * and implements replenish balance command
 */
public class ReplenishBalanceCommand implements Command {
	
	private static final Logger LOG = LogManager.getLogger(ReplenishBalanceCommand.class);
	
	private final BalanceOperationService balanceOperationService;

	public ReplenishBalanceCommand(BalanceOperationService balanceOperationService) {
		this.balanceOperationService = balanceOperationService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandExeption {
		try {
			LOG.debug("ReplenishBalanceCommand starts");
			BalanceOperation balanceOperation = getBalanceOperation(request);
			balanceOperationService.create(balanceOperation);
			String referPage = HttpUtil.formRedirectUrl(request, COMMAND_SHOW_USER_PROFILE);
			LOG.trace("Refer page --> {}", referPage);
			LOG.debug("ReplenishBalanceCommand finish success!");
			return SEND_TO_REDIRECT+referPage;
		} catch (ServiceException e) {
			LOG.error("Exception replenishing user balance", e);
			return SEND_TO_FORWARD+VIEW_503_ERROR;
		}
	}
	
	private BalanceOperation getBalanceOperation(HttpServletRequest request) {
		int userID = (int) request.getSession().getAttribute(SESSION_ATTR_USER_ID);
		LOG.trace("UserID --> {}",userID);
		double sum = Double.parseDouble(request.getParameter(REQUEST_PARAM_SUM_FOR_REPLENISHMENT));
		LOG.trace("Sum --> {}",sum);

		BalanceOperation balanceOperation = new BalanceOperation();
		balanceOperation.setIdUser(userID);
		balanceOperation.setSum(BigDecimal.valueOf(sum));
		balanceOperation.setType(BalanceOperationType.BALANCE_REPLENISHMENT);
		balanceOperation.setLocalDate(LocalDate.now());
		
		return balanceOperation;
	}
	
	
}

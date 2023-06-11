package com.card.security.permission;

import com.card.task.service.CardService;
import com.card.util.AppConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class TaskCardServiceOwnerShipInterceptor implements HandlerInterceptor {

    private final CardService cardService;

    public TaskCardServiceOwnerShipInterceptor(CardService cardService) {
        this.cardService = cardService;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        final var requestVariablesMap = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String cardId = Objects.nonNull(requestVariablesMap) ? requestVariablesMap.get(AppConstant.TASK_CARD_ID_PATH_PARAM) : null;
        if(StringUtils.isNoneEmpty(cardId)){
        checkUserCardPermission(Long.valueOf(cardId));
    }
        return true;
    }


    private void checkUserCardPermission(Long cardId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        var authorities = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        final var card = cardService.findCardById(cardId);
        if (!StringUtils.equals(auth.getName(), card.createdBy()) && !authorities.contains(AppConstant.ADMIN_ROLE.getName())) {
            throw new AccessDeniedException(AppConstant.NOT_PERMITTED_RESPONSE_MSG);
        }
    }
}

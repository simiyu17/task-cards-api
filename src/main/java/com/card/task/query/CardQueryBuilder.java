package com.card.task.query;

import com.card.task.domain.Card;
import com.card.task.domain.QCard;
import com.card.task.dto.CardSearchCriteria;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class CardQueryBuilder {

    public BooleanBuilder buildPredicate(CardSearchCriteria cardSearchCriteria){

        QCard qCard = QCard.card;
        BooleanBuilder builder = new BooleanBuilder();

        List<Predicate> predicateList = new ArrayList<>();

        if(Objects.nonNull(cardSearchCriteria.name())){
            BooleanExpression expression = qCard.name.likeIgnoreCase(cardSearchCriteria.name());
            predicateList.add(expression);
        }

        if(Objects.nonNull(cardSearchCriteria.color())){
            BooleanExpression expression = qCard.color.eq(cardSearchCriteria.color());
            predicateList.add(expression);
        }

        BooleanExpression statusExpression;
        if(Objects.nonNull(cardSearchCriteria.status())){
            statusExpression = qCard.cardStatus.eq(Card.CardStatus.valueOf(cardSearchCriteria.status()));
        }else  {
            statusExpression = qCard.cardStatus.ne(Card.CardStatus.DELETED);
        }
        predicateList.add(statusExpression);

        if(Objects.nonNull(cardSearchCriteria.dateCreated())){
            BooleanExpression expression = qCard.dateCreated.eq(cardSearchCriteria.dateCreated());
            predicateList.add(expression);
        }

        if(Objects.nonNull(cardSearchCriteria.dateCreatedFrom()) && Objects.nonNull(cardSearchCriteria.dateCreatedTo())){
            BooleanExpression expression = qCard.dateCreated.between(cardSearchCriteria.dateCreatedFrom(), cardSearchCriteria.dateCreatedTo());
            predicateList.add(expression);
        }else if(Objects.nonNull(cardSearchCriteria.dateCreatedFrom())){
            BooleanExpression expression = qCard.dateCreated.goe(cardSearchCriteria.dateCreatedFrom());
            predicateList.add(expression);
        }else if(Objects.nonNull(cardSearchCriteria.dateCreatedTo())){
            BooleanExpression expression = qCard.dateCreated.loe(cardSearchCriteria.dateCreatedTo());
            predicateList.add(expression);
        }

        builder.orAllOf(predicateList.toArray(new Predicate[0]));

        return builder;
    }
}

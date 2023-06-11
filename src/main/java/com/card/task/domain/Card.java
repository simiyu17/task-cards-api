package com.card.task.domain;

import com.card.shared.entity.BaseEntity;
import com.card.task.dto.CardRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Objects;

@Entity
@Table(name = "cards")
public class Card extends BaseEntity {

    private String name;

    private String description;

    private String color;

    @Enumerated(EnumType.STRING)
    private CardStatus cardStatus;

    public Card() {
    }

    private Card(String name, String description, String color) {
        this.name = name;
        this.description = description;
        this.color = color;
        this.cardStatus = CardStatus.TODO;
    }

    public static Card createcard(CardRequestDto cardRequestDto){
        return new Card(cardRequestDto.taskName(), cardRequestDto.description(), cardRequestDto.color());
    }

    public void updateCard(CardRequestDto cardRequestDto){
        if(StringUtils.isNotBlank(cardRequestDto.taskName()) && !StringUtils.equals(cardRequestDto.taskName(), this.name)){
            this.name = cardRequestDto.taskName();
        }

        if(!StringUtils.equals(cardRequestDto.description(), this.description)){
            this.description = StringUtils.defaultIfEmpty(cardRequestDto.description(), null);
        }

        if(!StringUtils.equals(cardRequestDto.color(), this.color)){
            this.color = StringUtils.defaultIfEmpty(cardRequestDto.color(), null);
        }

    }

    public void setCardStatus(CardStatus cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getColor() {
        return color;
    }

    public CardStatus getCardStatus() {
        return cardStatus;
    }

    public String getCardStatusOrNull() {
        return Objects.isNull(getCardStatus()) ? "" : getCardStatus().getName();
    }

    public String getCardCreatorIdOrNull() {
        return Objects.isNull(getCreatedBy()) ? "" : getCreatedBy().getUsername();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o)).append(getId(), card.getId()).append(getName(), card.getName())
                .append(getDescription(), card.getDescription()).append(getColor(), card.getColor()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode()).append(getId()).append(getName())
                .append(getDescription()).append(getColor()).toHashCode();
    }

    public enum CardStatus {
        TODO("To Do"),
        IN_PROGRESS("In Progress"),
        DONE("Done"),
        DELETED("Deleted");

        private final String name;

        CardStatus(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}

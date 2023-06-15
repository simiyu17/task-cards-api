package com.card.task.domain;

import com.card.shared.entity.BaseEntity;
import com.card.task.dto.CardRequestDto;
import com.card.task.dto.CardUpdateRequestDto;
import jakarta.persistence.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Objects;

@Entity
@Table(name = "cards")
public class Card extends BaseEntity {

    @Column(nullable = false)
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

    public void updateCard(CardUpdateRequestDto cardRequestDto){
        final var newName = cardRequestDto.taskName();
        if(StringUtils.isNotBlank(newName) && !StringUtils.equals(newName, this.name)){
            this.name = newName.trim();
        }

        final var newDesc = cardRequestDto.description();
        if(Objects.nonNull(newDesc) && !StringUtils.equals(newDesc, this.description)){
            this.description = StringUtils.defaultIfBlank(newDesc.trim(), null);
        }

        final var newColor = cardRequestDto.color();
        if(Objects.nonNull(newColor) && !StringUtils.equals(newColor, this.color)){
            this.color = StringUtils.defaultIfBlank(newColor.trim(), null);
        }

        final var newStatus = cardRequestDto.status();
        if(Objects.nonNull(newStatus) && !Objects.equals(newStatus, this.cardStatus)){
            this.cardStatus = newStatus;
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

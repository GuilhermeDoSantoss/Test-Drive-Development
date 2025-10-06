package com.isiflix.TDD.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tbl_subscription")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "subscription_number")
    private Integer subscriptionNumber;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "subscribed_user_id")
    private User subscriptionUser;

    @ManyToOne
    @JoinColumn(name = "indication_user_id", nullable = true)
    private User indicationUser;

    public Integer getSubscriptionNumber() {
        return subscriptionNumber;
    }

    public void setSubscriptionNumber(Integer subscriptionNumber) {
        this.subscriptionNumber = subscriptionNumber;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getSubscriptionUser() {
        return subscriptionUser;
    }

    public void setSubscriptionUser(User subscriptionUser) {
        this.subscriptionUser = subscriptionUser;
    }

    public User getIndicationUser() {
        return indicationUser;
    }

    public void setIndicationUser(User indicationUser) {
        this.indicationUser = indicationUser;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Subscription that = (Subscription) obj;
        return Objects.equals(subscriptionNumber, that.subscriptionNumber) && Objects.equals(event, that.event) && Objects.equals(subscriptionUser, that.subscriptionUser) && Objects.equals(indicationUser, that.indicationUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subscriptionNumber, event, subscriptionUser, indicationUser);
    }
}

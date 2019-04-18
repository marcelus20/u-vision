package models.members;

import models.AbstractRecord;
import models.Registrable;
import models.titles.Title;
import java.util.List;
import java.util.Objects;

public abstract class Member<T extends Title> extends AbstractRecord implements Registrable {

    public static final String directory = AbstractRecord.directory+"/members";


    protected String phone;
    private MembershipCard membershipCard;
    private List<T> rentedTitles;

    public static Member searchMember(Integer member) {
        return null;
    }


    public enum CardType{
        CREDIT, DEBIT;
    }

    public class MembershipCard {
        private Integer cardId;
        private CardType cardType;
        private Integer pointBalance;

        public MembershipCard(Integer cardId, CardType cardType, Integer pointBalance) {
            this.cardId = cardId;
            this.cardType = cardType;
            this.pointBalance = pointBalance;
        }

        public MembershipCard(Integer cardId, CardType cardType) {
            this.cardId = cardId;
            this.cardType = cardType;
            this.pointBalance = 0;
        }

        public MembershipCard(Integer cardId) {
            this.cardId = cardId;
            this.cardType = CardType.CREDIT;
            this.pointBalance = 0;
        }

        public Integer getCardId() {
            return cardId;
        }

        public void setCardId(Integer cardId) {
            this.cardId = cardId;
        }

        public CardType getCardType() {
            return cardType;
        }

        public void setCardType(CardType cardType) {
            this.cardType = cardType;
        }

        public Integer getPointBalance() {
            return pointBalance;
        }

        public void setPointBalance(Integer pointBalance) {
            this.pointBalance = pointBalance;
        }

        public void updateBalance(){
            if(isEligible()){
                pointBalance = -100;
            }else{
                pointBalance += 10;
            }
        }

        public Boolean isEligible(){
            if(pointBalance >= 100){
                return true;
            }else{
                return false;
            }
        }
    }


    public Member(Integer id, String name, String phone, MembershipCard membershipCard, List<T> rentedTitles) {
        super(id, name);
        this.phone = phone;
        this.membershipCard = membershipCard;
        this.rentedTitles = rentedTitles;
        incrementInstanceAmount();
    }


    public Member(String id, String name, String phone) {
        super(Integer.parseInt(id), name);
        this.phone = phone;
        this.membershipCard = new MembershipCard(Integer.parseInt(id));
        incrementInstanceAmount();
    }

    public Member(String name, String phone) {
        super(AbstractRecord.instanceAmount, name);
        this.phone = phone;
        this.membershipCard = new MembershipCard(id);
    }

    @Override
    public void incrementInstanceAmount() {
        AbstractRecord.instanceAmount += 1;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public MembershipCard getMembershipCard() {
        return membershipCard;
    }

    public void setMembershipCard(MembershipCard membershipCard) {
        this.membershipCard = membershipCard;
    }

    public List<T> getRentedTitles() {
        return rentedTitles;
    }

    public void setRentedTitles(List<T> rentedTitles) {
        this.rentedTitles = rentedTitles;
    }




    public Integer getRemainingPointsToBeEligigle(){
        if(membershipCard.isEligible()){
            return 0;
        }else{
            return 100 - membershipCard.getPointBalance();
        }
    }

    public abstract Boolean checkIfIsEligibleToRentTitle(Title title);




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Member member = (Member) o;
        return Objects.equals(phone, member.phone) &&
                Objects.equals(membershipCard, member.membershipCard) &&
                Objects.equals(rentedTitles, member.rentedTitles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), phone, membershipCard, rentedTitles);
    }

    @Override
    public String toString() {
        return  this.getClass().getName()+"\n"+
                id +"\n"+
                name +"\n"+
                phone;
    }
}

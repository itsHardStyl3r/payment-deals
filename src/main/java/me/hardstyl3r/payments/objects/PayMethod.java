package me.hardstyl3r.payments.objects;

public class PayMethod {
    private final String id;
    private final int discount;
    private final double limit;

    public PayMethod(String id, int discount, double limit) {
        this.id = id;
        this.discount = discount;
        this.limit = limit;
    }

    public String getId() {
        return id;
    }

    public int getDiscount() {
        return discount;
    }

    public double getLimit() {
        return limit;
    }

    @Override
    public String toString() {
        return "PayMethod{" +
                "id='" + id + "'" +
                ", discount=" + discount +
                ", limit=" + limit +
                '}';
    }
}

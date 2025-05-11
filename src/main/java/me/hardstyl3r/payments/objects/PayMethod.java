package me.hardstyl3r.payments.objects;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PayMethod that = (PayMethod) obj;
        return new EqualsBuilder()
                .append(id, that.id)
                .append(discount, that.discount)
                .append(limit, that.limit)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(discount)
                .append(limit)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append("id", id)
                .append("discount", discount)
                .append("limit", limit)
                .toString();
    }
}

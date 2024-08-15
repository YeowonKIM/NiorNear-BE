package nior_near.server.domain.payment.entity;

public enum PaymentStatus {
    OK("ok"),
    READY("ready"),
    CANCELED("canceled"),
    SUCCESS("success");

    private final String name;

    private PaymentStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

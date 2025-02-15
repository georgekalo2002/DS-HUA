package com.example.real_estate_system.entity;

public enum RentalRequestStatus {
    PENDING, // Σε αναμονή
    APPROVED, // Εγκρίθηκε
    REJECTED; // Απορρίφθηκε

    public String getDisplayName() {
        switch (this) {
            case PENDING: return "Σε αναμονή";
            case APPROVED: return "Εγκρίθηκε";
            case REJECTED: return "Απορρίφθηκε";
            default: return this.name();
        }
    }

    public static RentalRequestStatus fromString(String status) {
        for (RentalRequestStatus value : RentalRequestStatus.values()) {
            if (value.name().equalsIgnoreCase(status)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid RentalRequestStatus: " + status);
    }
}

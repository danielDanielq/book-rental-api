package com.bookrental.app.enums;

// Note: Finite State Machine (FSM), limited states and strict transaction between states;
// Note: This will also represent an Audit Trial, one FINISHED rental does not modify or delete! It is a proof that the transaction had taken place;
// Analogy: think it as a butterfly life span (Cocoon -> Caterpillar -> Butterfly) = 1 proof;
public enum RentalStatus {
    PENDING{
        @Override
        public boolean isNextStatePossible(RentalStatus rentalStatus) {
            return (rentalStatus.equals(IN_PROGRESS) || rentalStatus.equals(CANCELED));
        }
    },
    IN_PROGRESS {
        @Override
        public boolean isNextStatePossible(RentalStatus rentalStatus) {
            return (rentalStatus.equals(FINISHED) || rentalStatus.equals(DELAYED));
        }
    },
    FINISHED {
        @Override
        public boolean isNextStatePossible(RentalStatus rentalStatus) {
            return false;
        }
    },
    DELAYED {
        @Override
        public boolean isNextStatePossible(RentalStatus rentalStatus) {
            return (rentalStatus.equals(FINISHED) || rentalStatus.equals(CANCELED));
        }
    },
    CANCELED {
        @Override
        public boolean isNextStatePossible(RentalStatus rentalStatus) {
            return false;
        }
    };


    public abstract boolean isNextStatePossible(RentalStatus rentalStatus);
}

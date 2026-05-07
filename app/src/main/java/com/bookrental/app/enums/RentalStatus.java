package com.bookrental.app.enums;

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
            return false;
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

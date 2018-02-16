package com.BarcelonaSC.BarcelonaApp.models.firebase;

/**
 * Created by Carlos on 01/02/2018.
 */

public class FirebaseEvent {

    EVENT event;

    public FirebaseEvent(EVENT event) {
        this.event = event;
    }

    public EVENT getEvent() {
        return event;
    }

    public void setEvent(EVENT event) {
        this.event = event;
    }

    public enum EVENT {

        REFRESCAR_SOLICITUDES_PENDIENTES(1), REFRESCAR_SOLICITUDES_ENVIADAS(2), REFRESCAR_AMIGOS(3), REFRESCAR_GRUPOS(4), USER_CONVERSATION(5);

        private int value = 0;

        private EVENT(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }
}

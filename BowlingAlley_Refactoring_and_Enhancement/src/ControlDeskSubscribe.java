import java.util.Iterator;

public class ControlDeskSubscribe {
    /**
     * Allows objects to subscribe as observers
     *
     * @param adding	the ControlDeskObserver that will be subscribed
     *
     */

    public static void subscribe(ControlDesk cd,ControlDeskObserver adding) {
        cd.subscribers.add(adding);
    }

    /**
     * Broadcast an event to subscribing objects.
     *
     * @param event	the ControlDeskEvent to broadcast
     *
     */

    public static void publish(ControlDesk cd) {
        Iterator eventIterator = cd.subscribers.iterator();
        while (eventIterator.hasNext()) {
            (
                    (ControlDeskObserver) eventIterator
                            .next())
                    .receiveControlDeskEvent(
                            cd);
        }
    }

}

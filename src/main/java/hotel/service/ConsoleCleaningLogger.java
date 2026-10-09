package hotel.service;

public class ConsoleCleaningLogger implements CleaningObserver {
    @Override
    public void update(int roomNum) {
        System.out.println("[OBSERVER] Номер " + roomNum + " надіслав запит на прибирання. Сповіщення надіслано в службу підтримки.");
    }
}
package main;


import java.util.Random;

public class DynamicAnonymousClassApp {
    static Parking parking;

    public static void main(String[] args) throws InterruptedException {

        parking = new Parking();
        new ParkTheCar().start();
        new ShootCarData().start();
        new EvacuateCar().start();


    }

}

////////////////////////////Resoursce////////////////////////////
//EJB
class EmptyCar {

}

class Car extends EmptyCar {
    private String model;
    private String number;


    public Car() {

    }

    public Car(String model, String number) {
        this.model = model;
        this.number = number;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

class Parking {
    static EmptyCar placeA = new EmptyCar();
    final static Object lock = new Object();

}

////////////////////////////THREADS////////////////////////////
class ParkTheCar extends Thread {

    @Override
    public void run() {
        try {
            sleep(new Random().nextInt(3000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Parking.placeA = new Car("BMW", "XYZ 987");
    }
}

class ShootCarData extends Thread {

    @Override
    public void run() {
        while (!(Parking.placeA instanceof Car)) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        shoot();
    }

    private synchronized void shoot() {

        if (Parking.placeA instanceof Car) {
            synchronized (Parking.placeA) {
                try {
                    sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(((Car) Parking.placeA).getModel());
                System.out.println(((Car) Parking.placeA).getNumber());
            }
        } else {
            System.out.println("OOPS, Missed it !!! ");
        }
    }
}

class EvacuateCar extends Thread {

    @Override
    public void run() {
        while (!(Parking.placeA instanceof Car)) {
            try {
                sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        synchronized (Parking.placeA) {
            Parking.placeA = null;
        }
    }
}

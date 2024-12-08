class Table {
    synchronized void printable(int n) {
        for (int i = 1; i <= 5; i++) {
            System.out.println(n * i);
            try {
                Thread.sleep(400);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}

class MyThread1 extends Thread {
    Table t;

    public MyThread1(Table t) {
        this.t = t;
    }

    public void run() {
        t.printable(5);
    }
}

class MyThread2 extends Thread {
    Table t;

    public MyThread2(Table t) {
        this.t = t;
    }

    public void run() {
        t.printable(100);
    }
}

public class SetPriority {
    public static void main(String[] args) {
        Table obj = new Table();

        MyThread1 t1 = new MyThread1(obj);
        MyThread2 t2 = new MyThread2(obj);

        // Set thread priorities
        t1.setPriority(Thread.MIN_PRIORITY); // Priority 1
        t2.setPriority(Thread.MAX_PRIORITY); // Priority 10

        t1.start();
        t2.start();
    }
}
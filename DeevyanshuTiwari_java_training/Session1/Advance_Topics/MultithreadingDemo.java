class Task extends Thread {
    private String taskName;

    Task(String name) {
        this.taskName = name;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 3; i++) {
            System.out.println(taskName + " - Step " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class MultithreadingDemo {
    public static void main(String[] args) {
        Task t1 = new Task("Download");
        Task t2 = new Task("Upload");

        t1.start(); // both run concurrently
        t2.start();
    }
}
"""
Program to demonstrate
join() method.
"""

import threading
import time


SLEEP_TIME: int = 3


def perform_task() -> None:
    """
    Simulate a task.
    """

    print("Task Started")

    time.sleep(
        SLEEP_TIME
    )

    print("Task Completed")


def main() -> None:
    """
    Execute the program.
    """

    worker_thread = threading.Thread(
        target=perform_task
    )

    worker_thread.start()

    worker_thread.join()

    print(
        "Main Thread Finished"
    )


if __name__ == "__main__":
    main()
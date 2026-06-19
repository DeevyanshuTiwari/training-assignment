"""
Program to calculate squares
using multiprocessing.
"""

import multiprocessing


NUMBER_LIST = [1, 2, 3, 4, 5]


def calculate_square(
        number: int
) -> None:
    """
    Print square of a number.
    """

    print(
        f"Square of {number}: "
        f"{number * number}"
    )


def main() -> None:
    """
    Execute the program.
    """

    process_list = []

    for number in NUMBER_LIST:

        process = multiprocessing.Process(
            target=calculate_square,
            args=(number,)
        )

        process_list.append(
            process
        )

        process.start()

    for process in process_list:
        process.join()


if __name__ == "__main__":
    main()
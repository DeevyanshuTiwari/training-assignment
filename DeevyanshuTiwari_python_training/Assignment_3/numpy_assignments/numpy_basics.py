"""
Assignment 1: NumPy Basics

Tasks:
1. Create a NumPy array.
2. Find Mean, Max, Min, and Sum.
3. Perform Addition and Multiplication on two arrays.
4. Create a 3x3 matrix.
"""

import numpy as np


ARRAY_VALUES = [10, 20, 30, 40, 50]

FIRST_ARRAY = [1, 2, 3]
SECOND_ARRAY = [4, 5, 6]


def display_array_statistics() -> None:
    """
    Display mean, max, min, and sum of an array.
    """

    number_array = np.array(
        ARRAY_VALUES
    )

    print("Array:", number_array)

    print(
        "Mean:",
        np.mean(number_array)
    )

    print(
        "Max:",
        np.max(number_array)
    )

    print(
        "Min:",
        np.min(number_array)
    )

    print(
        "Sum:",
        np.sum(number_array)
    )


def perform_array_operations() -> None:
    """
    Perform addition and multiplication
    on two NumPy arrays.
    """

    first_array = np.array(
        FIRST_ARRAY
    )

    second_array = np.array(
        SECOND_ARRAY
    )

    print(
        "\nAddition:",
        first_array + second_array
    )

    print(
        "Multiplication:",
        first_array * second_array
    )


def create_matrix() -> None:
    """
    Create and display a 3x3 matrix.
    """

    matrix = np.array(
        [
            [1, 2, 3],
            [4, 5, 6],
            [7, 8, 9]
        ]
    )

    print("\n3x3 Matrix:")

    print(matrix)


def main() -> None:
    """
    Execute all NumPy tasks.
    """

    display_array_statistics()

    perform_array_operations()

    create_matrix()


if __name__ == "__main__":
    main()
"""
Program to calculate factorial
using recursion.
"""


def calculate_factorial(
        number: int
) -> int:
    """
    Calculate factorial recursively.

    Args:
        number: Input number.

    Returns:
        Factorial of the number.
    """

    if number == 0 or number == 1:
        return 1

    return (
            number
            * calculate_factorial(
        number - 1
    )
    )


def main() -> None:
    """
    Execute the program.
    """

    user_number = int(
        input("Enter a number: ")
    )

    factorial = calculate_factorial(
        user_number
    )

    print(
        f"Factorial: {factorial}"
    )


if __name__ == "__main__":
    main()
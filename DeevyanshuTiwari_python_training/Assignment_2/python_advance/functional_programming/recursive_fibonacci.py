"""
Program to calculate Fibonacci
using recursion.
"""


def calculate_fibonacci(
        position: int
) -> int:
    """
    Calculate Fibonacci number recursively.

    Args:
        position: Fibonacci position.

    Returns:
        Fibonacci value.
    """

    if position <= 1:
        return position

    return (
            calculate_fibonacci(
                position - 1
            )
            +
            calculate_fibonacci(
                position - 2
            )
    )


def main() -> None:
    """
    Execute the program.
    """

    position = int(
        input(
            "Enter position: "
        )
    )

    fibonacci_number = (
        calculate_fibonacci(
            position
        )
    )

    print(
        f"Fibonacci Number: "
        f"{fibonacci_number}"
    )


if __name__ == "__main__":
    main()
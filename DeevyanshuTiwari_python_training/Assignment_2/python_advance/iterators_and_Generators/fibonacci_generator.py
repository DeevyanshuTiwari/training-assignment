"""
Program to generate Fibonacci numbers
using a generator.
"""


def generate_fibonacci_numbers(
        total_terms: int
):
    """
    Yield Fibonacci numbers.

    Args:
        total_terms: Number of terms to generate.
    """

    first_number = 0
    second_number = 1

    for _ in range(total_terms):
        yield first_number

        next_number = (
                first_number + second_number
        )

        first_number = second_number
        second_number = next_number


def display_fibonacci_numbers(
        total_terms: int
) -> None:
    """
    Display Fibonacci numbers.
    """

    fibonacci_generator = (
        generate_fibonacci_numbers(
            total_terms
        )
    )

    for fibonacci_number in fibonacci_generator:
        print(fibonacci_number)


def main() -> None:
    """
    Execute the program.
    """

    total_terms = int(
        input(
            "Enter number of terms: "
        )
    )

    display_fibonacci_numbers(
        total_terms
    )


if __name__ == "__main__":
    main()
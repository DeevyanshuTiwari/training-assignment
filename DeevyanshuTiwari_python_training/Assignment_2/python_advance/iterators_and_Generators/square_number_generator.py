"""
Program to generate square numbers
up to N using a generator.
"""


def generate_square_numbers(
        maximum_number: int
):
    """
    Yield square numbers from 1 to N.

    Args:
        maximum_number: Upper limit.
    """

    for number in range(
            1,
            maximum_number + 1
    ):
        yield number * number


def display_square_numbers(
        maximum_number: int
) -> None:
    """
    Display square numbers.
    """

    square_generator = (
        generate_square_numbers(
            maximum_number
        )
    )

    for square_number in square_generator:
        print(square_number)


def main() -> None:
    """
    Execute the program.
    """

    maximum_number = int(
        input("Enter N: ")
    )

    display_square_numbers(
        maximum_number
    )


if __name__ == "__main__":
    main()
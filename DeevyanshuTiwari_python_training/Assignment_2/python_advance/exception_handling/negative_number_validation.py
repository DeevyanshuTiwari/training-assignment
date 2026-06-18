"""
Program to raise ValueError
for negative numbers.
"""


def validate_positive_number(
        number: int
) -> None:
    """
    Raise ValueError if
    the number is negative.

    Args:
        number: Number to validate.
    """

    if number < 0:
        raise ValueError(
            "Negative numbers are not allowed."
        )

    print(
        f"Valid Number: {number}"
    )


def main() -> None:
    """
    Execute the program.
    """

    try:
        user_number = int(
            input("Enter a number: ")
        )

        validate_positive_number(
            user_number
        )

    except ValueError as error:
        print(
            f"Error: {error}"
        )


if __name__ == "__main__":
    main()
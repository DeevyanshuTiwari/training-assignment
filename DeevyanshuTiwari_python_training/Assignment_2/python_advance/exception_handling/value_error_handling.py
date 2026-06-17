"""
Program to handle ValueError for invalid integer input.
"""


def validate_integer_input() -> None:
    """
    Take a number as input and handle ValueError.
    """

    try:
        user_number = int(
            input("Enter a number: ")
        )

        print(
            f"You entered: {user_number}"
        )

    except ValueError:
        print(
            "Invalid input. Please enter a valid integer."
        )


def main() -> None:
    """
    Execute the program.
    """

    validate_integer_input()


if __name__ == "__main__":
    main()
"""
Program to find square of a number
using a lambda function.
"""


def main() -> None:
    """
    Execute the program.
    """

    square_number = (
        lambda number: number * number
    )

    user_number = int(
        input("Enter a number: ")
    )

    print(
        f"Square: {square_number(user_number)}"
    )


if __name__ == "__main__":
    main()
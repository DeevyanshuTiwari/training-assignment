"""
Program to create and use
a custom AgeException.
"""


MINIMUM_AGE: int = 18


class AgeException(Exception):
    """
    Custom exception for age validation.
    """
    pass


def validate_age(
        age: int
) -> None:
    """
    Validate age and raise
    AgeException if age is below 18.

    Args:
        age: User age.
    """

    if age < MINIMUM_AGE:
        raise AgeException(
            f"Age must be at least {MINIMUM_AGE}."
        )

    print(
        "Age is valid."
    )


def main() -> None:
    """
    Execute the program.
    """

    try:
        user_age = int(
            input("Enter your age: ")
        )

        validate_age(
            user_age
        )

    except AgeException as error:
        print(
            f"Error: {error}"
        )


if __name__ == "__main__":
    main()
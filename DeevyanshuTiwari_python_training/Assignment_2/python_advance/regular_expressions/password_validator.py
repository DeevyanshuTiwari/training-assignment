"""
Program to validate password
using regular expressions.
"""

import re


MINIMUM_PASSWORD_LENGTH: int = 8

PASSWORD_PATTERN: str = (
    r"^(?=.*\d)"
    r"(?=.*[@$!%*?&])"
    r"[A-Za-z\d@$!%*?&]{8,}$"
)


def validate_password(
        password: str
) -> bool:
    """
    Validate password strength.
    """

    return bool(
        re.match(
            PASSWORD_PATTERN,
            password
        )
    )


def main() -> None:
    """
    Execute the program.
    """

    password = input(
        "Enter password: "
    )

    if validate_password(
            password
    ):
        print(
            "Valid Password"
        )
    else:
        print(
            "Invalid Password"
        )


if __name__ == "__main__":
    main()
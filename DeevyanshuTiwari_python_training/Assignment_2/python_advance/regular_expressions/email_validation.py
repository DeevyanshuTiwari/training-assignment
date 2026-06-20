"""
Program to validate
an email address.
"""

import re


EMAIL_PATTERN: str = (
    r"^[A-Za-z0-9._%+-]+"
    r"@[A-Za-z0-9.-]+"
    r"\.[A-Za-z]{2,}$"
)


def validate_email(
        email_address: str
) -> bool:
    """
    Validate email address.

    Args:
        email_address: Email to validate.

    Returns:
        True if valid else False.
    """

    return bool(
        re.match(
            EMAIL_PATTERN,
            email_address
        )
    )


def main() -> None:
    """
    Execute the program.
    """

    email_address = input(
        "Enter email: "
    )

    if validate_email(
            email_address
    ):
        print(
            "Valid Email"
        )
    else:
        print(
            "Invalid Email"
        )


if __name__ == "__main__":
    main()
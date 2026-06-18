"""
Program to validate
a 10-digit mobile number.
"""

import re


MOBILE_PATTERN: str = (
    r"^[0-9]{10}$"
)


def validate_mobile_number(
        mobile_number: str
) -> bool:
    """
    Validate mobile number.

    Args:
        mobile_number: Mobile number.

    Returns:
        True if valid else False.
    """

    return bool(
        re.match(
            MOBILE_PATTERN,
            mobile_number
        )
    )


def main() -> None:
    """
    Execute the program.
    """

    mobile_number = input(
        "Enter mobile number: "
    )

    if validate_mobile_number(
            mobile_number
    ):
        print(
            "Valid Mobile Number"
        )
    else:
        print(
            "Invalid Mobile Number"
        )


if __name__ == "__main__":
    main()
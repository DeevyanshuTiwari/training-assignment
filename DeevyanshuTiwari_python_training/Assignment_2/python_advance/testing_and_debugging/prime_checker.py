"""
Program to check
whether a number is prime.
"""


def is_prime(
        number: int
) -> bool:
    """
    Check if number is prime.
    """

    if number <= 1:
        return False

    for divisor in range(
            2,
            int(number ** 0.5) + 1
    ):
        if number % divisor == 0:
            return False

    return True
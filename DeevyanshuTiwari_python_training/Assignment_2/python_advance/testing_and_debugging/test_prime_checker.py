"""
Pytest test cases for
is_prime().
"""

from prime_checker import is_prime


def test_prime_number() -> None:
    """
    Test prime number.
    """

    assert is_prime(7) is True


def test_non_prime_number() -> None:
    """
    Test non-prime number.
    """

    assert is_prime(8) is False


def test_negative_number() -> None:
    """
    Test negative number.
    """

    assert is_prime(-5) is False


def test_zero() -> None:
    """
    Test zero.
    """

    assert is_prime(0) is False


def test_one() -> None:
    """
    Test one.
    """

    assert is_prime(1) is False
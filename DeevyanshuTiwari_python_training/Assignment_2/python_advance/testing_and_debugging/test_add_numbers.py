"""
Pytest test cases for
add_numbers().
"""

from add_numbers import add_numbers


def test_add_positive_numbers() -> None:
    """
    Test positive numbers.
    """

    assert add_numbers(10, 20) == 30


def test_add_negative_numbers() -> None:
    """
    Test negative numbers.
    """

    assert add_numbers(-5, -10) == -15


def test_add_zero() -> None:
    """
    Test addition with zero.
    """

    assert add_numbers(5, 0) == 5
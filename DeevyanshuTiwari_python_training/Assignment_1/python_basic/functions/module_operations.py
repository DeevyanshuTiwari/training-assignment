"""
Programs for:
22. Math Module
23. Random Module
24. Custom Module
"""

import math
import random
from custom_module import add_numbers


def perform_math_operations():
    """Use math module functions."""

    number = int(input("Enter a number: "))

    print("Square Root =", math.sqrt(number))
    print("Power =", math.pow(number, 2))
    print("Factorial =", math.factorial(number))


def generate_random_numbers():
    """Generate random numbers."""

    print("Random Numbers:")

    for _ in range(5):
        print(random.randint(1, 100))


def use_custom_module():
    """Use custom module function."""

    first_number = int(input("Enter first number: "))
    second_number = int(input("Enter second number: "))

    result = add_numbers(
        first_number,
        second_number
    )

    print("Sum =", result)


def main():
    """Run all module programs."""

    print("Question 22")
    perform_math_operations()

    print("\nQuestion 23")
    generate_random_numbers()

    print("\nQuestion 24")
    use_custom_module()


if __name__ == "__main__":
    main()
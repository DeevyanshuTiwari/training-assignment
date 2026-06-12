"""
Programs for:
12. Print numbers from 1 to 100
13. Multiplication Table
14. Factorial of a Number
15. Reverse a Number
16. Prime Number Check
"""


def print_numbers():
    """Print numbers from 1 to 100."""

    for number in range(1, 101):
        print(number, end=" ")

    print()


def print_multiplication_table():
    """Print multiplication table of a number."""

    number = int(input("Enter a number: "))

    for multiplier in range(1, 11):
        print(
            f"{number} x {multiplier} = "
            f"{number * multiplier}"
        )


def calculate_factorial():
    """Calculate factorial of a number."""

    number = int(input("Enter a number: "))

    factorial = 1

    for current_number in range(1, number + 1):
        factorial *= current_number

    print("Factorial =", factorial)


def reverse_number():
    """Reverse a number using loop."""

    number = int(input("Enter a number: "))

    reversed_number = 0

    while number > 0:
        digit = number % 10
        reversed_number = reversed_number * 10 + digit
        number //= 10

    print("Reversed Number =", reversed_number)


def check_prime_number():
    """Check whether a number is prime."""

    number = int(input("Enter a number: "))

    if number <= 1:
        print("Not a Prime Number.")
        return

    for divisor in range(2, number):
        if number % divisor == 0:
            print("Not a Prime Number.")
            return

    print("Prime Number.")


def main():
    """Run all loop programs."""

    print("Question 12")
    print_numbers()

    print("\nQuestion 13")
    print_multiplication_table()

    print("\nQuestion 14")
    calculate_factorial()

    print("\nQuestion 15")
    reverse_number()

    print("\nQuestion 16")
    check_prime_number()


if __name__ == "__main__":
    main()
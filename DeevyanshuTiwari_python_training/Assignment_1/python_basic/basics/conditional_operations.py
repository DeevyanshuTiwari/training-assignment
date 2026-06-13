"""
Programs for:
7. Even or Odd
8. Positive, Negative, or Zero
9. Largest of Three Numbers
10. Grade Calculation
11. Leap Year Check
"""


def check_even_or_odd():
    """Check whether a number is even or odd."""

    number = int(input("Enter a number: "))

    if number % 2 == 0:
        print("The number is Even.")
    else:
        print("The number is Odd.")


def check_number_type():
    """Check whether a number is positive, negative, or zero."""

    number = float(input("Enter a number: "))

    if number > 0:
        print("The number is Positive.")
    elif number < 0:
        print("The number is Negative.")
    else:
        print("The number is Zero.")


def find_largest_number():
    """Find the largest among three numbers."""

    first_number = float(input("Enter first number: "))
    second_number = float(input("Enter second number: "))
    third_number = float(input("Enter third number: "))

    largest_number = max(
        first_number,
        second_number,
        third_number
    )

    print("Largest Number =", largest_number)


def calculate_grade():
    """Calculate grade based on marks."""

    marks = float(input("Enter marks: "))

    if marks >= 80:
        print("Grade: A")
    elif marks >= 60:
        print("Grade: B")
    elif marks >= 40:
        print("Grade: C")
    else:
        print("Grade: Fail")


def check_leap_year():
    """Check whether a year is a leap year."""

    year = int(input("Enter a year: "))

    if (year % 400 == 0) or (year % 4 == 0 and year % 100 != 0):
        print(year, "is a Leap Year.")
    else:
        print(year, "is not a Leap Year.")


def main():
    """Run all conditional programs."""

    print("Question 7")
    check_even_or_odd()

    print("\nQuestion 8")
    check_number_type()

    print("\nQuestion 9")
    find_largest_number()

    print("\nQuestion 10")
    calculate_grade()

    print("\nQuestion 11")
    check_leap_year()


if __name__ == "__main__":
    main()
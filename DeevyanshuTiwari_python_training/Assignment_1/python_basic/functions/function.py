"""
Programs for:
17. Square of a Number
18. Palindrome Check (Number and String)
19. Maximum Number in a List
20. Function with Default Parameters
"""


def calculate_square(number):
    """Return square of a number."""
    return number * number


def check_palindrome(value):
    """Check whether a number or string is palindrome."""
    value = str(value)

    if value == value[::-1]:
        return True

    return False


def find_maximum_number(numbers):
    """Return maximum number from a list."""
    return max(numbers)


def greet_user(name="Guest"):
    """Display greeting message using default parameter."""
    print(f"Hello, {name}!")


def main():
    """Run all function programs."""

    print("Question 17")
    number = int(input("Enter a number: "))
    print("Square =", calculate_square(number))

    print("\nQuestion 18")
    user_input = input("Enter a number or string: ")

    if check_palindrome(user_input):
        print("Palindrome")
    else:
        print("Not a Palindrome")

    print("\nQuestion 19")
    number_list = [10, 25, 8, 45, 32]
    print("List:", number_list)
    print("Maximum Number =", find_maximum_number(number_list))

    print("\nQuestion 20")
    greet_user()
    greet_user("Deevyanshu")


if __name__ == "__main__":
    main()
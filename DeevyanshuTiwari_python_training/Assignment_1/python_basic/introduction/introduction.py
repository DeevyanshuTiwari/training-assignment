"""
Introduction to Python Programs
"""

import sys


def print_welcome_message():
    """Print welcome message."""
    print("Welcome to Python Training")


def print_python_version():
    """Print Python version."""
    print("Python Version:", sys.version)


def get_user_details():
    """Take user input and print formatted message."""
    name = input("Enter your name: ")
    age = input("Enter your age: ")

    print(f"Hello {name}! You are {age} years old.")


def main():
    """Run all programs."""
    print("Question 1")
    print_welcome_message()

    print("\nQuestion 2")
    print_python_version()

    print("\nQuestion 3")
    get_user_details()


if __name__ == "__main__":
    main()
"""
Advantages of IDE debugger
over print statements.
"""


def display_debugger_advantages() -> None:
    """
    Print debugger advantages.
    """

    advantages = [
        "Inspect variables in real time",
        "Set breakpoints anywhere",
        "Execute code step by step",
        "View call stack information",
        "Modify variable values during execution",
        "Reduce excessive print statements",
        "Faster bug identification",
        "Better visualization of program flow"
    ]

    for advantage in advantages:
        print(advantage)


def main() -> None:
    """
    Execute the program.
    """

    display_debugger_advantages()


if __name__ == "__main__":
    main()
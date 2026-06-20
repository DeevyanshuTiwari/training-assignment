"""
Assignment 5: Matplotlib Charts

Tasks:
1. Create Bar Chart.
2. Create Line Chart.
3. Create Histogram.
4. Create Scatter Plot.
"""

import matplotlib.pyplot as plt


DEPARTMENTS = [
    "HR",
    "IT",
    "Finance"
]

EMPLOYEE_COUNTS = [
    5,
    12,
    7
]

SALARY_LIST = [
    30000,
    40000,
    50000,
    60000,
    45000
]

AGE_LIST = [
    25,
    30,
    28,
    35,
    29
]


def create_bar_chart() -> None:
    """
    Create a bar chart.
    """

    plt.figure()

    plt.bar(
        DEPARTMENTS,
        EMPLOYEE_COUNTS
    )

    plt.title(
        "Employees by Department"
    )

    plt.xlabel(
        "Department"
    )

    plt.ylabel(
        "Employees"
    )

    plt.show()


def create_line_chart() -> None:
    """
    Create a line chart.
    """

    plt.figure()

    plt.plot(
        DEPARTMENTS,
        EMPLOYEE_COUNTS,
        marker="o"
    )

    plt.title(
        "Employees by Department"
    )

    plt.xlabel(
        "Department"
    )

    plt.ylabel(
        "Employees"
    )

    plt.show()


def create_histogram() -> None:
    """
    Create salary histogram.
    """

    plt.figure()

    plt.hist(
        SALARY_LIST,
        bins=5
    )

    plt.title(
        "Salary Distribution"
    )

    plt.xlabel(
        "Salary"
    )

    plt.ylabel(
        "Frequency"
    )

    plt.show()


def create_scatter_plot() -> None:
    """
    Create age vs salary scatter plot.
    """

    plt.figure()

    plt.scatter(
        AGE_LIST,
        SALARY_LIST
    )

    plt.title(
        "Age vs Salary"
    )

    plt.xlabel(
        "Age"
    )

    plt.ylabel(
        "Salary"
    )

    plt.show()


def main() -> None:
    """
    Execute all chart examples.
    """

    create_bar_chart()

    create_line_chart()

    create_histogram()

    create_scatter_plot()


if __name__ == "__main__":
    main()